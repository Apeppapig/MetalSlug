package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

public class Bomb extends ElementObj {
	
	private int moveNum=3;//移动速度值
	private List<ElementObj> bombList;//炸弹对象集合
	private List<ImageIcon> bombImageList;//炸弹图片集合
	private ElementManager em;
	private long changeTime=0L;
	private String recString;
	private int i=0;
	private int boomIndex=0;
	
	public Bomb() {
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
				case "pic1":
					bombImageList=GameLoad.imgMap.get(split2[1]);
					icon=bombImageList.get(0);
					this.setIcon(icon);
					break;
				case "pic2":
					recString=split2[1];
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
	protected void updateImage(long gameTime) {
		if (this.getLive()&&gameTime-changeTime>15) {
			changeTime=gameTime;
			if (i<=bombImageList.size()-1) {
				this.setIcon(bombImageList.get(i++));
			}
			else {
				i=0;
			}
		}
	}
	
	@Override
	protected void move(long gameTime) {
		if (this.getY()<380) {
			this.setY(this.getY()+2);
		}
		if (this.getY()>=380&&gameTime-changeTime>10) {
			bombImageList=GameLoad.imgMap.get(recString);
			changeTime=gameTime;
			if (boomIndex<=bombImageList.size()-1) {
				Bomb.this.setIcon(bombImageList.get(boomIndex++));
			}
			else {
				this.setLive(false);
			}
			
		}
	}

	public void die(int i) {
		bombList=em.getElementsByKey(GameElement.BOMB);
		bombList.remove(i);
	}
}
