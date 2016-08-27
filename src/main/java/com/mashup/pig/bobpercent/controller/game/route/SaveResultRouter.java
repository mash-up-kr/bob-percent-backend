package com.mashup.pig.bobpercent.controller.game.route;

import com.google.gson.reflect.TypeToken;
import com.mashup.pig.bobpercent.GsonLoader;
import com.mashup.pig.bobpercent.controller.game.GameController;
import com.mashup.pig.bobpercent.model.FoodModel;
import com.mashup.pig.bobpercent.service.game.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class SaveResultRouter implements Route {

    private GameService gameService;

    public SaveResultRouter(GameService gameService) {
        this.gameService = gameService;
    }

    public Object handle(Request request, Response response) throws Exception {
        String userId = request.params(GameController.KEY_USER_ID);
        String gameId = request.params(GameController.KEY_GAME_ID);

        Type type = new TypeToken<ArrayList<FoodModel>>(){}.getType();
        List<FoodModel> foods = GsonLoader.getInstance().getGson().fromJson(response.body(), type);

        return gameService.saveResult(Integer.valueOf(userId), Integer.valueOf(gameId), foods, response);
    }
}
