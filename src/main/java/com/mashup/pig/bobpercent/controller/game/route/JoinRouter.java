package com.mashup.pig.bobpercent.controller.game.route;

import com.mashup.pig.bobpercent.controller.game.GameController;
import com.mashup.pig.bobpercent.db.UserDBHelper;
import com.mashup.pig.bobpercent.model.UserModel;
import com.mashup.pig.bobpercent.service.game.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class JoinRouter implements Route {

    private GameService gameService;

    public JoinRouter(GameService gameService) {
        this.gameService = gameService;
    }

    public Object handle(Request request, Response response) throws Exception {
        String userId = request.params(GameController.KEY_USER_ID);

        UserModel user = UserDBHelper.getInstance().select(Integer.valueOf(userId));
        return gameService.join(user.getUserId(), user.getCode(), response);
    }
}
