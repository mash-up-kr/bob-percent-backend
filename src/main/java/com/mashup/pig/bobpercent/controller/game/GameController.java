package com.mashup.pig.bobpercent.controller.game;

import com.mashup.pig.bobpercent.controller.BaseController;
import com.mashup.pig.bobpercent.controller.game.route.GetResultRouter;
import com.mashup.pig.bobpercent.controller.game.route.JoinRouter;
import com.mashup.pig.bobpercent.controller.game.route.SaveResultRouter;
import com.mashup.pig.bobpercent.service.game.GameService;
import com.mashup.pig.bobpercent.service.game.GameServiceImpl;
import spark.Spark;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class GameController extends BaseController {

    private static final String PATH_JOIN = "/game/join/:userId";
    private static final String PATH_SAVE_RESULT = "/game/:gameId/user/:userId";
    private static final String PATH_GET_RESULT = "/game/:gameId/user/:userId";


    public static final String KEY_USER_ID = ":userId";
    public static final String KEY_GAME_ID = ":gameId";


    private JoinRouter joinRouter;
    private SaveResultRouter saveResultRouter;
    private GetResultRouter getResultRouter;


    public GameController() {
        GameService gameService = new GameServiceImpl();

        joinRouter = new JoinRouter(gameService);
        saveResultRouter = new SaveResultRouter(gameService);
        getResultRouter = new GetResultRouter(gameService);
    }


    public void start() {
        Spark.post(PATH_JOIN, joinRouter);
        Spark.post(PATH_SAVE_RESULT, saveResultRouter);
        Spark.get(PATH_GET_RESULT, getResultRouter);
    }
}
