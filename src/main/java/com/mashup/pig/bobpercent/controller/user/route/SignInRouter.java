package com.mashup.pig.bobpercent.controller.user.route;

import com.mashup.pig.bobpercent.GsonLoader;
import com.mashup.pig.bobpercent.model.UserModel;
import com.mashup.pig.bobpercent.service.user.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class SignInRouter implements Route {

    private UserService userService;

    public SignInRouter(UserService userService) {
        this.userService = userService;
    }

    public Object handle(Request request, Response response) throws Exception {
        UserModel user = GsonLoader.fromJson(request.body(), UserModel.class);
        return userService.login(user.getEmail(), user.getPassword(), response);
    }
}
