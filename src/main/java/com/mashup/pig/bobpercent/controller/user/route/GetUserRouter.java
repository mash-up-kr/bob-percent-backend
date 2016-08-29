package com.mashup.pig.bobpercent.controller.user.route;

import com.mashup.pig.bobpercent.controller.user.UserController;
import com.mashup.pig.bobpercent.service.user.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by bigstark on 2016. 8. 28..
 */
public class GetUserRouter implements Route {

    private UserService userService;

    public GetUserRouter(UserService userService) {
        this.userService = userService;
    }

    public Object handle(Request request, Response response) throws Exception {
        String userId = request.params(UserController.KEY_PATH_USERID);
        return userService.getUser(Integer.valueOf(userId), response);
    }
}
