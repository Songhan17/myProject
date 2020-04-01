package com.han.game;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * 
 * @author 十七
 * 介绍窗口
 */
public class InfoFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfoFrame(){
		
		this.setSize( 350, 600);
		this.setTitle("玩法介绍");
		this.setVisible(true);
		this.setLocationRelativeTo(null); 
		
		JLabel text1=new JLabel("     按 上 ，下，左，右 键移动， Z键发射子弹");
		JLabel text2=new JLabel("     Shift可聚焦攻击，并且进入减速模式");
		JLabel text3=new JLabel("     玩家初始生命为3，触碰敌人或敌人子弹则减少生命");
		JLabel text4=new JLabel("     玩家将在初始位置复活，附带5秒的保护期");
		JLabel text5=new JLabel("     击中敌人可获得分数，达到一定分数进行boss战");
		JLabel text6=new JLabel("     拾取P点可增加火力，改变攻击状态");
		this.add(text1);
		this.add(text2);
		this.add(text3);
		this.add(text4);
		this.add(text5);
		this.add(text6);
		this.setLayout(new GridLayout(6,1));
	}
}
