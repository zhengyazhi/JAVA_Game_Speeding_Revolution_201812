package com.tusky.thread;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import com.tusky.frame.MyJpanel;
import com.tusky.model.manager.ElementManager;
import com.tusky.model.manager.MoveType;
import com.tusky.model.vo.Player;
import com.tusky.model.vo.SuperElement;


public class GameListener implements KeyListener{
	private List<?> list;

	@Override//按下 左37 上38 右39 下40
	public void keyPressed(KeyEvent e) {	
		//System.out.println("keyPressed"+e.getKeyCode());
		//前端从后端获取数据唯一入口
		if(MyJpanel.ifstart==1) {
		list=ElementManager.getManager().getElementList("Player");//对应这里map.put("play", list);
		Player play=(Player)list.get(0);//明确知道是player，所以可以强制转换，但是还是要判定play属于谁
		switch (e.getKeyCode()) {
		case 37:
			play.setLeft(true);
			break;
		case 38:
			play.setTop(true);
			break;
		case 39:
			play.setRight(true);
			break;
		case 40:
			play.setDown(true);
			break;
		case 32:
			play.setAttack(true);
			break;
		}
		}
		switch (e.getKeyCode()) {
		case 10:
			if(MyJpanel.ifstart==0) {
				MyJpanel.ifstart=1;
			}
			break;
        case 27:
        	if(MyJpanel.ifstart==2) {
				MyJpanel.ifstart=0;
				Player.score=0;
				Player.hp=3;
				GameThread.gameover=false;
			}
			break;
		}
	}

	@Override//松开
	public void keyReleased(KeyEvent e) {
	
		if(MyJpanel.ifstart==1) {
		list=ElementManager.getManager().getElementList("Player");//对应这里map.put("play", list);
		Player play=(Player)list.get(0);
		
		
		switch (e.getKeyCode()) {
		case 37://当前松开的方向就是移动的方向才给松开
			if(play.isLeft() == true)
				play.setLeft(false);
			break;
		case 38:
			if(play.isTop() == true)
				play.setTop(false);
			break;
		case 39:
			if(play.isRight() == true)
				play.setRight(false);
			break;
		case 40:
			if(play.isDown() == true)
				play.setDown(false);
			break;
		case 32:
			if(play.isAttack() == true)
				play.setAttack(false);
			break;
		}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
}
