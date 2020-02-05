package com.tusky.frame;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tusky.thread.GameThread;


public class MyJFrame extends JFrame{

	//定义的属性 采用set注入的方式
		private KeyListener keyListener;//键盘监听
		private MouseListener mouseListener;//鼠标监听
		private MouseMotionListener mouseMotionListener;//鼠标动作监听
		private JPanel jp; //包
		
		public MyJFrame() {
			// 构造函数（一般继承与父类的方法，构造中调用父类的初始化等不写在构造方法）
			init();

		}
		//初始化方法:构造方法无法被继承，而init方法可以被继承（即Init方法可以被重写）
		public void init() {
			Image icon = Toolkit.getDefaultToolkit().getImage("img/tubiao.jpg"); 
			setIconImage(icon);
			this.setTitle("头文字C：超速革命");
			this.setSize(600, 800);//设置大小
			this.setResizable(false);//设置窗体不可修改大小
			//以上初始化可从配置文件读取设置
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭状态
			this.setLocationRelativeTo(null);//居中
//			this.setBackground(Color.BLACK);
			
			
		}
		
		//绑定监听
		public void addListener() {
			// TODO 自动生成的方法存根
			if (keyListener !=null) {//如果键盘监听不为空，则添加键盘监听
				this.addKeyListener(keyListener);
			}
			if (mouseListener !=null) {
				this.addMouseListener(mouseListener);
			}
			if(mouseMotionListener !=null) {
				this.addMouseMotionListener(mouseMotionListener);
			}
		}
		//采用set注入的方式
		public void setKeyListener(KeyListener keyListener) {
			this.keyListener = keyListener;
		}
		public void setMouseListener(MouseListener mouseListener) {
			this.mouseListener = mouseListener;
		}
		public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
			this.mouseMotionListener = mouseMotionListener;
		}
		public void setJp(JPanel jp) {
			this.jp = jp;
		}
		//画板绑定
		public void addJPanels() {
			if(jp!=null)
				this.add(jp);
		/*	else 
				throw new RuntimeException("游戏初始化加载失败");*/
		}
		public void start() {
			//线程启动...
			//
			GameThread gt=new GameThread();
			gt.start();
			
			//线程不只一个，界面刷新线程启动,jp跟线程无关系，但底层子类MyJPanel跟线程有关系
			if(jp instanceof Runnable) {//检查jp引用指向的实体对象是不是Runnable的子类
				new Thread((Runnable)jp).start();//
			}
			
			
			this.setVisible(true);//窗体显示
		}
}
