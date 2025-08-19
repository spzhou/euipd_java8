/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package rabbitmq;
import rabbitmq.util.ConnectionUtil2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 消息消费者
 */
public class Consumer {
    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);
/*
    //为了避免加载,取消注入
//    @Autowired
    private ConnectionUtil2 connectionUtil;

    public void receiveMqMsg(String queueName) throws Exception {
        String message = "";
        //创建连接
        Connection connection = connectionUtil.getConnection();
        if(connection == null){
            return;
        }
        // 创建频道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        *//**
         * 参数1：队列名称（消费者与提供者传输的队列名称需要一致）
         * 参数2：是否定义持久化队列（重启后是否能够再次看见未销毁的队列）
         * 参数3：是否独占本次连接（是否一个channel占用一个队列）
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：队列其它参数
         *//*
        channel.queueDeclare(queueName, true, false, false, null);

        //创建消费者；并设置消息处理
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            *//**
             * consumerTag 消息者标签，在channel.basicConsume时候可以指定
             * envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志(收到消息失败后是否需要重新发送)
             * properties 属性信息
             * body 消息
             *//*
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //路由key
                LOG.debug("路由key为：" + envelope.getRoutingKey());
                //交换机
                LOG.debug("交换机为：" + envelope.getExchange());
                //消息id
                LOG.debug("消息id为：" + envelope.getDeliveryTag());
                //收到的消息
                LOG.debug("接收到的消息为：" + new String(body, "utf-8"));
                LOG.debug("__________________________________________________________");
            }
        };

        //监听消息
        *//**
         * 参数1：队列名称
         * 参数2：是否自动确认，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动确认
         * 参数3：消息接收到后回调
         *//*
        channel.basicConsume(queueName, true, consumer);

        //不关闭资源，应该一直监听消息
        //channel.close();
        //connection.close();
    }*/
}

