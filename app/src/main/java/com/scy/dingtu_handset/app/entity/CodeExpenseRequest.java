package com.scy.dingtu_handset.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2021/1/12 16:51
 */
public class CodeExpenseRequest {


    /**
     * Amount : 0
     * Pattern : 1
     * PayCount : 0
     * PayKey : string
     * GoodsDetails : [{"Id":"string","Eid":"string","GoodsNo":0,"OrderNo":0,"GoodsName":"string","Price":0,"Amount":0,"Count":0,"CreateTime":"2021-01-12T08:49:49.588Z"}]
     * Number : 0
     * CodeContent : string
     */

    private double Amount;
    private int Pattern;
    private int PayCount;
    private String PayKey;
    private int Number;
    private String CodeContent;
    private List<GoodsDetailsBean> GoodsDetails;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public int getPattern() {
        return Pattern;
    }

    public void setPattern(int Pattern) {
        this.Pattern = Pattern;
    }

    public int getPayCount() {
        return PayCount;
    }

    public void setPayCount(int PayCount) {
        this.PayCount = PayCount;
    }

    public String getPayKey() {
        return PayKey;
    }

    public void setPayKey(String PayKey) {
        this.PayKey = PayKey;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public String getCodeContent() {
        return CodeContent;
    }

    public void setCodeContent(String CodeContent) {
        this.CodeContent = CodeContent;
    }

    public List<GoodsDetailsBean> getGoodsDetails() {
        return GoodsDetails;
    }

    public void setGoodsDetails(List<GoodsDetailsBean> GoodsDetails) {
        this.GoodsDetails = GoodsDetails;
    }

    public static class GoodsDetailsBean implements Parcelable {

        private int GoodsNo;
        private int Count;



        public GoodsDetailsBean(int goodsNo, int count) {
            GoodsNo = goodsNo;
            Count = count;
        }

        public int getGoodsNo() {
            return GoodsNo;
        }

        public void setGoodsNo(int GoodsNo) {
            this.GoodsNo = GoodsNo;
        }


        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.GoodsNo);
            dest.writeInt(this.Count);
        }

        protected GoodsDetailsBean(Parcel in) {
            this.GoodsNo = in.readInt();
            this.Count = in.readInt();
        }

        public static final Parcelable.Creator<GoodsDetailsBean> CREATOR = new Parcelable.Creator<GoodsDetailsBean>() {
            @Override
            public GoodsDetailsBean createFromParcel(Parcel source) {
                return new GoodsDetailsBean(source);
            }

            @Override
            public GoodsDetailsBean[] newArray(int size) {
                return new GoodsDetailsBean[size];
            }
        };
    }

}
