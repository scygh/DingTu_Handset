package com.scy.dingtu_handset.app.entity;

import java.io.Serializable;

public class RegisterParam implements Serializable {

    /**
     * DepartmentId : string
     * Name : string
     * Deadline : 2021-01-13T06:19:15.583Z
     * SerialNo : string
     * Number : 0
     * CardType : 0
     * Leve : 0
     * Phone : string
     * EmpID : string
     * IDCard : string
     * Address : string
     * Password : string
     * PayKey : string
     * Cost : 0
     * Sex : 0
     * Age : 0
     * State : 0
     * Amount : 0
     * DonateAmount : 0
     * IsGotCard : true
     */

    private String DepartmentId;
    private String Name;
    private String Deadline;
    private String SerialNo;
    private int Number;
    private int CardType;
    private int Leve;
    private String Phone;
    private String EmpID;
    private String IDCard;
    private String Address;
    private String Password;
    private String PayKey;
    private double Cost;
    private int Sex;
    private int Age;
    private int State;
    private double Amount;
    private double DonateAmount;
    private boolean IsGotCard;

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String Deadline) {
        this.Deadline = Deadline;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public int getCardType() {
        return CardType;
    }

    public void setCardType(int CardType) {
        this.CardType = CardType;
    }

    public int getLeve() {
        return Leve;
    }

    public void setLeve(int Leve) {
        this.Leve = Leve;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String EmpID) {
        this.EmpID = EmpID;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPayKey() {
        return PayKey;
    }

    public void setPayKey(String PayKey) {
        this.PayKey = PayKey;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getDonateAmount() {
        return DonateAmount;
    }

    public void setDonateAmount(double DonateAmount) {
        this.DonateAmount = DonateAmount;
    }

    public boolean isIsGotCard() {
        return IsGotCard;
    }

    public void setIsGotCard(boolean IsGotCard) {
        this.IsGotCard = IsGotCard;
    }
}
