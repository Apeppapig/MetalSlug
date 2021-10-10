package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import element.ElementObj;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

public class PlayerBullet extends ElementObj{
	
	private boolean left=false;
	private boolean right=false;
	private int attack;//攻击力
	private int moveNum=3;//移动速度值
	private String direction;
	private List<ElementObj> bulletList;
	private ElementManager em;
	
	//剩下的大家扩展; 可以扩展出多种子弹： 激光，导弹等等。(玩家类就需要有子弹类型)
	public PlayerBullet() {
		em=ElementManager.getManager();
	}
	
	//对创建这个对象的过程进行封装，外界只需要传输必要的约定参数，返回值就是对象实体
	//模仿与服务器通信并获取到的JSON格式字符串{x:3,y:5,f:up,pic:...}，并对其进行解析
	@Override   
	public ElementObj createElement(String str) {//定义字符串的规则
		ImageIcon icon=null;
		String[] split = str.split(",");
		//split[0]是x:3；split[1]是y:5；split[2]是f:up；split[3]是pic:player_pistolright_bullet
		for(String str1 : split) {
			//split2[0]是 x,y,f,pic而split2[1]是x,y,f,pic对应的值
			String[] split2 = str1.split(":");
			switch(split2[0]) {
				case "x":
					this.setX(Integer.parseInt(split2[1]));
					break;
				case "y":
					this.setY(Integer.parseInt(split2[1]));
					break;
				case "f":
					this.direction = split2[1];
					break;
				case "pic":
					icon=GameLoad.imgMap.get(split2[1]).get(0);
					this.setIcon(icon);
					break;
			}
		}
		this.setW(icon.getIconWidth());
		this.setH(icon.getIconHeight());
		return this;
	}
	
	@Override
	public void showElement(Graphics g) {//显示子弹图片	
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	
	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// 按下
			switch (key) { // 怎么优化 大家中午思考;加监听会持续触发；有没办法触发一次
			case 37://左
				this.right = false;
				this.left = true;
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
				break;
			}
		} else {//松开按键
			switch (key) {
			case 37://左
				this.left = false;
				break;
//			case 38://上
//				this.up = false;
//				break;
			case 39://右
				this.right = false;
				break;
			}
		}
	}
	
	@Override
	protected void move(long gameTime) {
		if(this.getX()<0 || this.getX() >800 || 
				this.getY() <0 || this.getY()>480) {
			this.setLive(false);
			return;
		}
		switch(this.direction) {	//控制子弹的飞行方向
//			case "up":
//				this.setY(this.getY() - this.moveNum);
//				break;
			case "left":
				if (left) {
					this.setX(this.getX() - this.moveNum);
				}
				else {
					this.setX(this.getX() - this.moveNum);
				}
				break;
			case "right":
				if (right) {
					this.setX(this.getX() + this.moveNum);
				}
				else {
					this.setX(this.getX() + this.moveNum);
				}
				break;
		}
		
	}
	
	@Override
	public void attackNow(ElementObj obj) {
		if (obj instanceof Boss) {
			this.setLive(false);
		}
	}
	
	/**
	 * 对于子弹来说：1.出边界  2.与敌人碰撞
	 * 处理方式就是，当达到死亡的条件时，只进行修改死亡状态的操作。
	 */
	@Override
	public void die(int i) {
		bulletList=em.getElementsByKey(GameElement.PLAYER_BULLET);
		bulletList.remove(i);
//		em.addElement(this,GameElement.DIE);//将其归入到已死亡元素
	}
	
}
