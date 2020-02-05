package com.tusky.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tusky.main.GameStart;
import com.tusky.model.manager.ElementManager;
import com.tusky.model.vo.Player;
import com.tusky.model.vo.SuperElement;
import com.tusky.thread.GameThread;


public class MyJpanel extends JPanel implements Runnable{
	
//	public static int time=0;//敌人子弹控制
//	public static int score=0;
	private ImageIcon background1=new ImageIcon("img/background1.png");
	private ImageIcon background2=new ImageIcon("img/background1.png");
	private int moveY=0;   //向下读
	private int moveYY=800;//向上读
	
	public static int ifstart=0;
	
	/*
	 * 1.这个paint是由 底层自动调用的，我们重写父类的方法
	 * 2.这个方法只会执行一次，如果不持续调用就不会持续执行
	 * 
	 * 帧：50-100毫秒每帧 即20-10帧/秒
	 *
	 * 
	 * */
	
	@Override//作用：用来显示
	public void paint(Graphics g) {
		super.paint(g);
		//给一个判定值，当前关卡在什么状态；也可以使用枚举类型（记录当前状态）GameType枚举写在manager里
		if(ifstart==0) {
			ImageIcon imageIcon=new ImageIcon("img/startPage.png");
			g.drawImage(imageIcon.getImage(), 0, 0, 600,800,null);
		}
		else if(ifstart==1){
			//1.前动画 给定一个boolean值， 根据值来启动前动画，由自动化Thread负责提供boolean值
//			this.setBackground(Color.WHITE);
			SetbackGround(g);
			//2.gameRuntime
			gameRunTime(g);//画笔
			//3.衔接动画
		}
		else if(ifstart==2){
			GameOver(g);
		}
	}
	
	private void GameOver(Graphics g) {
		ImageIcon img=new ImageIcon("img/end0001.png");
		g.drawImage(img.getImage(),0,0,600,800,null);
		
		
		g.setColor(Color.WHITE);
		
		g.setFont(new Font("黑体", Font.ITALIC, 60));
		g.drawString("Your Score:", 55, 120);
		g.drawString(""+Player.score, 400, 120);
		
		g.setFont(new Font("楷体", Font.BOLD, 60));
		g.setColor(Color.red);
		g.drawString("游戏结束", 180, 250);
		
		g.setFont(new Font("楷体", Font.ITALIC, 25));
		g.setColor(Color.WHITE);
		g.drawString("Click esc Restart", 188, 300);
	}

	private void SetbackGround(Graphics g) {
/*		String url="img/beijing.png";
		ImageIcon background=new ImageIcon(url);
		JLabel jLabel=new JLabel(background);
		jLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.add(jLabel);
		jLabel.setLayout(null);
	*/	
		if(GameStart.FlagTime==80) {
			ImageIcon background1=new ImageIcon("img/background2.png");
			ImageIcon background2=new ImageIcon("img/background2.png");
			setBackground1(background1);
			setBackground2(background2);
			
		}
		if(GameStart.FlagTime==160) {
			ImageIcon background1=new ImageIcon("img/background4.png");
			ImageIcon background2=new ImageIcon("img/background4.png");
			setBackground1(background1);
			setBackground2(background2);
		}
		if(GameStart.FlagTime==260) {
			ImageIcon background1=new ImageIcon("img/background3.png");
			ImageIcon background2=new ImageIcon("img/background3.png");
			setBackground1(background1);
			setBackground2(background2);
		}
		
		moveY=(moveY>=800)?0:moveY;
		moveYY=(moveYY<=0)?800:moveYY;
		g.drawImage(background2.getImage(), 
				getX(), getY(), 
				getX()+600, getY()+800, 
				0, moveYY, 
				600, 800+moveYY, 
				null);
		g.drawImage(background1.getImage(), 
				getX(), getY(), 
				getX()+600, getY()+800, 
				0, -moveY, 
				600, 800-moveY, 
				null);
		int x=20;
		int y=60;
		ImageIcon score=new ImageIcon("img/score.png");
		g.drawImage(score.getImage(),20,30,100,40,null);
		ImageIcon hp=new ImageIcon("img/hp.png");
		g.drawImage(hp.getImage(),20,70,100,38,null);
		g.setColor(Color.RED);
		Font font=new Font(Font.DIALOG,Font.BOLD,30);
		g.setFont(font);
		g.drawString(" : "+Player.score, x+100, y);
		y+=40;
		g.drawString(" : "+Player.hp, x+100, y);
		
	}

	private void gameRunTime(Graphics g) {
		// 游戏进行时
	/*	List<SuperElement>list = ElementManager.getManager().getElementList("XX");
		
		g.drawString("*", 100, 100);//由元素管理器提供数据
		太麻烦了
		*/
		
		Map<String, List<SuperElement>> map=
				ElementManager.getManager().getMap();
		Set<String> set=map.keySet();
		
		
		/*
		 * List<String> list=new ArrayList<>();
		list.addAll(set);
		Collections.sort(list);//自然顺序
		Collections.sort(list,"比较器对象");//自定义顺序
		知识点：java集合对象的排序规则和2个接口有关
		 * 
		 * 
		 */
		
		
		for(String key:set) {
			List<SuperElement> list=map.get(key);
			for(int i=0;i<list.size();i++) {
				list.get(i).showElement(g);
			}
		}
		moveY+=5;
		moveYY-=5;
		
	}

	/*
	 * 什么是重写？
	 * 1.是有继承的类与类之间的语法现象（多态的一种实现）
	 * 2.重写的方法必须和父类的方法的签名一样（返回值，方法名称，参数序列）
	 * 3.重写的方法访问修饰符只可以比父类的更加开放，不可以比父类的更加封闭(如父类：public，子类:private)
	 * 4.重写的方法抛出的异常不可以比父类的更加开放（父类没抛异常，子类不能抛异常，若父类抛了，子类抛的不能更开放）
	 * 
	 * */
	//使用多线程
	@Override
	public void run() {
		while(true) {//死循环：界面会不停止地刷新
			//线程的休眠，为了做到每秒刷新10-20次
			try {
				Thread.sleep(50);//1秒刷新10次，50->20次
			} catch (InterruptedException e) {
				// 不能向上抛出throws，因为Runnable不允许run方法抛
				e.printStackTrace();
			}
/*			System.out.println("分数是："+score);
			time++;
			if(time>=1000) {
				time=0;
			}*/
			
			this.repaint();//要求面板再次刷新，面板JPanel自己的方法
		}
		
	}

	public ImageIcon getBackground1() {
		return background1;
	}

	public void setBackground1(ImageIcon background1) {
		this.background1 = background1;
	}

	public ImageIcon getBackground2() {
		return background2;
	}

	public void setBackground2(ImageIcon background2) {
		this.background2 = background2;
	}
	
	
	
}
