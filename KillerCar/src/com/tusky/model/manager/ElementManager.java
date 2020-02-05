package com.tusky.model.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tusky.model.vo.SuperElement;
import com.tusky.model.manager.ElementFactory;
import com.tusky.model.load.ElementLoad;
import com.tusky.model.manager.ElementManager;

public class ElementManager {
	//最少需要一个集合：NPC元素、场景元素...
	Map<String, List<SuperElement>> map;   //问题：好处是什么

    //private MoveType moveType;
	//得到一个完整的map集合
	public Map<String, List<SuperElement>> getMap() {
		return map;
	}
	//得到某一个元素的集合
	public List<SuperElement> getElementList(String key){
		return map.get(key);
	}
	
	//为了得到一个单例：
	//单例：需要一个唯一的引用
	private static ElementManager elementManager;
	//设为private防止别人随意篡改
	
	//构造方法 public 的别人能够实例化，不能让人随便实例化，保持唯一性
	//构造方法私有化：只有在本类中可以new
	private ElementManager() {
		init();
	}
	
	protected void init() {
		//资源加载
		map=new HashMap<>();
		List<SuperElement> list=new ArrayList<>();
		map.put("Player", list);
		List<SuperElement> list2=new ArrayList<>();
		map.put("PlayerFire", list2);
		List<SuperElement> list3=new ArrayList<>();
		map.put("Enemy", list3);
		List<SuperElement> list4=new ArrayList<>();
		map.put("EnemyFire", list4);
		List<SuperElement> list5=new ArrayList<>();
		map.put("People", list5);
		List<SuperElement> list6=new ArrayList<>();
		map.put("Boss", list6);
		List<SuperElement> list7=new ArrayList<>();
		map.put("Tools", list7);
		
		//注意这里涉及到一个图层顺序问题	
	}
	
	//静态的语句块 是在类加载的时候（完全还没加载的时候） 就会执行
    //每个类只会加载一次，则此语句只会执行一次
	static {
		if(elementManager == null) {
			elementManager=new ElementManager();
		}
	}
	
	//元素管理器的公共入口，提供出来给外部访问的唯一入口
	//synchronized线程保护锁：解决多线程不安全性，但效率会低
	public static/* synchronized */ElementManager getManager() {
		return elementManager;
	}
	
	//资源加载
	public void load() {
		//资源加载
		ElementLoad.getElementLoad().readImgPro();//关卡图片资源
		ElementLoad.getElementLoad().readPlayPro();//玩家资源
//		ElementLoad.getElementLoad().readEnemyPro();//敌人资源
		//开放一个状态，界面可以做前戏了（前面的过度信息）
		//...
		ElementLoad.getElementLoad().readGamepro();//游戏流程资源
		
		map.get("Player").add(ElementFactory.elementFactory("onePlayer"));
//		map.get("Enemy").add(ElementFactory.elementFactory("enemy"));
	}
	
	//控制流程 游戏进行时间
	public void linkGame(long time) {
		//可以拿到流程List 001=enemyA,enemyA,20,40,40,40,2000能拿到时间，跟time比较
		
		List<String> list = ElementLoad.getElementLoad().getGameList();
		if(list.size()==0) {
			return;//流程结束
		}
		String string = list.get(list.size()-1);//从最后一个读起
		String[] arr=string.split(",");
		int runTime=Integer.parseInt(arr[arr.length-1]);//读到了最后那个时间
		
		if(time>runTime) {//工厂去创建一个敌人放进来
			map.get("Enemy").add(ElementFactory.elementFactory("enemy"));
			//流程当中最前面的可以清除了
			list.remove(list.size()-1);
		}
	}
}
