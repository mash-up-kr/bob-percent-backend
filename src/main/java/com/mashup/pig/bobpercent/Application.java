package com.mashup.pig.bobpercent;

import com.mashup.pig.bobpercent.controller.user.UserController;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class Application {

    public static void main(String[] args) {
        startControllers();
    }

    private static void startControllers() {
        new UserController().start();
    }


}
