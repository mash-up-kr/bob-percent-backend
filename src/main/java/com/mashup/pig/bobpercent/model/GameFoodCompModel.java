package com.mashup.pig.bobpercent.model;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class GameFoodCompModel {

    private int gameId;
    private int foodCompId;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getFoodCompId() {
        return foodCompId;
    }

    public void setFoodCompId(int foodCompId) {
        this.foodCompId = foodCompId;
    }

    @Override
    public String toString() {
        return "GameFoodCompModel{" +
                "gameId=" + gameId +
                ", foodCompId=" + foodCompId +
                '}';
    }
}
