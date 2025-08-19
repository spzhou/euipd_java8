/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemUUIDFetcher {

    public static void main(String[] args) {
        try {
            String uuid = getSystemUUID();
            System.out.println("System UUID: " + uuid);
        } catch (Exception e) {
            System.err.println("Error fetching UUID: " + e.getMessage());
        }
    }

    public static String getSystemUUID() throws IOException, InterruptedException {
        Process process = new ProcessBuilder()
                .command("sudo", "dmidecode", "-s", "system-uuid") // 直接获取UUID参数模式
                .redirectErrorStream(true)
                .start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {

            // 读取命令输出
            String output = reader.readLine();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Command failed with exit code: " + exitCode);
            }

            if (output == null || output.trim().isEmpty()) {
                throw new IOException("UUID not found in dmidecode output");
            }

            return output.trim();
        }
    }
}
