package com.scy.dingtu_handset.app.nfc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.ThermalPrinter;
import com.telpo.tps550.api.util.StringUtil;
import com.telpo.tps550.api.util.SystemUtil;


public class BlackSetPrinterUtils {
    private Activity activity;
    private String printVersion;
    private final int NOPAPER = 3;
    private final int LOWBATTERY = 4;
    private final int PRINTVERSION = 5;
    private final int PRINTBARCODE = 6;
    private final int PRINTQRCODE = 7;
    private final int PRINTPAPERWALK = 8;
    private final int PRINTCONTENT = 9;
    private final int CANCELPROMPT = 10;
    private final int PRINTERR = 11;
    private final int OVERHEAT = 12;
    private final int MAKER = 13;
    private final int PRINTPICTURE = 14;
    private final int NOBLACKBLOCK = 15;


    MyHandler handler;

    private String Result;
    private Boolean nopaper = false;
    private boolean LowBattery = false;

    public static String barcodeStr;
    public static String qrcodeStr;
    public static int paperWalk;
    public static String printContent;
    private int leftDistance = 0;
    private int lineDistance;
    private int wordFont;
    private int printGray = 5;
    private ProgressDialog progressDialog;
    private final static int MAX_LEFT_DISTANCE = 255;
    ProgressDialog dialog;
    ThermalPrinter mThermalPrinter;


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOPAPER:
                    noPaperDlg();
                    break;
                case LOWBATTERY:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle(R.string.operation_result);
                    alertDialog.setMessage(activity.getString(R.string.LowBattery));
                    alertDialog.setPositiveButton(activity.getString(R.string.dialog_comfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                    break;
                case NOBLACKBLOCK:
                    Toast.makeText(activity, R.string.maker_not_find, Toast.LENGTH_SHORT).show();
                    break;
                case PRINTPAPERWALK:
                    new paperWalkPrintThread().start();
                    break;
                case PRINTCONTENT:
                    new contentPrintThread().start();
                    break;
                case CANCELPROMPT:
                    if (progressDialog != null && !activity.isFinishing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    break;
                case OVERHEAT:
                    AlertDialog.Builder overHeatDialog = new AlertDialog.Builder(activity);
                    overHeatDialog.setTitle(R.string.operation_result);
                    overHeatDialog.setMessage(activity.getString(R.string.overTemp));
                    overHeatDialog.setPositiveButton(activity.getString(R.string.dialog_comfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    overHeatDialog.show();
                    break;
                default:
                    Toast.makeText(activity, "打印错误!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


    private BlackSetPrinterUtils(Activity activity) {
        super();
        this.activity = activity;
        init();
    }

    private static volatile BlackSetPrinterUtils instance;

    public static BlackSetPrinterUtils getInstance(Activity activity) {
        if (instance == null) {
            synchronized (BlackSetPrinterUtils.class) {
                if (instance == null) {
                    instance = new BlackSetPrinterUtils(activity);
                }
            }
        }
        return instance;
    }


    protected void init() {
        mThermalPrinter = new ThermalPrinter();
        handler = new MyHandler();

        if (printReceive == null) {
            IntentFilter pIntentFilter = new IntentFilter();
            pIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
            pIntentFilter.addAction("android.intent.action.BATTERY_CAPACITY_EVENT");
            activity.registerReceiver(printReceive, pIntentFilter);
        }
        try {
            mThermalPrinter.start(activity);
        } catch (TelpoException e) {
            e.printStackTrace();
        }

    }


    private final BroadcastReceiver printReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_NOT_CHARGING);
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                //TPS390 can not print,while in low battery,whether is charging or not charging
                if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390.ordinal()) {
                    if (level * 5 <= scale) {
                        LowBattery = true;
                    } else {
                        LowBattery = false;
                    }
                } else {
                    if (status != BatteryManager.BATTERY_STATUS_CHARGING) {
                        if (level * 5 <= scale) {
                            LowBattery = true;
                        } else {
                            LowBattery = false;
                        }
                    } else {
                        LowBattery = false;
                    }
                }
            }
            //Only use for TPS550MTK devices
            else if (action.equals("android.intent.action.BATTERY_CAPACITY_EVENT")) {
                int status = intent.getIntExtra("action", 0);
                int level = intent.getIntExtra("level", 0);
                if (status == 0) {
                    if (level < 1) {
                        LowBattery = true;
                    } else {
                        LowBattery = false;
                    }
                } else {
                    LowBattery = false;
                }
            }
        }
    };

    private void noPaperDlg() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
        dlg.setTitle(activity.getString(R.string.noPaper));
        dlg.setMessage(activity.getString(R.string.noPaperNotice));
        dlg.setCancelable(false);
        dlg.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dlg.show();
    }

    private class paperWalkPrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mThermalPrinter.reset();
                mThermalPrinter.walkPaper(paperWalk);
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    nopaper = true;
                } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
                    handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
                } else {
                    handler.sendMessage(handler.obtainMessage(PRINTERR, 1, 0, null));
                }
            } finally {
                handler.sendMessage(handler.obtainMessage(CANCELPROMPT, 1, 0, null));
                if (nopaper) {
                    handler.sendMessage(handler.obtainMessage(NOPAPER, 1, 0, null));
                    nopaper = false;
                    return;
                }
            }
        }
    }


    private class contentPrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {

                mThermalPrinter.reset();
                mThermalPrinter.setGray(printGray);

                mThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
                mThermalPrinter.setFontSize(2);
                mThermalPrinter.enlargeFontSize(1, 2);
                String name = (String) SpUtils.get(activity, AppConstant.Receipt.NAME, "");
                if (!TextUtils.isEmpty(name)) {
                    mThermalPrinter.addString(name);
                } else {
                    mThermalPrinter.addString("杭州鼎图信息");
                }
                mThermalPrinter.printString();


                mThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
                mThermalPrinter.setFontSize(2);
                mThermalPrinter.enlargeFontSize(1, 1);
                mThermalPrinter.addString("********************************");
                mThermalPrinter.addString(printContent);
                mThermalPrinter.addString("\n********************************");
                String address = (String) SpUtils.get(activity, AppConstant.Receipt.ADDRESS, "");
                String phone = (String) SpUtils.get(activity, AppConstant.Receipt.PHONE, "");
                mThermalPrinter.addString("\n地址：" + address);
                mThermalPrinter.addString("\n电话：" + phone);
                mThermalPrinter.printString();
                mThermalPrinter.walkPaper(100);

            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    nopaper = true;
                } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
                    handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
                } else {
                    handler.sendMessage(handler.obtainMessage(PRINTERR, 1, 0, null));
                }
            } finally {
                handler.sendMessage(handler.obtainMessage(CANCELPROMPT, 1, 0, null));
                if (nopaper) {
                    handler.sendMessage(handler.obtainMessage(NOPAPER, 1, 0, null));
                    nopaper = false;
                    return;
                }
            }
        }
    }


    public void releaseAllPrinter() {
        if (progressDialog != null && !activity.isFinishing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        activity.unregisterReceiver(printReceive);
        mThermalPrinter.stop();

    }


    public void printLianxi(StringBuilder str) {
        boolean isPrint = (boolean) SpUtils.get(activity, AppConstant.Receipt.isPrint, false);
        if (isPrint) {
            printContent = str.toString();
            if (printContent == null || printContent.length() == 0) {
                Toast.makeText(activity, "内容为空", Toast.LENGTH_LONG).show();
                return;
            }
            if (LowBattery == true) {
                handler.sendMessage(handler.obtainMessage(LOWBATTERY, 1, 0, null));
            } else {
                if (!nopaper) {
//                progressDialog = ProgressDialog.show(activity, activity.getString(R.string.bl_dy), activity.getString(R.string.printing_wait));
                    handler.sendMessage(handler.obtainMessage(PRINTCONTENT, 1, 0, null));
                } else {
                    Toast.makeText(activity, activity.getString(R.string.ptintInit), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
