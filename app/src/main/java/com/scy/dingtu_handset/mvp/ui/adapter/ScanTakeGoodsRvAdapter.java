package com.scy.dingtu_handset.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.entity.OrderGoodsDetailList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description ：NetSettingRvAdapter
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/7/29 08:46
 */
public class ScanTakeGoodsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<OrderGoodsDetailList.ContentBean> rowsBeans;
    private OnMyItemClickListener myItemClickListener;

    public interface OnMyItemClickListener {
        void onItemClick(String id, String name, int positon);
    }

    public void setMyItemClickListener(OnMyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public ScanTakeGoodsRvAdapter(Context context, List<OrderGoodsDetailList.ContentBean> rowsBeans) {
        this.context = context;
        this.rowsBeans = rowsBeans;
    }

    private int selectPosition;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.num)
        TextView num;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myItemClickListener != null) {

                    }
                }
            });


        }

        public void bind() {
            name.setText(rowsBeans.get(getAdapterPosition()).getGoods().getGoodsName());
            price.setText(rowsBeans.get(getAdapterPosition()).getGoods().getPrice() + "元");
            num.setText("X" + rowsBeans.get(getAdapterPosition()).getOrderGoodsDetail().getQuantity());
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.scan_take_food_rvitem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind();
    }

    @Override
    public int getItemCount() {
        return rowsBeans != null ? rowsBeans.size() : 0;
    }

}
