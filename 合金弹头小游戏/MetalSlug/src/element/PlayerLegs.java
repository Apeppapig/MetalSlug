package element;

import java.awt.Graphics;
import java.util.List;
import javax.swing.ImageIcon;

import manager.GameLoad;

/**
 * @说明 主角下半身类
 * @author DELL
 *
 */
public class PlayerLegs extends ElementObj{
	
	private boolean left = false; // 左
	private boolean up = false; // 上
	private boolean right = false;// 右
	private boolean down = false; // 下
	private boolean walk=false;	//行走状态
	private boolean jump=false;	//跳跃状态
	private boolean squat=false;	//蹲下状态
	//因为站立静止状态static只有一张图片，当上面三种状态都为false时就加载
	private long imgtime=0L;		//用于控制图片变化速度
	private int i=0;
	// 专门用来记录当前主角面向的方向,默认是right
	private String direction = "right";
	//记录当前主角的大腿状态
	private String stateString=null;
	//当前大腿照片列表
	private List<ImageIcon> legList;

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
		legList = GameLoad.imgMap.get(splitStrings[2]);
		ImageIcon icon2=legList.get(0);
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}

	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// 按下
			switch (key) {
			case 37://左
				this.right = false;
				this.direction = "left";
				this.walk=true;
				this.left = true;
				break;
//			case 38:
//				this.right = false;
//				this.left = false;
//				this.down = false;
//				this.up = true;
//				this.direction = "up";
//				break;
			case 39://右
				this.left = false;
				this.direction = "right";
				this.walk=true;
				this.right = true;
				break;
			case 40://下
				this.jump=false;
				this.squat = true;
				break;
//			case 65:
//				break;// 开启攻击状态
			case 83:
				this.down=false;
				this.jump=true;
				
				break;
			}
		} else {//松开
			switch (key) {
			case 37:
				this.left = false;
				this.walk=false;
				break;
//			case 38:
//				this.up = false;
//				break;
			case 39:
				this.walk=false;
				this.right = false;
				break;
			case 40:
				this.squat = false;
				break;
//			case 65:
//				break;// 关闭攻击状态
			case 83:
				this.jump=false;
				break;
			}
		}
	}
	
	@Override
	protected void move(long gameTime) {
		if (this.left && this.getX() > 50) {
			this.setX(getX()-2);
		}
		if (this.jump && this.getY() > 293) {
			this.setY(this.getY() - 5);
		}
		if (this.right && this.getX() < 800 - 80) { // 坐标的跳转由大家来完成
			this.setX(getX() + 2);
		}
		if (!this.jump && this.getY() < 393) {
			this.setY(getY() + 5);
		}
	}
	
	@Override
	protected void updateImage(long gameTime) {
		buildStateString();
		legList=GameLoad.imgMap.get(stateString);
		if (gameTime-imgtime>5) {
			imgtime=gameTime;
			if (i<=legList.size()-1) {
				this.setIcon(legList.get(i++));
			}
			else {
				i=0;
			}
		}
	}
	
	private void buildStateString() {
		// 先根据腿部状态构造字符串
		if (this.direction.equals("left")) {// 若角色面向左
			if (jump) {// 若角色当前为跳起状态
				stateString = "player_leftleg_jump";
			} else {// 若角色当前为非跳起状态
				System.out.println(squat + "+" + walk);
				if (squat) {// 若角色当前为蹲下状态
					if (walk) {// 若角色当前为走动状态
						stateString = "player_leftleg_squatwalk";
					} else {// 若角色当前为静止状态
						stateString = "player_leftleg_squatstatic";
					}
				} else {// 若角色当前为站立状态
					if (walk) {// 若角色当前为走动状态
						stateString = "player_leftleg_standwalk";
					} else {// 若角色当前为静止状态
						stateString = "player_leftleg_standstatic";
					}
				}
			}

		} else if (this.direction.equals("right")) {// 若角色面向右
			if (jump) {// 若角色当前为跳起状态
				stateString = "player_rightleg_jump";
			} else {// 若角色当前为非跳起状态
				if (squat) {// 若角色当前为蹲下状态
					if (walk) {// 若角色当前为走动状态
						stateString = "player_rightleg_squatwalk";
					} else {// 若角色当前为静止状态
						stateString = "player_rightleg_squatstatic";
					}
				} else {// 若角色当前为站立状态
					if (walk) {// 若角色当前为走动状态
						stateString = "player_rightleg_standwalk";
					} else {// 若角色当前为静止状态
						stateString = "player_rightleg_standstatic";
					}
				}
			}
		}
	}
}
