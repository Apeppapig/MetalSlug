package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

public class Boss extends ElementObj {
	
	private long changTime=0L;
	private long bulletTime=0L;
	private List<ImageIcon> bossImgList;
	private ImageIcon bossImg;
	private int hp=10;	//BOSSѪ��
	private int i=0;

	private ElementManager em;
	
	public Boss() {
		em=ElementManager.getManager();
	}

	public ElementObj createElement(String str) {
		String[] splitStrings = str.split(",");
		this.setX(Integer.parseInt(splitStrings[0]));
		this.setY(Integer.parseInt(splitStrings[1]));
		//�Ӽ�������imgMap��̬ͼƬӳ���л�ȡ��ǰ�ɻ�����ͼƬ�б�
		bossImgList=GameLoad.imgMap.get(splitStrings[2]);
		//�ӻ���ͼƬ�б���ȡ��Ĭ��״̬�ĵ�һ��ͼƬ��Ĭ�����ҷɣ�
		bossImg=bossImgList.get(0);	
		this.setW(bossImg.getIconWidth());
		this.setH(bossImg.getIconHeight());
		this.setIcon(bossImg);
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
		if (gameTime-changTime>15) {
			changTime=gameTime;
			if (i<=bossImgList.size()-1) {
				this.setIcon(bossImgList.get(i++));
			}
			else {	//��ֹ�±�Խ�磬���ҿ���ѭ���л�ͼƬ
				i=0;
			}
		}
	}

	protected void add(long gameTime){	
		if (gameTime-bulletTime>150) {
			bulletTime=gameTime;
			ElementObj bulletObj = new BossBullet().createElement(createBullet());
			em.addElement(bulletObj, GameElement.BOSS_BULLET);
		}
	}

	private String createBullet() {
		int x = this.getX();
		int y = this.getY();
		//��ʼ��ը�����ֵ�λ��
		x-=10;
		y+=110;
		return "x:" + x + ",y:" + y 
				+ ",pic:"+"boss_bullet";
	}
	
	@Override
	public void attackNow(ElementObj obj) {
		if (obj instanceof PlayerBullet) {
			this.hp--;
		}
		if (this.hp==0) {
			this.setLive(false);
		}
	}
	
	@Override
	public void die(int i) {
		em.getElementsByKey(GameElement.BOSS).remove(i);
	}
}
