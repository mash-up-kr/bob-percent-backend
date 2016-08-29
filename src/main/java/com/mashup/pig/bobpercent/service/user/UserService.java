package com.mashup.pig.bobpercent.service.user;

import spark.Response;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public interface UserService {

    String signUp(String name, String email, String password, Response response);


    String login(String email, String password, Response response);


    String genCode(int userId, Response response);


    String matchUser(int userId, String code, Response response);


    String getUser(int userId, Response response);

}
