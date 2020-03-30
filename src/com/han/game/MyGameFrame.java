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
 * 主窗口
 * @author 张涵霖
 *
 */
public class MyGameFrame extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image offScreenImage = null;
	// 多敌人多子弹集合
	private ArrayList<GameObject> go = new ArrayList<GameObject>();
	// 加载图片
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
	// 背景2张叠加长度循环
	int back_y=-bgImg.getHeight(null)+bgImg.getHeight(null);
	// 初始变量
	static double time = 0.00;
    static int time1 = 0;
    static int time2 = 0;
    static int time3 = 20;
    static int score = 0;
    static double fire = 0;
	int flyEnteredIndex = 0;
	/**
	 * 道中敌人是否存活
	 */
    boolean live = true;
    /**
     * 是否无敌
     */
    boolean isM = false;
    /**
     * 是否胜利
     */
    boolean isV = false;
	// 移动增量
	int dx = 10;
	double dy = 0;
	// 定时器
	Timer timer = new Timer();
//	Timer timer2 = new Timer();
//	Timer timer3 = new Timer();
	// 加载初始图片对象
    Plane plane = new Plane(planeImg, 300, 850, 12, 32);
    Enemy enemy = new Enemy(enemyImg, 300, 150, 71, 100);
    // 子弹集合
    ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    ArrayList<Bullet> eBulletList = new ArrayList<Bullet>();
    ArrayList<Bullet> eBulletList2 = new ArrayList<Bullet>();
    
    
	// 绘制
    @Override
    public void paint(Graphics g) {
    	// 绘制背景，无限循环移动
		g.drawImage(bgImg, 0, back_y++, null);
		g.drawImage(bgImg, 0, back_y - bgImg.getHeight(null), null);
		if (back_y == bgImg.getHeight(null)) {
			back_y = 0;
		}
    	// 复活无敌5秒
		if (isM) {
			g.drawImage(dunImg, (int)plane.x -50, (int)plane.y - 40, null);
		}
		
		// 画分
    	paintScore(g);
    	// 画敌人
		paintFlyingObjects(g);
    	// boss入场条件
    	if (score >= 10 && isV == false) {
    		enemy.live = true;
    		enemy.drawSelf(g);
    		g.drawRect(30, 100, 500, 10);
    		 // boss血条阶段管理
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
			g.drawString("游戏胜利，恭喜恭喜", 0, 500);
			g.drawString("   按Y重置游戏", 0, 600);
		}
    	// 自机生命数管理
    	if (plane.life == 3) {
    		// 绘制自机
        	plane.drawSelf(g);
    		g.drawImage(planeImg, 600, 850, null);
    		g.drawImage(planeImg, 630, 850, null);
    		g.drawImage(planeImg, 660, 850, null);
		}else if (plane.life == 2) {
			// 绘制自机
	    	plane.drawSelf(g);
    		g.drawImage(planeImg, 600, 850, null);
    		g.drawImage(planeImg, 630, 850, null);
		}else if(plane.life == 1) {
			// 绘制自机
	    	plane.drawSelf(g);
    		g.drawImage(planeImg, 600, 850, null);
		}else {
			g.setColor(Color.white);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
			g.drawString("游戏结束,是否继续？", 0, 500);
			g.drawString("    回车键继续", 0, 600);
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
    	// boss攻击方式
    	if (enemy.fire == 3 && enemy.live == true) {// 3阶段 ：阶段从高到低，与血条数相匹配
    		for (int i = 0; i < eBulletList.size(); i++) {
    			Bullet m = eBulletList.get(i);
    			m.drawEnemyBullet01(g);
    			m.degree = 1.5;
    			// 碰撞检测
    			if (m.getRect().intersects(plane.getRect()) && isM == false) {
    				reimu();
    			}
    			// 移除越界的对象
    			if (m.y > 1000 || m.x <= 0 || m.x >= 550) {
    				eBulletList.remove(m);
    			}
    		}
		}else if (enemy.fire == 2) {// 2阶段
			for (int i = 0; i < eBulletList.size(); i++) {
    			Bullet m = eBulletList.get(i);
    			m.drawEnemyBullet02(g);
    			// 碰撞检测，千篇一律
    			if (m.getRect().intersects(plane.getRect()) && isM == false) {
    				reimu();
    			}
    			// 移除越界的对象
    			if (m.y > 1000 || m.x <= 0 || m.x >= 550) {
    				eBulletList.remove(m);
    			}
    		}
		}else if (enemy.fire == 1) {// 1阶段
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
    			// 碰撞检测
				if (m.getRect().intersects(plane.getRect()) && isM == false) {
					reimu();
				}
    			// 移除越界的对象
    			if (m.y > 1000 || m.x <= 0 || m.x >= 550) {
    				eBulletList.remove(m);
    			}
    		}
		}
    	// 遍历集合，把其中的子弹画出来
		for (int i = 0; i < bulletList.size(); i++) {
			Bullet m = bulletList.get(i);
			// 聚焦火力
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
			} else if(plane.life != 0) { // 常态火力
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
			// 移除越界的对象
			if (m.y <= 15 || m.x <= 0 || m.x >= 530) {
				bulletList.remove(m);
			}
			// boss碰撞后血条与阶段状态的管理
			if (m.getRect().intersects(enemy.getRect()) && enemy.live == true) {
//    	    		System.out.println("碰撞");
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
     * 自机碰撞事件
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
	 * 随机生成敌人
	 */
	public static GameObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(30); // 生成0到30的随机数
		if (type <= 10 && score < 10) { // 随机数为0，返回bee;否则返回敌机
			return new SEnemy(sEnemyImg1, rand.nextInt(504), -35, 26, 35, 5);
		} else if (type >= 20 && score < 10) {
			return new SEnemy(sEnemyImg2, rand.nextInt(504), -35, 42, 43, 20);
		} else if (type > 10 && type < 20 && score < 10) {
			return new Power(powerImg, rand.nextInt(504), -35, 39, 39);
		}
		return new Power(powerImg, rand.nextInt(504), -35, 50, 50);
	}
    
	/**
	 * 敌人刷新入场的频率
	 */
    public void enterAction() {// 10毫秒走一次
		flyEnteredIndex++; // 每10毫秒增1
		if (flyEnteredIndex % 40 == 0) {
			GameObject obj = nextOne();
			go.add(obj);
			// 定时器控制敌人的子弹频率，超难点，并且使用临时定时器
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
     * 绘制敌人
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
						System.out.println("碰撞");
					}
				}
				if (f.hp < 0) {
					System.out.println("击杀");
					go.remove(i);
					score += new Random().nextInt(5) + 1;
					live = false;
				}
				if (f.getRect().intersects(plane.getRect()) && isM == false) {
					reimu();
				}
			}
		}
		// 进入画面再开始绘制敌人子弹
		for (int i = 0; i < eBulletList2.size(); i++) {
			Bullet m = eBulletList2.get(i);
			if (m.y > 0) {
				m.drawEnemyBullet04(g);
			}
			// 碰撞检测
			if (m.getRect().intersects(plane.getRect()) && isM == false) {
				reimu();
			}
			// 移除越界的对象
			if (m.y > 1000) {
				eBulletList2.remove(m);
			}
		}
	}
    
    
	/**
	 * 敌人移动的方法
	 */
	public void stepAction() { // 10毫秒走一次
		int num = time1 / 15;
		for (int i = 0; i < go.size(); i++) {
			for (int j = 0; j <= num; j++) {
				// 当前对象的移动
				go.get(i).step();
//				System.out.println(go.get(i).y);
				// 敌机与自机子弹的碰撞检测
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
//								System.out.println("碰撞");
//								bulletList.remove(k);
//							}
//							if (go.get(i).hp <= 0 || go.get(i).x >= 530 || go.get(i).x <= -50
//									|| go.get(i).y >= 550) {
//								live = false;
//							}
//						}
//						// 移除
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
	 * boss分阶段有不同攻击方式
	 */
	public void remiFire() {
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("时间");
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
	 * boss随机移动
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
	 * 画分，画命
	 */
	public void paintScore(Graphics g) {
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("SCORE: " + score, 600, 200);
		g.drawString("fire: " + fire, 600, 300);
	}
	
	/**
	 * 
	 * @author 张涵霖
	 * 500毫秒线程
	 */
	class eBulletThread extends Thread {
		public void run() {
			while (true) {
				// 计时器，无敌时间
				if (isM == true) {
					time2++;
					if (time2 == 5) {
						time2 = 0;
						isM = false;
					}
				}
				// boss最后阶段子弹
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
				// 游戏结束倒计时
				if (plane.life == 0) {
					time3--;
					if (time3 <= 0) {
						time3 = 0;
					}
				}
				try {
					// 线程调用间隔
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
    
	/**
	 * 
	 * @author 张涵霖 
	 * 子弹类的间隔时间控制，多线程
	 */
	class BulletThread extends Thread {
		public void run() {
			while (true) {
				// 绘制子弹
				if (plane.fire == true && bulletList.size() <= 60 && plane.live == true) {
					// 限制容器大小以保证每一次子弹数相同
					// 指按键一次的子弹量
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
					// 线程调用间隔
					Thread.sleep(190);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

    
    
	/**
	 * 
	 * @author 张涵霖 
	 * 内部类 多线程调用
	 */
	class PaintThread extends Thread {
		public void run() {
			while (true) {
//				System.out.println(isM);
//				System.out.println(time2);
				// 计时
				time = time + 0.01;
				time1 = (int) time;
				if (score >= 10) {
					remiStep();
				}
				stepAction();
				enterAction();
				// 胜利后可进行重置
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
				// 重画 update -> paint
				repaint();
				try {
					// 线程调用间隔
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
	 * @author 张涵霖 
	 * 内部类 按键监听事件
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
	 * 主窗口方法
	 */
	public void launchFrame() {
		// 主窗口参数设置
		setTitle("東方红魔乡");
		setSize(850, 1000);
		// 窗口显示
		setVisible(true);
		// 禁止大小调整
		setResizable(false);
		// 获得屏幕分辨率
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置窗口位置
		setLocation((int)screenSize.getWidth()/4, (int)screenSize.getHeight()/4-240);
		// 设置关闭运行
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
		// 加载boss火力
		remiFire();
		// 启动重画线程
		new PaintThread().start(); 
		new BulletThread().start();
		new eBulletThread().start();
		// 设置键盘监听
		addKeyListener(new KeyMonitor());
		
		
		
	}
	
	// main方法
//	public static void main(String[] args) {
//		MyGameFrame f = new MyGameFrame();
//		f.launchFrame();
//		
//		
//	}
	
	
	/**
	 * 双缓冲，适用于Frame，不适用于JFrame
	 */
	public void update(Graphics g) {
		if (offScreenImage == null)
			// 游戏窗口的宽度和高度
			offScreenImage = this.createImage(850, 1000);
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
}
