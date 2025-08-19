/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package apitest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CPUSerialNumberFetcher {

    /**
     * 主方法
     * 根据操作系统类型获取CPU序列号
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                // Windows
                String cpuId = getCPUSerialNumberWindows();
                System.out.println("CPU Serial Number (Windows): " + cpuId);
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                // Linux or macOS
                String cpuId = getCPUSerialNumberLinux();
                System.out.println("CPU Serial Number (Linux/macOS): " + cpuId);
            } else {
                System.out.println("Unsupported operating system: " + os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Windows系统CPU序列号
     * 使用wmic命令获取Windows系统的CPU处理器ID
     * 
     * @return 返回CPU序列号字符串
     * @throws Exception 执行命令异常
     */
    private static String getCPUSerialNumberWindows() throws Exception {
        Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            output.append(line.trim());
        }

        // Parse the output to extract the Processor ID
        return output.toString().replace("ProcessorId", "").trim();
    }

    /**
     * 获取Linux/macOS系统CPU序列号
     * 使用dmidecode命令获取Linux/macOS系统的系统信息
     * 
     * @return 返回CPU序列号字符串，如果获取失败则返回null
     * @throws Exception 执行命令异常
     */
    private static String getCPUSerialNumberLinux() throws Exception {
        try {
            // 注意：根据你的实际情况调整命令
            Process process = Runtime.getRuntime().exec(new String[]{"sudo", "dmidecode", "-t", "system"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            /*String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }*/
            String line = reader.readLine();
            if (line == null || line.isEmpty() || line.equals("0")) {
                // Some systems may not have a serial number, or it might be 0
                return "No serial number available";
            }

            reader.close();
            return line.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
