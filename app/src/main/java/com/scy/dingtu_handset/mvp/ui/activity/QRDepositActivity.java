package com.scy.dingtu_handset.mvp.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
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
import com.scy.dingtu_handset.app.entity.QRDepositParam;
import com.scy.dingtu_handset.app.listening.RFCardListening;
import com.scy.dingtu_handset.app.nfc.NfcJellyBeanActivity;
import com.scy.dingtu_handset.app.utils.AntiShake;
import com.scy.dingtu_handset.app.utils.AudioUtils;
import com.scy.dingtu_handset.app.utils.ShakeAnimation;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.utils.card.Commands;
import com.scy.dingtu_handset.app.utils.card.ReadCardUtils;
import com.scy.dingtu_handset.di.component.DaggerQRDepositComponent;
import com.scy.dingtu_handset.mvp.contract.QRDepositContract;
import com.scy.dingtu_handset.mvp.presenter.QRDepositPresenter;
import com.scy.dingtu_handset.mvp.ui.widget.LoadDialog;
import com.scy.dingtu_handset.mvp.ui.widget.label.LabelRelativeLayout;

import java.util.Arrays;
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
public class QRDepositActivity extends NfcJellyBeanActivity<QRDepositPresenter> implements QRDepositContract.View {
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
    @BindView(R.id.cost)
    EditText cost;
    @BindView(R.id.cardview1)
    CardView cardview1;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.label_rl)
    LabelRelativeLayout labelRelativeLayout;
    CardInfoBean mCardInfoBean;
    CardInfoTo mCardInfoTo;
    @BindView(R.id.cardtype)
    TextView cardtype;
    @BindView(R.id.cardcount)
    TextView cardcount;
    private ReadCardUtils readCardUtils;
    private boolean isFirst = true;
    private String key;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerQRDepositComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_qrdeposit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("扫码充值");
        setupWindowAnimations();

        confirm.setEnabled(false);
        confirm.setText("请先刷卡，启用按钮");
        key = (String) SpUtils.get(this, AppConstant.NFC.NFC_KEY, "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initCardInfoReceived();//设置接收卡的循环
        }
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
                if (readCardUtils.Authen(4, key)) {
                    readCardUtils.Read(4);
                }
            }

            @Override
            public void readRFCardListening(String data) {
                Log.e(TAG, "handleMessage: data:" + data);
                mCardInfoBean = readCardUtils.cardInfoBean(data);
                Log.e(TAG, "handleMessage: cardInfoBean" + JSON.toJSONString(mCardInfoBean));
                UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(QRDepositActivity.this);
                if (TextUtils.isEmpty(mCardInfoBean.getCode())) {
                    return;
                }
                if (TextUtils.equals("0000", mCardInfoBean.getCode())) {
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                } else if (Integer.parseInt(mCardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
                if (!isFirst) {
                    return;
                }
                mPresenter.getCardInfo(mCardInfoBean.getNum());
                confirm.setEnabled(true);
                confirm.setText("扫码充值");
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
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(QRDepositActivity.this);
            mCardInfoBean = Commands.getInstance(QRDepositActivity.this).readTag(tag);
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
            mPresenter.getCardInfo(mCardInfoBean.getNum());
            confirm.setEnabled(true);
            confirm.setText("扫码充值");
        } else {
            System.out.println("intent null");
        }
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
    public void showLoading() {
        LoadDialog loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        if (!LoadDialog.getInstance().isAdded() && null == getSupportFragmentManager().findFragmentByTag(TAG)) {
            LoadDialog.getInstance().show(getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void hideLoading() {
        LoadDialog.getInstance().dismiss();
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

    @Override
    public void onCardInfo(CardInfoTo cardInfoTo) {
        isFirst = false;
        mCardInfoTo = cardInfoTo;
        name.setText(cardInfoTo.getName());
        number.setText("NO." + cardInfoTo.getSerialNo());
        String amount = String.format("￥%.2f", cardInfoTo.getCash());
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cardtype.setText(cardInfoTo.getCardTypeName());
        cardcount.setText(cardInfoTo.getTimes() + "次");
        balance.setText(builder);
        donate.setText(String.format("%.2f", cardInfoTo.getDonate()));
        subsidies.setText(String.format("%.2f", cardInfoTo.getSubsidy()));
        String value;
        switch (cardInfoTo.getUserState()) {
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
        ObjectAnimator nopeAnimator = ShakeAnimation.nope(cardview1);
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

    private static final int SCAN_REQUEST_CODE = 800;

    private void startScanActivity() {
        //Intent intent = new Intent(ScanTakeFoodActivity.this, CaptureActivity2.class);
        //intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);

        Intent intent = new Intent(QRDepositActivity.this, ZXingScanActivity.class);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCAN_REQUEST_CODE) {
                //Todo Handle the isbn number entered manually
                String isbn = data.getStringExtra("qrcode");
                Log.e(TAG, "onActivityResult: " + isbn);
                if (!TextUtils.isEmpty(isbn)) {
                    //todo something
                    QRDepositParam param = new QRDepositParam();
                    param.setQRContent(isbn);
                    param.setAmount(Double.valueOf(cost.getText().toString().trim()));
                    param.setNumber(mCardInfoTo.getNumber());
                    String deviceID = (String) SpUtils.get(QRDepositActivity.this, AppConstant.Receipt.NO, "");
                    param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
                    mPresenter.getQRDposit(param);
                }
            }
        }
    }

    protected AntiShake util = new AntiShake();

    @OnClick(R.id.confirm)
    public void onViewClicked() {
        if (util.check()) return;
        if (TextUtils.isEmpty(cost.getText().toString().trim())) {
            Toasty.error(QRDepositActivity.this, "请先输入消费金额", Toast.LENGTH_SHORT, true).show();
            return;
        }
        startScanActivity();
        confirm.setEnabled(false);
        confirm.setText("重新刷卡，启用按钮");
    }

    @Override
    protected void onDestroy() {
        if (readCardUtils != null) {
            readCardUtils.close();
        }
        super.onDestroy();
    }

}
