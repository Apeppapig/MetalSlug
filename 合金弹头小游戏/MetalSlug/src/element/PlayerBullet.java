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
	private int attack;//������
	private int moveNum=3;//�ƶ��ٶ�ֵ
	private String direction;
	private List<ElementObj> bulletList;
	private ElementManager em;
	
	//ʣ�µĴ����չ; ������չ�������ӵ��� ���⣬�����ȵȡ�(��������Ҫ���ӵ�����)
	public PlayerBullet() {
		em=ElementManager.getManager();
	}
	
	//�Դ����������Ĺ��̽��з�װ�����ֻ��Ҫ�����Ҫ��Լ������������ֵ���Ƕ���ʵ��
	//ģ���������ͨ�Ų���ȡ����JSON��ʽ�ַ���{x:3,y:5,f:up,pic:...}����������н���
	@Override   
	public ElementObj createElement(String str) {//�����ַ����Ĺ���
		ImageIcon icon=null;
		String[] split = str.split(",");
		//split[0]��x:3��split[1]��y:5��split[2]��f:up��split[3]��pic:player_pistolright_bullet
		for(String str1 : split) {
			//split2[0]�� x,y,f,pic��split2[1]��x,y,f,pic��Ӧ��ֵ
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
	public void showElement(Graphics g) {//��ʾ�ӵ�ͼƬ	
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	
	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// ����
			switch (key) { // ��ô�Ż� �������˼��;�Ӽ����������������û�취����һ��
			case 37://��
				this.right = false;
				this.left = true;
				break;
//			case 38://��
//				this.right = false;
//				this.left = false;
//				this.down = false;
//				this.up = true;
//				this.direction = "up";
//				break;
			case 39://��
				this.left = false;
				this.right = true;
				break;
			}
		} else {//�ɿ�����
			switch (key) {
			case 37://��
				this.left = false;
				break;
//			case 38://��
//				this.up = false;
//				break;
			case 39://��
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
		switch(this.direction) {	//�����ӵ��ķ��з���
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
	 * �����ӵ���˵��1.���߽�  2.�������ײ
	 * ����ʽ���ǣ����ﵽ����������ʱ��ֻ�����޸�����״̬�Ĳ�����
	 */
	@Override
	public void die(int i) {
		bulletList=em.getElementsByKey(GameElement.PLAYER_BULLET);
		bulletList.remove(i);
//		em.addElement(this,GameElement.DIE);//������뵽������Ԫ��
	}
	
}
