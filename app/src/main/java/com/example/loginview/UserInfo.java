package com.example.loginview;

public class UserInfo {

    private String fatherName;
    private String gotra;
    private String mobileNo2;
    private String landLineNo1;
    private String landLineNo2;
    private String add1;
    private String add2;
    private String add3;
    private String city;
    private String pin;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGotra() {
        return gotra;
    }

    public void setGotra(String gotra) {
        this.gotra = gotra;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }

    public String getLandLineNo1() {
        return landLineNo1;
    }

    public void setLandLineNo1(String landLineNo1) {
        this.landLineNo1 = landLineNo1;
    }

    public String getLandLineNo2() {
        return landLineNo2;
    }

    public void setLandLineNo2(String landLineNo2) {
        this.landLineNo2 = landLineNo2;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd3() {
        return add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    private String state;
    private String bloodGroup;
    private String dob;
    private String dom;
    private int regId;

    public UserInfo(String fatherName, String gotra, String mobileNo2, String landLineNo1, String landLineNo2, String add1, String add2, String add3, String city, String pin, String state, String bloodGroup, String dob, String dom) {
        this.fatherName = fatherName;
        this.gotra = gotra;
        this.mobileNo2 = mobileNo2;
        this.landLineNo1 = landLineNo1;
        this.landLineNo2 = landLineNo2;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.city = city;
        this.pin = pin;
        this.state = state;
        this.bloodGroup = bloodGroup;
        this.dob = dob;
        this.dom = dom;
    }
}
