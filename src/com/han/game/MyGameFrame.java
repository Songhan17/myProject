package com.han.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;




/**
 * ������
 * @author �ź���
 *
 */
public class MyGameFrame extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image offScreenImage = null;
	// ����˶��ӵ�����
	private ArrayList<GameObject> go = new ArrayList<GameObject>();
	// ����ͼƬ
	public static Image bgImg = GameUtil.getImage("images/bg.png");
	public static Image planeImg = GameUtil.getImage("images/hero.png");
	public static Image bullet01 = GameUtil.getImage("images/bullet01.png");
	public static Image bullet02 = GameUtil.getImage("images/bullet02.png");
	public static Image bullet03 = GameUtil.getImage("images/bullet03.png");
	public static Image enemyImg = GameUtil.getImage("images/remiriya.png");
	public static Image eBullet01 = GameUtil.getImage("images/eBullet01.png");
	public static Image eBullet02 = GameUtil.getImage("images/eBullet01.png");
	public static Image eBullet03 = GameUtil.getImage("images/eBullet02.png");
	public static Image eBullet04 = GameUtil.getImage("images/eBullet03.png");
	public static Image eBullet05 = GameUtil.getImage("images/eBullet04.png");
	public static Image eBullet06 = GameUtil.getImage("images/eBullet04.png");
	public static Image sEnemyImg1 = GameUtil.getImage("images/enemy01.png");
	public static Image sEnemyImg2 = GameUtil.getImage("images/enemy02.png");
	public static Image powerImg = GameUtil.getImage("images/power.png");
	public static Image dunImg = GameUtil.getImage("images/dun.png");
	// ����2�ŵ��ӳ���ѭ��
	int back_y=-bgImg.getHeight(null)+bgImg.getHeight(null);
	// ��ʼ����
	static double time = 0.00;
    static int time1 = 0;
    static int time2 = 0;
    static int time3 = 20;
    static int score = 0;
    static double fire = 0;
	int flyEnteredIndex = 0;
	/**
	 * ���е����Ƿ���
	 */
    boolean live = true;
    /**
     * �Ƿ��޵�
     */
    boolean isM = false;
    /**
     * �Ƿ�ʤ��
     */
    boolean isV = false;
	// �ƶ�����
	int dx = 10;
	double dy = 0;
	// ��ʱ��
	Timer timer = new Timer();
//	Timer timer2 = new Timer();
//	Timer timer3 = new Timer();
	// ���س�ʼͼƬ����
    Plane plane = new Plane(planeImg, 300, 850, 12, 32);
    Enemy enemy = new Enemy(enemyImg, 300, 150, 71, 100);
    // �ӵ�����
    ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    ArrayList<Bullet> eBulletList = new ArrayList<Bullet>();
    ArrayList<Bullet> eBulletList2 = new ArrayList<Bullet>();
    
    
	// ����
    @Override
    public void paint(Graphics g) {
    	// ���Ʊ���������ѭ���ƶ�
		g.drawImage(bgImg, 0, back_y++, null);
		g.drawImage(bgImg, 0, back_y - bgImg.getHeight(null), null);
		if (back_y == bgImg.getHeight(null)) {
			back_y = 0;
		}
    	// �����޵�5��
		if (isM) {
			g.drawImage(dunImg, (int)plane.x -50, (int)plane.y - 40, null);
		}
		
		// ����
    	paintScore(g);
    	// ������
		paintFlyingObjects(g);
    	// boss�볡����
    	if (score >= 10 && isV == false) {
    		enemy.live = true;
    		enemy.drawSelf(g);
    		g.drawRect(30, 100, 500, 10);
    		 // bossѪ���׶ι���
        	if (enemy.fire == 3 && enemy.hp > 0) {
        		g.setColor(Color.yellow);
        		g.fillRect(30, 100, enemy.hp, 11);
    		}else if (enemy.fire == 2 && enemy.hp > 0) {
    			g.setColor(Color.blue);
        		g.fillRect(30, 100, enemy.hp, 11);
    		}else if(enemy.fire == 1 && enemy.hp > 0) {
    			g.setColor(Color.red);
        		g.fillRect(30, 100, enemy.hp, 11);
    		}
		}
    	if (isV == true) {
    		g.setColor(Color.PINK);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
			g.drawString("��Ϸʤ������ϲ��ϲ", 0, 500);
			g.drawString("   ��Y������Ϸ", 0, 600);
		}
    	// �Ի�����������
    	if (plane.life == 3) {
    		// �����Ի�
        	plane.drawSelf(g);
    		g.drawImage(planeImg, 600, 850, null);
    		g.drawImage(planeImg, 630, 850, null);
    		g.drawImage(planeImg, 660, 850, null);
		}else if (plane.life == 2) {
			// �����Ի�
	    	plane.drawSelf(g);
    		g.drawImage(planeImg, 600, 850, null);
    		g.drawImage(planeImg, 630, 850, null);
		}else if(plane.life == 1) {
			// �����Ի�
	    	plane.drawSelf(g);
    		g.drawImage(planeImg, 600, 850, null);
		}else {
			g.setColor(Color.white);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
			g.drawString("��Ϸ����,�Ƿ������", 0, 500);
			g.drawString("    �س�������", 0, 600);
			g.drawString("" + time3, 250, 700);
		}
    	if (plane.life == 0 && plane.conti == true) {
    		time3 = 20;
			plane.life = 3;
			plane.live = true;
			plane.x = 300;
			plane.y = 850;
			plane.conti = false;
		}
    	// boss������ʽ
    	if (enemy.fire == 3 && enemy.live == true) {// 3�׶� ���׶δӸߵ��ͣ���Ѫ������ƥ��
    		for (int i = 0; i < eBulletList.size(); i++) {
    			Bullet m = eBulletList.get(i);
    			m.drawEnemyBullet01(g);
    			m.degree = 1.5;
    			// ��ײ���
    			if (m.getRect().intersects(plane.getRect()) && isM == false) {
    				reimu();
    			}
    			// �Ƴ�Խ��Ķ���
    			if (m.y > 1000 || m.x <= 0 || m.x >= 550) {
    				eBulletList.remove(m);
    			}
    		}
		}else if (enemy.fire == 2) {// 2�׶�
			for (int i = 0; i < eBulletList.size(); i++) {
    			Bullet m = eBulletList.get(i);
    			m.drawEnemyBullet02(g);
    			// ��ײ��⣬ǧƪһ��
    			if (m.getRect().intersects(plane.getRect()) && isM == false) {
    				reimu();
    			}
    			// �Ƴ�Խ��Ķ���
    			if (m.y > 1000 || m.x <= 0 || m.x >= 550) {
    				eBulletList.remove(m);
    			}
    		}
		}else if (enemy.fire == 1) {// 1�׶�
			for (int i = 0; i < eBulletList.size(); i++) {
    			Bullet m = eBulletList.get(i);
    				m.drawEnemyBullet03(g);
    		if (m.img == eBullet05) {
    			m.degree += 0.1;
			}else if(m.img == eBullet06) {
				m.degree -= 0.1;
			}else {
				m.degree = 0;
			}
    			// ��ײ���
				if (m.getRect().intersects(plane.getRect()) && isM == false) {
					reimu();
				}
    			// �Ƴ�Խ��Ķ���
    			if (m.y > 1000 || m.x <= 0 || m.x >= 550) {
    				eBulletList.remove(m);
    			}
    		}
		}
    	// �������ϣ������е��ӵ�������
		for (int i = 0; i < bulletList.size(); i++) {
			Bullet m = bulletList.get(i);
			// �۽�����
			if (plane.focus == true && plane.life != 0) {
				if (m.img == bullet01) {
					m.drawBulletFocus01(g);
				}
				if (m.img == bullet02 && fire >= 1) {
					m.drawBulletFocus02(g);
				}
				if (m.img == bullet03 && fire >= 1) {
					m.drawBulletFocus03(g);
				}
			} else if(plane.life != 0) { // ��̬����
				if (m.img == bullet01) {
					m.drawBullet01(g);
				} else if (m.img == bullet02 && fire >= 1) {
					m.drawBullet02(g);
					m.x -= 15;
				} else if (fire >= 1) {
					m.drawBullet03(g);
					m.x += 15;
				}
			}
			// �Ƴ�Խ��Ķ���
			if (m.y <= 15 || m.x <= 0 || m.x >= 530) {
				bulletList.remove(m);
			}
			// boss��ײ��Ѫ����׶�״̬�Ĺ���
			if (m.getRect().intersects(enemy.getRect()) && enemy.live == true) {
//    	    		System.out.println("��ײ");
				enemy.hp -= 1;
				score++;
				System.out.println(enemy.hp);
//				System.out.println(enemy.fire);
				if (enemy.hp <= 0 && enemy.fire > 0) {
					eBulletList.removeAll(eBulletList);
					enemy.fire--;
					enemy.hp = 500;
					if (enemy.fire < 0) {
						enemy.fire = 0;
					}
				}
				if (enemy.fire == 0) {
					enemy.hp = 0;
					enemy.live = false;
					isV = true;
					System.out.println(isV);
				}
			}
		}
    }
    
    
    /**
     * �Ի���ײ�¼�
     */
	public void reimu() {
		isM = true;
		plane.life--;
		plane.x = 300;
		plane.y = 850;
		eBulletList.removeAll(eBulletList);
		if (plane.life <= 0) {
			plane.live = false;
			plane.x = -100;
			plane.y = -100;
		}
	}
    
	/**
	 * 
	 * @return
	 * ������ɵ���
	 */
	public static GameObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(30); // ����0��30�������
		if (type <= 10 && score < 10) { // �����Ϊ0������bee;���򷵻صл�
			return new SEnemy(sEnemyImg1, rand.nextInt(504), -35, 26, 35, 5);
		} else if (type >= 20 && score < 10) {
			return new SEnemy(sEnemyImg2, rand.nextInt(504), -35, 42, 43, 20);
		} else if (type > 10 && type < 20 && score < 10) {
			return new Power(powerImg, rand.nextInt(504), -35, 39, 39);
		}
		return new Power(powerImg, rand.nextInt(504), -35, 50, 50);
	}
    
	/**
	 * ����ˢ���볡��Ƶ��
	 */
    public void enterAction() {// 10������һ��
		flyEnteredIndex++; // ÿ10������1
		if (flyEnteredIndex % 40 == 0) {
			GameObject obj = nextOne();
			go.add(obj);
			// ��ʱ�����Ƶ��˵��ӵ�Ƶ�ʣ����ѵ㣬����ʹ����ʱ��ʱ��
			new Timer().schedule(new TimerTask() {
				public void run() {
					if (obj.img != powerImg) {
						if (obj.img == sEnemyImg2) {
							eBulletList2.add(new Bullet(eBullet04, obj.x + 20, obj.y + 35, 20, 41));
							eBulletList2.add(new Bullet(eBullet04, obj.x, obj.y + 35, 20, 41));
						} else {
							eBulletList2.add(new Bullet(eBullet04, obj.x, obj.y + 20, 20, 41));
						}
						if (obj.x < 0 || obj.hp <= 0 || score >= 10 || live == false) {
							this.cancel();
						}
					}
				}
			}, 0, 1000);
		}
	}
    
    /**
     * 
     * @param g
     * ���Ƶ���
     */
	public void paintFlyingObjects(Graphics g) {
		live = true;
		for (int i = 0; i < go.size(); i++) {
			GameObject f = go.get(i);
			f.drawSelf(g);
//			System.out.println(f.hp);
			if (f.img == powerImg) {
				if (f.getRect().intersects(plane.getRect())) {
					fire += 0.5;
					go.remove(i);
				}
			} else {
				for (int j = 0; j < bulletList.size(); j++) {
					Bullet m = bulletList.get(j);
					if (f.getRect().intersects(m.getRect())) {
						bulletList.remove(j);
						f.hp--;
						System.out.println("��ײ");
					}
				}
				if (f.hp < 0) {
					System.out.println("��ɱ");
					go.remove(i);
					score += new Random().nextInt(5) + 1;
					live = false;
				}
				if (f.getRect().intersects(plane.getRect()) && isM == false) {
					reimu();
				}
			}
		}
		// ���뻭���ٿ�ʼ���Ƶ����ӵ�
		for (int i = 0; i < eBulletList2.size(); i++) {
			Bullet m = eBulletList2.get(i);
			if (m.y > 0) {
				m.drawEnemyBullet04(g);
			}
			// ��ײ���
			if (m.getRect().intersects(plane.getRect()) && isM == false) {
				reimu();
			}
			// �Ƴ�Խ��Ķ���
			if (m.y > 1000) {
				eBulletList2.remove(m);
			}
		}
	}
    
    
	/**
	 * �����ƶ��ķ���
	 */
	public void stepAction() { // 10������һ��
		int num = time1 / 15;
		for (int i = 0; i < go.size(); i++) {
			for (int j = 0; j <= num; j++) {
				// ��ǰ������ƶ�
				go.get(i).step();
//				System.out.println(go.get(i).y);
				// �л����Ի��ӵ�����ײ���
//				System.out.println(go.get(i).hp);
				if (score >= 10 && go.isEmpty() == false) {
					if (go.get(i).img == sEnemyImg1 || go.get(i).img == sEnemyImg2) {
						go.remove(go.get(i));
					}
				}
//					if (go.get(i).getRect().intersects(plane.getRect()) && go.isEmpty() == false) {
//						go.remove(go.get(i));
//						fire += 0.5;
//					}
				}
//				else if (go.get(i).img == sEnemyImg1 || go.get(i).img == sEnemyImg2) {
//					if (bulletList.isEmpty() == false) {
//						for (int k = 0; k < bulletList.size(); k++) {
//							if (bulletList.get(k).getRect().intersects(go.get(i).getRect())) {
//								go.get(i).hp--;
//								System.out.println("��ײ");
//								bulletList.remove(k);
//							}
//							if (go.get(i).hp <= 0 || go.get(i).x >= 530 || go.get(i).x <= -50
//									|| go.get(i).y >= 550) {
//								live = false;
//							}
//						}
//						// �Ƴ�
//						if (live == false) {
//							go.remove(go.get(i));
//							score++;
//						}
//					}
//				}
//				else {
					
//				}
		}
	}
		
	/**
	 * boss�ֽ׶��в�ͬ������ʽ
	 */
	public void remiFire() {
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("ʱ��");
				if (enemy.fire == 3) {
					for (int i = 0; i < 5; i++) {
						Bullet a = new Bullet(eBullet01, enemy.x, enemy.y, 15, 21);
						Bullet b = new Bullet(eBullet02, enemy.x, enemy.y, 15, 21);
//						Bullet c = new Bullet(eBullet03, enemy.x, enemy.y, 15, 21);
						eBulletList.add(a);
						eBulletList.add(b);
//						eBulletList.add(c);
					}
				} else if (enemy.fire == 2) {
					for (int i = 0; i < 25; i++) {
						Bullet a = new Bullet(eBullet01, 100, 200, 15, 21);
						Bullet b = new Bullet(eBullet02, 250, 400, 15, 21);
						Bullet c = new Bullet(eBullet02, 450, 200, 15, 21);
						eBulletList.add(a);
						eBulletList.add(b);
						eBulletList.add(c);
					}
				} else {
					timer.cancel();
				}
			}
		}, 1000, 2000);
	}
    
	/**
	 * boss����ƶ�
	 */
	public void remiStep() {
		enemy.x = enemy.x + dx;
		enemy.y = enemy.y + dy;
		if (enemy.x >= 500) {
			dx = -10;
			dy = Math.random() * 10;
		} else if (enemy.x <= 10) {
			dx = 10;
			dy = -Math.random() * 10;
		} else if (enemy.y >= 300) {
			dy = -5;
		} else if (enemy.y <= 100) {
			dy = 5;
		}
	}

	/**
	 * 
	 * @param g 
	 * ���֣�����
	 */
	public void paintScore(Graphics g) {
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("SCORE: " + score, 600, 200);
		g.drawString("fire: " + fire, 600, 300);
	}
	
	/**
	 * 
	 * @author �ź���
	 * 500�����߳�
	 */
	class eBulletThread extends Thread {
		public void run() {
			while (true) {
				// ��ʱ�����޵�ʱ��
				if (isM == true) {
					time2++;
					if (time2 == 5) {
						time2 = 0;
						isM = false;
					}
				}
				// boss���׶��ӵ�
				if (enemy.fire == 1) {
					for (int i = 0; i < 25; i++) {
						Bullet a = new Bullet(eBullet05, enemy.x + 30, enemy.y + 30, 15, 21);
						Bullet b = new Bullet(eBullet06, enemy.x + 30, enemy.y + 30, 15, 21);
						Bullet c = new Bullet(eBullet03, enemy.x + 30, enemy.y + 30, 64, 77);
						eBulletList.add(a);
						eBulletList.add(b);
						eBulletList.add(c);
					}
				}
				// ��Ϸ��������ʱ
				if (plane.life == 0) {
					time3--;
					if (time3 <= 0) {
						time3 = 0;
					}
				}
				try {
					// �̵߳��ü��
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
    
	/**
	 * 
	 * @author �ź��� 
	 * �ӵ���ļ��ʱ����ƣ����߳�
	 */
	class BulletThread extends Thread {
		public void run() {
			while (true) {
				// �����ӵ�
				if (plane.fire == true && bulletList.size() <= 60 && plane.live == true) {
					// ����������С�Ա�֤ÿһ���ӵ�����ͬ
					// ָ����һ�ε��ӵ���
					for (int i = 0; i < 3; i++) {
						Bullet b = new Bullet(bullet01, plane.x, plane.y, 20, 20);
						Bullet c = new Bullet(bullet02, plane.x, plane.y, 22, 21);
						Bullet d = new Bullet(bullet03, plane.x, plane.y, 22, 21);
						bulletList.add(b);
						bulletList.add(c);
						bulletList.add(d);
						Thread thread01 = new Thread(b);
						thread01.start();
						Thread thread02 = new Thread(c);
						thread02.start();
						Thread thread03 = new Thread(d);
						thread03.start();
					}
				}
				try {
					// �̵߳��ü��
					Thread.sleep(190);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

    
    
	/**
	 * 
	 * @author �ź��� 
	 * �ڲ��� ���̵߳���
	 */
	class PaintThread extends Thread {
		public void run() {
			while (true) {
//				System.out.println(isM);
//				System.out.println(time2);
				// ��ʱ
				time = time + 0.01;
				time1 = (int) time;
				if (score >= 10) {
					remiStep();
				}
				stepAction();
				enterAction();
				// ʤ����ɽ�������
				if (plane.restart == true && isV == true) {
					bulletList.removeAll(bulletList);
					eBulletList.removeAll(eBulletList);
					eBulletList2.removeAll(eBulletList2);
					go.removeAll(go);
					score = 0;
					fire = 0;
					flyEnteredIndex = 0;
					time = 0;
					time1 = 0;
					time2 = 0;
					plane.life = 3;
					plane.x = 300;
					plane.y = 850;
					enemy.fire = 3;
					enemy.live = false;
					dispose();
					MyGameFrame gw = new MyGameFrame();
					gw.launchFrame();
					plane.restart = false;
				}
				// �ػ� update -> paint
				repaint();
				try {
					// �̵߳��ü��
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				if (enemy.live == true) {
//					try {
//						sleep(3000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					enemy.live = false;
//				}
			}
		}
	}
    
	/**
	 * 
	 * @author �ź��� 
	 * �ڲ��� ���������¼�
	 */
	class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (time3 != 0) {
				plane.addDirection(e);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (time3 != 0) {
				plane.minusDirection(e);
			}
		}

	}
    
    
    
    
	/**
	 * �����ڷ���
	 */
	public void launchFrame() {
		// �����ڲ�������
		setTitle("�|����ħ��");
		setSize(850, 1000);
		// ������ʾ
		setVisible(true);
		// ��ֹ��С����
		setResizable(false);
		// �����Ļ�ֱ���
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// ���ô���λ��
		setLocation((int)screenSize.getWidth()/4, (int)screenSize.getHeight()/4-240);
		// ���ùر�����
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
		// ����boss����
		remiFire();
		// �����ػ��߳�
		new PaintThread().start(); 
		new BulletThread().start();
		new eBulletThread().start();
		// ���ü��̼���
		addKeyListener(new KeyMonitor());
		
		
		
	}
	
	// main����
//	public static void main(String[] args) {
//		MyGameFrame f = new MyGameFrame();
//		f.launchFrame();
//		
//		
//	}
	
	
	/**
	 * ˫���壬������Frame����������JFrame
	 */
	public void update(Graphics g) {
		if (offScreenImage == null)
			// ��Ϸ���ڵĿ�Ⱥ͸߶�
			offScreenImage = this.createImage(850, 1000);
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
}
