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
 * @说明  加载器（用于读取配置文件的工具），大多提供的是static方法
 *
 */
public class GameLoad {
	
	//Collection:用于集合排序的工具类，可以为所有对象类型的记录进行排序
	//被排序的集合只能为Collection的子类
	private static Properties properties=new Properties();//用于读取文件的类
	private static ElementManager eManager=ElementManager.getManager();
	//图片集合  使用map来进行存储     枚举类型配合移动(扩展)
//	public static Map<String,ImageIcon> imgMap=new HashMap<>();
	//合金弹头的存储方式（因为一个地图key对应着多张地图照片，多张照片需要用List存储）
	//或者是一个人物的上半身或者腿部对应着多张照片
	public static Map<String,List<ImageIcon>> imgMap=new HashMap<>();
	//对象映射
	private static Map<String,Class<?>> objMap=new HashMap<>();
	//敌人位置列表
	private static Map<String, List<String>> positionMap = new HashMap<>();
	
	/**
	 * @说明 加载地图，传入地图ID由加载方法依据配置文件规则自动产生地图文件名称，
	 *    并加载文件
	 * @param mapId 文件编号
	 */
	public static void MapLoad(int mapId) {
		loadObj();
		//得到了文件路径
		String mapString="0,0,game_map";
		//依靠存储在obj.pro文件里的字符串来读取和创建地图对象
		ElementObj map=getObj("game_map");	
		//创建出地图对象所拥有的全部元素
		ElementObj gameMap = map.createElement(mapString);
		//解耦，降低代码之间的耦合度，可以直接通过接口或者是抽象父类就可以获取到实体对象
		//通过配置文件来降低代码耦合度
		eManager.addElement(gameMap, GameElement.MAPS);
	}
	
	/**
	 * @说明 加载图片代码（将GameData.pro配置文件里的所有图片都加载到内存中）
	 * 加载图片时，代码和图片之间差一个路径问题
	 */
	public static void imgLoad() {	//可以带参数，因为不同的关卡也可能需要不一样的图片资源
		String textUrl="text/GameData.pro";//文件的命名可以更加有规律
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream textStream=classLoader.getResourceAsStream(textUrl);
		properties.clear();
		try {
			properties.load(textStream);
			Set<Object> set=properties.keySet();	//是一个Set集合
			for (Object object : set) {
				//获得当前key对应的图片路径字符串String[]数组
				System.out.println(object.toString());
				String[] nameStrings=properties.getProperty(object.toString()).split(";");
				//声明一个存放图片的List
				List<ImageIcon> imageList=new ArrayList<>();
				//从字符串数组中读取当前图片路径（即配置文件每一行的'='后面的字符串）
				//并生成对应的图片
				for (int i = 0; i < nameStrings.length; i++) {
					//System.out.println(nameStrings[i]);
					imageList.add(new ImageIcon(nameStrings[i]));
				}
				//往图片映射imgMap中添加当前key对应的图片列表List<ImageIcon>
				imgMap.put(object.toString(), imageList);
			}
			System.out.println(imgMap.toString());
			//之后就可以自动创建和加载地图
//			String[] arrs=properties.getProperty(keyString).split(";");
//			for(int i=0;i<arrs.length;i++) {
//				ElementObj obj=getObj("map");
//				ElementObj elementObj=obj.createElement(keyString+","+arrs[i]);
//				eManager.addElement(elementObj, GameElement.MAPS);
//			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载飞机（包括机身和螺旋桨）
	 */
	public static void planeLoad() {
		loadObj();
		String planeString="0,50,helicopter,helicopter_screw";
		ElementObj plane=getObj("helicopter");
		ElementObj helicopter=plane.createElement(planeString);
		eManager.addElement(helicopter, GameElement.HELICOPTER);
	}
	
	/**
	 * 加载玩家（分为上半身和下半身）
	 */
	public static void playerLoad() {
		loadObj();
		System.out.println(objMap.toString());
		//没有放到配置文件中
		String bodyString="100,350,player_rightbody_pistolwalk";
		String legString="100,393,player_rightleg_standstatic";
		//依靠存储在obj.pro文件里的字符串来读取和创建对象
		//同时创建上半身与下半身
		ElementObj body=getObj("player_body");	
		ElementObj leg=getObj("player_leg");
		//这个字符串是key，也是唯一id，相当于为每个类起了一个唯一的id名称
		//这个字符串名称一定要和obj.pro配置文件里的key相同
		//ElementObj player=new Play().createElement(playerString);
		//创建出该对象所拥有的全部元素
		ElementObj playerBody = body.createElement(bodyString);
		ElementObj playerLeg = leg.createElement(legString);
		//解耦，降低代码之间的耦合度，可以直接通过接口或者是抽象父类就可以获取到实体对象
		//通过配置文件来降低代码耦合度
		eManager.addElement(playerBody, GameElement.PLAYER);
		eManager.addElement(playerLeg, GameElement.PLAYER);
	}
	
	/**
	 * 加载人质
	 */
	public static void hostageLoad() {
		loadObj();
		//目前暂时用具体坐标来生成一个人质，最后换为相同时间间隔内随机生成多个坐标（多人质）
		String hostage1String="500,360,hostage_bind";
		ElementObj hostage1Obj=getObj("hostage");
		ElementObj hostage1=hostage1Obj.createElement(hostage1String);
		eManager.addElement(hostage1, GameElement.HOSTAGE);
	}
	
	/**
	 * 加载敌人
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
		String textUrl="text/Position.pro";//敌人位置数据配置文件位置
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream textStream=classLoader.getResourceAsStream(textUrl);
		properties.clear();	
		
		try {
			properties.load(textStream);
			Set<Object> set=properties.keySet();	//是一个Set集合
			for (Object object : set) {
				
				String[] positions =properties.getProperty(object.toString()).split(";");	//敌人出现的x轴坐标
				
				//创建位置列表
				List<String> strList=new ArrayList<>();
				for (int i = 0; i < positions.length; i++) {
					//System.out.println(nameStrings[i]);
					strList.add(positions[i]);
				}

				//加入敌人位置map
				positionMap.put(object.toString(), strList);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载BOSS
	 */
	public static void bossLoad() {
		loadObj();
		//目前暂时用具体坐标来生成一个boss，最后换为相同时间间隔内随机生成多个坐标（多人质）
		String bossString="600,240,boss";
		ElementObj boss1=getObj("boss");
		ElementObj boss=boss1.createElement(bossString);
		eManager.addElement(boss, GameElement.BOSS);
	}
	
	public static ElementObj getObj(String string) {
		try {
			Class<?> class1 = objMap.get(string);	//需要哪个类就提供类名字符串
			Object newInstance = class1.newInstance();
			if (newInstance instanceof ElementObj) {
				return (ElementObj) newInstance;	//这个对象就和new Play()等价
				//若以后的主角类不再是Play，而是GamePlayer
			}
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 扩展：使用配置文件，来实例化对象，通过固定的keyString(配置文件中"="左侧的字符串)来实例化
	 * 该方式会将配置文件中所有列举的key都生成对象并存储在objMap中
	 */
	public static void loadObj() {
		String textUrl="text/obj.pro";//从obj.pro配置文件中读取元素名
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream textStream=classLoader.getResourceAsStream(textUrl);
		properties.clear();
		try {
			properties.load(textStream);
			Set<Object> set=properties.keySet();	//是一个键keySet集合
			for (Object object : set) {
				//由keySet获取值集合
				String classUrl=properties.getProperty(object.toString());
				//使用反射的方式直接将类进行获取
				Class<?> forName=Class.forName(classUrl);
				//并将获取到的每一个类都放入对象映射objMap中
				objMap.put(object.toString(), forName);
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
//	//用于调试（输出地图中的每种墙材料的坐标）
//	public static void main(String[] args) {
//		MapLoad(5);
//		
//		//实现反射的三种方式
//		try {
//			//1.通过类路径名称：com.tedu.Play
//			Class<?> forName = Class.forName("");
//			//2.通过类名可以直接访问到这个类
//			Class<?> forName1=GameLoad.class;
//			//3.通过实体对象获取反射对象
//			GameLoad gameLoad=new GameLoad();
//			Class<? extends GameLoad> class1 = gameLoad.getClass();
//		} catch (ClassNotFoundException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	}
}