package com.mashup.pig.bobpercent.service.game;

import com.mashup.pig.bobpercent.GsonLoader;
import com.mashup.pig.bobpercent.db.FoodDBHelper;
import com.mashup.pig.bobpercent.db.GameDBHelper;
import com.mashup.pig.bobpercent.db.UserDBHelper;
import com.mashup.pig.bobpercent.model.*;
import spark.Response;

import java.util.*;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class GameServiceImpl implements GameService {

    private Map<String, Boolean> joinRequestHashMap = new HashMap<String, Boolean>();
    private Map<String, GameModel> gameHashMap = new HashMap<String, GameModel>();


    public String join(int userId, String code, Response response) {
        response.type("application/json");
        response.status(200);

        UserModel user = UserDBHelper.getInstance().select(userId);
        if (user.isPending()) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }


        if (joinRequestHashMap.get(code) == null || !joinRequestHashMap.get(code)) {
            joinRequestHashMap.put(code, true);
            int cycle = 0;
            while (true) {

                if (cycle == 50) {
                    joinRequestHashMap.remove(code);
                    ErrorModel error = new ErrorModel("Bad Request");
                    response.type("application/json");
                    response.status(400);
                    return GsonLoader.toJson(error);
                }

                if (gameHashMap.get(code) != null)
                    break;

                try {
                    cycle++;
                    Thread.sleep(100);
                } catch (Exception e) {}
            }



            GameModel game = gameHashMap.get(code);
            gameHashMap.remove(code);
            return GsonLoader.toJson(game);
        }

        joinRequestHashMap.remove(code);
        GameModel game = GameDBHelper.getInstance().insert(code);

        // set comp models
        List<FoodCompModel> compModels = FoodDBHelper.getInstance().getAllFoodComps();
        if (compModels.size() <= 15) {
            game.setComps(compModels);
        } else {
            int compSize = compModels.size();
            Set<Integer> indexSet = new HashSet<Integer>();
            Random rand = new Random();
            while (true) {
                if (indexSet.size() == 15) {
                    break;
                }

                indexSet.add(Math.abs(rand.nextInt()) % compSize);
            }

            List<FoodCompModel> tempCompList = new ArrayList<FoodCompModel>(15);
            for (Integer index : indexSet) {
                tempCompList.add(compModels.get(index));
            }
            game.setComps(tempCompList);
        }

        GameDBHelper.getInstance().saveGameFoodComps(game);

        gameHashMap.put(code, game);
        return GsonLoader.toJson(game);
    }


    public String saveResult(int userId, int gameId, List<FoodModel> foods, Response response) {
        response.type("application/json");
        response.status(204);

        UserModel user = UserDBHelper.getInstance().select(userId);
        if (user == null || user.isPending()) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        GameModel game = GameDBHelper.getInstance().select(gameId);
        if (game == null) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }


        GameDBHelper.getInstance().saveHistory(user, game, foods);
        return "";
    }


    public String getResult(int userId, int gameId, Response response) {
        response.type("application/json");
        response.status(200);

        UserModel user = UserDBHelper.getInstance().select(userId);
        if (user == null || user.isPending()) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        GameModel game = GameDBHelper.getInstance().select(gameId);
        if (game == null) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        List<UserHistoryModel> histories = GameDBHelper.getInstance().getHistory(game.getGameId());
        if (histories.size() == 0) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        HashMap<Integer, Integer> checkPercentHashMap = new HashMap<Integer, Integer>();

        int totalCount = histories.size() / 2;
        int sameCount = 0;

        List<FoodModel> foods = new ArrayList<FoodModel>();

        for (UserHistoryModel history : histories) {
            if (history.getUserId() == userId) {
                FoodModel food = FoodDBHelper.getInstance().getFood(history.getFoodId());
                foods.add(food);
            }

            if (checkPercentHashMap.get(history.getFoodCompId()) == null) {
                checkPercentHashMap.put(history.getFoodCompId(), history.getFoodId());
                continue;
            }

            if (checkPercentHashMap.get(history.getFoodCompId()) == history.getFoodId()) {
                sameCount++;
            }
        }

        float percent = (float) sameCount / (float) totalCount;
        GameResultModel gameResultModel = new GameResultModel();
        gameResultModel.setPercent(percent);
        gameResultModel.setSelectFoodItems(foods);
        return GsonLoader.toJson(gameResultModel);
    }
}
