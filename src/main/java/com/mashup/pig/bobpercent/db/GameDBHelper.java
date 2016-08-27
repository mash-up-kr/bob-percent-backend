package com.mashup.pig.bobpercent.db;

import com.mashup.pig.bobpercent.model.*;
import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class GameDBHelper {

    private volatile static GameDBHelper instance;

    public static GameDBHelper getInstance() {
        if (instance == null) {
            synchronized (GameDBHelper.class) {
                if (instance == null) {
                    instance = new GameDBHelper();
                }
            }
        }

        return instance;
    }

    private static final String QUERY_INSERT_GAME = "INSERT INTO Game(code) VALUES(:code)";
    private static final String QUERY_SELECT_GAME = "SELECT * FROM Game WHERE code = :code ORDER BY createDate DESC";
    private static final String QUERY_SELECT_GAME_GAME_ID = "SELECT * FROM Game WHERE gameId = :gameId ORDER BY createDate DESC";

    private static final String QUERY_INSERT_GAME_FOOD_COMP = "INSERT INTO GameFoodComp(gameId, foodCompId) " +
                                                              "VALUES(:gameId, :foodCompId)";

    private static final String QUERY_INSERT_GAME_HISTORY = "INSERT INTO UserGameHistory(userId, code, foodCompId, foodId, gameId) " +
                                                            "VALUES(:userId, :code, :foodCompId, :foodId, :gameId)";

    private static final String QUERY_SELECT_GAME_HISTORY = "SELECT * FROM UserGameHistory WHERE gameId = :gameId";

    public GameModel insert(String code) {
        Connection conn = null;
        try {

            conn = DBHelper.getInstance().beginTransaction();
            conn.createQuery(QUERY_INSERT_GAME)
                    .addParameter("code", code)
                    .executeUpdate();
            conn.commit();
            conn.close();

            return select(code);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conn != null) {
            conn.close();
        }

        return null;
    }


    public GameModel select(String code) {
        Connection conn = null;

        try {
            conn = DBHelper.getInstance().open();
            GameModel game = conn.createQuery(QUERY_SELECT_GAME)
                    .addParameter("code", code)
                    .executeAndFetchFirst(GameModel.class);
            conn.close();

            return game;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return null;
    }


    public GameModel select(int gameId) {
        Connection conn = null;

        try {
            conn = DBHelper.getInstance().open();
            GameModel game = conn.createQuery(QUERY_SELECT_GAME_GAME_ID)
                    .addParameter("gameId", gameId)
                    .executeAndFetchFirst(GameModel.class);
            conn.close();

            return game;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return null;
    }


    public boolean saveGameFoodComps(GameModel game) {
        Connection conn = null;
        try {

            conn = DBHelper.getInstance().beginTransaction();

            List<FoodCompModel> comps = game.getComps();
            for (FoodCompModel comp : comps) {
                conn.createQuery(QUERY_INSERT_GAME_FOOD_COMP)
                        .addParameter("gameId", game.getGameId())
                        .addParameter("foodCompId", comp.getCompId())
                        .executeUpdate();
            }

            conn.commit();
            conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conn != null) {
            conn.close();
        }

        return false;
    }


    public boolean saveHistory(UserModel user, GameModel game, List<FoodModel> foods) {
        Connection conn = null;

        try {
            conn = DBHelper.getInstance().beginTransaction();

            List<FoodCompModel> comps = game.getComps();
            for (FoodCompModel comp : comps) {
                FoodModel food = null;

                for (FoodModel foodModel : foods) {
                    if (comp.getFirstFoodId() == foodModel.getFoodId()) {
                        food = comp.getFirstFood();
                        break;
                    }

                    if (comp.getSecondFoodId() == foodModel.getFoodId()) {
                        food = comp.getSecondFood();
                        break;
                    }
                }

                conn.createQuery(QUERY_INSERT_GAME_HISTORY)
                        .addParameter("userId", user.getUserId())
                        .addParameter("code", user.getCode())
                        .addParameter("foodCompId", comp.getCompId())
                        .addParameter("foodId", food.getFoodId())
                        .addParameter("gameId", game.getGameId())
                        .executeUpdate();
            }

            conn.commit();
            conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conn != null) {
            conn.close();
        }

        return false;
    }


    public List<UserHistoryModel> getHistory(int gameId) {
        Connection conn = null;

        try {
            conn = DBHelper.getInstance().open();
            List<UserHistoryModel> histories = conn.createQuery(QUERY_SELECT_GAME_HISTORY)
                    .addParameter("gameId", gameId)
                    .executeAndFetch(UserHistoryModel.class);
            conn.close();

            return histories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return new ArrayList<UserHistoryModel>(0);
    }

}
