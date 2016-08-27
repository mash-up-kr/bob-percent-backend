package com.mashup.pig.bobpercent.controller.game.route;

import com.mashup.pig.bobpercent.controller.game.GameController;
import com.mashup.pig.bobpercent.service.game.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class GetResultRouter implements Route {

    private GameService gameService;

    public GetResultRouter(GameService gameService) {
        this.gameService = gameService;
    }

    public Object handle(Request request, Response response) throws Exception {
        String userId = request.params(GameController.KEY_USER_ID);
        String gameId = request.params(GameController.KEY_GAME_ID);
        return gameService.getResult(Integer.valueOf(userId), Integer.valueOf(gameId), response);
    }
}
