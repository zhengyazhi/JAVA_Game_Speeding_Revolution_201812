package com.tusky.model.vo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class SuperElement {
	int x;
	int y;
	int w;
	int h;
	int hp;
	protected boolean visible;//角色是否存活
	public LinkedList<Explore> explores = new LinkedList<Explore>();//爆炸容器
	
	protected SuperElement() {
		
	}
	/*
	 * jvm给每个类都会默认增加一个默认无参数的构造方法
	 * 但是如果我们手动写了一个构造方法（无论是否有参）jvm都不会再添加默认构造
	 * 一般作为父类，如果有其他构造，最好写一个无参构造方法，防止继承报错
	 */
	public SuperElement(int x,int y,int w,int h) {
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.visible=true;//默认存活
		this.hp=0;
	}
	
	//这些方法是可以有顺序执行的。模板模式（Player类有addFire方法，但父类没有，直接添加到父类来不对）
	public void update() {//模板函数:Player需要重写update，因为为了用上addFire；但PlayerFire不需要用到
		move();
	    destroy();
	}
	
	public boolean gamePK(SuperElement se) {
		Rectangle r1=new Rectangle(x, y, w, h);
		Rectangle r2=new Rectangle(se.x, se.y, se.w, se.h);
		return r1.intersects(r2);//如果矩形有交集，则返回true
	}
	
	public void showElement(Graphics g)
	{
		for(int i = 0;i<explores.size();i++)
		{
			Explore explore = explores.get(i);
			explore.drawExplore(g);
		}
	}
	public abstract void move();
	public abstract void destroy();
	
	
	//java开发套路：针对某些图片转换后开始点不同，设置此两个方法，
	//默认值仍为标准点，但可根据图片实际情况重写方法
	public int getShowX() {
		return getX();
	}
    public int getShowY() {
		return getY();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
