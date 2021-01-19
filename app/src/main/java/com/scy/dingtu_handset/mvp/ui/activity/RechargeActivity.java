package com.scy.dingtu_handset.mvp.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.configuration.UserInfoHelper;
import com.scy.dingtu_handset.app.entity.CardInfoBean;
import com.scy.dingtu_handset.app.entity.CardInfoTo;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.MoneyParam;
import com.scy.dingtu_handset.app.entity.RefundRequest;
import com.scy.dingtu_handset.app.entity.RefundResponse;
import com.scy.dingtu_handset.app.entity.UserGetTo;
import com.scy.dingtu_handset.app.listening.RFCardListening;
import com.scy.dingtu_handset.app.nfc.NfcJellyBeanActivity;
import com.scy.dingtu_handset.app.utils.AntiShake;
import com.scy.dingtu_handset.app.utils.ArithUtil;
import com.scy.dingtu_handset.app.utils.AudioUtils;
import com.scy.dingtu_handset.app.utils.ShakeAnimation;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.utils.card.Commands;
import com.scy.dingtu_handset.app.utils.card.ReadCardUtils;
import com.scy.dingtu_handset.app.utils.card.SoundTool;
import com.scy.dingtu_handset.di.component.DaggerRechargeComponent;
import com.scy.dingtu_handset.mvp.contract.RechargeContract;
import com.scy.dingtu_handset.mvp.presenter.RechargePresenter;
import com.scy.dingtu_handset.mvp.ui.widget.LoadDialog;
import com.scy.dingtu_handset.mvp.ui.widget.label.LabelRelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RechargeActivity extends NfcJellyBeanActivity<RechargePresenter> implements RechargeContract.View {
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.donate)
    TextView donate;
    @BindView(R.id.subsidies)
    TextView subsidies;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.et_donate)
    EditText et_donate;
    @BindView(R.id.et_money)
    EditText et_money;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.label_rl)
    LabelRelativeLayout labelRelativeLayout;
    @BindView(R.id.cardType)
    TextView cardtypetv;
    LoadDialog loadDialog;
    CardInfoBean mCardInfoBean;
    int type;
    DeviceReadCardResponse deviceReadCardResponse;

    private static final int MESSAGE_SEARCH = 0x1;
    private static long INTERVAL = 1000; // 输入变化时间间隔
    boolean isPayBegin = true;

    CardInfoTo mCardInfoTo;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE_SEARCH) {
                if (!TextUtils.isEmpty(et_amount.getText().toString().trim())) {
                    mPresenter.onDonate(type, Double.valueOf(et_amount.getText().toString().trim()));
                    et_money.setText(et_amount.getText().toString().trim());
                } else {
                    et_donate.setText("");
                    et_money.setText("");
                }

            }
        }
    };
    @BindView(R.id.cardcount)
    TextView cardcount;
    @BindView(R.id.recharge_tv1_name)
    TextView rechargeTv1Name;
    @BindView(R.id.recharge_tv2_name)
    TextView rechargeTv2Name;
    @BindView(R.id.donate_ll)
    LinearLayout linearLayout;
    private String key;
    private ReadCardUtils readCardUtils;
    private DeviceReadCardRequest deviceReadCardRequest;
    int company = 0;
    int device = 0;
    List<DeviceReadCardResponse.FinancesBean> flist = new ArrayList<>();
    String fcash;
    String fsubsidy;
    int ftimes;
    String fdonate;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRechargeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recharge; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("充值");
        setupWindowAnimations();

        loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        // 设置监听R
        et_amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mHandler.hasMessages(MESSAGE_SEARCH)) {
                    mHandler.removeMessages(MESSAGE_SEARCH);
                }
                if (type == 2 || type == 3 || type == 4) {
                    return;
                }
                mHandler.sendEmptyMessageDelayed(MESSAGE_SEARCH, INTERVAL);
            }
        });

        submit.setEnabled(false);
        submit.setText("请先刷卡，启用按钮");
        key = (String) SpUtils.get(this, AppConstant.NFC.NFC_KEY, "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initCardInfoReceived();//设置接收卡的循环
        }
        company = UserInfoHelper.getInstance(this).getCode();//得到全局用户信息单例，拿到CompanyCode 在用户登录成功时就已经复值了
        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);//拿到机器号
    }

    private void initCardInfoReceived() {
        readCardUtils = new ReadCardUtils();
        readCardUtils.init();
        readCardUtils.run();
        readCardUtils.setRFCardListening(new RFCardListening() {
            @Override
            public void findRFCardListening(String s) {
                if (readCardUtils.Authen(4, key)) {
                    readCardUtils.Read(4);
                }
            }

            @Override
            public void readRFCardListening(String data) {
                UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(RechargeActivity.this);
                mCardInfoBean = readCardUtils.cardInfoBean(data);
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
                if (!isPayBegin) {
                    return;
                }
                SoundTool.getMySound(RechargeActivity.this).playMusic("success");
                deviceReadCardRequest = new DeviceReadCardRequest(mCardInfoBean.getNum());
                mPresenter.deviceReadCard(company, device, deviceReadCardRequest);
            }

            @Override
            public void noFindRFCardListening() {
                isPayBegin = true;
            }

            @Override
            public void failReadRFCardListening() {
                isPayBegin = true;
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
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(RechargeActivity.this);
            mCardInfoBean = Commands.getInstance(RechargeActivity.this).readTag(tag);
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
            deviceReadCardRequest = new DeviceReadCardRequest(mCardInfoBean.getNum());
            mPresenter.deviceReadCard(company, device, deviceReadCardRequest);
        } else {
            System.out.println("intent null");
        }
    }

    @Override
    public void onReadCard(DeviceReadCardResponse content) {
        flist.clear();
        flist.addAll(content.getFinances());
        for (DeviceReadCardResponse.FinancesBean financesBean : flist) {
            if (financesBean.getKind() == 0) {
                fcash = String.format("￥%.2f", financesBean.getBalance());
            } else if (financesBean.getKind() == 1) {
                fsubsidy = String.format("%.2f", financesBean.getBalance());
            } else if (financesBean.getKind() == 2) {
                ftimes = (int) financesBean.getBalance();
            } else if (financesBean.getKind() == 3) {
                fdonate = String.format("%.2f", financesBean.getBalance());
            } else if (financesBean.getKind() == 4) {

            }
        }
        submit.setEnabled(true);//按钮可选
        submit.setText("确认充值");
        et_amount.setText("");
        et_donate.setText("");
        et_money.setText("");
        isPayBegin = false;
        deviceReadCardResponse = content;
        name.setText(content.getUser().getName());
        number.setText("NO." + content.getCard().getNumber());
        type = content.getCard().getType();

        if (content.getCard().getType() == 2 || content.getCard().getType() == 3 || content.getCard().getType() == 4) {
            cardtypetv.setText("计次卡");
            rechargeTv1Name.setText("充值次数");
            cardcount.setText(ftimes + "次");
            linearLayout.setVisibility(View.GONE);
            balance.setText(ftimes + "");
        } else {
            cardtypetv.setText("正常卡");
            rechargeTv1Name.setText("充值金额");
            linearLayout.setVisibility(View.VISIBLE);
            rechargeTv2Name.setText("赠送金额");
            donate.setText(fdonate);
            subsidies.setText(fsubsidy);
            SpannableStringBuilder builder = new SpannableStringBuilder(fcash);
            builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), fcash.indexOf("￥"), fcash.indexOf("￥") + 1
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), fcash.indexOf("."), fcash.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            balance.setText(builder);
        }

        String value;
        switch (content.getUser().getState()) {
            case 0:
                value = "未开户";
                break;
            case 1:
                value = "正常";
                break;
            case 2:
                value = "已挂失";
                break;
            case 3:
                value = "已注销";
                break;
            case 4:
                value = "未审核";
                break;
            case 5:
                value = "审核失败";
                break;
            default:
                value = "";
                break;
        }
        labelRelativeLayout.setTextContent(value);
        ObjectAnimator nopeAnimator = ShakeAnimation.nope(cardview);
        nopeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        nopeAnimator.start();
        Flowable.just(1)
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        nopeAnimator.end();
                    }
                });
    }

    @Override
    public void onCardInfo(CardInfoTo cardInfoTo) {
    }

    @Override
    public void onDonate(double d) {
        et_donate.setText(String.format("%.2f", d));
    }


    private void setupWindowAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_left_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);

        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
    }

    @Override
    protected void onDestroy() {
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

    protected AntiShake util = new AntiShake();

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (util.check()) return;
        submit.setEnabled(false);
        submit.setText("重新刷卡，启用按钮");
        String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        RefundRequest param = new RefundRequest();

        param.setNumber(deviceReadCardResponse.getCard().getNumber());
        param.setAmount(TextUtils.isEmpty(et_amount.getText().toString().trim()) ? 0 : Double.valueOf(et_amount.getText().toString().trim()));
        param.setDonate(TextUtils.isEmpty(et_donate.getText().toString().trim()) ? 0 : Double.valueOf(et_donate.getText().toString().trim()));
        param.setPattern(5);
        param.setPayCount(deviceReadCardResponse.getNextPayCount());
        mPresenter.recharge(company, device, param);

    }

    @Override
    public void onRecharge(RefundResponse refundResponse) {
        deviceReadCardRequest = new DeviceReadCardRequest(refundResponse.getDepositDetail().getNumber());
        mPresenter.deviceReadCard(company, device, deviceReadCardRequest);
        if (type == 1) {
            AudioUtils.getInstance().speakText(String.format("充值%.2f元", refundResponse.getDepositDetail().getAmount()));
        } else if (type == 4) {
            AudioUtils.getInstance().speakText("充值" + (int) refundResponse.getDepositDetail().getAmount() + "次");
        }
    }
}
