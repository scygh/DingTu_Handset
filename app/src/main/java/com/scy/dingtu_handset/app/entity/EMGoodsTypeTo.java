package com.scy.dingtu_handset.app.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

public class EMGoodsTypeTo implements Serializable, IPickerViewData {


    /**
     * Id : 667306bd-1ada-460a-b2f9-1766ced7829c
     * Name : 7777
     * Order : 7777
     * ParentId : null
     * Description : 7777
     * State : 1
     */

    private String Id;
    private String Name;
    private int Order;
    private String ParentId;
    private String Description;
    private int State;

    @Override public String getPickerViewText() {
        return Name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int Order) {
        this.Order = Order;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }
}
