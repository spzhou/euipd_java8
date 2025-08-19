/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.util;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class NoWaterRipple extends Configurable implements GimpyEngine {
		public NoWaterRipple(){}

	/**
	 * 获取扭曲后的图像
	 * 对基础图像进行扭曲处理，去除水波纹效果
	 * 
	 * @param baseImage 基础图像
	 * @return 返回处理后的扭曲图像
	 */
	@Override
	public BufferedImage getDistortedImage(BufferedImage baseImage) {
			//NoiseProducer noiseProducer = this.getConfig().getNoiseImpl();
			BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), 2);
			Graphics2D graphics = (Graphics2D)distortedImage.getGraphics();
			//RippleFilter rippleFilter = new RippleFilter();
			//rippleFilter.setWaveType(0);
			//rippleFilter.setXAmplitude(2.6F);
			//rippleFilter.setYAmplitude(1.7F);
			//rippleFilter.setXWavelength(15.0F);
			//rippleFilter.setYWavelength(5.0F);
			//rippleFilter.setEdgeAction(0);
			//WaterFilter waterFilter = new WaterFilter();
			//waterFilter.setAmplitude(1.5F);
			//waterFilter.setPhase(10.0F);
			//waterFilter.setWavelength(2.0F);
			//BufferedImage effectImage = waterFilter.filter(baseImage, (BufferedImage)null);
			//effectImage = rippleFilter.filter(effectImage, (BufferedImage)null);
			graphics.drawImage(baseImage, 0, 0, (Color)null, (ImageObserver)null);
			graphics.dispose();
			//noiseProducer.makeNoise(distortedImage, 0.1F, 0.1F, 0.25F, 0.25F);
			//noiseProducer.makeNoise(distortedImage, 0.1F, 0.25F, 0.5F, 0.9F);
			return distortedImage;
		}
	}
