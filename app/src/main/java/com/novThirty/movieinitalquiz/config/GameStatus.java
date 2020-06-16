package com.novThirty.movieinitalquiz.config;

import com.novThirty.movieinitalquiz.model.User;

public class GameStatus {
    public static User user;
    public static int numOfstepPerStage = 5; // 스테이지 당 스템의 개수

    public static int movScriptHintPoint = 10;
    public static int movActorHintPoint = 15;
    public static int movImgHintPoint = 20;
    public static int movNameHintPoint = 50;

    public static int movAnswerPoint = 100;
    public static int movRewardPoint = 1000;
}
