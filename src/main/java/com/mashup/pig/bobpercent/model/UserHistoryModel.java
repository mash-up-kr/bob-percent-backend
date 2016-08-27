package com.mashup.pig.bobpercent.model;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class UserHistoryModel {

    private int userId;
    private int foodCompId;
    private int foodId;
    private int gameId;

    public int getUserId() {
        return userId;
    }

    public int getFoodCompId() {
        return foodCompId;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "UserHistoryModel{" +
                "userId=" + userId +
                ", foodCompId=" + foodCompId +
                ", foodId=" + foodId +
                ", gameId=" + gameId +
                '}';
    }
}
