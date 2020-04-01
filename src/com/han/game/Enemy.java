package com.han.game;

import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * @author 十七 
 * boss类
 */
public class Enemy extends GameObject {
	boolean live = false;
	// 初始血量
	int hp = 500;
	// 生命数、阶段状态
	int fire = 3;

	public void drawSelf(Graphics g) {
		if (live) {
			super.drawSelf(g);
			// 防止出界
			if (x < 10) {
				x = 10;
			}
			if (x > 530) {
				x = 530;
			}
			if (y > 925) {
				y = 925;
			}
			if (y < 40) {
				y = 40;
			}
		}
	}

	public Enemy(Image img, double x, double y, int width, int height) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
