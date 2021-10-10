package show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import element.ElementObj;
import manager.ElementManager;
import manager.GameElement;

/**
 * @˵�� ��Ϸ����Ҫ���
 * @author renjj
 * @����˵�� ��Ҫ����Ԫ�ص���ʾ��ͬʱ���н����ˢ��(���߳�)
 * 
 * @���⻰ java����ʵ��˼����Ӧ���ǣ����̳л����ǽӿ�ʵ��
 * 
 * @���߳�ˢ�� 1.����ʵ���߳̽ӿ�
 *             2.�����ж���һ���ڲ�����ʵ��
 */
public class GameMainJPanel extends JPanel implements Runnable{
	//����������
	private ElementManager em;
	
	public GameMainJPanel() {
		init();
	}

	public void init() {
		em = ElementManager.getManager();//�õ�Ԫ�ع���������
	}
	/**
	 * paint�����ǽ��л滭Ԫ�ء�
	 * �滭ʱ���й̶���˳���Ȼ滭��ͼƬ���ڵײ㣬��滭��ͼƬ�Ḳ���Ȼ滭��
	 * Լ����������ִֻ��һ��,��ʵʱˢ����Ҫʹ�� ���߳�
	 */
	@Override  //���ڻ滭��    Graphics ���� ר�����ڻ滭��
	public void paint(Graphics g) {
		super.paint(g);  //���ø����paint����
//		map  key-value  key�����򲻿��ظ��ġ�
//		set  ��map��keyһ�� ���򲻿��ظ���
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
//		GameElement.values()Ϊ���ط��� ������ֵ��һ�����飬�����˳����Ƕ���ö�ٵ�˳��
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for(int i=0;i<list.size();i++) {
				ElementObj obj=list.get(i);//��ȡΪ����
//				if(ge.equals(GameElement.PLAYFILE)) {
//					System.out.println(":::::::::::"+obj);
//				}
				obj.showElement(g);//����ÿ������Լ���show��������Լ�����ʾ
			}
		}
		
//		Set<GameElement> set = all.keySet(); //�õ����е�key����
//		for(GameElement ge:set) { //������
//			List<ElementObj> list = all.get(ge);
//			for(int i=0;i<list.size();i++) {
//				ElementObj obj=list.get(i);//��ȡΪ����
//				obj.showElement(g);//����ÿ������Լ���show��������Լ�����ʾ
//			}
//		}
		
	}
	@Override
	public void run() {  //�ӿ�ʵ��
		while(true) {
//			System.out.println("���߳��˶�");
			this.repaint();
//			һ������£����̶߳���ʹ��һ������,�����ٶ�
			try {
				Thread.sleep(10); //����10���� 1��ˢ��20��
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}
