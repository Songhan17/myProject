package com.han.game;
 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
 /**
  * 
  * @author 张涵霖
  * 所有物体的父类
  */
public class GameObject {
	// 图片对象
	Image img; 
	// 坐标对象
    double x,y;    
    // 初始速度
    int speed = 20;
    // 碰撞检测的宽度和高度
    int width,height;
    // 血量
    int hp;
    double dx,dy;
     
    /**
     * 怎么样绘制本对象
     * @param g
     */
    public void drawSelf(Graphics  g){
        g.drawImage(img, (int)x, (int)y, null);
    }
     
    public GameObject(Image img, double x, double y) {
        this.img = img;
        this.x = x;
        this.y = y;
        if(img!=null){
            this.width = img.getWidth(null);
            this.height = img.getHeight(null);
        }
    }
     
    public GameObject(Image img, double x, double y, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
     
    public GameObject() {
    }
    
    /**
     * 飞行物走步
     */
  	public void step() {
  		
	}
     
    /**
     * 返回物体对应矩形区域，便于后续在碰撞检测中使用
     * @return
     */
    public Rectangle getRect(){
        return  new Rectangle((int)x,(int) y, width, height);
    }  
}