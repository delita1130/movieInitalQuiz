package com.novThirty.movieinitalquiz.model;

public class Movie {
    private int movNum;
    private String movName;
    private String stage;
    private String step;
    private String movScript;
    private String movActor;
    private String movImgPath;

    public Movie(int movNum, String movName, String stage, String step, String movScript, String movActor, String movImgPath){
        this.movNum = movNum;
        this.movName = movName;
        this.stage = stage;
        this.step = step;
        this.movScript = movScript;
        this.movActor = movActor;
        this.movImgPath = movImgPath;
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

    @Override
    public String toString() {
        return "Movie{" +
                "movNum=" + movNum +
                ", movName='" + movName + '\'' +
                ", stage='" + stage + '\'' +
                ", step='" + step + '\'' +
                ", movScript='" + movScript + '\'' +
                ", movActor='" + movActor + '\'' +
                ", movImgPath='" + movImgPath + '\'' +
                '}';
    }


}
