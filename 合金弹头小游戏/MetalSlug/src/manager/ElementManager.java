package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import element.ElementObj;

/**
 * @˵�� ������Ԫ�ع�������ר�Ŵ洢���е�Ԫ�أ�ͬʱ���ṩ����
 * 		������ͼ�Ϳ��ƻ�ȡ����
 * @author renjj
 * @����һ���洢����Ԫ�����ݣ���ô��ţ� list map set 3�󼯺�
 * @�����������������ͼ�Ϳ���Ҫ���ʣ��������ͱ���ֻ��һ��������ģʽ
 */
public class ElementManager {
	/*
	 * String ��Ϊkey ƥ�����е�Ԫ��  play -> List<Object> listPlay
	 *                             enemy ->List<Object> listEnemy 
	 * ö�����ͣ�����map��key�������ֲ�һ������Դ�����ڻ�ȡ��Դ
	 * List��Ԫ�صķ��� Ӧ���� Ԫ�� ����
	 * ����Ԫ�ض����Դ�ŵ� map�����У���ʾģ��ֻ��Ҫ��ȡ�� ���map�Ϳ���
	 * ��ʾ���еĽ�����Ҫ��ʾ��Ԫ��(����Ԫ�ػ���� showElement())
	 */
	private Map<GameElement,List<ElementObj>> gameElements;	
	
//	������һ��������
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
	
//	���Ԫ��(����ɼ���������)
	public void addElement(ElementObj obj,GameElement ge) {
//		List<ElementObj> list = gameElements.get(ge);
//		list.add(obj);
		gameElements.get(ge).add(obj);//��Ӷ��󵽼����У���keyֵ���д洢
	}
	
//	����key���� list���ϣ�ȡ��ĳһ��Ԫ��
	public List<ElementObj> getElementsByKey(GameElement ge){
		//һ�������С���⣺String str = new String("abcd");
		//������������ʹ����������������ֱ���ʲô���ֱ�洢�����
		//һ������3��������1.��������str���洢��ջ  2.�ַ�������"abcd"���洢�ڳ����أ���̬�洢����  
		//3.new������һ���ַ�������String���洢�ڶ�
		//��һ���ڴ�û���κ�һ������ָ���ʱ�򣬻ᱻGC���ա�
		return gameElements.get(ge);
	}	
	
	/**
	 * ����ģʽ���ڴ�������ֻ��һ��ʵ����
	 * ����ģʽ-���������Զ�����ʵ��
	 * ����ģʽ-����Ҫʹ�õ�ʱ��ż���ʵ��
	 * 
	 * ��д��ʽ��
	 * 1.��Ҫһ����̬������(����һ������) ����������
	 * 2.�ṩһ����̬�ķ���(�������ʵ��) return����������
	 * 3.һ��Ϊ��ֹ�������Լ�ʹ��(���ǿ���ʵ����),���Ի�˽�л����췽��
	 *    ElementManager em=new ElementManager();
	 */
	private static ElementManager EM=null; //����
	
//	synchronized�߳���->��֤������ִ����ֻ��һ���߳�
	public static synchronized ElementManager getManager() {
		if(EM == null) {//�����ж�
			EM=new ElementManager();
		}
		return EM;
	}
	
	private ElementManager() {//˽�л����췽��
		init(); //ʵ��������
	}
	
//	static { //����ʵ��������   //��̬���������౻���ص�ʱ��ֱ��ִ��
//		EM=new ElementManager(); //ֻ��ִ��һ��
//	}
	
	/**
	 * ��������Ϊ �������ܳ��ֵĹ�����չ����дinit����׼���ġ�
	 */
	public void init() {//ʵ�������������
//		hashMap hashɢ��
		gameElements=new HashMap<GameElement,List<ElementObj>>();
//		��ÿ��Ԫ�ؼ��϶����뵽 map��
//		gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());
		//��ʹ��ѭ����ȡ�ķ�ʽʱ���ǰ�ö�����ͱ�����ö�����е�����˳�������е�
		for(GameElement ge:GameElement.values()) { //ͨ��ѭ����ȡö�����͵ķ�ʽ��Ӽ���
			gameElements.put(ge,new ArrayList<ElementObj>());
		}
//		���ߣ��ӵ�����ըЧ��������Ч��
	}
	
}