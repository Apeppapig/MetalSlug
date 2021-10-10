package element;

import java.awt.Graphics;
import java.util.List;
import javax.swing.ImageIcon;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @说明 游戏地图类
 * @author DELL
 *
 */
public class GameMap extends ElementObj{
	
	private String name;	//地图名称
	
	private int imgx=0;//由方向控制
	private long imgtime=0;		//用于控制图片变化速度
	private boolean left=false; //左
	private boolean right=false;//右
	//当前地图照片列表
	private List<ImageIcon> mapList;
	private ElementManager eManager;
	private ImageIcon icon2;

	public GameMap() {
		eManager=ElementManager.getManager();
	}
	
	//显示当前元素
	@Override
	public void showElement(Graphics g) {
		//由于地图图片长度过大，需要分块显示
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 		//传入游戏窗口左上角图标(0,0)
				this.getX()+800, this.getY()+480,	//传入游戏窗口右下角图标(800,480)
				0+getImgx(), 0, 			//需加载的图片的左上角坐标
				400+getImgx(), 220, 		//需加载的图片的右下角坐标（整张地图加载）
				null);
	}

	//依据传入的字符串参数构造元素
	@Override
	public ElementObj createElement(String str) {
		String[] splitStrings=str.split(",");
		this.setX(Integer.parseInt(splitStrings[0]));
		this.setY(Integer.parseInt(splitStrings[1]));
		//从加载器的imgMap静态图片映射中获取当前地图图片列表
		mapList=GameLoad.imgMap.get(splitStrings[2]);
		System.out.println(GameLoad.imgMap.get(splitStrings[2]));
		//再从地图图片列表里取出默认状态的第一张图片
		icon2=mapList.get(0);	
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}
	
	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// 按下
			switch (key) { // 怎么优化 大家中午思考;加监听会持续触发；有没办法触发一次
			case 37:
				this.right = false;
				this.left = true;	
				break;
			case 39:
				this.left = false;
				this.right = true;
//				imgx+=10;
				break;
			}
		} else {//松开按键
			switch (key) {
			case 37:
				this.left = false;
				break;
			case 39:
				this.right = false;
				break;
			}
		}
	}
	
	//仿照真正的合金弹头，不能走回头路
	@Override
	protected void move(long gameTime) {
		if (this.left) {	
//			imgx--;
		}
		//只有当主角行进方向为右并且地图里没有敌人时
		if (this.right&&getImgx()<1080) { 
			imgx = getImgx() + 1;
		}
	}
	
	/**
	 * 判断当前地图中是否还有敌人和BOSS，有则地图先停下；若没有地图则继续向前移动
	 */
	private boolean hasEnemy() {
		if (eManager.getElementsByKey(GameElement.BOSS).isEmpty()&&
				eManager.getElementsByKey(GameElement.ENEMY).isEmpty()) {
			return false;
		}
		return true;
	}

	public int getImgx() {
		return imgx;
	}
}
