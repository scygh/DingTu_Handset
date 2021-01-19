package com.scy.dingtu_handset.mvp.ui.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.entity.OrderDetailGet;
import com.scy.dingtu_handset.app.entity.OrderGoodsDetailList;
import com.scy.dingtu_handset.app.entity.OrderTake;
import com.scy.dingtu_handset.app.nfc.BlackSetPrinterUtils;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.utils.card.PrinterUtils;
import com.scy.dingtu_handset.di.component.DaggerScanTakeFoodComponent;
import com.scy.dingtu_handset.mvp.contract.ScanTakeFoodContract;
import com.scy.dingtu_handset.mvp.presenter.ScanTakeFoodPresenter;
import com.scy.dingtu_handset.mvp.ui.adapter.ScanTakeGoodsRvAdapter;
import com.scy.dingtu_handset.mvp.ui.widget.UMExpandLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2020 10:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ScanTakeFoodActivity extends BaseActivity<ScanTakeFoodPresenter> implements ScanTakeFoodContract.View {


    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.start_scan)
    Button startScan;
    private static final int SCAN_REQUEST_CODE = 1;
    @BindView(R.id.orderType)
    TextView orderType;
    @BindView(R.id.expand_view)
    UMExpandLayout expandView;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.orderId)
    TextView orderId;
    @BindView(R.id.scan_take_food_rv)
    RecyclerView scanTakeFoodRv;
    @BindView(R.id.AheadAmount)
    TextView AheadAmount;
    @BindView(R.id.Amount)
    TextView Amount;
    @BindView(R.id.CreateTime)
    TextView CreateTime;
    @BindView(R.id.TakeDate)
    TextView TakeDate;
    @BindView(R.id.start_takeorder)
    Button startTakeorder;
    @BindView(R.id.iv_detail)
    ImageView ivdetail;
    @BindView(R.id.state)
    TextView tvState;
    private ScanTakeGoodsRvAdapter adapter;
    private String id;
    private boolean isPrint;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerScanTakeFoodComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_scan_take_food; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("扫码取餐");
        setupWindowAnimations();
        orderType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandView.toggleExpand();
                ObjectAnimator animator = expandView.isExpand() ? ObjectAnimator.ofFloat(ivdetail, "rotation", 0f, 180f) : ObjectAnimator.ofFloat(ivdetail, "rotation", 180f, 0f);
                animator.setDuration(2000);
                animator.setRepeatCount(1);
                animator.start();
            }
        });
        isPrint = (boolean) SpUtils.get(this, AppConstant.Receipt.isPrint, false);//获取打印开关状态
    }

    private void setupWindowAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_right_to_center);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);
        root.setLayoutAnimation(controller);
        root.startLayoutAnimation();
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


    @OnClick({R.id.start_scan, R.id.start_takeorder, R.id.toolbar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_scan:
                startScanActivity();
                break;
            case R.id.start_takeorder:
                mPresenter.orderTake(id);
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    /**
     * descirption: 开始扫描二维码
     */
    private void startScanActivity() {
        //Intent intent = new Intent(ScanTakeFoodActivity.this, CaptureActivity2.class);
        //intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);

        Intent intent = new Intent(ScanTakeFoodActivity.this, ZXingScanActivity.class);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }

    /**
     * descirption: 识别二维码的取餐码，并查询订餐信息
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCAN_REQUEST_CODE) {
                //String isbn = data.getStringExtra("CaptureIsbn");
                String isbn = data.getStringExtra("qrcode");
                if (!TextUtils.isEmpty(isbn)) {
                    Log.d("qr", "onActivityResult: " + isbn);
                    mPresenter.orderGet(isbn);
                }
            }
        }
    }

    /**
     * descirption: 查询到了订餐信息
     */
    @Override
    public void onOrderGet(OrderDetailGet get) {
        id = get.getContent().getId();
        orderId.setText(get.getContent().getId());
        int state = get.getContent().getState();
        if (state == -3) {
            tvState.setText("退款中");
            startTakeorder.setEnabled(false);
        } else if (state == -2) {
            tvState.setText("已过期");
            startTakeorder.setEnabled(false);
        } else if (state == -1) {
            tvState.setText("已取消");
            startTakeorder.setEnabled(false);
        } else if (state == 0) {
            tvState.setText("预创建");
            startTakeorder.setEnabled(false);
        } else if (state == 1) {
            tvState.setText("已订餐");
            startTakeorder.setEnabled(true);
        } else if (state == 2) {
            tvState.setText("已完成");
            startTakeorder.setEnabled(false);
        }
        AheadAmount.setText(get.getContent().getAheadAmount() + "元");
        Amount.setText(get.getContent().getAmount() + "元");
        CreateTime.setText(get.getContent().getCreateTime());
        TakeDate.setText(get.getContent().getTakeStartTime() + "-" + get.getContent().getTakeEndTime());
        if (get.getContent().getOrderType() == 2) {
            orderType.setText("商品");
            mPresenter.orderGoodsListGet(get.getContent().getId());
            ivdetail.setVisibility(View.VISIBLE);
        } else {
            orderType.setText("定额");
            ivdetail.setVisibility(View.GONE);
        }
    }

    List<OrderGoodsDetailList.ContentBean> contentBeans;

    @Override
    public void onOrderGoodsDetailList(OrderGoodsDetailList list) {
        contentBeans = new ArrayList<>();
        if (contentBeans.size() > 0) {
            contentBeans.clear();
        }
        contentBeans.addAll(list.getContent());
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        scanTakeFoodRv.setLayoutManager(manager);
        scanTakeFoodRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new ScanTakeGoodsRvAdapter(this, contentBeans);
        scanTakeFoodRv.setAdapter(adapter);
        expandView.resetViewDimensions();
    }

    StringBuilder printer = new StringBuilder();

    @Override
    public void onOrderTake(OrderTake take) {
        if (isPrint) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            printer.append("\n操作模式： 扫码取餐");
            printer.append("\n订单编号: " + take.getContent().getId());
            printer.append("\n用户编号: " + take.getContent().getUserId());
            printer.append("\n");
            String name;
            for (int i = 0; i < contentBeans.size(); i++) {
                name = contentBeans.get(i).getGoods().getGoodsName();
                if (name.length() < 6) {
                    int d = 6 - name.length();
                    while (d >= 1) {
                        name = name + "  ";
                        d--;
                    }
                }
                printer.append("\n" + name + "    " + String.format("%.2f", contentBeans.get(i).getGoods().getPrice()) + "        x" + contentBeans.get(i).getOrderGoodsDetail().getQuantity());
            }
            printer.append("\n");
            printer.append(String.format("\n预扣款: %.2f", take.getContent().getAheadAmount()));
            printer.append(String.format("\n金额: %.2f", take.getContent().getAmount()));
            printer.append(String.format("\n折扣: %s", take.getContent().getDiscountRate()));
            printer.append("\n" + str);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PrinterUtils.getInstance(this).printLianxi(printer);
            } else {
                BlackSetPrinterUtils.getInstance(this).printLianxi(printer);
            }
            printer.setLength(0);
        }
        mPresenter.orderGet(take.getContent().getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PrinterUtils.getInstance(this).close();
        }
    }
}
