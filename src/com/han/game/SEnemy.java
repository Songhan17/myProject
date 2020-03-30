package com.han.game;

import java.awt.Image;
import java.util.Random;


/**
 * 
 * @author 张涵霖
 * 敌人的类
 */
public class SEnemy extends GameObject {
		boolean live = true;
		int speed = 5;
		int time = 0;
		double dx,dy;
		
		public SEnemy(Image img, double x, double y, int width, int height, int hp) {
//			img = MyGameFrame.sEnemyImg1;
			this.img = img;
			this.x = x;
	        this.y = y;
	        this.width = width;
	        this.height = height;
	        this.hp = hp;
		}
		public void step() {
			time++;
			x = x + dx;
			y += speed;
			if (y > 200 + new Random().nextInt(500)) {
				speed = 0;
			}
			if (time == 200) {
				dx = -15;
				time = 0;
			}
		}

}
