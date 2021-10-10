package element;

import java.awt.Graphics;
import java.util.List;
import javax.swing.ImageIcon;

import element.ElementObj;
import element.PlayerBullet;
import manager.ElementManager;
import manager.GameElement;

import manager.GameLoad;

/**
 * @说明 该类为主角上半身类
 * @author DELL
 *
 */
public class PlayerBody extends ElementObj{
	
	private boolean left = false; // 左
	private boolean up = false; // 上
	private boolean right = false;// 右
	private boolean down = false;//下
	private boolean walk=false;	//步行状态
	private boolean jump=false;	//跳跃状态
	private boolean attack = false;//攻击状态 true 攻击 false停止
	private int jumpHeight=5;	//move运行一次，主角的跳跃高度
	private int i=0;
	private int bulletNum=0;
	private long imgTime=0;		//用于控制图片变化速度
	private long bulletTime=0;	//用于控制子弹发射
	//记录当前主角的朝向（向上/向左/向右），默认为向右
	private String direction="right";
	//记录当前主角的上半身状态
	private String stateString=null;
	//主角的武器标识（默认为手枪）
	private String weaponString="pistol";
	//当前身体照片列表
	private List<ImageIcon> bodyList;
	private ElementManager em;
	
	public PlayerBody() {
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
		bodyList=GameLoad.imgMap.get(splitStrings[2]);
		System.out.println(GameLoad.imgMap.get(splitStrings[2]));
		//再从身体图片列表里取出默认状态的第一张图片
		ImageIcon icon2=bodyList.get(0);	
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}

	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// 按下
			switch (key) { // 怎么优化 大家中午思考;加监听会持续触发；有没办法触发一次
			case 37://左
				this.right = false;
				this.left = true;
				if (!this.jump&&this.direction.equals("right")) {//为了使上半身匹配下半身
					this.setX(this.getX()-50);
				}
				this.walk=true;
				this.direction = "left";
				break;
//			case 38://上
//				this.right = false;
//				this.left = false;
//				this.down = false;
//				this.up = true;
//				this.direction = "up";
//				break;
			case 39://右
				this.left = false;
				this.right = true;
				if (!this.jump&&this.direction.equals("left")) {//为了使上半身匹配下半身
					this.setX(this.getX()+50);
				}
				this.walk=true;
				this.direction = "right";
				break;
			case 40://下
//				this.right = false;
//				this.left = false;
				this.jump=false;//
				this.up = false;//
				this.down = true;
				if (this.direction.equals("right")) {
					this.setY(this.getY()+7);
					this.setX(this.getX()-5);
				}
				if (this.direction.equals("left")) {
					this.setY(this.getY()+7);
					this.setX(this.getX()+5);
				}
				//this.direction = "down";
				break;
			case 65:	// 开启攻击状态
				
				this.attack = true;
				break;
			case 83:	//开启跳跃状态
				this.down=false;
				this.jump=true;	//
				this.setW(65);	//因为身体跳起与正常行走的图片大小不同
				this.setH(55);	//∴需要更换下尺寸
				if (direction.equals("right")) {
					this.setX(getX()-15);
				}
				if (direction.equals("left")) {
					this.setX(getX()+35);
				}
				break;
			}
		} else {//松开按键
			switch (key) {
			case 37://左
				this.left = false;
				break;
			case 38://上
				this.up = false;
				break;
			case 39://右
				this.right = false;
				break;
			case 40://下
				this.down = false;
				if (this.direction.equals("right")) {
					this.setY(this.getY()-7);
					this.setX(this.getX()+5);
				}
				if (this.direction.equals("left")) {
					this.setY(this.getY()-7);
					this.setX(this.getX()-5);
				}
				break;
			case 65:// 关闭攻击状态
				this.attack = false;
				break;
			case 83:
				this.jump=false;	
				this.setW(85);
				this.setH(55);
				if (direction.equals("right")) {
					this.setX(getX()+15);
				}
				if (direction.equals("left")) {
					this.setX(getX()-35);
				}
				break;
			}
		}
	}
	
	/**
	 * 动态改变当前玩家的人物图片
	 */
	@Override
	protected void updateImage(long gameTime) {	
		//1.先生成当前上半身状态字符串
		if (this.getBulletNum()==0) {
			this.setWeaponString("pistol");
			this.setBulletNum(0);
		}
		buildStateString();
		bodyList=GameLoad.imgMap.get(stateString);
		//2.再遍历该种状态下的图片列表，依次为主角更换图片
		if (gameTime-imgTime>5) {
			imgTime=gameTime;
			if (i<=bodyList.size()-1) {
				this.setIcon(bodyList.get(i++));
			}
			else {	//防止下标越界，并且可以循环切换图片
				i=0;
			}
		}
	}
	
	@Override
	protected void move(long gameTime) {	
		
		if (this.left && this.getX() > 0) {
			this.setX(this.getX() - 2);
		}
		if (this.jump && this.getY() > 245) {
			this.setY(this.getY() - jumpHeight);
		}
		if (this.right && this.getX() < 800 - 80) { // 坐标的跳转由大家来完成
			this.setX(this.getX() + 2);
		}
		if (!this.jump &&this.getY()<350) {
			this.setY(this.getY() + jumpHeight);
		}
		
	}

	/**
	 * 加入子弹/道具
	 */
	@Override
	protected void add(long gameTime) {
		if (this.attack&&gameTime-bulletTime>25) {
			bulletTime=gameTime;//控制子弹的生成间隔
//			this.attack = false;// 按一次，发射一个子弹。拼手速(也可以增加变量bulletTime来控制)
			ElementObj playerBullet = new PlayerBullet().createElement(createBullet());
			em.addElement(playerBullet, GameElement.PLAYER_BULLET);
			// 如果要控制子弹速度等等，还需要代码编写
		}
		
	}
	
	@Override
	public void attackNow(ElementObj obj) {
		if (obj instanceof PlayerProp) {
			this.setWeaponString("rifle");
			this.setBulletNum(50);//拿到步枪，有50颗子弹
		}
	}
	
	private String createBullet() {
		// {x:3,y:5,f:up,t:A} 模仿json格式(如果是网络游戏，通过与服务器通信获取到JSON字符串之后再做出解析)
		int x = this.getX();
		int y = this.getY();
		// 子弹在发射时候就已经给予固定的轨迹。可以加上目标，修改json格式
		if (this.getWeaponString().equals("pistol")) {
			switch (this.direction) { 
			case "up":
				x += 20;
				break;
			// 一般不会写20等数值，一般情况下图片大小就是显示大小，可以使用图片大小参与运算
			case "left":
				y += 20;
				break;
			case "right":
				x += 80;
				y += 20;
				break;
			case "down":
				y += 50;
				x += 20;
				break;
			}
		}
		else if (this.getWeaponString().equals("rifle")) {
			bulletNum--;
			switch (this.direction) { 
			case "up":
				x += 20;
				break;
			// 一般不会写20等数值，一般情况下图片大小就是显示大小，可以使用图片大小参与运算
			case "left":
				x-=30;
				y += 20;
				break;
			case "right":
				x += 80;
				y += 20;
				break;
			case "down":
				y += 50;
				x += 20;
				break;
			}
		}
		return "x:" + x + ",y:" + y + ",f:" + this.direction+
				",pic:"+"player_"+this.weaponString+this.direction+"_bullet";
	}
	
	private void buildStateString() {
		if (direction.equals("left")) {
			if (jump) {
				stateString = "player_leftbody_"+this.weaponString+"jump";
			} else {
				if (attack) {
					stateString = "player_leftbody_"+this.getWeaponString()+"attack";
				} else {
					stateString = "player_leftbody_"+this.getWeaponString()+"walk";
				}
			}
		} else if (direction.equals("right")) {
			if (jump) {
				stateString = "player_rightbody_"+this.weaponString+"jump";
			} else {
				if (attack) {
					stateString = "player_rightbody_"+this.getWeaponString()+"attack";
				} else {
					stateString = "player_rightbody_"+this.getWeaponString()+"walk";
				}
			}
		}
	}

	public String getWeaponString() {
		return weaponString;
	}

	public void setWeaponString(String weaponString) {
		this.weaponString = weaponString;
	}

	public int getBulletNum() {
		return bulletNum;
	}

	public void setBulletNum(int bulletNum) {
		this.bulletNum = bulletNum;
	}
}
