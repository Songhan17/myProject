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
 * @author �ź���
 * �������
 */
public class Welcome extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Welcome() {
		// ��С
		this.setSize(850, 1000);
		// ����
		this.setTitle("�|����ħ��");
		// ���ھ���
		this.setLocationRelativeTo(null);
		// �ɼ�
		this.setVisible(true);
		// ���ùر�����
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
		// ��ť��ֵ
		JButton startGame = new JButton("��ʼ��Ϸ");
		JButton infoGame = new JButton("�淨����");
		JButton endGame = new JButton("�˳�");
		startGame.setBounds(300, 600, 200, 50);
		infoGame.setBounds(300, 700, 200, 50);
		endGame.setBounds(300, 800, 200, 50);
		// ��Ӱ�ť
		this.add(startGame);
		this.add(infoGame);
		this.add(endGame);
		this.setLayout(null);
		// ���ر���
		ImageIcon img1 = new ImageIcon("src/images/headBg.png");
		JLabel la3 = new JLabel(img1);
	    la3.setBounds(0,0,850,1000);
		this.getLayeredPane().add(la3, new Integer(Integer.MIN_VALUE));
		getContentPane().add(la3);
		this.setResizable(false);

		startGame.setMnemonic(KeyEvent.VK_ENTER);// ��ӿ�ݼ� ALT + �� ��
		/**
		 * �㿪ʼ��Ϸ���������˵����� ����ѡ��ģʽ����
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
		 * ����淨���ܣ������淨���ܴ���
		 */
		infoGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoFrame();
			}
		});
		/**
		 * ����˳������ٴ���
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

