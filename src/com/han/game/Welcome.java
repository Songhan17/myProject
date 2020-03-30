package com.han.game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * 
 * @author 张涵霖
 * 标题界面
 */
public class Welcome extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Welcome() {
		// 大小
		this.setSize(850, 1000);
		// 标题
		this.setTitle("東方红魔乡");
		// 窗口居中
		this.setLocationRelativeTo(null);
		// 可见
		this.setVisible(true);
		// 设置关闭运行
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
		// 按钮赋值
		JButton startGame = new JButton("开始游戏");
		JButton infoGame = new JButton("玩法介绍");
		JButton endGame = new JButton("退出");
		startGame.setBounds(300, 600, 200, 50);
		infoGame.setBounds(300, 700, 200, 50);
		endGame.setBounds(300, 800, 200, 50);
		// 添加按钮
		this.add(startGame);
		this.add(infoGame);
		this.add(endGame);
		this.setLayout(null);
		// 加载背景
		ImageIcon img1 = new ImageIcon("src/images/headBg.png");
		JLabel la3 = new JLabel(img1);
	    la3.setBounds(0,0,850,1000);
		this.getLayeredPane().add(la3, new Integer(Integer.MIN_VALUE));
		getContentPane().add(la3);
		this.setResizable(false);

		startGame.setMnemonic(KeyEvent.VK_ENTER);// 添加快捷键 ALT + “ ”
		/**
		 * 点开始游戏，销毁主菜单窗口 创建选择模式窗口
		 */
		startGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				MyGameFrame gw = new MyGameFrame();
				gw.launchFrame();
			}
		});
		/**
		 * 点击玩法介绍，创建玩法介绍窗口
		 */
		infoGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoFrame();
			}
		});
		/**
		 * 点击退出，销毁窗口
		 */
		endGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				addWindowListener(new WindowAdapter() {
				    @Override
				    public void windowClosing(WindowEvent e) {
				        System.exit(0);
				    }
				});
			}
		});
	}


	public static void main(String[] args) {
		new Welcome();
		
	}

}

