/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package rabbitmq.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 创建连接工具类
 */
@Component
public class ConnectionUtil2 {

/*    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private String port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;*/


/*    public Connection getConnection() throws Exception {
       //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //主机地址;默认为 localhost
        connectionFactory.setHost(host);
        //连接端口;默认为 5672
        connectionFactory.setPort(Integer.parseInt(port));
        //连接用户名；默认为guest
        connectionFactory.setUsername(username);
        //连接密码；默认为guest
        connectionFactory.setPassword(password);

        //创建连接
        return connectionFactory.newConnection();
    }*/
}
