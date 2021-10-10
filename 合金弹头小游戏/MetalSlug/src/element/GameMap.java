package element;

import java.awt.Graphics;
import java.util.List;
import javax.swing.ImageIcon;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @˵�� ��Ϸ��ͼ��
 * @author DELL
 *
 */
public class GameMap extends ElementObj{
	
	private String name;	//��ͼ����
	
	private int imgx=0;//�ɷ������
	private long imgtime=0;		//���ڿ���ͼƬ�仯�ٶ�
	private boolean left=false; //��
	private boolean right=false;//��
	//��ǰ��ͼ��Ƭ�б�
	private List<ImageIcon> mapList;
	private ElementManager eManager;
	private ImageIcon icon2;

	public GameMap() {
		eManager=ElementManager.getManager();
	}
	
	//��ʾ��ǰԪ��
	@Override
	public void showElement(Graphics g) {
		//���ڵ�ͼͼƬ���ȹ�����Ҫ�ֿ���ʾ
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 		//������Ϸ�������Ͻ�ͼ��(0,0)
				this.getX()+800, this.getY()+480,	//������Ϸ�������½�ͼ��(800,480)
				0+getImgx(), 0, 			//����ص�ͼƬ�����Ͻ�����
				400+getImgx(), 220, 		//����ص�ͼƬ�����½����꣨���ŵ�ͼ���أ�
				null);
	}

	//���ݴ�����ַ�����������Ԫ��
	@Override
	public ElementObj createElement(String str) {
		String[] splitStrings=str.split(",");
		this.setX(Integer.parseInt(splitStrings[0]));
		this.setY(Integer.parseInt(splitStrings[1]));
		//�Ӽ�������imgMap��̬ͼƬӳ���л�ȡ��ǰ��ͼͼƬ�б�
		mapList=GameLoad.imgMap.get(splitStrings[2]);
		System.out.println(GameLoad.imgMap.get(splitStrings[2]));
		//�ٴӵ�ͼͼƬ�б���ȡ��Ĭ��״̬�ĵ�һ��ͼƬ
		icon2=mapList.get(0);	
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}
	
	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// ����
			switch (key) { // ��ô�Ż� �������˼��;�Ӽ����������������û�취����һ��
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
		} else {//�ɿ�����
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
	
	//���������ĺϽ�ͷ�������߻�ͷ·
	@Override
	protected void move(long gameTime) {
		if (this.left) {	
//			imgx--;
		}
		//ֻ�е������н�����Ϊ�Ҳ��ҵ�ͼ��û�е���ʱ
		if (this.right&&getImgx()<1080) { 
			imgx = getImgx() + 1;
		}
	}
	
	/**
	 * �жϵ�ǰ��ͼ���Ƿ��е��˺�BOSS�������ͼ��ͣ�£���û�е�ͼ�������ǰ�ƶ�
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
