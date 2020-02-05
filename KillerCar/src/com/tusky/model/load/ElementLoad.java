package com.tusky.model.load;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;

import com.tusky.model.load.ElementLoad;
/*
 * 资源加载器 
*/
public class ElementLoad {

	private Map<String, ImageIcon> map;//资源库集合
	private Map<String, List<String>> playmap;   //主角用到的
	private Map<String, List<String>> enemymap;   //敌人用到的
	private List<String> gameList;//游戏的流程控制（敌人兵力出现控制）
	
    private static ElementLoad load;
    //pro文件读取对象
  	private Properties pro;
	
	
	
	//初始化
	private ElementLoad() {
		map= new HashMap<>();
		playmap=new HashMap<>();
		pro=new Properties();
		gameList=new ArrayList<>();
	}
	
	public static synchronized ElementLoad getElementLoad() {
		if(load==null) {
			load=new ElementLoad();
		}
		return load;
	}
	
	//可以放入参数，根据参数设置关卡加载流程
	//读取游戏流程
	public void readGamepro() {
		InputStream inputStream = ElementLoad.class.
				getClassLoader().getResourceAsStream("com/tusky/pro/gamerun1.pro");
		pro.clear();
		try {
			pro.load(inputStream);
			for(Object o:pro.keySet()) {
				String string=pro.getProperty(o.toString());
				gameList.add(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//读取主角配置
	public void readPlayPro() {
		InputStream inputStream = ElementLoad.class.
				getClassLoader().getResourceAsStream("com/tusky/pro/player.pro");
		pro.clear();
		try {
			pro.load(inputStream);
			List<String>list=new ArrayList<>();
			for(Object o:pro.keySet()) {
				String string=pro.getProperty(o.toString());
				list.add(string);
				playmap.put(o.toString(), list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/*	//读取敌人配置
	public void readEnemyPro() {
		InputStream inputStream = ElementLoad.class.
				getClassLoader().getResourceAsStream("com/tusky/pro/enemy.pro");
		pro.clear();
		try {
			pro.load(inputStream);
			List<String>list=new ArrayList<>();
			for(Object o:pro.keySet()) {
				String string=pro.getProperty(o.toString());
				list.add(string);
				enemymap.put(o.toString(), list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	//读取图片的资源
	//根据关卡数选择配置文件
	public void readImgPro(/*int guan*/) {
		InputStream inputStream = ElementLoad.class.
				getClassLoader().getResourceAsStream("com/tusky/pro/gamemap1.pro");
		pro.clear();
		try {
			pro.load(inputStream);
			Set<?> set = pro.keySet();
			for(Object o:set) {
				String url=pro.getProperty(o.toString());
				map.put(o.toString(), new ImageIcon(url));
			}//所有图片都可以来Map里取了
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//向工厂提供资源
	public List<String> getGameList() {
		return gameList;
	}
	public Map<String, ImageIcon> getMap() {
		return map;
	}
	public Map<String, List<String>> getPlaymap() {
		return playmap;
	}
	public Map<String, List<String>> getEnemymap() {
		return enemymap;
	}
}
