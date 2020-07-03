package com.scy.dingtu_handset.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class QRExpenseParam implements Serializable {

    /**
     * QRContent : 134782713409480847
     * ListGoods : [{"GoodsNo":0,"Count":0}]
     * Number : 0
     * Amount : 0.01
     * Pattern : 1
     * PayCount : 0
     * PayKey : string
     * DeviceID : 1
     * DeviceType : 2
     * QRType : 1
     */

    private String QRContent;
    private int Number;
    private double Amount;
    private int Pattern;
    private int PayCount;
    private String PayKey;
    private int DeviceID;
    private int DeviceType;
    private int QRType;
    private List<ListGoodsBean> ListGoods;

    public String getQRContent() {
        return QRContent;
    }

    public void setQRContent(String QRContent) {
        this.QRContent = QRContent;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

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

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int DeviceID) {
        this.DeviceID = DeviceID;
    }

    public int getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(int DeviceType) {
        this.DeviceType = DeviceType;
    }

    public int getQRType() {
        return QRType;
    }

    public void setQRType(int QRType) {
        this.QRType = QRType;
    }

    public List<ListGoodsBean> getListGoods() {
        return ListGoods;
    }

    public void setListGoods(List<ListGoodsBean> ListGoods) {
        this.ListGoods = ListGoods;
    }

    public static class ListGoodsBean implements Parcelable {
        /**
         * GoodsNo : 0
         * Count : 0
         */

        private int GoodsNo;
        private int Count;



        public ListGoodsBean(int goodsNo, int count) {
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

        protected ListGoodsBean(Parcel in) {
            this.GoodsNo = in.readInt();
            this.Count = in.readInt();
        }

        public static final Parcelable.Creator<ListGoodsBean> CREATOR = new Parcelable.Creator<ListGoodsBean>() {
            @Override
            public ListGoodsBean createFromParcel(Parcel source) {
                return new ListGoodsBean(source);
            }

            @Override
            public ListGoodsBean[] newArray(int size) {
                return new ListGoodsBean[size];
            }
        };
    }
}
