package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import manager.ElementManager;

import element.ElementObj;
import manager.GameElement;

/**
 * @说明 游戏监听器类，监听键盘或者鼠标的输入事件
 * @author DELL
 *
 */
public class GameListener implements KeyListener{
	private ElementManager em=ElementManager.getManager();
	//获取游戏内需要添加按键事件监听的元素（主角/地图）
	private List<ElementObj> listenObjs;
	
	/* 通过一个集合来记录所有按下的键，如果重复触发，就直接结束
	 * 防止重复触发同一事件
	 * 同时，第1次按下，记录到集合中，第2次判定集合中否有。
	 *       松开就直接删除集合中的记录。
	 * set集合
	 * */
	private Set<Integer> set=new HashSet<Integer>();

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//拿到玩家集合
		System.out.println("按下"+e.getKeyCode());
		int key=e.getKeyCode();
		if(set.contains(key)) { //判定集合中是否已经存在,包含这个对象
			//如果包含直接结束方法
			return;
		}
		set.add(key);
		//获取游戏内需要添加按键事件监听的元素（主角/地图）
		listenObjs = new ArrayList<ElementObj>();
		listenObjs.addAll(em.getElementsByKey(GameElement.PLAYER));
		listenObjs.addAll(em.getElementsByKey(GameElement.MAPS));
		listenObjs.addAll(em.getElementsByKey(GameElement.PLAYER_BULLET));
		for(ElementObj obj:listenObjs) {
			obj.keyClick(true, e.getKeyCode());
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!set.contains(e.getKeyCode())) {//如果这个不存在，就停止
			return;
		}//存在(已经按过这个按键)
		set.remove(e.getKeyCode());//移除数据
		for(ElementObj obj:listenObjs) {
			obj.keyClick(false, e.getKeyCode());
		}
		
	}

}
