package com.example.loginview;

public class FamilyInfo {

    private String spouce;
    private String dobSpouce;
    private String child1;
    private String dobChild1;
    private String child2;
    private String dobChild2;
    private String child3;
    private String dobChild3;

    public FamilyInfo(String spouce, String dobSpouce, String child1, String dobChild1, String child2, String dobChild2, String child3, String dobChild3) {
        this.spouce = spouce;
        this.dobSpouce = dobSpouce;
        this.child1 = child1;
        this.dobChild1 = dobChild1;
        this.child2 = child2;
        this.dobChild2 = dobChild2;
        this.child3 = child3;
        this.dobChild3 = dobChild3;
    }

    public String getSpouce() {
        return spouce;
    }

    public void setSpouce(String spouce) {
        this.spouce = spouce;
    }

    public String getDobSpouce() {
        return dobSpouce;
    }

    public void setDobSpouce(String dobSpouce) {
        this.dobSpouce = dobSpouce;
    }

    public String getChild1() {
        return child1;
    }

    public void setChild1(String child1) {
        this.child1 = child1;
    }

    public String getDobChild1() {
        return dobChild1;
    }

    public void setDobChild1(String dobChild1) {
        this.dobChild1 = dobChild1;
    }

    public String getChild2() {
        return child2;
    }

    public void setChild2(String child2) {
        this.child2 = child2;
    }

    public String getDobChild2() {
        return dobChild2;
    }

    public void setDobChild2(String dobChild2) {
        this.dobChild2 = dobChild2;
    }

    public String getChild3() {
        return child3;
    }

    public void setChild3(String child3) {
        this.child3 = child3;
    }

    public String getDobChild3() {
        return dobChild3;
    }

    public void setDobChild3(String dobChild3) {
        this.dobChild3 = dobChild3;
    }



}
