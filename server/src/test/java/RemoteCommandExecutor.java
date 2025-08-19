/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

import com.jcraft.jsch.*;
import java.io.InputStream;

public class RemoteCommandExecutor {
    public static final String  R250_SYSTEM_UUID = "4c4c4544-0032-3810-8030-b6c04f4a5a33";

    /**
     * 主方法
     * 通过SSH远程执行命令获取系统UUID，并验证用户登录权限
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String host = "7r9tsm5320250329.sh3.ikuai7.com";
        int port = 20882; // SSH远程外网端口
        String username = "root";
        String password = "YOUR_PASSWORD";
        String command = "sudo dmidecode -s system-uuid"; // 要执行的命令

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // 配置StrictHostKeyChecking为no，避免首次连接时的手动确认
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // 获取标准输出流和错误流
            InputStream in = channel.getInputStream();
            ((ChannelExec) channel).setErrStream(System.err);

            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    String systemUuid = new String(tmp, 0, i);
                    System.out.print("System-uuid:"+ systemUuid);
                    //去掉systemUuid前后的空格
                    systemUuid = systemUuid.trim();
                    if(R250_SYSTEM_UUID.equals(systemUuid)){
                        System.out.print("User have the System login authority!");
                    }
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    System.out.println("Exit status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
