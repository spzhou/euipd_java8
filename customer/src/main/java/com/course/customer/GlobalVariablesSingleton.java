/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer;

import org.springframework.stereotype.Component;

/**
 * 使用单例模式 实现全局变量
 * 我们还可以使用单例模式来定义全局变量。通过将变量定义在一个单例类中，可以确保全局变量只有一个实例，并且可以在应用的任何地方访问到。
 */
@Component
public class GlobalVariablesSingleton {

    private static GlobalVariablesSingleton instance;

    private Boolean globalVar;

    private GlobalVariablesSingleton() {
        // 初始化全局变量
        globalVar = false;
    }

    /**
     * 获取单例实例
     * 使用懒汉式单例模式获取全局变量单例实例
     * 
     * @return 返回全局变量单例实例
     */
    public static GlobalVariablesSingleton getInstance() {
        if (instance == null) {
            instance = new GlobalVariablesSingleton();
        }
        return instance;
    }



    /**
     * 获取全局变量值
     * 
     * @return 返回全局变量的布尔值
     */
    public Boolean getGlobalVar() {
        return globalVar;
    }

    /**
     * 设置全局变量值
     * 
     * @param value 要设置的布尔值
     */
    public void setGlobalVar(Boolean value){
        globalVar = value;
    }
}
