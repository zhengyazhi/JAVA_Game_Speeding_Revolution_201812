package com.tusky.model.vo;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.tusky.frame.MyJpanel;
import com.tusky.main.GameStart;
import com.tusky.model.load.ElementLoad;
import com.tusky.model.manager.ElementManager;
import com.tusky.thread.GameThread;



public class Enemy extends SuperElement{

	private int hp;                //血量
	private ImageIcon imageIcon;
	private String name;//敌人种类
	private String FireType;       //子弹类型
	
	public Enemy() {
		super();
	}

	public Enemy(int x, int y, int w, int h,int hp,String FireType,ImageIcon imageIcon) {
		super(x, y, w, h);
		this.imageIcon=imageIcon;
		this.FireType=FireType;
		this.hp=hp;
	}
/*	
	public static Enemy createEnemy(String str) {
		String [] arr = str.split(",");
		int x=Integer.parseInt(arr[2]);
		int y=Integer.parseInt(arr[3]);
		int w=Integer.parseInt(arr[4]);
		int h=Integer.parseInt(arr[5]);
		int hp=1;
		ImageIcon imageIcon=ElementLoad.getElementLoad().getMap().get(arr[0]);
		Enemy enemy=null;
		switch(arr[1]) {//在配置文件中可以给予类型，为了让敌人子弹、飞行方式不一样
		case "enemyA":
			enemy=new Enemy(x, y, w, h,hp, arr[1],imageIcon);//新建一个子弹不一样的敌人
			break;
		case "enemyB":
			enemy=new Enemy(x, y, w, h,hp,arr[1], imageIcon);//新建一个子类
			break;
		default:
			enemy=new Enemy(x,y,w,h,hp,arr[1],imageIcon);		
		}
		
		return enemy;
	}
*/
	public static Enemy createEnemy() {
		Enemy enemy=null;
		int i=(int)(Math.random()*8);
		String url="img/enemy/enemy00"+i+".png";
		ImageIcon imageIcon=new ImageIcon(url);
		
		int j=(int)(Math.random()*3);
		String url2="img/fire/"+j+".png";
		
		//产生一个20~400的数作为敌人的x坐标
		int min=20;int max=400; 
		int x=(int)min+(int)(Math.random()*(max-min));
 		
		enemy=new Enemy(x,0,75,125,1,url2,imageIcon);
		return enemy;
	        
	      
	}
	@Override
	public void showElement(Graphics g) {
		super.showElement(g);
		g.drawImage(imageIcon.getImage(), getX(), getY(), getW(), getH(), null);
	}

	@Override
	public void move() {
		setY(getY()+7);
		if(getY()>675)
			setVisible(false);
		//超过边界的敌人要销毁 destroy()
	}
	
	//重写父类的模板
	public void update() {
		super.update();//若没有这句话，就是重新制定新模板
/*		int min=2;    //定义随机数的最小值
        int max=80;    //定义随机数的最大值
        //产生一个2~40的数
        int i=(int)min+(int)(Math.random()*(max-min));
		if(i==2)addFire(FireType);//追加*/
		int j=(int)GameThread.time;
		int i=(int)GameStart.FlagTime;
		if(i/2!=0) {
			if(j%15==0&&j%10==0) {
				addFire(FireType);
			}
		}
			
		
		
	}
	
	//在ElementManage里创建子弹集合
	//从资源管理器获取子弹集合
	//依据子弹类型不同，获取为String
	private void addFire(String str) {
		
	//从资源管理器获取子弹集合
	//依据子弹类型不同，获取为String
		List<SuperElement> list=
			ElementManager.getManager().getElementList("EnemyFire");
			
		if(list==null) {
			list=new ArrayList<>();
		}
		//String类子弹类型
		list.add(EnemyFire.createEnemyFire(getX()-50, getY(), str));
			
		ElementManager.getManager().getMap().put("EnemyFire", list);

		}

	@Override
	public void destroy() {
		// 销毁的时候，需要创建爆炸对象 添加入 到爆炸的List集合中,自己去扩展
		//if(!isVisible())
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFireType() {
		return FireType;
	}

	public void setFireType(String fireType) {
		FireType = fireType;
	}
	
	public void setVisible(boolean visible) {
		if(getHp()!=0) {
			setHp(getHp()-1);
			this.visible = visible;
		}
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
