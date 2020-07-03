package com.scy.dingtu_handset.mvp.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.configuration.UserInfoHelper;
import com.scy.dingtu_handset.app.entity.QRExpenseParam;
import com.scy.dingtu_handset.app.nfc.BlackSetPrinterUtils;
import com.scy.dingtu_handset.app.nfc.NfcJellyBeanActivity;
import com.scy.dingtu_handset.app.utils.AdConstant;
import com.scy.dingtu_handset.app.utils.AnimSpring;
import com.scy.dingtu_handset.app.utils.AudioUtils;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.utils.card.Commands;
import com.scy.dingtu_handset.app.utils.card.PrinterUtils;
import com.scy.dingtu_handset.app.utils.card.ReadCardUtils;
import com.scy.dingtu_handset.app.utils.card.SoundTool;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.entity.CardInfoBean;
import com.scy.dingtu_handset.app.entity.EMGoodsTo2;
import com.scy.dingtu_handset.app.entity.EMGoodsTypeTo;
import com.scy.dingtu_handset.app.entity.ReadCardTo;
import com.scy.dingtu_handset.app.entity.SimpleExpenseParam;
import com.scy.dingtu_handset.app.entity.SimpleExpenseTo;
import com.scy.dingtu_handset.app.listening.RFCardListening;
import com.scy.dingtu_handset.di.component.DaggerWarenverbrauchComponent;
import com.scy.dingtu_handset.mvp.contract.WarenverbrauchContract;
import com.scy.dingtu_handset.mvp.presenter.WarenverbrauchPresenter;
import com.scy.dingtu_handset.mvp.ui.adapter.EMDetailGoodsRvAdapter;
import com.scy.dingtu_handset.mvp.ui.adapter.EMGoodsRvAdapter;
import com.scy.dingtu_handset.mvp.ui.adapter.EMSelectedGoodsRvAdapter;
import com.scy.dingtu_handset.mvp.ui.widget.CustomDialog;
import com.scy.dingtu_handset.mvp.ui.widget.PayDialog;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/28/2019 11:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WarenverbrauchActivity extends NfcJellyBeanActivity<WarenverbrauchPresenter> implements WarenverbrauchContract.View {
    @BindView(R.id.ware_type_rv)
    RecyclerView wareTypeRv;
    @BindView(R.id.ware_type_tv)
    TextView wareTypeTv;
    @BindView(R.id.ware_detail_rv)
    RecyclerView wareDetailRv;
    @BindView(R.id.ware_car)
    ImageView wareCar;
    @BindView(R.id.ware_goods_count)
    TextView wareGoodsCount;
    @BindView(R.id.ware_goods_price)
    TextView wareGoodsPrice;
    @BindView(R.id.payall)
    TextView payall;
    @BindView(R.id.ware_pb)
    ProgressBar progressBar;
    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    EMDetailGoodsRvAdapter detailAdapter;
    EMSelectedGoodsRvAdapter selectAdapter;
    private List<EMGoodsTypeTo> emGoodsTypeToList = new ArrayList<>();//商品分类列表
    private List<EMGoodsTo2.RowsBean> contentBeans = new ArrayList<>();//分类详情列表
    private List<List<EMGoodsTo2.RowsBean>> selectedBeans = new ArrayList<>();//已选中的列表
    private int goodsCount = 0;
    private double goodsPrices = 0;
    private int currentItem;
    private CardInfoBean mCardInfoBean;//读到的卡对象
    int company = 0;
    int device = 0;
    private int payCount = 0;
    SimpleExpenseParam param;
    StringBuilder printer = new StringBuilder();
    ReadCardTo readCardTo;
    View bottomView;
    TextView clearBottom;
    RecyclerView bottomRv;
    private static final int SCAN_REQUEST_CODE = 6;
    private String[] payMethod = new String[]{"扫码支付", "刷卡支付"};
    private boolean isExits;
    private ReadCardUtils readCardUtils;
    private String key;
    private AlertDialog alert;
    private boolean isPrint;
    private boolean isFirst = true;
    private List<SimpleExpenseParam.ListGoodsBean> listGoodsBean = new ArrayList<>();//消费提交body

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWarenverbrauchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return R.layout.activity_warenverbrauch;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("商品消费");
        mPresenter.setList();//获取商品分类列表
        company = UserInfoHelper.getInstance(this).getCode();
        key = (String) SpUtils.get(this, AppConstant.NFC.NFC_KEY, "");
        isPrint = (boolean) SpUtils.get(this, AppConstant.Receipt.isPrint, false);
        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);
        bottomView = getLayoutInflater().from(this).inflate(R.layout.ware_bottom_layout, null);//购物车界面
        clearBottom = bottomView.findViewById(R.id.clear_bottom);
        bottomRv = bottomView.findViewById(R.id.ware_bottom_rv);
        clearBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
                bottomSheetLayout.dismissSheet();
            }
        });
    }

    /**
     * descirption: 红色机NFC接收卡信息
     */
    private void initCardInfoReceived() {
        readCardUtils = new ReadCardUtils();
        readCardUtils.init();
        readCardUtils.run();
        readCardUtils.setRFCardListening(new RFCardListening() {
            @Override
            public void findRFCardListening(String s) {
                if (readCardUtils.Authen(4, key) && isFirst) {
                    readCardUtils.Read(4);
                }
            }

            @Override
            public void readRFCardListening(String data) {
                Log.e(TAG, "handleMessage: data:" + data);
                UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(WarenverbrauchActivity.this);
                mCardInfoBean = readCardUtils.cardInfoBean(data);
                Log.e(TAG, "handleMessage: cardInfoBean" + JSON.toJSONString(mCardInfoBean));
                if (!TextUtils.isEmpty(mCardInfoBean.getCode())) {
                    if (TextUtils.equals("0000", mCardInfoBean.getCode())) {
                        onFailed();
                        AudioUtils.getInstance().speakText("空白卡");
                        showMessage("空白卡");
                        return;
                    } else if (Integer.parseInt(mCardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                        onFailed();
                        AudioUtils.getInstance().speakText("非本单位卡");
                        showMessage("非本单位卡");
                        return;
                    } else if (isFirst) {
                        isFirst = false;
                        SoundTool.getMySound(WarenverbrauchActivity.this).playMusic("success");
                        mPresenter.readtCardInfo(company, device, mCardInfoBean.getNum());
                    }
                }
            }

            @Override
            public void noFindRFCardListening() {
                isFirst = true;

            }

            @Override
            public void failReadRFCardListening() {
                isFirst = true;

            }
        });
    }

    /**
     * descirption: 黑色机NFC接收卡信息
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return;
        }
        if (intent != null) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            System.out.println(Arrays.toString(tag.getTechList()));
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(WarenverbrauchActivity.this);
            mCardInfoBean = Commands.getInstance(WarenverbrauchActivity.this).readTag(tag);
            Timber.wtf("M1卡信息：" + mCardInfoBean.getCode());
            if (TextUtils.isEmpty(mCardInfoBean.getCode())) {
                return;
            } else {
                if (TextUtils.equals("0000", mCardInfoBean.getCode())) {
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                } else if (Integer.parseInt(mCardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
            }
            mPresenter.readtCardInfo(company, device, mCardInfoBean.getNum());
        } else {
            System.out.println("intent null");
        }
    }

    @Override
    public void onReadCard(ReadCardTo readCardTo) {
        this.readCardTo = readCardTo;
        payCount = readCardTo.getPayCount();
        mPresenter.getPaySgetPayKeySwitch2();
    }

    @Override
    public void creatBill2(boolean isOpen) {
        if (payCount == -1) {
            showMessage("重新放置卡片");
            AudioUtils.getInstance().speakText("重新放置卡片");
            return;
        }
        if (isOpen) {
            createPayDialog();
        } else {
            String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
            param = new SimpleExpenseParam();
            param.setNumber(mCardInfoBean.getNum());
            param.setAmount(goodsPrices);
            param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
            param.setPayCount(payCount + 1);
            param.setPayKey(pwd);
            param.setPattern(4);
            param.setDeviceType(2);
            param.setListGoods(listGoodsBean);
            mPresenter.createSimpleExpense(param);
        }
    }

    @Override
    public void creatSuccess(SimpleExpenseTo simpleExpenseTo) {
        alert.dismiss();
        if (isPrint) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);

            printer.append("\n工作模式: 商品消费");
            printer.append("\n姓    名: " + readCardTo.getUserName());
            printer.append(String.format("\n折扣比率: %s", simpleExpenseTo.getExpenseDetail().getDiscountRate()));
            printer.append(String.format("\n实际消费: %.2f", simpleExpenseTo.getExpenseDetail().getAmount()));
            printer.append(String.format("\n账户余额: %.2f", simpleExpenseTo.getExpenseDetail().getBalance()));
            printer.append("\n" + str);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PrinterUtils.getInstance(this).printLianxi(printer);
            } else {
                BlackSetPrinterUtils.getInstance(this).printLianxi(printer);
            }
//            SpUtils.put(this, AppConstant.Print.STUB2, printer.toString());
            printer.setLength(0);
        }
        payCount = -1;
        AudioUtils.getInstance().speakText("消费" + simpleExpenseTo.getExpenseDetail().getAmount() + "元");
        createConsumerInfo(simpleExpenseTo);
        Toasty.success(this, "消费成功", Toast.LENGTH_SHORT, true).show();
        clearAll();
    }

    /**
     * descirption: 消费成功提示
     */
    public void createConsumerInfo(SimpleExpenseTo expenseTo) {
        final CustomDialog dialog = new CustomDialog(this, R.layout.pop_list, R.style.Dialog);
        RelativeLayout animContainer = dialog.findViewById(R.id.anim_container);
        AnimSpring.getInstance().startAnim(AdConstant.ANIM_UP_TO_DOWN, animContainer, AdConstant.BOUNCINESS, AdConstant.SPEED);
        TextView account = dialog.findViewById(R.id.name);
        TextView balance = dialog.findViewById(R.id.balance);
        TextView cost = dialog.findViewById(R.id.cost);
        account.setText(readCardTo.getUserName());
        String amount = String.format("%.2f", expenseTo.getExpenseDetail().getBalance());
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 27)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        balance.setText(builder);
        cost.setText(String.format("%.2f", expenseTo.getExpenseDetail().getAmount()));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        if (!dialog.isShowing())
            dialog.show();

        Flowable.just(1)
                .delay(7, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                        dialog.dismiss();
                    }
                });
    }

    PayDialog payDialog = null;
    private String pwd = "";

    /**
     * descirption: 输入密码
     */
    public void createPayDialog() {
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setPasswordCallback(new PayDialog.PasswordCallback() {
                @Override
                public void callback(String password) {
//                if ("0000".equals(password)) {
//                    payDialog.clearPasswordText();
//                    Toasty.error(ManualActivity.this, "密码为错误，请重试", Toast.LENGTH_SHORT, true).show();
//
//                } else {
//                    Toast.makeText(ManualActivity.this, "密码为：" + password, Toast.LENGTH_SHORT).show();
//                    payDialog.dismiss();
//                }
                    pwd = password;
                    payDialog.dismiss();
                    String deviceID = (String) SpUtils.get(WarenverbrauchActivity.this, AppConstant.Receipt.NO, "");
                    param = new SimpleExpenseParam();
                    param.setNumber(mCardInfoBean.getNum());
                    param.setAmount(goodsPrices);
                    param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
                    param.setPayCount(payCount + 1);
                    param.setPayKey(pwd);
                    param.setPattern(4);
                    param.setDeviceType(2);
                    param.setListGoods(listGoodsBean);
                    mPresenter.createSimpleExpense(param);
                }
            });
        }
        payDialog.clearPasswordText();
        payDialog.setMoney(goodsPrices + "");
        payDialog.show();
        payDialog.setCancelable(false);
        payDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * descirption: 获得商品分类列表
     */
    @Override
    public void onPagers(List<EMGoodsTypeTo> emGoodsTypeTo) {
        if (emGoodsTypeToList != null) {
            emGoodsTypeToList.clear();
        }
        emGoodsTypeToList.addAll(emGoodsTypeTo);
        initRecyclerView();
    }

    /**
     * descirption:初始化获得商品分类列表
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WarenverbrauchActivity.this, RecyclerView.VERTICAL, false);
        wareTypeRv.setLayoutManager(linearLayoutManager);
        EMGoodsRvAdapter adapter = new EMGoodsRvAdapter(WarenverbrauchActivity.this, emGoodsTypeToList);
        wareTypeRv.setAdapter(adapter);
        adapter.setMyItemClickListener(new EMGoodsRvAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(String id, String name, int position) {
                requestDetail(id, name);// 请求详细的数据
                currentItem = position;
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();
            }
        });
        requestDetail(emGoodsTypeToList.get(0).getId(), emGoodsTypeToList.get(0).getName());
    }

    /**
     * descirption: 请求详细的数据
     */
    private void requestDetail(String id, String name) {
        progressBar.setVisibility(View.VISIBLE);
        wareTypeTv.setText(name);
        mPresenter.getEmGoodsDetail(id);
    }

    /**
     * descirption: 获取详细的数据
     */
    @Override
    public void onEMGoodsDetailGet(List<EMGoodsTo2.RowsBean> emGoodsTo2List) {
        progressBar.setVisibility(View.GONE);
        if (contentBeans != null) {
            contentBeans.clear();
        }
        contentBeans.addAll(emGoodsTo2List);

        Iterator<EMGoodsTo2.RowsBean> iterator = contentBeans.iterator();
        while (iterator.hasNext()) {
            iterator.next().setImgCount(0);
        }
        // TODO: 2019/10/22 显示已选择的商品
        if (selectedBeans.size() > 0) {
            Log.d(TAG, "onEMGoodsDetailGet: asd");
            for (int i = 0; i < selectedBeans.size(); i++) {
                for (int j = 0; j < contentBeans.size(); j++) {
                    if (selectedBeans.get(i).get(0).getGoods().getGoodsName().equals(contentBeans.get(j).getGoods().getGoodsName())
                            && selectedBeans.get(i).get(0).getGoods().getPrice() == contentBeans.get(j).getGoods().getPrice()) {
                        contentBeans.get(j).setImgCount(selectedBeans.get(i).size());
                        break;
                    }
                }
            }
        }
        if (detailAdapter != null) {
            detailAdapter.notifyDataSetChanged();
        } else {
            initDetailGoodsRecyclerView();
        }
    }

    /**
     * descirption: 初始化详细数据
     */
    private void initDetailGoodsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WarenverbrauchActivity.this, RecyclerView.VERTICAL, false);
        wareDetailRv.setLayoutManager(linearLayoutManager);
        wareDetailRv.addItemDecoration(new DividerItemDecoration(WarenverbrauchActivity.this, DividerItemDecoration.VERTICAL));
        detailAdapter = new EMDetailGoodsRvAdapter(WarenverbrauchActivity.this, contentBeans);
        wareDetailRv.setAdapter(detailAdapter);
        detailAdapter.setMyItemClickListener(new EMDetailGoodsRvAdapter.OnMyItemClickListener() {
            @Override
            public void onAddClick(int position, int count) {
                goodsCount += 1;
                BigDecimal b1 = new BigDecimal(String.valueOf(goodsPrices));
                BigDecimal b2 = new BigDecimal(String.valueOf(contentBeans.get(position).getGoods().getPrice()));
                goodsPrices = b1.add(b2).doubleValue();
                updateData();
                for (int i = 0; i < selectedBeans.size(); i++) {//如果已存在，就直接添加到已存在的列表后
                    if (selectedBeans.get(i).get(0).getGoods().getGoodsName().equals(contentBeans.get(position).getGoods().getGoodsName()) && selectedBeans.get(i).get(0).getGoods().getPrice() == contentBeans.get(position).getGoods().getPrice()) {
                        selectedBeans.get(i).add(contentBeans.get(position));
                        isExits = true;
                        break;
                    }
                }
                if (!isExits) {//如果没有存在，就新增
                    List<EMGoodsTo2.RowsBean> c = new ArrayList<>();
                    c.add(contentBeans.get(position));
                    selectedBeans.add(c);
                }
                isExits = false;
            }

            @Override
            public void onReduceClick(int position, int count) {
                if (goodsCount > 0 && goodsPrices >= contentBeans.get(position).getGoods().getPrice()) {
                    goodsCount -= 1;
                    BigDecimal b1 = new BigDecimal(String.valueOf(goodsPrices));
                    BigDecimal b2 = new BigDecimal(String.valueOf(contentBeans.get(position).getGoods().getPrice()));
                    goodsPrices = b1.subtract(b2).doubleValue();
                    updateData();
                    for (int i = 0; i < selectedBeans.size(); i++) {
                        boolean isSameName = selectedBeans.get(i).get(0).getGoods().getGoodsName().equals(contentBeans.get(position).getGoods().getGoodsName());
                        boolean isSamePrice = selectedBeans.get(i).get(0).getGoods().getPrice() == contentBeans.get(position).getGoods().getPrice();
                        if (isSameName && isSamePrice) {
                            //这里注意切换界面之后 remove 的对象不是同一个了
                            selectedBeans.get(i).remove(0);
                            if (selectedBeans.get(i).size() == 0) {
                                selectedBeans.remove(i);
                            }
                            break;
                        }
                    }
                }
                if (goodsCount == 0) {
                    wareGoodsCount.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.ware_car, R.id.payall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ware_car:
                if (selectedBeans.size() > 0) {
                    if (selectAdapter == null) {
                        initSelectedRv();
                    } else {
                        selectAdapter.notifyDataSetChanged();
                    }
                    if (bottomSheetLayout.isSheetShowing()) {
                        bottomSheetLayout.dismissSheet();
                    } else {
                        bottomSheetLayout.showWithSheetView(bottomView);
                    }
                }
                break;
            case R.id.payall:
                if (goodsPrices > 0) {
                    choicePay();
                    SimpleExpenseParam.ListGoodsBean listGoods;
                    for (int i = 0; i < selectedBeans.size(); i++) {
                        for (int j = 0; j < selectedBeans.get(i).size(); j++) {
                            listGoods = new SimpleExpenseParam.ListGoodsBean();
                            listGoods.setGoodsNo(selectedBeans.get(i).get(j).getGoods().getGoodsNo());
                            listGoods.setCount(1);
                            listGoodsBean.add(listGoods);
                        }
                    }
                } else {
                    Toast.makeText(this, "请先选择商品", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void choicePay() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("选择您的支付方式")
                .setIcon(R.mipmap.ic_launcher)
                .setItems(payMethod, new DialogInterface.OnClickListener() {//添加列表
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            if (goodsPrices > 0) {
                                startScanActivity();
                            } else {
                                Toast.makeText(WarenverbrauchActivity.this, "请先选择商品", Toast.LENGTH_SHORT).show();
                            }
                        } else if (i == 1) {
                            goToSimpleExpense();
                        }
                    }
                }).create();
        alertDialog.show();
    }

    private void goToSimpleExpense() {
        alert = new AlertDialog.Builder(WarenverbrauchActivity.this)
                .setTitle("刷卡支付")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("支付" + goodsPrices + "元").create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    initCardInfoReceived();//设置接收卡的循环
                }
            }
        });
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (readCardUtils != null) {
                    readCardUtils.close();
                }
            }
        });
        alert.show();

    }

    private void startScanActivity() {
        //Intent intent = new Intent(ScanTakeFoodActivity.this, CaptureActivity2.class);
        //intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);

        Intent intent = new Intent(WarenverbrauchActivity.this, ZXingScanActivity.class);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }

    private void initSelectedRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WarenverbrauchActivity.this, RecyclerView.VERTICAL, false);
        bottomRv.setLayoutManager(linearLayoutManager);
        bottomRv.addItemDecoration(new DividerItemDecoration(WarenverbrauchActivity.this, DividerItemDecoration.VERTICAL));
        selectAdapter = new EMSelectedGoodsRvAdapter(WarenverbrauchActivity.this, selectedBeans);
        bottomRv.setAdapter(selectAdapter);

        selectAdapter.setMyItemClickListener(new EMSelectedGoodsRvAdapter.OnMyItemClickListener() {
            @Override
            public void onAddClick(int position, int count) {
                selectedBeans.get(position).add(selectedBeans.get(position).get(0));
                selectAdapter.notifyDataSetChanged();
                goodsCount += 1;
                BigDecimal b1 = new BigDecimal(String.valueOf(goodsPrices));
                BigDecimal b2 = new BigDecimal(String.valueOf(selectedBeans.get(position).get(0).getGoods().getPrice()));
                goodsPrices = b1.add(b2).doubleValue();
                updateData();
                updateDetail();
            }

            @Override
            public void onReduceClick(int position, int count) {
                goodsCount -= 1;
                BigDecimal b1 = new BigDecimal(String.valueOf(goodsPrices));
                BigDecimal b2 = new BigDecimal(String.valueOf(selectedBeans.get(position).get(0).getGoods().getPrice()));
                goodsPrices = b1.subtract(b2).doubleValue();
                updateData();
                if (goodsCount == 0) {
                    wareGoodsCount.setVisibility(View.GONE);
                }
                if (count == 0) {
                    update0(position);
                }
                selectedBeans.get(position).remove(selectedBeans.get(position).get(0));
                if (selectedBeans.get(position).size() == 0) {
                    selectedBeans.remove(position);
                }
                selectAdapter.notifyDataSetChanged();
                bottomSheetLayout.peekSheet();
                if (count > 0) {
                    updateDetail();
                }
            }
        });
    }

    private void update0(int position) {
        for (int j = 0; j < contentBeans.size(); j++) {
            if (selectedBeans.get(position).get(0).getGoods().getGoodsName().equals(contentBeans.get(j).getGoods().getGoodsName())
                    && selectedBeans.get(position).get(0).getGoods().getPrice() == contentBeans.get(j).getGoods().getPrice()) {
                contentBeans.get(j).setImgCount(0);
                break;
            }
        }
        detailAdapter.notifyDataSetChanged();
    }

    private void updateDetail() {
        for (int i = 0; i < selectedBeans.size(); i++) {
            for (int j = 0; j < contentBeans.size(); j++) {
                if (selectedBeans.get(i).get(0).getGoods().getGoodsName().equals(contentBeans.get(j).getGoods().getGoodsName())
                        && selectedBeans.get(i).get(0).getGoods().getPrice() == contentBeans.get(j).getGoods().getPrice()) {
                    contentBeans.get(j).setImgCount(selectedBeans.get(i).size());
                    break;
                }
            }
        }
        detailAdapter.notifyDataSetChanged();
    }

    private void clearAll() {
        goodsCount = 0;
        goodsPrices = 0;
        wareGoodsCount.setVisibility(View.GONE);
        wareGoodsCount.setText(goodsCount + "");
        wareGoodsPrice.setText(goodsPrices + "");
        requestDetail(emGoodsTypeToList.get(currentItem).getId(), emGoodsTypeToList.get(currentItem).getName());
        readCardTo = null;
        mCardInfoBean = null;
        param = null;
        selectedBeans.clear();
        listGoodsBean.clear();
    }

    /**
     * descirption: 更新购物车状态
     */
    private void updateData() {
        wareGoodsCount.setVisibility(View.VISIBLE);
        wareGoodsCount.setText(goodsCount + "");
        wareGoodsPrice.setText(goodsPrices + "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCAN_REQUEST_CODE) {
                String isbn = data.getStringExtra("qrcode");
                if (!TextUtils.isEmpty(isbn)) {
                    Log.e(TAG, "onActivityResult: " + isbn);
                    Intent intent = new Intent();
                    intent.putExtra("content", isbn);
                    intent.putExtra("amount", goodsPrices);
                    ArrayList<QRExpenseParam.ListGoodsBean> listGoodsBeans = new ArrayList<>();
                    for (int i = 0; i < listGoodsBean.size(); i++) {
                        listGoodsBeans.add(new QRExpenseParam.ListGoodsBean(listGoodsBean.get(i).getGoodsNo(), listGoodsBean.get(i).getCount()));
                    }
                    intent.putParcelableArrayListExtra("ListGoods", listGoodsBeans);
                    intent.setClass(WarenverbrauchActivity.this, PaymentActivity.class);
                    startActivity(intent);
                }
                clearAll();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            clearAll();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onFailed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        goodsCount = 0;
        goodsPrices = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PrinterUtils.getInstance(this).close();
        }
        if (readCardUtils != null) {
            readCardUtils.close();
        }
    }
}
