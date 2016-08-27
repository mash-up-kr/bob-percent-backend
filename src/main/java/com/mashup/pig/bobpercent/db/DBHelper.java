package com.mashup.pig.bobpercent.db;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 * Created by bigstark on 2016. 8. 27..
 */
public class DBHelper {

    private volatile static DBHelper instance;

    public static DBHelper getInstance() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper();
                }
            }
        }

        return instance;
    }


    private static final String DB_URL = "jdbc:mysql://localhost:3306/BobPercent";
    private static final String DB_USER = "bigstark";
    private static final String DB_PASSWORD = "";

    private Sql2o sql2o;


    public DBHelper() {
        sql2o = new Sql2o(DB_URL, DB_USER, DB_PASSWORD);
    }


    public Connection open() {
        return sql2o.open();
    }


    public Connection beginTransaction() {
        return sql2o.beginTransaction();
    }

}
