package com.han.game;
import java.awt.Image;
/**
 * 
 * @author 十七
 * P点，影响火力
 */
public class Power extends GameObject {
	double dx,dy;
	int speed = 10;
	public Power(Image img, double x, double y, int width, int height) {
		this.img = img;
		this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
	}
    public void step(){   	
		y += speed;
	}
}
