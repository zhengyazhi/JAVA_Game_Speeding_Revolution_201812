package com.tusky.main;

import com.tusky.frame.MyJFrame;
import com.tusky.frame.MyJpanel;
import com.tusky.thread.GameListener;
import com.tusky.thread.GameThread;

public class GameStart {
	//游戏开始时间
	public static long StartTime;
	//实时程序运行时间
    public static long NowTime=0;
    //实际的运行时间
    public static long FlagTime=NowTime;
	
	//整个游戏的入口，启动
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//资源加载
		
		//窗体加载（自动化...）
		MyJFrame jf=new MyJFrame();
		MyJpanel jp=new MyJpanel();
		jf.setJp(jp);//画板注入
		GameListener listener=new GameListener();
		jf.setKeyListener(listener);
		jf.setJp(jp);//画板注入
		
		//监听加载
		jf.addListener();
		jf.addJPanels();//加载jp
			
//		StartTime=GameThread.getCurrentTime();
		StartTime=System.currentTimeMillis();
		//游戏启动（开始）
		jf.start();
	}
	
	/*
	 * 1.定义一个VO类，继承superElement
	 * 2.在工厂中做实例化
	 * 3.配置文件中进行配置
	 * 4.如果需要监听，请在监听中写代码
	 * 
	 * */

}
