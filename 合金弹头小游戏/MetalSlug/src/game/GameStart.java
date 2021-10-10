package game;

import controller.GameListener;
import controller.GameThread;
import show.GameJFrame;
import show.GameMainJPanel;

public class GameStart {
	/**
	 * �����Ψһ���
	 */
	public static void main(String[] args) {
		GameJFrame gj = new GameJFrame();
		// ʵ������壬ע�뵽jframe��
		GameMainJPanel jp = new GameMainJPanel();
		// ʵ��������
		GameListener listener = new GameListener();
		// ʵ�������߳�
		GameThread th = new GameThread();
		// ע��
		gj.setjPanel(jp);
		gj.setKeyListener(listener);
		gj.setThead(th);
		gj.start();
	}
}
