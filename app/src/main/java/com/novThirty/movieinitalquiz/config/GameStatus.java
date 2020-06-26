package com.novThirty.movieinitalquiz.config;

import com.novThirty.movieinitalquiz.model.User;

public class GameStatus {
    public static User user;
    public static int numOfstepPerStage = 5; // 스테이지 당 스템의 개수

    public static int movScriptHintPoint = 250;
    public static int movActorHintPoint = 250;
    public static int movImgHintPoint = 250;
    public static int movNameHintPoint = 500;

    public static int movAnswerPoint = 50;
    public static int movRewardPoint = 150;

    public static int mainScrollPosition = 0;
}
