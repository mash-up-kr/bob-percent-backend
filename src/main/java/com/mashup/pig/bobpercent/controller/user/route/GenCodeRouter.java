package com.mashup.pig.bobpercent.controller.user.route;

import com.mashup.pig.bobpercent.controller.user.UserController;
import com.mashup.pig.bobpercent.service.user.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class GenCodeRouter implements Route {

    private UserService userService;

    public GenCodeRouter(UserService userService) {
        this.userService = userService;
    }

    public Object handle(Request request, Response response) throws Exception {
        String userId = request.params(UserController.KEY_PATH_USERID);
        return userService.genCode(Integer.valueOf(userId), response);
    }
}
