/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;

public class JdbcUtil {
    public static void main(String[] args) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Connection connection;
        PreparedStatement prepareStatement;
        try {
            dataSource.setDriverClass( "com.mysql.cj.jdbc.Driver" );
            dataSource.setJdbcUrl( "jdbc:mysql://rm-uf6gtv7wr3f54g707lo.mysql.rds.aliyuncs.com:3306/aiimooc_jenvei" );
            dataSource.setUser("zhousp");
            dataSource.setPassword("YOUR_PASSWORD");

            connection = dataSource.getConnection();
            String sql = "SELECT * FROM destoon_member WHERE mobile='13405140509';";
            prepareStatement = connection.prepareStatement(sql );
//            prepareStatement.executeUpdate(); //新建 插入等用这个函数
            ResultSet rs = prepareStatement.executeQuery(sql);
            System.out.println("用户数据");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString("truename"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            dataSource.close();
        }
    }
}
