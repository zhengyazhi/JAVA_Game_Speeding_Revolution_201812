package com.tusky.model.vo;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tusky.model.load.ElementLoad;

public class People extends SuperElement{

	private int hp;                //血量
	private ImageIcon imageIcon;

	
	
	
	public People() {
		super();
	}

	public People(int x, int y, int w, int h,int hp,ImageIcon imageIcon) {
		super(x, y, w, h);
		this.imageIcon=imageIcon;
		this.hp=hp;
	}
	
	public static People createPeople() {
		People people=null;
		
		int i=(int)(Math.random()*4);
		String url="img/people/people00"+i+".png";
		ImageIcon imageIcon=new ImageIcon(url);
		//产生一个20~400的数作为平民车的x坐标
		int min=20;int max=400; 
		int x=(int)min+(int)(Math.random()*(max-min));
	 		
		people=new People(x,0,75,125,1,imageIcon);	
		return people;
		
	}

	@Override
	public void showElement(Graphics g) {
		g.drawImage(imageIcon.getImage(), getX(), getY(), getW(), getH(), null);	
	}

	@Override
	//暂时设置平民车辆只能从对面过来
	public void move() {
		setY(getY()+7);
		if(getY()>600)
			setVisible(false);
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
	
	public void setVisible(boolean visible) {
		if(getHp()!=0) {
			setHp(getHp()-1);
		}
		else {
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
