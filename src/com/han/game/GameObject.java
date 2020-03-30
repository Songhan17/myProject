package com.han.game;
 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
 /**
  * 
  * @author �ź���
  * ��������ĸ���
  */
public class GameObject {
	// ͼƬ����
	Image img; 
	// �������
    double x,y;    
    // ��ʼ�ٶ�
    int speed = 20;
    // ��ײ���Ŀ�Ⱥ͸߶�
    int width,height;
    // Ѫ��
    int hp;
    double dx,dy;
     
    /**
     * ��ô�����Ʊ�����
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
     * �������߲�
     */
  	public void step() {
  		
	}
     
    /**
     * ���������Ӧ�������򣬱��ں�������ײ�����ʹ��
     * @return
     */
    public Rectangle getRect(){
        return  new Rectangle((int)x,(int) y, width, height);
    }  
}