package com.scy.dingtu_handset.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.configuration.UserInfoHelper;
import com.scy.dingtu_handset.app.entity.CodeExpenseRequest;
import com.scy.dingtu_handset.app.entity.CodeExpenseResponse;
import com.scy.dingtu_handset.app.entity.CodeReadRequest;
import com.scy.dingtu_handset.app.entity.CodeReadResponse;
import com.scy.dingtu_handset.app.entity.QRExpenseParam;
import com.scy.dingtu_handset.app.entity.QRExpenseTo;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.di.component.DaggerPaymentComponent;
import com.scy.dingtu_handset.mvp.contract.PaymentContract;
import com.scy.dingtu_handset.mvp.presenter.PaymentPresenter;
import com.scy.dingtu_handset.mvp.ui.widget.LoadDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PaymentActivity extends BaseActivity<PaymentPresenter> implements PaymentContract.View {
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.retry)
    TextView retry;
    @BindView(R.id.cardview1)
    CardView cardview1;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.cardview2)
    CardView cardview2;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.serial_number)
    TextView serialNumber;
    @BindView(R.id.cardview3)
    CardView cardview3;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.balance_hui)
    TextView balanceHui;
    String str;
    double dou;
    int company = 0;
    int device = 0;
    ArrayList<CodeExpenseRequest.GoodsDetailsBean> listGoodsBeans;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPaymentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_payment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("支付结果页");
        str = getIntent().getStringExtra("content");
        dou = getIntent().getDoubleExtra("amount", 0);
        listGoodsBeans = getIntent().getParcelableArrayListExtra("ListGoods");
        CodeReadRequest readRequest = new CodeReadRequest();
        readRequest.setCodeContent(str);
        company = UserInfoHelper.getInstance(this).getCode();//得到全局用户信息单例，拿到CompanyCode 在用户登录成功时就已经复值了
        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);//拿到机器号
        mPresenter.codeRead(company, device, readRequest);
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
        loading.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        llContent.setVisibility(View.VISIBLE);
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
    public void onCodeRead(CodeReadResponse codeReadResponse) {
        CodeExpenseRequest param = new CodeExpenseRequest();
        param.setCodeContent(str);
        if (codeReadResponse.getCard() != null) {
            param.setNumber(codeReadResponse.getCard().getNumber());
        }
        param.setAmount(dou);
        if (listGoodsBeans != null && listGoodsBeans.size() > 0) {
            param.setPattern(4);
            param.setGoodsDetails(listGoodsBeans);
        } else {
            param.setPattern(1);
        }
        param.setPayCount(codeReadResponse.getNextPayCount());
        mPresenter.codeExpense(company, device, param);
    }

    @Override
    public void onPaySuccess(CodeExpenseResponse codeExpenseResponse) {
        if (codeExpenseResponse.getThirdPartyExpenseDetail() != null) {
            time.setText("支付时间 " + codeExpenseResponse.getThirdPartyExpenseDetail().getCreateTime());
            name.setText("订单号 " + codeExpenseResponse.getThirdPartyExpenseDetail().getOurSourceId());
            if (codeExpenseResponse.getCodeType() == 1)
                number.setText("支付方式：微信支付");
            if (codeExpenseResponse.getCodeType() == 2)
                number.setText("支付方式：支付宝支付");
            balance.setText(String.format("￥ %.2f", codeExpenseResponse.getThirdPartyExpenseDetail().getAmount()));
            serialNumber.setText(codeExpenseResponse.getThirdPartyExpenseDetail().getId());
            balanceHui.setVisibility(View.GONE);
        } else if (codeExpenseResponse.getExpenseDetail() != null) {
            time.setText("支付时间 " + codeExpenseResponse.getExpenseDetail().getCreateTime());
            name.setText("原价 " + String.format("￥ %.2f", codeExpenseResponse.getExpenseDetail().getOriginalAmount()));
            number.setText("支付方式：会员码支付");
            balance.setText(String.format("￥ %.2f", codeExpenseResponse.getExpenseDetail().getAmount()));
            balanceHui.setText("余额 " + codeExpenseResponse.getExpenseDetail().getBalance() + "元");
            serialNumber.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);

        }
        cardview1.setVisibility(View.GONE);
        cardview2.setVisibility(View.VISIBLE);
        cardview3.setVisibility(View.VISIBLE);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom);
        cardview2.setLayoutAnimation(controller);
        cardview2.scheduleLayoutAnimation();
        cardview3.setLayoutAnimation(controller);
        cardview3.scheduleLayoutAnimation();
    }

    @Override
    public void onPayFailure() {
        cardview1.setVisibility(View.VISIBLE);
        cardview2.setVisibility(View.GONE);
        cardview3.setVisibility(View.GONE);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom);
        cardview1.setLayoutAnimation(controller);
        cardview1.scheduleLayoutAnimation();
    }


    @OnClick({R.id.retry, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.retry:
                onBackPressed();
                break;
            case R.id.confirm:
                onBackPressed();
                break;
        }
    }
}
