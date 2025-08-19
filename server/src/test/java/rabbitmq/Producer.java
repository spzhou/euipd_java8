/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package rabbitmq;

import rabbitmq.util.ConnectionUtil2;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class Producer {
    private static final Logger LOG = LoggerFactory.getLogger(Producer.class);
/*

    // 为了避免加载,取消注入
//    @Autowired
    private ConnectionUtil2 connectionUtil;

    public void sendMqMsg(String queueName, String message) throws Exception {
        //创建连接
        Connection connection = connectionUtil.getConnection();
        if(connection == null){
            return;
        }
        // 创建频道
        Channel channel = connection.createChannel();
        // 参数配置map
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("x-message-ttl", 20000);//20秒钟后没有被接收,将自动删除
        // 声明（创建）队列
        */
/**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列（重启后是否能够再次看见未销毁的队列）
         * 参数3：是否独占本次连接（是否一个channel占用一个队列）
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：队列其它参数
         *//*

        //channel.queueDeclare(queueName, true, false, false, null);
        channel.queueDeclare(queueName, true, false, false, configMap);
        // 要发送的信息
        //String message = "Hello RabbitMQ Test！";
        */
/**
         * 参数1：交换机名称，如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：消息其它属性
         * 参数4：消息内容
         *//*

        channel.basicPublish("", queueName, null, message.getBytes());
        //System.out.println("已发送消息：" + message);
        LOG.debug("已发送消息：" + message);

        // 关闭资源
        channel.close();
        connection.close();
    }
*/

}
