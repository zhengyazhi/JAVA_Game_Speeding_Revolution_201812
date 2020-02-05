package com.tusky.model.vo;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Tools extends SuperElement{

	private int hp;                //血量
	private ImageIcon imageIcon;
	public int typeTool;           //道具类别
	
	public Tools()
	{
		super();
	}

	public Tools(int x, int y, int w, int h,int hp,int typeTool,ImageIcon imageIcon) {
		super(x, y, w, h);
		this.imageIcon=imageIcon;
		this.hp=hp;
		this.typeTool=typeTool;
	}
	
	public static Tools createtools() {
		Tools tools=null;
		int i=(int)(Math.random()*2);
		i+=1;
		String url="img/people/cure0"+i+".png";
		ImageIcon imageIcon=new ImageIcon(url);
		//产生一个20~400的数作为道具的x坐标
		int min=20;int max=400; 
		int x=(int)min+(int)(Math.random()*(max-min));
 		
		tools=new Tools(x,0,40,40,1,i,imageIcon);
		return tools;
	}

	@Override
	public void showElement(Graphics g) {
		g.drawImage(imageIcon.getImage(), getX(), getY(), getW(), getH(), null);
	}

	@Override
	public void move() {
		setY(getY()+7);
		if(getY()>600)
			setVisible(false);
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
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

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public int getTypeTool() {
		return typeTool;
	}

	public void setTypeTool(int typeTool) {
		this.typeTool = typeTool;
	}
	
}
