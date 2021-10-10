package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

public class BossBullet extends ElementObj {
	private int moveNum=3;//移动速度值
	private List<ElementObj> bulletList;//炸弹对象集合
	private List<ImageIcon> bulletImageList;//炸弹图片集合
	private ElementManager em;

	public BossBullet() {
		em=ElementManager.getManager();
	}
	
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
				case "pic":
					bulletImageList=GameLoad.imgMap.get(split2[1]);
					icon=bulletImageList.get(0);
					this.setIcon(icon);
					break;
			}
		}
		this.setW(icon.getIconWidth());
		this.setH(icon.getIconHeight());
		return this;
	}

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	
	
	@Override
	protected void move(long gameTime) {
		if(this.getX()<0 || this.getX() >800 || 
				this.getY() <0 || this.getY()>480) {
			this.setLive(false);
			return;
		}
	   this.setX(this.getX() - this.moveNum);
	}

	public void die(int i) {
		bulletList=em.getElementsByKey(GameElement.BOSS_BULLET);
		bulletList.remove(i);
//		em.addElement(this,GameElement.DIE);//将其归入到已死亡元素
	}
}
