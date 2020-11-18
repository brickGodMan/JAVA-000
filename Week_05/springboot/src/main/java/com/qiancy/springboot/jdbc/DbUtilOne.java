package com.qiancy.springboot.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/17
 * @since 1.0.0
 */
public class DbUtilOne {

    private static Properties prop;

    static {
        //第一步加载驱动
        prop = new Properties();
        InputStream in = DbUtilOne.class.getClassLoader().getResourceAsStream("application.yaml");
        try {
            prop.load(in);
            Class.forName(prop.getProperty("driverName"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 第二步获取数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("user"),"");
    }
    //第三步获取数据库操作句柄

    //第四步获取结果集

    //第五步处理结果集

    //第六步关闭结果集，关闭句柄，关闭数据库连接
}
