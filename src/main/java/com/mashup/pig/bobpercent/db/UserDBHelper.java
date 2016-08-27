package com.mashup.pig.bobpercent.db;

import com.mashup.pig.bobpercent.model.UserModel;
import org.sql2o.Connection;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class UserDBHelper {

    private volatile static UserDBHelper instance;

    public static UserDBHelper getInstance() {
        if (instance == null) {
            synchronized (UserDBHelper.class) {
                if (instance == null) {
                    instance = new UserDBHelper();
                }
            }
        }

        return instance;
    }

    private static final String QUERY_INSERT_USER = "INSERT INTO User(name, email, password) " +
                                                    "VALUES (:name, :email, :password)";

    private static final String QUERY_SELECT_USER_NAME_PASSWORD = "SELECT * FROM User " +
                                                                  "WHERE email = :email and password = :password";

    private static final String QUERY_SELECT_USER_ID = "SELECT * FROM User " +
                                                        "WHERE userId = :userId";

    private static final String QUERY_SELECT_USER_CODE = "SELECT * FROM User " +
                                                         "WHERE code = :code";

    private static final String QUERY_UPDATE_USER_CODE = "UPDATE USER SET code = :code, pending = :pending " +
                                                         "WHERE userId = :userId";



    public UserModel insert(String name, String email, String password) {
        if (name == null || email == null || password == null) {
            return null;
        }

        Connection conn = null;
        try {

            conn = DBHelper.getInstance().beginTransaction();
            conn.createQuery(QUERY_INSERT_USER)
                    .addParameter("name", name)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeUpdate();
            conn.commit();
            conn.close();

            return select(email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conn != null) {
            conn.close();
        }

        return null;
    }


    public UserModel select(String email, String password) {
        Connection conn = null;
        UserModel user = null;

        try {
            conn = DBHelper.getInstance().open();
            user = conn.createQuery(QUERY_SELECT_USER_NAME_PASSWORD)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeAndFetchFirst(UserModel.class);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return user;
    }


    public UserModel select(int userId) {
        Connection conn = null;
        UserModel user = null;

        try {
            conn = DBHelper.getInstance().open();
            user = conn.createQuery(QUERY_SELECT_USER_ID)
                    .addParameter("userId", userId)
                    .executeAndFetchFirst(UserModel.class);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return user;
    }


    public UserModel select(String code) {
        Connection conn = null;
        UserModel user = null;

        try {
            conn = DBHelper.getInstance().open();
            user = conn.createQuery(QUERY_SELECT_USER_CODE)
                    .addParameter("code", code)
                    .executeAndFetchFirst(UserModel.class);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return user;
    }


    public UserModel update(int userId, String code, boolean pending) {
        if (code == null) {
            return null;
        }

        Connection conn = null;
        try {

            conn = DBHelper.getInstance().beginTransaction();
            conn.createQuery(QUERY_UPDATE_USER_CODE)
                    .addParameter("code", code)
                    .addParameter("pending", pending)
                    .addParameter("userId", userId)
                    .executeUpdate();
            conn.commit();
            conn.close();

            return select(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conn != null) {
            conn.close();
        }

        return null;
    }



}
