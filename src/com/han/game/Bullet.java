package com.han.game;

import java.awt.Graphics;
import java.awt.Image;
/**
 * 
 * @author 张涵霖
 * 子弹类
 */
public class Bullet extends GameObject implements Runnable {
	// 子弹走步步数，只有y坐标在变
	private int speed = 10; 
	public Image img;
	double degree = 0;// = Math.random()*Math.PI*2;
	double a = Math.random();
	
	
	
	// 自机子弹的绘制逻辑
	public void drawBullet01(Graphics g) {
		g.drawImage(img, (int)x - 15, (int)y, null);
		g.drawImage(img, (int)x + 25, (int)y, null);
	}
	public void drawBullet02(Graphics g) {
		g.drawImage(img, (int)x - 25, (int)y + 15, null);
	}
	public void drawBullet03(Graphics g) {
		g.drawImage(img, (int)x + 25, (int)y + 15, null);
	}
	
	// 自机子弹聚焦的绘制逻辑
	public void drawBulletFocus01(Graphics g) {
		g.drawImage(img, (int)x - 5, (int)y, null);
		g.drawImage(img, (int)x + 15, (int)y, null);
	}
	public void drawBulletFocus02(Graphics g) {
		g.drawImage(img, (int)x - 15, (int)y, null);
	}
	public void drawBulletFocus03(Graphics g) {
		g.drawImage(img, (int)x + 25, (int)y, null);
	}
	
	// 敌人子弹的绘制逻辑
	public void drawEnemyBullet01(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
		x += speed*Math.cos((degree + Math.PI/2) * a);
        y += speed*Math.sin((degree + Math.PI/2) * a);
	}
	public void drawEnemyBullet02(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
		x += (speed-4)*Math.cos((degree + Math.PI*2) * a);
        y += (speed-4)*Math.sin((degree + Math.PI*2) * a);
	}
	public void drawEnemyBullet03(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
		x += speed*Math.cos((degree + Math.PI/2));
        y += speed*Math.sin((degree + Math.PI/2)) + 3;
	}
	public void drawEnemyBullet04(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
		y += speed;
	}
	public void drawEnemyBullet05(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
		x += speed*Math.cos((degree * Math.PI/2));
        y += speed*Math.sin((degree * Math.PI/2));
	}
	
	
	
	public Bullet(Image img, double x, double y, int width, int height) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
        this.height = height;
        
	}
	

	// 子弹发射速率控制，多线程
	@Override
	public void run() {
		saleBullet();
	}
	/**
	 * 同步锁
	 */
	public synchronized void saleBullet() {
		while (y > 5) {
			y -= speed;
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}
