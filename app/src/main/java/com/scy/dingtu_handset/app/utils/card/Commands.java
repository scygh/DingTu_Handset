package com.scy.dingtu_handset.app.utils.card;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.widget.Toast;

import com.scy.dingtu_handset.BuildConfig;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.entity.CardInfoBean;
import com.scy.dingtu_handset.app.utils.SpUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import es.dmoral.toasty.Toasty;

/**
 * 读写IC卡工具类
 */
public class Commands {

    protected static Commands instance;
    private Context mContext;

    private Commands(Context context) {
        mContext = context.getApplicationContext();
    }

    public static Commands getInstance(Context context) {
        if (instance == null) {
            synchronized (Commands.class) {
                if (instance == null) {
                    instance = new Commands(context);
                }
            }
        }
        instance.mContext = context;
        return instance;
    }

    public CardInfoBean readTag(Tag tag) {

        String str = "";
        CardInfoBean cardInfoBean = new CardInfoBean();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals("android.nfc.action.TECH_DISCOVERED")) {
            // 3) Get an instance of the TAG from the NfcAdapter

            // 4) Get an instance of the Mifare classic card from this TAG
            // intent
            String[] techList = tag.getTechList();
            boolean haveMifareUltralight = false;
            for (String tech : techList) {
                if (tech.indexOf("IsoDep") >= 0) {
                    haveMifareUltralight = false;
                    break;
                }
                if (tech.indexOf("MifareClassic") >= 0) {
                    haveMifareUltralight = true;
                    break;
                }
            }

            MifareClassic mfc = null;
            if (tag != null && haveMifareUltralight) {
                mfc = MifareClassic.get(tag);
            }

            if (mfc != null) {

                try {
                    mfc.connect();
                    boolean auth = false;
                    String nfc_key = (String) SpUtils.get(mContext, AppConstant.NFC.NFC_KEY, "");
                    auth = mfc.authenticateSectorWithKeyA(1, hexStringToBytes(nfc_key));// 验证密码
                    if (auth) {
                        // 读取M1卡的第4块即1扇区第0块

                        //block4
                        str = bytesToHexString(mfc.readBlock(4));
                        String num = str.substring(0, 6);
                        cardInfoBean.setNum(Integer.parseInt(num, 16));
                        cardInfoBean.setName(BytesUtils2.getString(BytesUtils.hexStringToBytes(str.substring(8, 26))));
                        String code = str.substring(26, 30);
                        cardInfoBean.setCode(code);
                        String level_type = str.substring(30);
                        cardInfoBean.setType(Integer.parseInt(level_type.substring(1), 16));
                        cardInfoBean.setLevel(Integer.parseInt(level_type.substring(0, 1), 16));

                        //block5
                        str = bytesToHexString(mfc.readBlock(5));
                        String cash_account = str.substring(0, 6);
                        int int_cash_account = Integer.parseInt(cash_account, 16);
                        Double double_cash_account = (double) int_cash_account / 100;
                        cardInfoBean.setCash_account(double_cash_account);

                        String allowance_account = str.substring(8, 14);
                        int int_allowance_account = Integer.parseInt(allowance_account, 16);
                        Double double_allowance_account = (double) int_allowance_account / 100;
                        cardInfoBean.setAllowance_account(double_allowance_account);

                        String spending_time = str.substring(16, 22);
                        spending_time = "20" + spending_time;
                        cardInfoBean.setSpending_time(spending_time);

                        String meal_times = str.substring(22, 24);
                        cardInfoBean.setMeal_times(Integer.parseInt(meal_times, 16));

                        String consumption_num = str.substring(26, 30);
                        cardInfoBean.setConsumption_num(Integer.parseInt(consumption_num, 16));

                        //block6
                        str = bytesToHexString(mfc.readBlock(6));
                        String spending_limit = str.substring(0, 6);
                        cardInfoBean.setSpending_limit((Integer.parseInt(spending_limit, 16)) / 100);
                        String guaranteed_amount = str.substring(6, 10);
                        cardInfoBean.setGuaranteed_amount(Integer.parseInt(guaranteed_amount, 16));
                        String isdiscount = str.substring(22, 24);
                        byte a = Byte.parseByte(isdiscount);
                        boolean b;
                        b = (a == 0x00) ? false : true;
                        cardInfoBean.setIsDiscount(b);
                        String discount = str.substring(24, 26);
                        cardInfoBean.setDiscount(Integer.parseInt(discount, 16));
                        String card_validity = str.substring(10, 16);
                        card_validity = "20" + card_validity;
                        cardInfoBean.setCard_validity(card_validity);
                        String subsidies_time = str.substring(16, 20);
                        subsidies_time = "20" + subsidies_time;
                        cardInfoBean.setSubsidies_time(subsidies_time);

                    } else
                        Toasty.error(mContext, "认证失败", Toast.LENGTH_SHORT, true).show();
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace();
                    }

                } finally {
                    try {
                        mfc.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }
        return cardInfoBean;
    }


    public boolean writeTag(Tag tag, CardInfoBean cardInfoBean) {
        MifareClassic mfc = null;
        try {
            // 4) Get an instance of the Mifare classic card from this TAG
            // intent
            mfc = MifareClassic.get(tag);
            mfc.connect();
            boolean auth = false;
            short sectorAddress = 1;
            String nfc_key = (String) SpUtils.get(mContext, AppConstant.NFC.NFC_KEY, "");
            auth = mfc.authenticateSectorWithKeyA(sectorAddress, hexStringToBytes(nfc_key));
            if (auth) {
                // the last block of the sector is used for KeyA and KeyB
                // cannot be overwritted
                // 必须为16字节不够自己补0

                //BLOCK4
                byte[] head = intToByte3(cardInfoBean.getNum());
                byte[] head_check = SumCheck(intToByte3(cardInfoBean.getNum()), 1);

                byte[] b_gbk = BytesUtils2.getBytes(cardInfoBean.getName().trim());
                byte[] b_9_gbk = (byte[]) arrayAddLength(b_gbk, 9 - b_gbk.length);
                byte[] body = hexStringToBytes(cardInfoBean.getCode());

                String s_level = Integer.toHexString(cardInfoBean.getLevel());
                String s_type = Integer.toHexString(cardInfoBean.getType());
                byte[] bottom = hexStringToBytes(s_level + s_type);
                byte[] totle1 = concatAll(head, head_check, b_9_gbk, body, bottom);
                if (totle1.length == 16) {
                    mfc.writeBlock(4, totle1);
                } else
                    Toasty.warning(mContext, "BLOCK4 must write 16 bytes", Toast.LENGTH_SHORT, true).show();

                //BLOCK5
                int a = (int) cardInfoBean.getCash_account() * 100;
                byte[] byte_a = intToByte3(a);
                byte[] head_check_a = SumCheck(byte_a, 1);
                int b = (int) cardInfoBean.getAllowance_account() * 100;
                byte[] byte_b = intToByte3(b);
                byte[] head_check_b = SumCheck(byte_b, 1);

                String s = cardInfoBean.getSpending_time();
                s = s.replace("-", "");
                byte[] byte_c = str2Bcd(s.substring(2));
                byte[] byte_d = ubyteToBytes(cardInfoBean.getMeal_times());
                byte[] byte_xfcs = unsignedShortToByte2(cardInfoBean.getConsumption_num());
                byte[] byte_Xor = new byte[]{getXor(byte_xfcs)};
                byte[] totle2 = concatAll(byte_a, head_check_a, byte_b, head_check_b, byte_c, byte_d, new byte[]{(byte) 0x00}, byte_xfcs, byte_Xor);
                if (totle2.length == 16) {
                    mfc.writeBlock(5, totle2);
                } else
                    Toasty.warning(mContext, "BLOCK5 must write 16 bytes", Toast.LENGTH_SHORT, true).show();

                //BLOCK6
                byte[] xfxe = intToByte3(cardInfoBean.getSpending_limit() * 100);
                byte[] bded = unsignedShortToByte2(cardInfoBean.getGuaranteed_amount());
                String cv = cardInfoBean.getCard_validity();
                cv = cv.replace("-", "");
                byte[] byte_cv = str2Bcd(cv.substring(2));
                String st = cardInfoBean.getSubsidies_time();
                st = st.replace("-", "");
                byte[] byte_st = str2Bcd(st.substring(2));

                byte X;
                boolean Y = false;
                X = (byte) (Y ? 0x01 : 0x00);
                byte[] bx = new byte[]{X};

                byte[] d = ubyteToBytes(cardInfoBean.getDiscount());
                byte[] totle3 = concatAll(xfxe, bded, byte_cv, byte_st, bx, d, new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
                if (totle3.length == 16) {
                    mfc.writeBlock(6, totle3);
                } else
                    Toasty.warning(mContext, "BLOCK6 must write 16 bytes", Toast.LENGTH_SHORT, true).show();

                mfc.close();
                Toasty.success(mContext, "写入成功", Toast.LENGTH_SHORT, true).show();
                return true;
            } else {
                Toasty.error(mContext, "认证失败", Toast.LENGTH_SHORT, true).show();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mfc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 校验和
     *
     * @param msg    需要计算校验和的byte数组
     * @param length 校验和位数
     * @return 计算出的校验和数组
     */
    public static byte[] SumCheck(byte[] msg, int length) {
        long mSum = 0;
        byte[] mByte = new byte[length];

        /** 逐Byte添加位数和 */
        for (byte byteMsg : msg) {
            long mNum = ((long) byteMsg >= 0) ? (long) byteMsg : ((long) byteMsg + 256);
            mSum += mNum;
        } /** end of for (byte byteMsg : msg) */

        /** 位数和转化为Byte数组 */
        for (int liv_Count = 0; liv_Count < length; liv_Count++) {
            mByte[length - liv_Count - 1] = (byte) (mSum >> (liv_Count * 8) & 0xff);
        } /** end of for (int liv_Count = 0; liv_Count < length; liv_Count++) */

        return mByte;
    }

    /**
     * int整数转换为3字节的byte数组
     *
     * @param i 整数
     * @return byte数组
     */
    public static byte[] intToByte3(int i) {
        byte[] targets = new byte[3];
        targets[2] = (byte) (i & 0xFF);
        targets[1] = (byte) (i >> 8 & 0xFF);
        targets[0] = (byte) (i >> 16 & 0xFF);
        return targets;
    }

    /**
     * short整数转换为2字节的byte数组
     *
     * @param s short整数
     * @return byte数组
     */
    public static byte[] unsignedShortToByte2(int s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

    /**
     * int整数转换为1字节的byte数组
     *
     * @param -b整数
     * @return byte数组
     */
    public static byte[] ubyteToBytes(int n) {
        byte[] b = new byte[1];
        b[0] = (byte) (n & 0xff);
        return b;
    }

    /**
     * Java异或运算、校验
     * 将字节数组两两异或，返回最后异或值
     * 如果是校验则看异或结果是否为0即可
     */
    public static byte getXor(byte[] datas) {

        byte temp = datas[0];

        for (int i = 1; i < datas.length; i++) {
            temp ^= datas[i];
        }

        return temp;
    }

    /**
     * Description: Array add length
     *
     * @param oldArray
     * @param addLength
     * @return Object
     */
    public static Object arrayAddLength(Object oldArray, int addLength) {
        Class c = oldArray.getClass();
        if (!c.isArray()) return null;
        Class componentType = c.getComponentType();
        int length = Array.getLength(oldArray);
        int newLength = length + addLength;
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(oldArray, 0, newArray, 0, length);
        return newArray;
    }

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    public static byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    /**
     * @功能: 10进制串转为BCD码
     * @参数: 10进制串
     * @结果: BCD码
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }
}
