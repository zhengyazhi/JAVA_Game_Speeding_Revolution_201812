package com.tusky.model.vo;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.tusky.main.GameStart;
import com.tusky.model.manager.ElementManager;
import com.tusky.thread.GameThread;

public class Boss extends SuperElement{
	
	public static int hp;                //血量
	private ImageIcon imageIcon;
	private String name;//敌人种类
	private String FireType;       //子弹类型
	private boolean flag;
	

	public Boss() {
		super();
	}

	public Boss(int x, int y, int w, int h,int hp,ImageIcon imageIcon) {
		super(x, y, w, h);
		this.imageIcon=imageIcon;
		this.hp=hp;
	}

	public static Boss createBoss(int num) {
		
		Boss boss=null;
		int hp = 0;
		switch (num) {
		case 1:
			hp=30;
			break;
		case 2:
			hp=40;
			break;
		case 3:
			hp=50;
			break;
		case 4:
			hp=70;
			break;
		}
		String url="img/boss/boss00"+num+".png";
		ImageIcon imageIcon=new ImageIcon(url);
		
		int j=(int)(Math.random()*2);
		String url2="img/fire/1"+j+".png";
		
		//产生一个20~400的数作为敌人的x坐标
		int min=20;int max=400; 
		int x=(int)min+(int)(Math.random()*(max-min));
 		
		boss=new Boss(x,-300,300,300,hp,imageIcon);
		return boss;
	}

	@Override
	public void showElement(Graphics g) {
		g.drawImage(imageIcon.getImage(), getX(), getY(), getW(), getH(), null);
	}

	@Override
	public void move() {
		//方向判定
		if(getX()>=300)
			flag=false;
		if(getX()<=-20)
			flag=true;
		//向下移动
		if(getY()<=15)
			setY(getY()+2);
		//左右移动
		if(getY()>15) {
			if (flag) {
				setX(getX()+3);
			}else {
				setX(getX()-3);
			}
		}
	}
	
	//重写父类的模板
	public void update() {
		super.update();//若没有这句话，就是重新制定新模板
		int j=(int)GameThread.time;
		int i=(int)GameStart.FlagTime;
		if(i%2!=0&&j%13==0) {//奇数
			addFire(13);
		}
		if(i%2==0&&j%27==0) {//偶数
			addFire(14);
		}
	}
	private void addFire(int i) {
			
		List<SuperElement> list=
				ElementManager.getManager().getElementList("EnemyFire");
					
		if(list==null) {
			list=new ArrayList<>();
		}
		String str="img/fire/"+i+".png";
		//String类子弹类型
		list.add(EnemyFire.createEnemyFire(getX()+140, getY()+20, str));
		list.add(EnemyFire.createEnemyFire(getX()-10, getY()-50, str));
					
		ElementManager.getManager().getMap().put("EnemyFire", list);
	}
				
			
			
	

	@Override
	public void destroy() {
		if(hp<=0) {
			setVisible(false);
		}
		
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	

}
