package com.mashup.pig.bobpercent.service.user;

import com.mashup.pig.bobpercent.GsonLoader;
import com.mashup.pig.bobpercent.db.UserDBHelper;
import com.mashup.pig.bobpercent.model.ErrorModel;
import com.mashup.pig.bobpercent.model.UserModel;
import spark.Response;

import java.security.SecureRandom;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class UserServiceImpl implements UserService {

    private static final short CODE_LENGTH = 4;
    private static final char[] CODE_CHARACTERS = {
            'a','b','c', 'd','e','f', 'g','h','i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C', 'D','E','F', 'G','H','I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            '0','1','2','3','4','5','6','7','8','9'
    };


    public String signUp(String name, String email, String password, Response response) {
        response.type("application/json");
        response.status(200);

        UserModel user = UserDBHelper.getInstance().insert(name, email, password);
        if (user == null) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        user.setPassword(null);
        return GsonLoader.toJson(user);
    }

    public String login(String email, String password, Response response) {
        response.type("application/json");
        response.status(200);

        UserModel user = UserDBHelper.getInstance().select(email, password);
        if (user == null) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        user.setPassword(null);
        return GsonLoader.toJson(user);
    }

    public String genCode(int userId, Response response) {
        response.type("application/json");
        response.status(200);

        String code = generateCode();
        UserModel user = UserDBHelper.getInstance().update(userId, code, true);
        if (user == null) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        user.setPassword(null);
        return GsonLoader.toJson(user);
    }


    // generate user token
    private String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuffer token = new StringBuffer();

        for (int i = 0; i < CODE_LENGTH; i++) {
            token.append(CODE_CHARACTERS[random.nextInt(CODE_CHARACTERS.length)]);
        }

        return token.toString();
    }

    public String matchUser(int userId, String code, Response response) {
        response.type("application/json");
        response.status(200);

        UserModel partner = UserDBHelper.getInstance().select(code);
        if (partner == null) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        UserModel user = UserDBHelper.getInstance().update(userId, code, false);
        UserDBHelper.getInstance().update(partner.getUserId(), code, false);
        user.setPassword(null);
        return GsonLoader.toJson(user);
    }

    public String getUser(int userId, Response response) {
        response.type("application/json");
        response.status(200);

        UserModel user = UserDBHelper.getInstance().select(userId);
        if (user == null) {
            ErrorModel error = new ErrorModel("Bad Request");
            response.type("application/json");
            response.status(400);
            return GsonLoader.toJson(error);
        }

        return GsonLoader.toJson(user);
    }
}
