package com.scy.dingtu_handset.app.entity;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2021/1/11 10:03
 */
public class DeviceReadCardRequest {


    /**
     * Number : 0
     */

    private int Number;

    public DeviceReadCardRequest(int number) {
        Number = number;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }
}
