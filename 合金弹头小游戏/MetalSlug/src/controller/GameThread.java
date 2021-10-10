package controller;

import java.util.List;
import java.util.Map;

import element.ElementObj;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @˵�� ��Ϸ�����̣߳����ڿ�����Ϸ���أ���Ϸ�ؿ�����Ϸ����ʱ�Զ���
 * 		��Ϸ�ж�����Ϸ��ͼ�л� ��Դ�ͷź����¶�ȡ������
 * @author renjj
 * @�̳� ʹ�ü̳еķ�ʽʵ�ֶ��߳�(һ�㽨��ʹ�ýӿ�ʵ��)
 */
public class GameThread extends Thread{
	public static long gameTime = 0;
	private ElementManager em;
	
	public GameThread() {
		em=ElementManager.getManager();
	}
	@Override
	public void run() {//��Ϸ��run����  ���߳�
		while(true) { //��չ,���Խ�true��Ϊһ���������ڿ��ƽ���
//		��Ϸ��ʼǰ   ����������������Ϸ��Դ(������Դ)
			gameLoad();
//		��Ϸ����ʱ   ��Ϸ������
			gameRun();
//		��Ϸ��������  ��Ϸ��Դ����(������Դ)
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
	 * ��Ϸ�ļ���
	 */
	private void gameLoad() {
		//������Ϸ���������ͼƬ
		GameLoad.imgLoad();		
		//���ص�ͼ����������Ϊ������ÿһ�ض����¼��أ�
		GameLoad.MapLoad(5);
		//����ֱ���ɻ�
		GameLoad.planeLoad();
		//��������
		GameLoad.playerLoad();	//Ҳ���Դ���������������˫��
		//��������
		GameLoad.hostageLoad();
		//���ص���/NPC��
		GameLoad.bossLoad();
		//ȫ��������ɣ���Ϸ������
	}
	
	/**
	 * @˵��  ��Ϸ����ʱ
	 * @����˵��  ��Ϸ��������Ҫ�������飺1.�Զ�����ҵ��ƶ�����ײ������
	 *                                 2.��Ԫ�ص�����(NPC��������ֵ���)
	 *                                 3.��ͣ�ȵȡ���������
	 * ��ʵ�����ǵ��ƶ�
	 * */
	private void gameRun() {
		long gameTime=5L;//int���;Ϳ���
		while(true) {// Ԥ����չ   true���Ա�Ϊ���������ڿ��ƹܹؿ�������
			//��Ϸ����ʱ��̬���ص���
			GameLoad.enemyLoad();
			
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			//��ȡ����Ԫ��
			List<ElementObj> player=em.getElementsByKey(GameElement.PLAYER);
			//��ȡ����Ԫ��
			List<ElementObj> playerProps=em.getElementsByKey(GameElement.PLAYER_PROP);
			//��ȡBOSSԪ��
			List<ElementObj> boss=em.getElementsByKey(GameElement.BOSS);
			//��ȡ����Ԫ��
			List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
			//��ȡը��Ԫ��
			List<ElementObj> bombs=em.getElementsByKey(GameElement.BOMB);
			//��ȡ�ӵ�Ԫ��
			List<ElementObj> bullets = em.getElementsByKey(GameElement.PLAYER_BULLET);
			//��ȡ����Ԫ��
			List<ElementObj> hostages=em.getElementsByKey(GameElement.HOSTAGE);
			//��ȡ��ͼԪ��
			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
			//��ϷԪ���Զ�������
			moveAndUpdate(all,gameTime);
			//�������ӵ���BOSS����ײ�ж�
			ElementPK(bullets,boss);
			//�������ӵ������С������ײ�ж�
			ElementPK(bullets,enemies);
			//�������ӵ����ͼ����ײ�ж����Ƿ񳬳����棩
			ElementPK(maps, bullets);
			//���������������ж�
			ElementPK(player, hostages);
			//��������������ж�
			ElementPK(player, playerProps);
			//������ɻ��ӵ�ը�����ж�
			ElementPK(player, bombs);
			//Ψһ��ʱ�����
			gameTime++;	//1s������100��
			try {
				sleep(10);//Ĭ�����Ϊ 1��ˢ��100�� 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Ϊ���ֲ�ͬ���͵�Ԫ�ص����ж�������ײ���
	 * @param listA
	 * @param listB
	 */
	public void ElementPK(List<ElementObj> listA,List<ElementObj> listB) {
		//ʹ��ѭ������һ��һ�ж������Ϊ�棬������2�����������״̬
		for(int i=0;i<listA.size();i++) {
			ElementObj objA=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj objB=listB.get(j);
				if(objA.pk(objB)) {
//					���⣺ �����boss����ôҲһǹһ���𣿣�����
//					�� setLive(false) ��Ϊһ���ܹ��������������Դ�������һ������Ĺ�����
//					���ܹ�������ִ��ʱ�����Ѫ����Ϊ0���ٽ�����������״̬Ϊ false
					//���������չ
//					objA.setLive(false);
					objA.attackNow(objB);
					objB.attackNow(objA);
//					if (objB instanceof Bomb) {//Ϊը����ʱ
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
	
	//��ϷԪ���Զ�������
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime) {
//		GameElement.values();//���ط���  ����ֵ��һ������,�����˳����Ƕ���ö�ٵ�˳��
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
//			��д����ֱ�Ӳ����������ݵĴ��뽨�鲻Ҫʹ�õ�������
//			for(int i=0;i<list.size();i++) {
			for(int i=list.size()-1;i>=0;i--){	
				ElementObj obj=list.get(i);//��ȡΪ����
				if(!obj.getLive()) {//�������
//					list.remove(i--);  //����ʹ�������ķ�ʽ
//					����һ����������(�����п�������������:��������/��װ��/���ʱ���)
					obj.die(i);//��Ҫ����Լ�����
//					list.remove(i);	//Ӧ����ÿ��Ԫ�����die()��remove
//					continue;
				}
				obj.model(gameTime);//���õ�ģ�巽��model()������move()
			}
		}	
	}
	
	/**
	 * ��Ϸ�л��ؿ�
	 */
	private void gameOver() {
		
	}

}