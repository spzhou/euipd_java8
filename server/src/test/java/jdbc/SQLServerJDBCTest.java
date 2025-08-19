/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package jdbc;
import com.course.server.dto.rdms.RdmsSAPMaterialDto;
import com.course.server.service.util.jdbc.JDBCService;

import java.util.List;


public class SQLServerJDBCTest {

    // 数据库连接信息
    private static final String URL = "jdbc:sqlserver://192.168.1.13:1433;databaseName=Raykeen_SH;encrypt=true;trustServerCertificate=true;charset=UTF-8";
    private static final String USER = "rduser";
    private static final String PASSWORD = "Raykeen@RD";

    public static void main(String[] args) {

        JDBCService jdbcService = new JDBCService();
        List<RdmsSAPMaterialDto> sapdbMaterialList = jdbcService.getSAPDBMaterialList(5, 10);
        System.out.println(sapdbMaterialList);


//       Properties dbProps = new Properties();
//        dbProps.setProperty("user", USER);
//        dbProps.setProperty("password", PASSWORD);
//
//        try (Connection connection = DriverManager.getConnection(URL, dbProps);
//             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM VW_Material_Onhandim");
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            // 处理结果集
//            while (resultSet.next()) {
//                // 获取每一列的数据
//                int columnCount = resultSet.getMetaData().getColumnCount();
//                for (int i = 1; i <= columnCount; i++) {
//                    System.out.print(resultSet.getString(i) + "\t");
//                }
//                System.out.println();
//            }
//
//        } catch (SQLException e) {
//            // 记录日志
//            e.printStackTrace();
//            // 或者使用日志框架记录日志
//            // Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Database error", e);
//        }*/
    }
}

