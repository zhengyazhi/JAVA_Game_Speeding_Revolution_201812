package com.tusky.model.vo;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Explore {

	private int x,y,step;
	public boolean elive = true;
	public boolean isElive() {
		return elive;
	}

	public void setElive(boolean elive) {
		this.elive = elive;
	}

	Image bang = new ImageIcon("img//explore.gif").getImage();
	
	public Explore(int x,int y) {
		this.x=x;
		this.y=y;
	
	}
	
	public void drawExplore(Graphics g) {
		if(step == 1)
		{
			step =0;
			setElive(false);
			return ;
		}
		step++;
		if(!elive)
		{
			return;
		}
		g.drawImage(bang,x-20,y-20,null);
	}
	
	
	
	
}
