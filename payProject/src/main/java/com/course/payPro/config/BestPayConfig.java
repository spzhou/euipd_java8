/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.payPro.config;

import com.lly835.bestpay.config.AliPayConfig;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by 廖师兄
 */
@Component
public class BestPayConfig {

    @Autowired
    private WxAccountConfig wxAccountConfig;

    @Autowired
    private AlipayAccountConfig alipayAccountConfig;

    /**
     * 配置最佳支付服务
     * 创建并配置BestPayService实例，集成微信支付和支付宝支付配置
     * 
     * @param wxPayConfig 微信支付配置对象
     * @return 返回配置好的最佳支付服务实例
     */
    @Bean
    public BestPayService bestPayService(WxPayConfig wxPayConfig) {
        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId(alipayAccountConfig.getAppId());
        aliPayConfig.setPrivateKey(alipayAccountConfig.getPrivateKey());
        aliPayConfig.setAliPayPublicKey(alipayAccountConfig.getPublicKey());
        aliPayConfig.setNotifyUrl(alipayAccountConfig.getNotifyUrl());
        aliPayConfig.setReturnUrl(alipayAccountConfig.getReturnUrl());

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        bestPayService.setAliPayConfig(aliPayConfig);
        return bestPayService;
    }

    /**
     * 配置微信支付
     * 创建并配置微信支付配置对象
     * 
     * @return 返回配置好的微信支付配置对象
     */
    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(wxAccountConfig.getAppId());
        wxPayConfig.setMchId(wxAccountConfig.getMchId());
        wxPayConfig.setMchKey(wxAccountConfig.getMchKey());
        //192.168.50.101 同一局域网可访问
        //125.121.56.227 云服务器可行，家庭宽带不行(路由器、光猫)
        wxPayConfig.setNotifyUrl(wxAccountConfig.getNotifyUrl());
        wxPayConfig.setReturnUrl(wxAccountConfig.getReturnUrl());
        return wxPayConfig;
    }
}
