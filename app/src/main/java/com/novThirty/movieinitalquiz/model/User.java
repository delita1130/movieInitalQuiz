package com.novThirty.movieinitalquiz.model;

public class User {
    private String userCode;
    private int doneMovNum;
    private int currMovNum;
    private int point;

    public User(String userCode, int doneMovNum, int currMovNum, int point) {
        this.userCode = userCode;
        this.doneMovNum = doneMovNum;
        this.currMovNum = currMovNum;
        this.point = point;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getDoneMovNum() {
        return doneMovNum;
    }

    public void setDoneMovNum(int doneMovNum) {
        this.doneMovNum = doneMovNum;
    }

    public int getCurrMovNum() {
        return currMovNum;
    }

    public void setCurrMovNum(int currMovNum) {
        this.currMovNum = currMovNum;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}