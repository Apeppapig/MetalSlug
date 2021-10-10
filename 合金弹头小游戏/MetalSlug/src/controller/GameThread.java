package controller;

import java.util.List;
import java.util.Map;

import element.ElementObj;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，游戏运行时自动化
 * 		游戏判定；游戏地图切换 资源释放和重新读取。。。
 * @author renjj
 * @继承 使用继承的方式实现多线程(一般建议使用接口实现)
 */
public class GameThread extends Thread{
	public static long gameTime = 0;
	private ElementManager em;
	
	public GameThread() {
		em=ElementManager.getManager();
	}
	@Override
	public void run() {//游戏的run方法  主线程
		while(true) { //扩展,可以讲true变为一个变量用于控制结束
//		游戏开始前   读进度条，加载游戏资源(场景资源)
			gameLoad();
//		游戏进行时   游戏过程中
			gameRun();
//		游戏场景结束  游戏资源回收(场景资源)
			gameOver();
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 游戏的加载
	 */
	private void gameLoad() {
		//加载游戏所需的所有图片
		GameLoad.imgLoad();		
		//加载地图（参数可以为变量，每一关都重新加载）
		GameLoad.MapLoad(5);
		//加载直升飞机
		GameLoad.planeLoad();
		//加载主角
		GameLoad.playerLoad();	//也可以带参数，单机还是双人
		//加载人质
		GameLoad.hostageLoad();
		//加载敌人/NPC等
		GameLoad.bossLoad();
		//全部加载完成，游戏才启动
	}
	
	/**
	 * @说明  游戏进行时
	 * @任务说明  游戏过程中需要做的事情：1.自动化玩家的移动，碰撞，死亡
	 *                                 2.新元素的增加(NPC死亡后出现道具)
	 *                                 3.暂停等等。。。。。
	 * 先实现主角的移动
	 * */
	private void gameRun() {
		long gameTime=5L;//int类型就可以
		while(true) {// 预留扩展   true可以变为变量，用于控制管关卡结束等
			//游戏运行时动态加载敌人
			GameLoad.enemyLoad();
			
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			//获取主角元素
			List<ElementObj> player=em.getElementsByKey(GameElement.PLAYER);
			//获取道具元素
			List<ElementObj> playerProps=em.getElementsByKey(GameElement.PLAYER_PROP);
			//获取BOSS元素
			List<ElementObj> boss=em.getElementsByKey(GameElement.BOSS);
			//获取敌人元素
			List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
			//获取炸弹元素
			List<ElementObj> bombs=em.getElementsByKey(GameElement.BOMB);
			//获取子弹元素
			List<ElementObj> bullets = em.getElementsByKey(GameElement.PLAYER_BULLET);
			//获取人质元素
			List<ElementObj> hostages=em.getElementsByKey(GameElement.HOSTAGE);
			//获取地图元素
			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
			//游戏元素自动化方法
			moveAndUpdate(all,gameTime);
			//将主角子弹与BOSS做碰撞判定
			ElementPK(bullets,boss);
			//将主角子弹与敌人小兵做碰撞判定
			ElementPK(bullets,enemies);
			//将主角子弹与地图做碰撞判定（是否超出界面）
			ElementPK(maps, bullets);
			//将主角与人质做判定
			ElementPK(player, hostages);
			//将主角与道具做判定
			ElementPK(player, playerProps);
			//主角与飞机扔的炸弹做判定
			ElementPK(player, bombs);
			//唯一的时间控制
			gameTime++;	//1s自增了100次
			try {
				sleep(10);//默认理解为 1秒刷新100次 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 为两种不同类型的元素的所有对象开启碰撞检测
	 * @param listA
	 * @param listB
	 */
	public void ElementPK(List<ElementObj> listA,List<ElementObj> listB) {
		//使用循环，做一对一判定，如果为真，就设置2个对象的死亡状态
		for(int i=0;i<listA.size();i++) {
			ElementObj objA=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj objB=listB.get(j);
				if(objA.pk(objB)) {
//					问题： 如果是boss，那么也一枪一个吗？？？？
//					将 setLive(false) 变为一个受攻击方法，还可以传入另外一个对象的攻击力
//					当受攻击方法执行时，如果血量减为0，再进行设置生存状态为 false
					//留给大家扩展
//					objA.setLive(false);
					objA.attackNow(objB);
					objB.attackNow(objA);
//					if (objB instanceof Bomb) {//为炸弹类时
//						if (objB.getY()>=380) {
//							objA.setLive(false);
//						}
//					}
					//objB.setLive(false);
					break;
				}
			}
		}
	}
	
	//游戏元素自动化方法
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime) {
//		GameElement.values();//隐藏方法  返回值是一个数组,数组的顺序就是定义枚举的顺序
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
//			编写这样直接操作集合数据的代码建议不要使用迭代器。
//			for(int i=0;i<list.size();i++) {
			for(int i=list.size()-1;i>=0;i--){	
				ElementObj obj=list.get(i);//读取为基类
				if(!obj.getLive()) {//如果死亡
//					list.remove(i--);  //可以使用这样的方式
//					启动一个死亡方法(方法中可以做事情例如:死亡动画/掉装备/人质奔跑)
					obj.die(i);//需要大家自己补充
//					list.remove(i);	//应该在每个元素类的die()里remove
//					continue;
				}
				obj.model(gameTime);//调用的模板方法model()而不是move()
			}
		}	
	}
	
	/**
	 * 游戏切换关卡
	 */
	private void gameOver() {
		
	}

}