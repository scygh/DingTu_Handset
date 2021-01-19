package com.scy.dingtu_handset.mvp.ui.activity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.configuration.UserInfoHelper;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.nfc.BlackSetPrinterUtils;
import com.scy.dingtu_handset.app.nfc.NfcJellyBeanActivity;
import com.scy.dingtu_handset.app.utils.AdConstant;
import com.scy.dingtu_handset.app.utils.AnimSpring;
import com.scy.dingtu_handset.app.utils.AntiShake;
import com.scy.dingtu_handset.app.utils.AudioUtils;
import com.scy.dingtu_handset.app.utils.DoubleUtil;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.utils.card.Commands;
import com.scy.dingtu_handset.app.utils.card.PrinterUtils;
import com.scy.dingtu_handset.app.utils.card.ReadCardUtils;
import com.scy.dingtu_handset.app.utils.card.SoundTool;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.entity.CardInfoBean;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.SimpleExpenseParam;
import com.scy.dingtu_handset.app.entity.SimpleExpenseTo;
import com.scy.dingtu_handset.app.listening.RFCardListening;
import com.scy.dingtu_handset.di.component.DaggerAutoComponent;
import com.scy.dingtu_handset.mvp.contract.AutoContract;
import com.scy.dingtu_handset.mvp.presenter.AutoPresenter;
import com.scy.dingtu_handset.mvp.ui.widget.CustomDialog;
import com.scy.dingtu_handset.mvp.ui.widget.LoadDialog;
import com.scy.dingtu_handset.mvp.ui.widget.MyAnimation;
import com.scy.dingtu_handset.mvp.ui.widget.PayDialog;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AutoActivity extends NfcJellyBeanActivity<AutoPresenter> implements AutoContract.View {
    LoadDialog loadDialog;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.cost)
    EditText cost;
    @BindView(R.id.btn_print)
    Button btnPrint;
    //NFC key
    private String key;

    SimpleExpenseParam param = new SimpleExpenseParam();
    private String name = "";
    StringBuilder printer = new StringBuilder();
    private String pwd = "";
    private int payCount = 0;

    int company = 0;
    int device = 0;
    private CardInfoBean cardInfoBean;
    private ReadCardUtils readCardUtils;

    private boolean isFirst = true;
    private boolean isPrint;
    private DeviceReadCardRequest deviceReadRequest;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAutoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_auto; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("自动消费");
        setupWindowAnimations();//设置动画
        loadDialog = LoadDialog.getInstance();//初始化加载dialog样式
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        key = (String) SpUtils.get(this, AppConstant.NFC.NFC_KEY, "");//获取nfc key
        String money = (String) SpUtils.get(this, AppConstant.Print.COST, "");//如果自动消费已经设置金额（onDestroy中设置了）则获取
        if (!TextUtils.isEmpty(money)) {
            cost.setText(money);
            cost.setSelection(cost.getText().length());//设置光标的位置
        }
        company = UserInfoHelper.getInstance(this).getCode();//得到全局用户信息单例，拿到CompanyCode 在用户登录成功时就已经复值了
        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);//拿到机器号
        cardInfoBean = new CardInfoBean();//初始化卡对象
        isPrint = (boolean) SpUtils.get(this, AppConstant.Receipt.isPrint, false);//获取打印开关状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initCardInfoReceived();//设置接收卡的循环
        }
    }

    /**
     * descirption: activity转入动画
     */
    private void setupWindowAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_right_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);
        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
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
            public void findRFCardListening(String uuid) {
                if (readCardUtils.Authen(4, key)) {//卡密码是否有效
                    readCardUtils.Read(4);//去读卡
                }
            }

            @Override
            public void readRFCardListening(String data) {
                Log.e(TAG, "handleMessage: data:" + data);
                cardInfoBean = readCardUtils.cardInfoBean(data);//装填卡信息到对象
                Log.e(TAG, "handleMessage: cardInfoBean" + JSON.toJSONString(cardInfoBean));
                UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(AutoActivity.this);
                //判断卡的状态
                if (TextUtils.isEmpty(cardInfoBean.getCode())) {
                    return;
                }
                if (TextUtils.equals("0000", cardInfoBean.getCode())) {
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                } else if (Integer.parseInt(cardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
                if (!isFirst) {//保证是同一次的消费
                    return;
                }
                SoundTool.getMySound(AutoActivity.this).playMusic("success");//播放音频
                deviceReadRequest = new DeviceReadCardRequest(cardInfoBean.getNum());
                mPresenter.deviceReadCard(company, device, deviceReadRequest);//请求接口去消费
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
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(AutoActivity.this);
            cardInfoBean = Commands.getInstance(AutoActivity.this).readTag(tag);
            Timber.wtf("M1卡信息：" + cardInfoBean.getCode());
            if (TextUtils.isEmpty(cardInfoBean.getCode())) {
                return;
            } else {
                if (TextUtils.equals("0000", cardInfoBean.getCode())) {
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                } else if (Integer.parseInt(cardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
            }
            deviceReadRequest = new DeviceReadCardRequest(cardInfoBean.getNum());
            mPresenter.deviceReadCard(company, device, deviceReadRequest);
        } else {
            System.out.println("intent null");
        }
    }

    /**
     * descirption: 读卡返回，再去读卡是否有密码
     */
    @Override
    public void onReadCard(DeviceReadCardResponse readCardTo) {
        isFirst = false;
        param.setNumber(readCardTo.getCard().getNumber());
        name = readCardTo.getUser().getName();
        payCount = readCardTo.getNextPayCount();
        mPresenter.getPaySgetPayKeySwitch2();
    }

    /**
     * descirption: 读到了状态
     */
    @Override
    public void creatBill2(boolean isOpen) {
        if (checking())
            return;
        if (payCount == -1) {
            showMessage("重新放置卡片");
            AudioUtils.getInstance().speakText("重新放置卡片");
            return;
        }
        if (isOpen) {
            createPayDialog();
        } else {
            String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
            param.setNumber(cardInfoBean.getNum());
            param.setAmount(Double.parseDouble(cost.getText().toString().trim()));
            param.setPayCount(payCount);
            param.setPayKey(pwd);
            param.setPattern(2);
            mPresenter.createSimpleExpense(company, device, param);
        }
    }

    /**
     * descirption: 核对金额对不对
     */
    private boolean checking() {
        if (TextUtils.isEmpty(cost.getText().toString().trim())) {
            Toast.makeText(this, "请输入消费金额", Toast.LENGTH_LONG).show();
            return true;
        } else if (!DoubleUtil.isNumber(cost.getText().toString().trim())) {
            Toast.makeText(this, "消费金额不正确", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    /**
     * descirption: 密码框
     */
    PayDialog payDialog = null;

    public void createPayDialog() {
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setPasswordCallback(new PayDialog.PasswordCallback() {
                @Override
                public void callback(String password) {
                    pwd = password;
                    payDialog.dismiss();
                    String deviceID = (String) SpUtils.get(AutoActivity.this, AppConstant.Receipt.NO, "");
                    param.setNumber(cardInfoBean.getNum());
                    param.setAmount(Double.parseDouble(cost.getText().toString().trim()));
                    param.setPayCount(payCount);
                    param.setPayKey(pwd);
                    param.setPattern(2);
                    mPresenter.createSimpleExpense(company, device, param);
                }
            });
        }
        payDialog.clearPasswordText();
        payDialog.setMoney(cost.getText().toString().trim());
        payDialog.show();
        payDialog.setCancelable(false);
        payDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * descirption: 消费成功了
     */
    @Override
    public void creatSuccess(SimpleExpenseTo simpleExpenseTo) {
        if (isPrint) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            printer.append("\n工作模式: 自动扣款");
            printer.append("\n姓    名: " + name);
            printer.append(String.format("\n折扣比率: %s", simpleExpenseTo.getExpenseDetail().getDiscountRate()));
            printer.append(String.format("\n实际消费: %.2f", simpleExpenseTo.getExpenseDetail().getAmount()));
            printer.append(String.format("\n账户余额: %.2f", simpleExpenseTo.getExpenseDetail().getBalance()));
            printer.append("\n" + str);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PrinterUtils.getInstance(this).printLianxi(printer);
            } else {
                BlackSetPrinterUtils.getInstance(this).printLianxi(printer);
            }
            SpUtils.put(this, AppConstant.Print.STUB2, printer.toString());
            printer.setLength(0);
        }
        payCount = -1;
        AudioUtils.getInstance().speakText("消费" + simpleExpenseTo.getExpenseDetail().getAmount() + "元");
        createConsumerInfo(simpleExpenseTo);
        Toasty.success(this, "消费成功", Toast.LENGTH_SHORT, true).show();
    }

    /**
     * descirption: 显示成功信息
     */
    public void createConsumerInfo(SimpleExpenseTo expenseTo) {
        final CustomDialog dialog = new CustomDialog(this, R.layout.pop_list, R.style.Dialog);
        RelativeLayout animContainer = dialog.findViewById(R.id.anim_container);
        AnimSpring.getInstance().startAnim(AdConstant.ANIM_UP_TO_DOWN, animContainer, AdConstant.BOUNCINESS, AdConstant.SPEED);
        TextView account = dialog.findViewById(R.id.name);
        TextView balance = dialog.findViewById(R.id.balance);
        TextView cost = dialog.findViewById(R.id.cost);
        account.setText(name);
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

    /**
     * descirption: 补打小票
     */
    protected AntiShake util = new AntiShake();

    @OnClick(R.id.btn_print)
    public void onViewClicked() {//补打小票
        if (util.check()) return;
        btnPrint.startAnimation(new MyAnimation());
        String print = (String) SpUtils.get(AutoActivity.this, AppConstant.Print.STUB2, "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PrinterUtils.getInstance(AutoActivity.this).printLianxi(new StringBuilder(print));
        } else {
            BlackSetPrinterUtils.getInstance(AutoActivity.this).printLianxi(new StringBuilder(print));
        }
    }

    /**
     * descirption: 关闭读卡和打印，保存金额
     */
    @Override
    protected void onDestroy() {
        SpUtils.put(this, AppConstant.Print.COST, cost.getText().toString().trim());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PrinterUtils.getInstance(this).close();
        }
        if (readCardUtils != null) {
            readCardUtils.close();
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        if (!loadDialog.isAdded() && null == getSupportFragmentManager().findFragmentByTag(TAG))
            loadDialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void hideLoading() {
        loadDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
