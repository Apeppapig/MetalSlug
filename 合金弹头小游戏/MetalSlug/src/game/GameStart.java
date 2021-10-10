package game;

import controller.GameListener;
import controller.GameThread;
import show.GameJFrame;
import show.GameMainJPanel;

public class GameStart {
	/**
	 * 程序的唯一入口
	 */
	public static void main(String[] args) {
		GameJFrame gj = new GameJFrame();
		// 实例化面板，注入到jframe中
		GameMainJPanel jp = new GameMainJPanel();
		// 实例化监听
		GameListener listener = new GameListener();
		// 实例化主线程
		GameThread th = new GameThread();
		// 注入
		gj.setjPanel(jp);
		gj.setKeyListener(listener);
		gj.setThead(th);
		gj.start();
	}
}
