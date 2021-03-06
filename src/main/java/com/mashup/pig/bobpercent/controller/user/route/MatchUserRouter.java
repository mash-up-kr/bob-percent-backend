package com.mashup.pig.bobpercent.controller.user.route;

import com.mashup.pig.bobpercent.controller.user.UserController;
import com.mashup.pig.bobpercent.service.user.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class MatchUserRouter implements Route {

    private UserService userService;

    public MatchUserRouter(UserService userService) {
        this.userService = userService;
    }

    public Object handle(Request request, Response response) throws Exception {
        String userId = request.params(UserController.KEY_PATH_USERID);
        String code = request.params(UserController.KEY_PATH_CODE);
        return userService.matchUser(Integer.valueOf(userId), code, response);
    }
}
