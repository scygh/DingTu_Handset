package com.scy.dingtu_handset.app.entity;

import java.io.Serializable;
import java.util.List;

public class SimpleExpenseParam implements Serializable {

    /**
     * Amount : 0
     * Pattern : 1
     * PayCount : 0
     * PayKey : string
     * GoodsDetails : [{"Id":"string","Eid":"string","GoodsNo":0,"OrderNo":0,"GoodsName":"string","Price":0,"Amount":0,"Count":0,"CreateTime":"2021-01-08T02:15:07.948Z"}]
     * Number : 0
     */

    private double Amount;
    private int Pattern;
    private int PayCount;
    private String PayKey;
    private int Number;
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

    public List<GoodsDetailsBean> getGoodsDetails() {
        return GoodsDetails;
    }

    public void setGoodsDetails(List<GoodsDetailsBean> GoodsDetails) {
        this.GoodsDetails = GoodsDetails;
    }

    public static class GoodsDetailsBean {
        /**
         * Id : string
         * Eid : string
         * GoodsNo : 0
         * OrderNo : 0
         * GoodsName : string
         * Price : 0
         * Amount : 0
         * Count : 0
         * CreateTime : 2021-01-08T02:15:07.948Z
         */


        private int GoodsNo;
        private int Count;


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

    }
}
