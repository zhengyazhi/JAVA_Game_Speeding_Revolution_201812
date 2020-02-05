package com.tusky.model.vo;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.tusky.main.GameStart;
import com.tusky.model.load.ElementLoad;
import com.tusky.model.manager.ElementManager;





public class Player extends SuperElement{
	
	public static int hp;                //血量
	public static int score;             //得分
	private boolean attack;        //攻击状态，默认为false
	private ImageIcon ImageIcon;
	private int moveX;
	private int moveY;
	private String name;           //当前玩家名称
	private String FireType;       //子弹类型
	private boolean top;           //玩家移动方向：上
	private boolean down;          //玩家移动方向：下
	private boolean left;          //玩家移动方向：左
	private boolean right;         //玩家移动方向：右

	
	public Player(int x,int y,int w,int h,int hp,String FireType,ImageIcon ImageIcon) {
		super(x,y,w,h);
		this.ImageIcon=ImageIcon;
		this.hp=hp;
		score=0;
		top=false;
		down=false;
		left=false;
		right=false;
		attack=false;
		this.FireType=FireType;
	}
	
	public static Player createPlayer(String str) {
		
		String [] arr=str.split(",");
		int hp=3;
		int x = Integer.parseInt(arr[2]);
		int y = Integer.parseInt(arr[3]);
		int w = Integer.parseInt(arr[4]);
		int h = Integer.parseInt(arr[5]);
		String FireType=arr[1];//子弹类型
		ImageIcon ImageIcon=ElementLoad.getElementLoad().getMap().get(arr[0]);
		return new Player(x, y, w, h,hp, FireType,ImageIcon);
	}
	
	//考虑赛车要不要变形态
	public void showElement(Graphics g) {
		
		if(GameStart.FlagTime==100) {
			ImageIcon=new ImageIcon("img/player/player006.png");
		}
		if(GameStart.FlagTime==180) {
			ImageIcon=new ImageIcon("img/player/player005.png");
		}
		if(GameStart.FlagTime==260) {
			ImageIcon=new ImageIcon("img/player/player007.png");
		}
		g.drawImage(ImageIcon.getImage(), 
				getX(), getY(),getW(),getH(), null);
	}

	@Override//move不要传参，监听只改变状态，自动化来改变值
	public void move() {
		if(top) {
			if(getY()>0)
				setY(getY()-8);
		}
		if(down) {
			if(getY()<630)
				setY(getY()+8);
		}
		if(left) {
			if(getX()>0) 
				setX(getX()-8);
		}
		if(right) {
			if(getX()<500)
				setX(getX()+8);
		}
	}
	
	//重写父类的模板
	public void update() {
		super.update();//若没有这句话，就是重新制定新模板
		updateImage();
		addFire(FireType);//追加
//		System.out.println("血量还有"+getHp());
//		System.out.println("分数是："+getScore());
/*		if(GameStart.FlagTime==5) {
			setImageIcon(new ImageIcon("img/player/player006.png"));
		}//换车车入口*/
	}

	//在ElementManage里创建子弹集合
	//从资源管理器获取子弹集合
	//依据子弹类型不同，获取为String
	private void addFire(String str) {
		if(!attack)
			return;
		
		//从资源管理器获取子弹集合
		//依据子弹类型不同，获取为String
		List<SuperElement> list=
				ElementManager.getManager().getElementList("PlayerFire");
		
		if(list==null) {
			list=new ArrayList<>();
		}
		//String类子弹类型
		list.add(PlayerFire.createPlayerFire(getX()+18, getY(), str));
		
		ElementManager.getManager().getMap().put("PlayerFire", list);
		attack=false;//每按一次 只能发射一颗子弹
	}
	
	

	//后期打算 车要变形的时候再调用
	private void updateImage() {
		
		
	}

	@Override
	//hp为0后死亡
	public void destroy() {
		if(getHp()<=0) {
			setVisible(false);
		}
	}

	public void setVisible(boolean visible) {
/*		if(getHp()!=0) {
			setHp(getHp()-1);
		}
		else {
			this.visible = visible;
		}*/
		this.visible = visible;
	}
	
	
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isAttack() {
		return attack;
	}

	public void setAttack(boolean attack) {
		this.attack = attack;
	}

	public ImageIcon getImageIcon() {
		return ImageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		ImageIcon = imageIcon;
	}

	public int getMoveX() {
		return moveX;
	}

	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}

	public int getMoveY() {
		return moveY;
	}

	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public String getFireType() {
		return FireType;
	}

	public void setFireType(String fireType) {
		FireType = fireType;
	}
	
	
}
