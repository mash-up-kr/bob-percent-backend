package com.mashup.pig.bobpercent.service.game;

import com.mashup.pig.bobpercent.model.FoodModel;
import spark.Response;

import java.util.List;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public interface GameService {

    String join(int userId, String code, Response response);



    String saveResult(int userId, int gameId, List<FoodModel> foods, Response response);



    String getResult(int userId, int gameId, Response response);
}
