package com.scy.dingtu_handset.app.entity;

import java.util.List;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2021/1/11 14:07
 */
public class CodeReadResponse {

    /**
     * User : {"Id":"string","DepartmentId":"string","Name":"string","EmpId":"string","IdCard":"string","Sex":0,"Age":0,"Address":"string","Phone":"string","CreateTime":"2021-01-11T06:06:29.967Z","State":0,"Password":"string","Photo":"string","PayKey":"string"}
     * Finances : [{"UserId":"string","Kind":0,"Balance":0}]
     * Card : {"Id":"string","UserId":"string","Number":0,"SerialNo":"string","Type":0,"Level":0,"Cost":0,"Deadline":"2021-01-11T06:06:29.967Z","PayCount":0,"LastSubsidyDate":"2021-01-11T06:06:29.967Z","CreateTime":"2021-01-11T06:06:29.967Z","IsGot":true,"State":0,"LastPayDateTime":"2021-01-11T06:06:29.967Z"}
     * State : 0
     * NextPayCount : 0
     * CodeType : 0
     */

    private UserBean User;
    private CardBean Card;
    private int State;
    private int NextPayCount;
    private int CodeType;
    private List<FinancesBean> Finances;

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean User) {
        this.User = User;
    }

    public CardBean getCard() {
        return Card;
    }

    public void setCard(CardBean Card) {
        this.Card = Card;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getNextPayCount() {
        return NextPayCount;
    }

    public void setNextPayCount(int NextPayCount) {
        this.NextPayCount = NextPayCount;
    }

    public int getCodeType() {
        return CodeType;
    }

    public void setCodeType(int CodeType) {
        this.CodeType = CodeType;
    }

    public List<FinancesBean> getFinances() {
        return Finances;
    }

    public void setFinances(List<FinancesBean> Finances) {
        this.Finances = Finances;
    }

    public static class UserBean {
        /**
         * Id : string
         * DepartmentId : string
         * Name : string
         * EmpId : string
         * IdCard : string
         * Sex : 0
         * Age : 0
         * Address : string
         * Phone : string
         * CreateTime : 2021-01-11T06:06:29.967Z
         * State : 0
         * Password : string
         * Photo : string
         * PayKey : string
         */

        private String Id;
        private String DepartmentId;
        private String Name;
        private String EmpId;
        private String IdCard;
        private int Sex;
        private int Age;
        private String Address;
        private String Phone;
        private String CreateTime;
        private int State;
        private String Password;
        private String Photo;
        private String PayKey;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

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

        public String getEmpId() {
            return EmpId;
        }

        public void setEmpId(String EmpId) {
            this.EmpId = EmpId;
        }

        public String getIdCard() {
            return IdCard;
        }

        public void setIdCard(String IdCard) {
            this.IdCard = IdCard;
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

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

        public String getPayKey() {
            return PayKey;
        }

        public void setPayKey(String PayKey) {
            this.PayKey = PayKey;
        }
    }

    public static class CardBean {
        /**
         * Id : string
         * UserId : string
         * Number : 0
         * SerialNo : string
         * Type : 0
         * Level : 0
         * Cost : 0
         * Deadline : 2021-01-11T06:06:29.967Z
         * PayCount : 0
         * LastSubsidyDate : 2021-01-11T06:06:29.967Z
         * CreateTime : 2021-01-11T06:06:29.967Z
         * IsGot : true
         * State : 0
         * LastPayDateTime : 2021-01-11T06:06:29.967Z
         */

        private String Id;
        private String UserId;
        private int Number;
        private String SerialNo;
        private int Type;
        private int Level;
        private double Cost;
        private String Deadline;
        private int PayCount;
        private String LastSubsidyDate;
        private String CreateTime;
        private boolean IsGot;
        private int State;
        private String LastPayDateTime;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int Number) {
            this.Number = Number;
        }

        public String getSerialNo() {
            return SerialNo;
        }

        public void setSerialNo(String SerialNo) {
            this.SerialNo = SerialNo;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }

        public double getCost() {
            return Cost;
        }

        public void setCost(double Cost) {
            this.Cost = Cost;
        }

        public String getDeadline() {
            return Deadline;
        }

        public void setDeadline(String Deadline) {
            this.Deadline = Deadline;
        }

        public int getPayCount() {
            return PayCount;
        }

        public void setPayCount(int PayCount) {
            this.PayCount = PayCount;
        }

        public String getLastSubsidyDate() {
            return LastSubsidyDate;
        }

        public void setLastSubsidyDate(String LastSubsidyDate) {
            this.LastSubsidyDate = LastSubsidyDate;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public boolean isIsGot() {
            return IsGot;
        }

        public void setIsGot(boolean IsGot) {
            this.IsGot = IsGot;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getLastPayDateTime() {
            return LastPayDateTime;
        }

        public void setLastPayDateTime(String LastPayDateTime) {
            this.LastPayDateTime = LastPayDateTime;
        }
    }

    public static class FinancesBean {
        /**
         * UserId : string
         * Kind : 0
         * Balance : 0
         */

        private String UserId;
        private int Kind;
        private double Balance;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public int getKind() {
            return Kind;
        }

        public void setKind(int Kind) {
            this.Kind = Kind;
        }

        public double getBalance() {
            return Balance;
        }

        public void setBalance(double Balance) {
            this.Balance = Balance;
        }
    }
}
