package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @说明 玩家道具类
 * @author DELL
 *
 */
public class PlayerProp extends ElementObj{
	
	private List<ElementObj> propList;
	private ElementManager em;
	private boolean right;
	private boolean left;
	
	public PlayerProp() {
		em=ElementManager.getManager();
		propList=em.getElementsByKey(GameElement.PLAYER_PROP);
	}
	
	@Override
	public ElementObj createElement(String str) {
		ImageIcon icon=null;
		String[] split = str.split(",");
		//split[0]是x:3；split[1]是y:5；split[2]是pic:player_prop
		for(String str1 : split) {
			//split2[0]是 x,y,pic而split2[1]是x,y,pic对应的值
			String[] split2 = str1.split(":");
			switch(split2[0]) {
			//设置道具的横坐标(使用parseInt方法的原因是从Hostage类传来的数字与字符串结合，成为了字符串的一部分（自动装箱）)
				case "x":
					this.setX(Integer.parseInt(split2[1]));
					break;
				case "y"://设置道具的纵坐标
					this.setY(Integer.parseInt(split2[1]));
					break;
				case "pic"://设置道具图片（不一定要get(0)可以生成随机数）
					icon=GameLoad.imgMap.get(split2[1]).get(2);
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
	public void attackNow(ElementObj obj) {
		if (obj instanceof PlayerBody||obj instanceof PlayerLegs) {
			this.setLive(false);
		}
	}

	@Override
	public void die(int i) {
		propList=em.getElementsByKey(GameElement.PLAYER_PROP);
		propList.remove(i);
	}
}
