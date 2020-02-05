package com.tusky.model.vo;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class EnemyFire extends SuperElement{
	
	private ImageIcon imageIcon;
	
	public EnemyFire() {
		super();
	}

	public EnemyFire(int x, int y, int w, int h,ImageIcon imageIcon) {
		super(x, y, w, h);
		this.imageIcon=imageIcon;
	}
	
	public static EnemyFire createEnemyFire(int x,int y,String str) {
		ImageIcon imageIcon = new ImageIcon(str);//从参数获取子弹类型
		return new EnemyFire(x,y,28,42,imageIcon);
	}

	@Override
	public void showElement(Graphics g) {
		g.drawImage(imageIcon.getImage(), getX()+70, getY()+150, getW(), getH(), null);
	}

	@Override
	public void move() {
		setY(getY()+10);
		if(getY()>670)
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
}
