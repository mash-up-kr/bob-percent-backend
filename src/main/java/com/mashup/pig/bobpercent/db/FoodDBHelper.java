package com.mashup.pig.bobpercent.db;

import com.mashup.pig.bobpercent.model.FoodCompModel;
import com.mashup.pig.bobpercent.model.FoodModel;
import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class FoodDBHelper {

    private volatile static FoodDBHelper instance;

    public static FoodDBHelper getInstance() {
        if (instance == null) {
            synchronized (FoodDBHelper.class) {
                if (instance == null) {
                    instance = new FoodDBHelper();
                }
            }
        }

        return instance;
    }

    private static final String QUERY_SELECT_FOODS = "SELECT * FROM FoodItem";
    private static final String QUERY_SELECT_FOOD = "SELECT * FROM FoodItem WHERE foodId = :foodId";
    private static final String QUERY_SELECT_FOOD_COMPS = "SELECT * FROM FoodComp";
    private static final String QUERY_SELECT_FOOD_COMP = "SELECT * FROM FoodComp WHERE compId = :compId";


    public List<FoodModel> getAllFoods() {
        Connection conn = null;

        try {
            conn = DBHelper.getInstance().open();
            List<FoodModel> foods = conn.createQuery(QUERY_SELECT_FOODS).executeAndFetch(FoodModel.class);
            conn.close();

            return foods;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return new ArrayList<FoodModel>(0);
    }


    public FoodModel getFood(int foodId) {
        Connection conn = null;

        try {
            conn = DBHelper.getInstance().open();
            FoodModel food = conn.createQuery(QUERY_SELECT_FOOD)
                    .addParameter("foodId", foodId)
                    .executeAndFetchFirst(FoodModel.class);
            conn.close();

            return food;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return null;
    }


    public List<FoodCompModel> getAllFoodComps() {
        Connection conn = null;

        try {
            conn = DBHelper.getInstance().open();
            List<FoodCompModel> foodComps = conn.createQuery(QUERY_SELECT_FOOD_COMPS)
                    .executeAndFetch(FoodCompModel.class);
            conn.close();

            int size = foodComps.size();
            for (int i = 0; i < size; i++) {
                FoodCompModel foodComp = foodComps.get(i);
                foodComp.setFirstFood(getFood(foodComp.getFirstFoodId()));
                foodComp.setSecondFood(getFood(foodComp.getSecondFoodId()));

                foodComps.remove(i);
                foodComps.add(i, foodComp);
            }

            return foodComps;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return new ArrayList<FoodCompModel>(0);
    }


    public FoodCompModel getFoodComp(int compId) {
        Connection conn = null;

        try {
            conn = DBHelper.getInstance().open();
            FoodCompModel foodComp = conn.createQuery(QUERY_SELECT_FOOD_COMPS)
                    .addParameter("compId", compId)
                    .executeAndFetchFirst(FoodCompModel.class);

            foodComp.setFirstFood(getFood(foodComp.getFirstFoodId()));
            foodComp.setSecondFood(getFood(foodComp.getSecondFoodId()));

            conn.close();
            return foodComp;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return null;
    }
}
