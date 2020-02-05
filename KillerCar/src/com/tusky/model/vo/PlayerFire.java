package com.tusky.model.vo;

import java.awt.Graphics;
import javax.swing.ImageIcon;


public class PlayerFire extends SuperElement{

	private ImageIcon imageIcon;
	
	public PlayerFire() {
		super();
	}

	public PlayerFire(int x, int y, int w, int h,ImageIcon imageIcon) {
		super(x, y, w, h);
		this.imageIcon=imageIcon;
	}
	
	public static PlayerFire createPlayerFire(int x,int y,String str) {
		ImageIcon imageIcon = new ImageIcon(str);//从参数获取子弹类型
		return new PlayerFire(x,y,42,108,imageIcon);
	}
	
	@Override
	public void showElement(Graphics g) {
		g.drawImage(imageIcon.getImage(), getX(), getY(), getW(), getH(), null);
	}

	@Override
	public void move() {
		setY(getY()-10);
		if(getY()<0)
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
