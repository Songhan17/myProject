package com.han.game;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * 
 * @author ʮ��
 * ���ܴ���
 */
public class InfoFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfoFrame(){
		
		this.setSize( 350, 600);
		this.setTitle("�淨����");
		this.setVisible(true);
		this.setLocationRelativeTo(null); 
		
		JLabel text1=new JLabel("     �� �� ���£����� ���ƶ��� Z�������ӵ�");
		JLabel text2=new JLabel("     Shift�ɾ۽����������ҽ������ģʽ");
		JLabel text3=new JLabel("     ��ҳ�ʼ����Ϊ3���������˻�����ӵ����������");
		JLabel text4=new JLabel("     ��ҽ��ڳ�ʼλ�ø������5��ı�����");
		JLabel text5=new JLabel("     ���е��˿ɻ�÷������ﵽһ����������bossս");
		JLabel text6=new JLabel("     ʰȡP������ӻ������ı乥��״̬");
		this.add(text1);
		this.add(text2);
		this.add(text3);
		this.add(text4);
		this.add(text5);
		this.add(text6);
		this.setLayout(new GridLayout(6,1));
	}
}
