package com.novThirty.movieinitalquiz.model;

public class GameInfo {
    private String userCode;
    private int doneMovNum;
    private int doneStage;
    private int currMovNum;
    private int point;
    private int movNum;
    private String movName;
    private String stage;
    private String step;
    private String movScript;
    private String movActor;
    private String movImgPath;

    public GameInfo(String userCode, int doneMovNum, int currMovNum, int point, int movNum,
                    String movName, String stage, String movActor, String movScript, String movImgPath){
        this.userCode = userCode;
        this.doneMovNum = doneMovNum;
        this.currMovNum = currMovNum;
        this.point = point;
        this.movNum = movNum;
        this.movName = movName;
        this.stage = stage;
        this.step = step;
        this.movScript = movScript;
        this.movImgPath = movImgPath;
    }

    public GameInfo(int movNum, String movName, String stage, String step, String movScript, String movActor, String movImgPath){
        this.movNum = movNum;
        this.movName = movName;
        this.stage = stage;
        this.step = step;
        this.movScript = movScript;
        this.movActor = movActor;
        this.movImgPath = movImgPath;
    }

    public GameInfo(String userCode, int doneMovNum, int currMovNum, int point){
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

    public int getMovNum() {
        return movNum;
    }

    public void setMovNum(int movNum) {
        this.movNum = movNum;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMovScript() {
        return movScript;
    }

    public void setMovScript(String movScript) {
        this.movScript = movScript;
    }

    public String getMovActor() {
        return movActor;
    }

    public void setMovActor(String movActor) {
        this.movActor = movActor;
    }

    public String getMovImgPath() {
        return movImgPath;
    }

    public void setMovImgPath(String movImgPath) {
        this.movImgPath = movImgPath;
    }
}
