package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @说明 人质类
 * @author DELL
 *
 */
public class Hostage extends ElementObj{
	
	private boolean right=false;
	private boolean left=false;
	private int recordX;
	private int recordY;
	private String stateString=null;
	//当前元素管理器
	private ElementManager em;
	//当前人质照片列表
	private List<ImageIcon> hostageImgList;
	private List<ElementObj> hostageList;
	//当前人质是否获救
	private boolean isRescue=false;//rescue的状态改变在GameThread里面改变
	//当前人质是否已经给主角道具(true为已经给；false为没给)
	private boolean isgiven=false;
	private long bindTime=5L;		//用于控制人质被绑时图片变化速度
	private long rescueTime=3L;		//用于控制人质刚脱险时图片变化速度
	private long giveTime=5L;		//用于控制人质掉落道具时图片变化速度
	private long fleeTime=0L;		//用于控制人质逃离现场时图片变化速度
	private int i=0;

	public Hostage() {
		em=ElementManager.getManager();
	}
	
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	
	@Override
	public ElementObj createElement(String str) {
		String[] splitStrings = str.split(",");
		this.setX(Integer.parseInt(splitStrings[0]));
		this.setY(Integer.parseInt(splitStrings[1]));
		//从加载器的imgMap静态图片映射中获取当前身体图片列表
		hostageImgList=GameLoad.imgMap.get(splitStrings[2]);
		//再从身体图片列表里取出默认状态的第一张图片
		ImageIcon icon2=hostageImgList.get(0);	
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
	
	/**
	 * 人质在获救，给道具，逃离等过程中的图片更新
	 */
	@Override
	protected void updateImage(long gameTime) {
		//如果人质还未被救（不断更新挣扎图片）
		if (this.getLive()&&(!this.getRescue())&&(!this.getGiven())) {	
			stateString="hostage_bind";
			hostageImgList=GameLoad.imgMap.get(stateString);
			if (gameTime-bindTime>20) {
				bindTime=gameTime;
				if (i<=hostageImgList.size()-1) {
					this.setIcon(hostageImgList.get(i++));
				}
				else {
					i=0;
				}
			}
		}
		//人质得到救援，但还未掉落道具，还未向左逃离
		//rescue的状态改变在GameThread里面变为真
		if (this.getLive()&&this.getRescue()&&(!this.getGiven())) {	
			stateString="hostage_rescue";
			hostageImgList=GameLoad.imgMap.get(stateString);
			if (gameTime-rescueTime>25) {
				rescueTime=gameTime;
				if (i<=hostageImgList.size()-1) {
					this.setIcon(hostageImgList.get(i++));
				}
				else {
					this.setGiven(true);//松绑的一系列图片更换完毕后，把掉落道具状态设为真
				}
			}	
		}
		//人质掉落道具
		if (this.getLive()&&this.getRescue()&&this.getGiven()) {
			stateString="hostage_give";
			hostageImgList=GameLoad.imgMap.get(stateString);
			if (gameTime-giveTime>20) {
				giveTime=gameTime;
				if (i<=hostageImgList.size()-1) {
					this.setIcon(hostageImgList.get(i++));
				}
				else {
					this.setLive(false);
					this.setRescue(false);
				}
			}
		}
		if ((!this.getLive())) {	//人质向左逃离
//			this.setRescue(false);
			stateString="hostage_runaway";
			hostageImgList=GameLoad.imgMap.get(stateString);
			if (gameTime-fleeTime>10) {
				fleeTime=gameTime;
				if (i<=hostageImgList.size()-1) {
					this.setIcon(hostageImgList.get(i++));
				}
				else {
					i=0;
				}
			}	
		}
	}
	
	@Override
	protected void move(long gameTime) {
		if (this.left) {

		}
		if (this.right&&this.getX()<1400) { // 坐标的跳转由大家来完成
//			this.setX(this.getX()-2);
		}
	}
	
	/**
	 * 人质获救，给主角道具
	 */
	@Override
	protected void add(long gameTime) {
		//如果人质处于给予道具状态，则生成道具
		if ((!this.getLive())&&(!this.getRescue())&&this.getGiven()) {	
			this.recordX=this.getX();
			this.recordY=this.getY();
			ElementObj playerProp=new PlayerProp().createElement(createProp());
			em.addElement(playerProp, GameElement.PLAYER_PROP);
			this.setGiven(false);
		}
		
	}
	
	private String createProp() {
		int x = recordX;
		int y = recordY;
		//初始化道具出现的位置
		x+=10;
		y+=40;
		return "x:" + x + ",y:" + y + ",pic:"+"player_prop";
	}
	
	@Override
	public void attackNow(ElementObj obj) {
		if (obj instanceof PlayerBody||obj instanceof PlayerLegs) {
			this.setRescue(true);
		}
	}

	/**
	 * 人质给主角道具后向左移动，直到离开地图左边界后从人质集合中remove
	 */
	@Override
	public void die(int i) {
		if ((!this.getLive())&&this.getX()>=0) {
			this.setX(this.getX() - 1);
		}
		if (this.getX()<0) {
			hostageList=em.getElementsByKey(GameElement.HOSTAGE);
			hostageList.remove(i);
		}
	}

	//Getter和Setter
	public boolean getRescue() {
		return isRescue;
	}

	public void setRescue(boolean isRescue) {
		this.isRescue = isRescue;
	}

	public boolean getGiven() {
		return isgiven;
	}

	public void setGiven(boolean isgiven) {
		this.isgiven = isgiven;
	}

	
}
