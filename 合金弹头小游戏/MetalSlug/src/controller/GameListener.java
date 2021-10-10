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
 * @˵�� ��Ϸ�������࣬�������̻������������¼�
 * @author DELL
 *
 */
public class GameListener implements KeyListener{
	private ElementManager em=ElementManager.getManager();
	//��ȡ��Ϸ����Ҫ��Ӱ����¼�������Ԫ�أ�����/��ͼ��
	private List<ElementObj> listenObjs;
	
	/* ͨ��һ����������¼���а��µļ�������ظ���������ֱ�ӽ���
	 * ��ֹ�ظ�����ͬһ�¼�
	 * ͬʱ����1�ΰ��£���¼�������У���2���ж������з��С�
	 *       �ɿ���ֱ��ɾ�������еļ�¼��
	 * set����
	 * */
	private Set<Integer> set=new HashSet<Integer>();

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//�õ���Ҽ���
		System.out.println("����"+e.getKeyCode());
		int key=e.getKeyCode();
		if(set.contains(key)) { //�ж��������Ƿ��Ѿ�����,�����������
			//�������ֱ�ӽ�������
			return;
		}
		set.add(key);
		//��ȡ��Ϸ����Ҫ��Ӱ����¼�������Ԫ�أ�����/��ͼ��
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
		if(!set.contains(e.getKeyCode())) {//�����������ڣ���ֹͣ
			return;
		}//����(�Ѿ������������)
		set.remove(e.getKeyCode());//�Ƴ�����
		for(ElementObj obj:listenObjs) {
			obj.keyClick(false, e.getKeyCode());
		}
		
	}

}
