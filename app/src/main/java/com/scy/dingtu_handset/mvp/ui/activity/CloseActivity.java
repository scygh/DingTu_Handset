package com.scy.dingtu_handset.mvp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
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

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.configuration.UserInfoHelper;
import com.scy.dingtu_handset.app.entity.CardInfoBean;
import com.scy.dingtu_handset.app.entity.CardInfoTo;
import com.scy.dingtu_handset.app.entity.MoneyParam;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
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
import com.scy.dingtu_handset.di.component.DaggerCloseComponent;
import com.scy.dingtu_handset.mvp.contract.CloseContract;
import com.scy.dingtu_handset.mvp.presenter.ClosePresenter;
import com.scy.dingtu_handset.mvp.ui.widget.HollowTextView;
import com.scy.dingtu_handset.mvp.ui.widget.label.LabelRelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
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
public class CloseActivity extends NfcJellyBeanActivity<ClosePresenter> implements CloseContract.View {
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
    TextView cost;
    @BindView(R.id.warn)
    TextView warn;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.cardview0)
    CardView cardview0;
    @BindView(R.id.label_rl)
    LabelRelativeLayout labelRelativeLayout;
    @BindView(R.id.submit)
    Button submit;
    //    LoadDialog loadDialog;
    CardInfoBean mCardInfoBean;
    Tag tag;
    UserGetTo userGetTo;
    @BindView(R.id.edit_cost)
    EditText editCost;
    @BindView(R.id.edit_amount)
    EditText editAmount;
    @BindView(R.id.cardtype)
    TextView cardtype;
    @BindView(R.id.cardcount)
    TextView cardcount;
    @BindView(R.id.number0)
    TextView number0;
    @BindView(R.id.balance0)
    TextView balance0;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv_back)
    HollowTextView tvBack;
    @BindView(R.id.ll0)
    LinearLayout ll0;
    private AnimatorSet inSet;
    private AnimatorSet outSet;

    private boolean showingGray = true;
    int company = 0;
    int device = 0;
    private ReadCardUtils readCardUtils;
    private String key;
    private boolean isPayBegin;
    private boolean isFirst = true;

    @Override
    protected void onDestroy() {
        closeReadNfc();
        super.onDestroy();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCloseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_close; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("注销");
        setupWindowAnimations();
        mCardInfoBean = new CardInfoBean();
        key = (String) SpUtils.get(this, AppConstant.NFC.NFC_KEY, "");
//        loadDialog = LoadDialog.getInstance();
//        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        submit.setEnabled(false);
        submit.setText("请先刷卡，启用按钮");

        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);

        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);

        company = UserInfoHelper.getInstance(this).getCode();

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
                UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(CloseActivity.this);
                if (TextUtils.equals("0000", mCardInfoBean.getCode())) {
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                }
                if (Integer.parseInt(mCardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
                if (!isFirst) {//保证是同一次的消费
                    return;
                }
                //mPresenter.readtCardInfo(company, device, mCardInfoBean.getNum());
                SoundTool.getMySound(CloseActivity.this).playMusic("success");//播放音频
                mPresenter.userGetTo(mCardInfoBean.getNum());
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
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            System.out.println(Arrays.toString(tag.getTechList()));
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(CloseActivity.this);
            mCardInfoBean = Commands.getInstance(CloseActivity.this).readTag(tag);
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
            mPresenter.userGetTo(mCardInfoBean.getNum());
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

    private void flipToGray() {
        if (!showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = true;

            cardview0.setCardElevation(0);
            cardview.setCardElevation(0);

            outSet.setTarget(cardview0);
            outSet.start();

            inSet.setTarget(cardview);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    cardview.setCardElevation(ArmsUtils.dip2px(CloseActivity.this, 12));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    private void flipToBlue() {
        if (showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = false;

            cardview.setCardElevation(0);
            cardview0.setCardElevation(0);

            outSet.setTarget(cardview);
            outSet.start();

            inSet.setTarget(cardview0);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    cardview0.setCardElevation(ArmsUtils.dip2px(CloseActivity.this, 12));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
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
    public void onUserGetTo(UserGetTo content) {
        isFirst = false;
        submit.setEnabled(true);//按钮可选
        submit.setText("确认注销");
        userGetTo = content;
        name.setText(content.getUser().getName());
        number.setText("NO." + content.getCard().getNumber());
        if (content.getCard().getType() == 2 || content.getCard().getType() == 3 || content.getCard().getType() == 4) {
            cardtype.setText("计次卡");
        } else {
            cardtype.setText("正常卡");
        }
        cardcount.setText((int) content.getFinances().get(2).getBalance() + "次");
        double sum1 = ArithUtil.add(content.getFinances().get(0).getBalance(), content.getFinances().get(3).getBalance());//现金加赠送 可以退的金额
        String amount = String.format("￥%.2f", content.getFinances().get(0).getBalance());//卡里正常金额
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(ArmsUtils.sp2px(this, 24)), amount.indexOf("."), amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        balance.setText(builder);
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
        donate.setText(String.format("%.2f", content.getFinances().get(3).getBalance()));
        subsidies.setText(String.format("%.2f", content.getFinances().get(1).getBalance()));
        cost.setText(String.format("工本费 %.2f", content.getCard().getCost()));
        editAmount.setText(String.format("%.2f", content.getFinances().get(0).getBalance()));
        editCost.setText(String.format("%.2f", content.getCard().getCost()));
        flipToGray();
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
    public void onDeregister() {
        mCardInfoBean.setCode("0000");
        mCardInfoBean.setNum(0);
        mCardInfoBean.setType(0);
        mCardInfoBean.setSpending_limit(0);
        mCardInfoBean.setCash_account(0.00);
        mCardInfoBean.setCard_validity("2000-00-00");
        mCardInfoBean.setConsumption_num(0);
        mCardInfoBean.setSubsidies_time("2000-00");
        mCardInfoBean.setName("");
        mCardInfoBean.setLevel(0);
        mCardInfoBean.setGuaranteed_amount(0);
        mCardInfoBean.setAllowance_account(0.00);
        mCardInfoBean.setSpending_time("2000-00-00");
        mCardInfoBean.setMeal_times(0);
        mCardInfoBean.setDiscount(0);
        Timber.wtf("写卡参数：" + JSON.toJSONString(mCardInfoBean));
        boolean isWrite;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isWrite = readCardUtils.Write(mCardInfoBean);
        } else {
            isWrite = Commands.getInstance(CloseActivity.this).writeTag(tag, mCardInfoBean);
        }
        Log.e(TAG, "onCardInfo: " + isWrite);
        flipToBlue();
        if (isWrite) {
            warn.setVisibility(View.INVISIBLE);
        } else {
            warn.setVisibility(View.VISIBLE);
        }
    }


    protected AntiShake util = new AntiShake();

    @OnClick({R.id.submit, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (util.check()) return;
                submit.setEnabled(false);
                submit.setText("重新刷卡，启用按钮");
                SoundTool.getMySound(CloseActivity.this).playMusic("success");
                String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
                MoneyParam param = new MoneyParam();

                param.setUserID(userGetTo.getUser().getId());
                param.setNumber(userGetTo.getCard().getNumber());
                param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
                param.setAmount(TextUtils.isEmpty(editAmount.getText().toString().trim()) ? 0 : Double.valueOf(editAmount.getText().toString().trim()));
                param.setCost(TextUtils.isEmpty(editCost.getText().toString().trim()) ? 0 : Double.valueOf(editCost.getText().toString().trim()));
                mPresenter.onDeregister(param);

                break;
            case R.id.tv_back:
                SoundTool.getMySound(CloseActivity.this).playMusic("success");
                mCardInfoBean.setCode("0000");
                mCardInfoBean.setNum(0);
                mCardInfoBean.setType(0);
                mCardInfoBean.setSpending_limit(0);
                mCardInfoBean.setCash_account(0.00);
                mCardInfoBean.setCard_validity("2000-00-00");
                mCardInfoBean.setConsumption_num(0);
                mCardInfoBean.setSubsidies_time("2000-00");
                mCardInfoBean.setName("");
                mCardInfoBean.setLevel(0);
                mCardInfoBean.setGuaranteed_amount(0);
                mCardInfoBean.setAllowance_account(0.00);
                mCardInfoBean.setSpending_time("2000-00-00");
                mCardInfoBean.setMeal_times(0);
                mCardInfoBean.setDiscount(0);
                Timber.wtf("写卡参数：" + JSON.toJSONString(mCardInfoBean));
                boolean isWrite;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    isWrite = readCardUtils.Write(mCardInfoBean);
                } else {
                    isWrite = Commands.getInstance(CloseActivity.this).writeTag(tag, mCardInfoBean);
                }
                break;
        }


    }

    public void closeReadNfc() {
        if (readCardUtils != null) {
            readCardUtils.close();
        }
    }
}
