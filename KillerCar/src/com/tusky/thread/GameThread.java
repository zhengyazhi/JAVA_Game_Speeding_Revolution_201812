package com.tusky.thread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tusky.frame.MyJpanel;
import com.tusky.main.GameStart;
import com.tusky.model.manager.ElementManager;
import com.tusky.model.vo.Boss;
import com.tusky.model.vo.Enemy;
import com.tusky.model.vo.Explore;
import com.tusky.model.vo.People;
import com.tusky.model.vo.Player;
import com.tusky.model.vo.SuperElement;
import com.tusky.model.vo.Tools;

/*
 * 游戏的多线程    Thread实现了Runnable接口
 * 控制游戏整个流程
 * java是单继承，多实现。通过内部类的方式，弥补单继承的缺陷
 * */
public class GameThread extends Thread{
	
		//代码的熟练和思想的进步 都是经过很多的项目锻炼
		//如果项目不多，请重构老项目
	    
		//计时数据
		public static long time;
		//实时血量
//		private int Hp;
		//Boss时间
		public boolean BossTime=false;
		//关卡数
		public int doors=1;
		
		private boolean flag;
		
		public static boolean gameover=false;
		
		
		public void run() {
			while (true) {    //控制游戏整体进度
				
/*				if(GameStart.FlagTime<=2) {
					ifstart=false;
				}
				else {
					ifstart=true;
				}**/
					//死循环，但会有变量（状态）进行控制,123都在循环里
					//1.加载地图，人物...
				    
					//2.显示地图，人物...（流程，自动化（移动，碰撞））
					if(MyJpanel.ifstart==1) {
						
						Time();
					time=0;
					loadElement();
					runGame();
					//3.结束本地图
					overGame();
					//为提高线程效率，设置休眠
					}
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}

		private void overGame() {
			MyJpanel.ifstart=2;
		}

		private void runGame() {
			//也需要死循环
			//把对象取出来
			//在此实现让所有元素都动起来（包括敌人）
			while (true) {      //控制每个关卡玩的时候的状态
				Time();
				time++;
				
			
		//		List<SuperElement> list = ElementManager.getManager().getElementList("play");
				Map<String, List<SuperElement>> map=
						ElementManager.getManager().getMap();
				Set<String> set=map.keySet();
				
				List<String> lists = new ArrayList<>();
				lists.addAll(set);
				for (int j = 0; j < lists.size(); j++) {
					String key=lists.get(j);
				
//				for(String key:set) {//迭代器在遍历的过程中，迭代器内的元素不可以增加或者删除，所以换了一种方式如上
					List<SuperElement> list=map.get(key);
					for(int i=list.size()-1;i>=0;i--) {
				//		list.get(i).showElement(g);
				//		list.get(i).move();//获取元素对象，使其能够移动起来
						list.get(i).update();
						if(gameover) {
							list.get(i).setVisible(false);
						}
						if(!list.get(i).isVisible()) {//每一个元素的存活判定
							list.remove(i);
						}
					}
				}
				if(gameover) {
					return;
				}
				//使用一个独立的方法来进行判定
				PK();
				
				//在此写飞机的添加到List到Map //游戏的流程控制
				linkGame();
				RunPeople();
				RunEnemy();
				runBoss(doors);
/*				
				CurrentTime=getCurrentTime();
				if(NowTime!=CurrentTime-GameStart.StartTime) {
					NowTime=CurrentTime-GameStart.StartTime;
					if(FlagTime!=NowTime) {
						FlagTime=NowTime;
					}
					if(NowTime%5==0) {
						RunPeople();
					}
					if(NowTime/2!=0) {//偶数添加敌人
						RunEnemy();
					}
					
					
				}
*/				
				
				
				
				
				//死亡、通关等状态 结束runGame方法，跳出死循环
				//循环起来非常快，还是需要给一个休眠
				try {
					sleep(45);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				if(GameStart.NowTime!=GameStart.FlagTime) {
//					System.out.println(GameStart.FlagTime);
//					GameStart.FlagTime=GameStart.NowTime;
			//	System.out.println(System.currentTimeMillis());
	/*				if(System.currentTimeMillis()%1500==0) {
						System.out.println("dushufaajfjafjkjfak");
						BossTime=true;
					}
					if(BossTime) {
						runBoss(doors);
						BossTime=false;
					}
					
*/					
//				}
				
				//1秒钟增加10下
				
//				System.out.println("游戏开始已经"+FlagTime+"秒了");
/*				if(doors>=6) {
					break;//结束游戏标志
				}*/
			}
		}
		
		//计时方法
		private void Time() {
			GameStart.NowTime=(System.currentTimeMillis()-GameStart.StartTime)/1000;
			if (GameStart.NowTime!=GameStart.FlagTime) {
				System.out.println("游戏时间："+GameStart.FlagTime+"秒");
				System.out.println("生命值："+Player.hp);
				System.out.println("分数："+Player.score);
				GameStart.FlagTime=GameStart.NowTime;
				flag=true;
			}
		}

		//Boss时间
		private void runBoss(int boss) {
			if(GameStart.FlagTime%80==0) {
				if(flag==true) {
					List<SuperElement> list=
							ElementManager.getManager().getElementList("Boss");
					list.add(Boss.createBoss(boss));
					flag=false;
					doors++;
					System.out.println("生成Boss");
				}
			}	
		}

		//随机添加道具与平民
		private void RunPeople() {
			if(time%27==0&&GameStart.NowTime%2==0) {
				int i=(int)(Math.random()*2);
				i+=1;
				if(i==1) {
					List<SuperElement> list=
							ElementManager.getManager().getElementList("People");
					list.add(People.createPeople());
				}
				if(i==2) {
					List<SuperElement> list2=
							ElementManager.getManager().getElementList("Tools");
					list2.add(Tools.createtools());
				}
			}
		}

		//随机添加敌人
		private void RunEnemy() {
			if(time%17==0) {
				List<SuperElement> list=
						ElementManager.getManager().getElementList("Enemy");
				list.add(Enemy.createEnemy());
//				System.out.println(time+"！！！！！！！！！！！！！！！！！！！");
			}
		}

		private void PK() {
			List<SuperElement> list1=
					ElementManager.getManager().getElementList("PlayerFire");
			List<SuperElement> list2=
					ElementManager.getManager().getElementList("Enemy");
			List<SuperElement> list3=
					ElementManager.getManager().getElementList("Player");
			List<SuperElement> list4=
					ElementManager.getManager().getElementList("EnemyFire");
			List<SuperElement> list5=
					ElementManager.getManager().getElementList("People");
			List<SuperElement> list6=
					ElementManager.getManager().getElementList("Tools");
			List<SuperElement> list7=
					ElementManager.getManager().getElementList("Boss");
			
			//子弹击中敌人
			PlayerFirePK(list1, list2);
			//子弹击中Boss
			KillBoss(list1,list7);
			//玩家扣血情况：敌人、敌人子弹、平民
			PlayerPK(list3, list4);
			PlayerPK(list3, list2);
			PlayerPK(list3, list5);
			//子弹与敌人、平民
			listPK(list1, list4);
			listPK(list1, list5);
			//玩家捡到道具
			ToolsPK(list3, list6);
			
		}
		
		//关于Boss扣血的比较函数   PlayerFire与Boss
		public void KillBoss(List<SuperElement> firelist,List<SuperElement> bosslist) {
			for(int i=0;i<firelist.size();i++) {
				for(int j=0;j<bosslist.size();j++) {
					if(firelist.get(i).gamePK(bosslist.get(j))) {
						firelist.get(i).setVisible(false);
						Boss.hp--;
						Player.score+=25;
					}
				}
			}
		}
		
		//关于玩家扣血的比较函数   Player与Enemy、EnemyFire
		public void PlayerPK(List<SuperElement> playerlist1,List<SuperElement> enemylist2) {
			for(int i=0;i<playerlist1.size();i++) {
				for(int j=0;j<enemylist2.size();j++) {
					if(playerlist1.get(i).gamePK(enemylist2.get(j))) {
						enemylist2.get(j).setVisible(false);
						Player.hp-=1;
						if(Player.hp<=0) {
							playerlist1.get(i).setVisible(false);
							gameover=true;
							return;
						}
					}
				}
			}
		}
		
		//关于打中敌人得分的比较函数   PlayerFire与Enemy
		public void PlayerFirePK(List<SuperElement> playerfirelist1,List<SuperElement> enemylist2) {
			for(int i=0;i<playerfirelist1.size();i++) {
				for(int j=0;j<enemylist2.size();j++) {				
					if(playerfirelist1.get(i).gamePK(enemylist2.get(j))) {
						int x=enemylist2.get(j).getX();
						int y=enemylist2.get(j).getY();
						enemylist2.get(j).explores.add(new Explore(x, y));
						enemylist2.get(j).setVisible(false);
						System.out.println("敌人中了！！！！！！！！！！！！！！");
						Player.score+=10;
						playerfirelist1.get(i).setVisible(false);
					}
				}
			}
		}
		//道具血包的函数listP
		public void ToolsPK(List<SuperElement> playerlist,List<SuperElement> toolslist) {
			for(int i=0;i<playerlist.size();i++) {
				for(int j=0;j<toolslist.size();j++) {	
					if(playerlist.get(i).gamePK(toolslist.get(j))) {
						if(((Tools)toolslist.get(j)).getTypeTool()==1) {
							Player.hp++;
							toolslist.get(j).setVisible(false);
						}
						if(((Tools)toolslist.get(j)).getTypeTool()==2) {
							Player.score+=10;
							toolslist.get(j).setVisible(false);
						}
						
						
//						playerlist.get(i).setHp(playerlist.get(i).getHp()+1);
					}
				}
			}
		}
		//比较函数 还可扩展，多种碰撞结果
		public void listPK(List<SuperElement> list1,List<SuperElement> list2) {
			for(int i=0;i<list1.size();i++) {
				for(int j=0;j<list2.size();j++) {
					
					if(list1.get(i).gamePK(list2.get(j))) {
						
						list2.get(j).setVisible(false);
						list1.get(i).setVisible(false);
					//	list1.get(i).setH(list1.get(i).getH()-10);//分数和血量可以在此设置
					}
				}
			}
		}

		//专门用来做 游戏的流程控制
		public void linkGame() {
//			Map<String, List<SuperElement>> map = ElementManager.getManager().getMap();
//			List<SuperElement> list = map.get("enemyList");
			//添加敌人   但是添加太快了，但是不能用sleep，不然会和上面的sleep叠加，刷新太慢
			//只能使用计时器，控制1秒增加1个敌人,但是控制方式来自于控制文件，这里只是暂时这么写的
			//在这里写敌人的创建，思考敌人是否需要自己的一个工厂
			//添加到的敌人永远是list第一个，添加完后在List删除掉，然后再Manager中添加
//			if(time%10==0)
//				list.add(Enemy1.createEnemy1(""));
			ElementManager.getManager().linkGame(time);
		}
		//控制进度，但是，作为控制，请不要接触load加载
		private void loadElement() {
			ElementManager.getManager().load();
		}
		
/*		public static void getCurrentTime() {

			GameStart.NowTime=
					(System.currentTimeMillis()-GameStart.StartTime)/1000;
			if(GameStart.NowTime!=GameStart.FlagTime) {
				System.out.println("游戏已经开始了!!!!!!!"+GameStart.FlagTime+"秒");
				GameStart.FlagTime=GameStart.NowTime;
			}
		}*/
}
