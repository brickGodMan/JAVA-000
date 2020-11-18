package com.qiancy.springboot.jdbc;

import java.sql.*;
import java.util.Random;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/17
 * @since 1.0.0
 */
public class UserDao {
    private Connection connection;
    private Statement sta;
    private PreparedStatement pre;
    private ResultSet rs;

    //查询
    public void getUser() {
        String sql = "select * from t_user";
        try {
            connection = DbUtilOne.openConnection();
            pre = connection.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                System.out.printf("userId:%s,userName:%s,userAge:%s%n",rs.getInt("user_id"),rs.getString("user_name"),rs.getString("user_age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,pre,rs);
        }
    }

    //批量增加
    public void addBatchUser() {
        String sql = "insert into t_user(user_id,user_name,user_age) values(?,?,?)";
        try {
            connection = DbUtilOne.openConnection();
            connection.setAutoCommit(false);
            pre = connection.prepareStatement(sql);
            int count = 9;
            for (int i = 0; i < count; i++) {
                pre.setInt(1, i);
                pre.setString(2, "jdbc插入" + i);
                pre.setString(3, "age: " + i);
                pre.addBatch();
            }
            if(pre.executeBatch().length < 9) {
                System.out.println("有数据插入失败");
            } else {
                System.out.println("批量插入成功");
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //添加
    public void addUser() {
        String sql = "insert into t_user(user_id,user_name,user_age) values(?,?,?)";
        try {
            connection = DbUtilOne.openConnection();
            pre = connection.prepareStatement(sql);
            int id = new Random().nextInt(10);
            pre.setInt(1, id);
            pre.setString(2, "jdbc插入" + id);
            pre.setString(3, "" + id);
            if (pre.executeUpdate() > 0) {
                System.out.println("数据插入成功");
            } else {
                System.out.println("数据插入失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //修改
    public void updataUser() {
        String sql = "update t_user set user_name = ? where user_id = ?";
        try {
            connection = DbUtilOne.openConnection();
            pre = connection.prepareStatement(sql);
            pre.setString(1,"jdbc插入8_update");
            pre.setInt(2,8);
            if(pre.executeUpdate() > 0){
                System.out.println("数据修改成功");
            } else {
                System.out.println("数据修改失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    //删除
    public void deleteUser() {
        String sql = "delete from t_user where user_id = ?";
        try {
            connection = DbUtilOne.openConnection();
            pre = connection.prepareStatement(sql);
            pre.setInt(1,8);
            if(pre.executeUpdate() > 0){
                System.out.println("数据删除成功");
            } else {
                System.out.println("数据删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    public static void close(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet) {
        try {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        UserDao dao = new UserDao();
        //测试添加
//        dao.addUser();
        //测试修改
//        dao.updataUser();
        //测试删除
//        dao.deleteUser();
        //测试批量添加
//        dao.addBatchUser();
        //测试查询
        dao.getUser();
        System.out.printf("用时 %sms",System.currentTimeMillis() - start);
    }
}
