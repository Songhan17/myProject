package com.han.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Timer;

/**
 * 
 * @author 十七
 * 自机的类
 */
public class Plane extends GameObject {
	// 按键判定
	public boolean left, up, right, down, fire, focus, restart, conti;
	boolean live = true;
	Timer timer = new Timer();
	int life = 3;
	// 绘制自机方法
	@Override
	public void drawSelf(Graphics g) {
		if (live) {
			
			super.drawSelf(g);
			
			if (left) {
				x -= speed;
			}
			if (right) {
				x += speed;
			}
			if (up) {
				y -= speed;
			}
			if (down) {
				y += speed;
			}
			
			// 防止自机出界
			if(x < 10) {
				x = 10;
			}
			if(x > 530) {
				x = 530;
			}
			if(y > 925) {
				y = 925;
			}
			if(y < 150) {
				y = 150;
			}
		}
		
	}

	public Plane(Image img, double x, double y, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
	
	/**
	 * 
	 * @param e
	 * 按下按键
	 */
	public void addDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Z:
	    	fire = true;
//			System.out.println("调用");
			
			break;
		case KeyEvent.VK_SHIFT:
			speed = 10;
			focus = true;

			break;
		case 37:
			left = true;
			break;
		case 38:
			up = true;
			break;
		case 39:
			right = true;
			break;
		case 40:
			down = true;
			break;
		}
	}
	
	/**
	 * 
	 * @param e
	 * 弹起按键
	 */
	public void minusDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Z:
			fire = false;
			break;
		case KeyEvent.VK_SHIFT:
			speed = 20;
			focus = false;
			break;
		case 37:
			left = false;
			break;
		case 38:
			up = false;
			break;
		case 39:
			right = false;
			break;
		case 40:
			down = false;
			break;
		case KeyEvent.VK_Y:
			restart = true;
			break;
		case KeyEvent.VK_ENTER:
			conti = true;
			break;
		}
	}
	
	
	
	
	
}
