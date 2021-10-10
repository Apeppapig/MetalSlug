package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @˵�� ��ҵ�����
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
		//split[0]��x:3��split[1]��y:5��split[2]��pic:player_prop
		for(String str1 : split) {
			//split2[0]�� x,y,pic��split2[1]��x,y,pic��Ӧ��ֵ
			String[] split2 = str1.split(":");
			switch(split2[0]) {
			//���õ��ߵĺ�����(ʹ��parseInt������ԭ���Ǵ�Hostage�ഫ�����������ַ�����ϣ���Ϊ���ַ�����һ���֣��Զ�װ�䣩)
				case "x":
					this.setX(Integer.parseInt(split2[1]));
					break;
				case "y"://���õ��ߵ�������
					this.setY(Integer.parseInt(split2[1]));
					break;
				case "pic"://���õ���ͼƬ����һ��Ҫget(0)���������������
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
