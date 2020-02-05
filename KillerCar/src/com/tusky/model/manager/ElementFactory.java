package com.tusky.model.manager;

import java.util.List;

import java.util.Map;

import com.tusky.model.load.ElementLoad;
import com.tusky.model.vo.Enemy;
import com.tusky.model.vo.Player;
import com.tusky.model.vo.SuperElement;

/*
 * 任务：依据参数不同调用自动读取资源，填充vo对象数据，存储到元素管理器
 * 工厂可写成单例的（自己改）
 * 工厂的作用：将比较复杂的构造方式进行封装
 * 
 * */
public class ElementFactory {
	
	//依据不同的Name加载不同的信息
	public static SuperElement elementFactory(String name) {
		Map<String, List<String>> map=//主角资源加载
				ElementLoad.getElementLoad().getPlaymap();
		Map<String, List<String>> map2=//敌人资源加载
				ElementLoad.getElementLoad().getEnemymap();
		//游戏流程读取
		List<String>list2 = ElementLoad.getElementLoad().getGameList();
		
		//依据参数不同调用自动读取资源
		switch (name) {
		case "onePlayer":
			List<String> list=map.get(name);
			//简化版，单双机切换还要自己思考
			String string=list.get(0);  //string=playerA,fire,150,300,40,40
			//填充vo对象数据  工厂要配合create
			
			return Player.createPlayer(string);
			//传name不够，还可以传一个时间参数进来，判断时间一到，则return 一个敌人对象
		case "enemy":
//			String string2=list2.get(list2.size()-1);
//			System.out.println("工程！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			System.out.println("工程！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			return Enemy.createEnemy();
		default:
			break;
		}
		return null;
	}

}
