package com.mashup.pig.bobpercent.controller.user;

import com.mashup.pig.bobpercent.controller.BaseController;
import com.mashup.pig.bobpercent.controller.user.route.GenCodeRouter;
import com.mashup.pig.bobpercent.controller.user.route.MatchUserRouter;
import com.mashup.pig.bobpercent.controller.user.route.SignInRouter;
import com.mashup.pig.bobpercent.controller.user.route.SignUpRouter;
import com.mashup.pig.bobpercent.service.user.UserService;
import com.mashup.pig.bobpercent.service.user.UserServiceImpl;
import spark.Spark;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class UserController extends BaseController {

    private static final String PATH_SIGN_UP = "/user";
    private static final String PATH_SIGN_IN = "/user/login";
    private static final String PATH_GEN_CODE = "/user/gen/:userId";
    private static final String PATH_MATCH_USER = "/user/match/:userId/:code";

    public static final String KEY_PATH_USERID = ":userId";
    public static final String KEY_PATH_CODE = ":code";

    private SignUpRouter signUpRouter;
    private SignInRouter signInRouter;
    private GenCodeRouter genCodeRouter;
    private MatchUserRouter matchUserRouter;


    public UserController() {
        UserService userService = new UserServiceImpl();

        signUpRouter = new SignUpRouter(userService);
        signInRouter = new SignInRouter(userService);
        genCodeRouter = new GenCodeRouter(userService);
        matchUserRouter = new MatchUserRouter(userService);
    }

    public void start() {
        Spark.post(PATH_SIGN_UP, signUpRouter);
        Spark.post(PATH_SIGN_IN, signInRouter);
        Spark.post(PATH_GEN_CODE, genCodeRouter);
        Spark.post(PATH_MATCH_USER, matchUserRouter);
    }
}
