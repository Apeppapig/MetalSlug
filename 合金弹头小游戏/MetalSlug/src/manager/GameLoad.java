package manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;

import element.ElementObj;
import element.GameMap;

/**
 * @˵��  �����������ڶ�ȡ�����ļ��Ĺ��ߣ�������ṩ����static����
 *
 */
public class GameLoad {
	
	//Collection:���ڼ�������Ĺ����࣬����Ϊ���ж������͵ļ�¼��������
	//������ļ���ֻ��ΪCollection������
	private static Properties properties=new Properties();//���ڶ�ȡ�ļ�����
	private static ElementManager eManager=ElementManager.getManager();
	//ͼƬ����  ʹ��map�����д洢     ö����������ƶ�(��չ)
//	public static Map<String,ImageIcon> imgMap=new HashMap<>();
	//�Ͻ�ͷ�Ĵ洢��ʽ����Ϊһ����ͼkey��Ӧ�Ŷ��ŵ�ͼ��Ƭ��������Ƭ��Ҫ��List�洢��
	//������һ��������ϰ�������Ȳ���Ӧ�Ŷ�����Ƭ
	public static Map<String,List<ImageIcon>> imgMap=new HashMap<>();
	//����ӳ��
	private static Map<String,Class<?>> objMap=new HashMap<>();
	//����λ���б�
	private static Map<String, List<String>> positionMap = new HashMap<>();
	
	/**
	 * @˵�� ���ص�ͼ�������ͼID�ɼ��ط������������ļ������Զ�������ͼ�ļ����ƣ�
	 *    �������ļ�
	 * @param mapId �ļ����
	 */
	public static void MapLoad(int mapId) {
		loadObj();
		//�õ����ļ�·��
		String mapString="0,0,game_map";
		//�����洢��obj.pro�ļ�����ַ�������ȡ�ʹ�����ͼ����
		ElementObj map=getObj("game_map");	
		//��������ͼ������ӵ�е�ȫ��Ԫ��
		ElementObj gameMap = map.createElement(mapString);
		//������ʹ���֮�����϶ȣ�����ֱ��ͨ���ӿڻ����ǳ�����Ϳ��Ի�ȡ��ʵ�����
		//ͨ�������ļ������ʹ�����϶�
		eManager.addElement(gameMap, GameElement.MAPS);
	}
	
	/**
	 * @˵�� ����ͼƬ���루��GameData.pro�����ļ��������ͼƬ�����ص��ڴ��У�
	 * ����ͼƬʱ�������ͼƬ֮���һ��·������
	 */
	public static void imgLoad() {	//���Դ���������Ϊ��ͬ�Ĺؿ�Ҳ������Ҫ��һ����ͼƬ��Դ
		String textUrl="text/GameData.pro";//�ļ����������Ը����й���
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream textStream=classLoader.getResourceAsStream(textUrl);
		properties.clear();
		try {
			properties.load(textStream);
			Set<Object> set=properties.keySet();	//��һ��Set����
			for (Object object : set) {
				//��õ�ǰkey��Ӧ��ͼƬ·���ַ���String[]����
				System.out.println(object.toString());
				String[] nameStrings=properties.getProperty(object.toString()).split(";");
				//����һ�����ͼƬ��List
				List<ImageIcon> imageList=new ArrayList<>();
				//���ַ��������ж�ȡ��ǰͼƬ·�����������ļ�ÿһ�е�'='������ַ�����
				//�����ɶ�Ӧ��ͼƬ
				for (int i = 0; i < nameStrings.length; i++) {
					//System.out.println(nameStrings[i]);
					imageList.add(new ImageIcon(nameStrings[i]));
				}
				//��ͼƬӳ��imgMap����ӵ�ǰkey��Ӧ��ͼƬ�б�List<ImageIcon>
				imgMap.put(object.toString(), imageList);
			}
			System.out.println(imgMap.toString());
			//֮��Ϳ����Զ������ͼ��ص�ͼ
//			String[] arrs=properties.getProperty(keyString).split(";");
//			for(int i=0;i<arrs.length;i++) {
//				ElementObj obj=getObj("map");
//				ElementObj elementObj=obj.createElement(keyString+","+arrs[i]);
//				eManager.addElement(elementObj, GameElement.MAPS);
//			}
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	/**
	 * ���طɻ��������������������
	 */
	public static void planeLoad() {
		loadObj();
		String planeString="0,50,helicopter,helicopter_screw";
		ElementObj plane=getObj("helicopter");
		ElementObj helicopter=plane.createElement(planeString);
		eManager.addElement(helicopter, GameElement.HELICOPTER);
	}
	
	/**
	 * ������ң���Ϊ�ϰ�����°���
	 */
	public static void playerLoad() {
		loadObj();
		System.out.println(objMap.toString());
		//û�зŵ������ļ���
		String bodyString="100,350,player_rightbody_pistolwalk";
		String legString="100,393,player_rightleg_standstatic";
		//�����洢��obj.pro�ļ�����ַ�������ȡ�ʹ�������
		//ͬʱ�����ϰ������°���
		ElementObj body=getObj("player_body");	
		ElementObj leg=getObj("player_leg");
		//����ַ�����key��Ҳ��Ψһid���൱��Ϊÿ��������һ��Ψһ��id����
		//����ַ�������һ��Ҫ��obj.pro�����ļ����key��ͬ
		//ElementObj player=new Play().createElement(playerString);
		//�������ö�����ӵ�е�ȫ��Ԫ��
		ElementObj playerBody = body.createElement(bodyString);
		ElementObj playerLeg = leg.createElement(legString);
		//������ʹ���֮�����϶ȣ�����ֱ��ͨ���ӿڻ����ǳ�����Ϳ��Ի�ȡ��ʵ�����
		//ͨ�������ļ������ʹ�����϶�
		eManager.addElement(playerBody, GameElement.PLAYER);
		eManager.addElement(playerLeg, GameElement.PLAYER);
	}
	
	/**
	 * ��������
	 */
	public static void hostageLoad() {
		loadObj();
		//Ŀǰ��ʱ�þ�������������һ�����ʣ����Ϊ��ͬʱ������������ɶ�����꣨�����ʣ�
		String hostage1String="500,360,hostage_bind";
		ElementObj hostage1Obj=getObj("hostage");
		ElementObj hostage1=hostage1Obj.createElement(hostage1String);
		eManager.addElement(hostage1, GameElement.HOSTAGE);
	}
	
	/**
	 * ���ص���
	 */
	public static void enemyLoad() {
		
		loadObj();
		loadPosition();
		
		GameMap gameMap = null;
		
		List<ElementObj> gameMaps = eManager.getElementsByKey(GameElement.MAPS);
		if(gameMaps.get(0) instanceof GameMap) {
			
			gameMap = (GameMap)gameMaps.get(0);
		}
		
		List<String> positions = positionMap.get("enemy_position");
				
		for(int i = positions.size() - 1; i >= 0; i--) {
			
			if(gameMap.getImgx() == Integer.parseInt(positions.get(i))) {
				
				String guardEnemyString = "800,370,guard_enemy_scared";
				ElementObj guardEnemyObj = getObj("guard_enemy");
				ElementObj guardEnemy = guardEnemyObj.createElement(guardEnemyString);
				
				eManager.addElement(guardEnemy, GameElement.ENEMY);
				positions.remove(i);
				
			}
		}
		
	}
	
	private static void loadPosition() {
		String textUrl="text/Position.pro";//����λ�����������ļ�λ��
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream textStream=classLoader.getResourceAsStream(textUrl);
		properties.clear();	
		
		try {
			properties.load(textStream);
			Set<Object> set=properties.keySet();	//��һ��Set����
			for (Object object : set) {
				
				String[] positions =properties.getProperty(object.toString()).split(";");	//���˳��ֵ�x������
				
				//����λ���б�
				List<String> strList=new ArrayList<>();
				for (int i = 0; i < positions.length; i++) {
					//System.out.println(nameStrings[i]);
					strList.add(positions[i]);
				}

				//�������λ��map
				positionMap.put(object.toString(), strList);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����BOSS
	 */
	public static void bossLoad() {
		loadObj();
		//Ŀǰ��ʱ�þ�������������һ��boss�����Ϊ��ͬʱ������������ɶ�����꣨�����ʣ�
		String bossString="600,240,boss";
		ElementObj boss1=getObj("boss");
		ElementObj boss=boss1.createElement(bossString);
		eManager.addElement(boss, GameElement.BOSS);
	}
	
	public static ElementObj getObj(String string) {
		try {
			Class<?> class1 = objMap.get(string);	//��Ҫ�ĸ�����ṩ�����ַ���
			Object newInstance = class1.newInstance();
			if (newInstance instanceof ElementObj) {
				return (ElementObj) newInstance;	//�������ͺ�new Play()�ȼ�
				//���Ժ�������಻����Play������GamePlayer
			}
		} catch (InstantiationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * ��չ��ʹ�������ļ�����ʵ��������ͨ���̶���keyString(�����ļ���"="�����ַ���)��ʵ����
	 * �÷�ʽ�Ὣ�����ļ��������оٵ�key�����ɶ��󲢴洢��objMap��
	 */
	public static void loadObj() {
		String textUrl="text/obj.pro";//��obj.pro�����ļ��ж�ȡԪ����
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream textStream=classLoader.getResourceAsStream(textUrl);
		properties.clear();
		try {
			properties.load(textStream);
			Set<Object> set=properties.keySet();	//��һ����keySet����
			for (Object object : set) {
				//��keySet��ȡֵ����
				String classUrl=properties.getProperty(object.toString());
				//ʹ�÷���ķ�ʽֱ�ӽ�����л�ȡ
				Class<?> forName=Class.forName(classUrl);
				//������ȡ����ÿһ���඼�������ӳ��objMap��
				objMap.put(object.toString(), forName);
			}
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
//	//���ڵ��ԣ������ͼ�е�ÿ��ǽ���ϵ����꣩
//	public static void main(String[] args) {
//		MapLoad(5);
//		
//		//ʵ�ַ�������ַ�ʽ
//		try {
//			//1.ͨ����·�����ƣ�com.tedu.Play
//			Class<?> forName = Class.forName("");
//			//2.ͨ����������ֱ�ӷ��ʵ������
//			Class<?> forName1=GameLoad.class;
//			//3.ͨ��ʵ������ȡ�������
//			GameLoad gameLoad=new GameLoad();
//			Class<? extends GameLoad> class1 = gameLoad.getClass();
//		} catch (ClassNotFoundException e) {
//			// TODO �Զ����ɵ� catch ��
//			e.printStackTrace();
//		}
//	}
}