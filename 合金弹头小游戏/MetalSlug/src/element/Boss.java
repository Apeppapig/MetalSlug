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
	private int hp=10;	//BOSS血量
	private int i=0;

	private ElementManager em;
	
	public Boss() {
		em=ElementManager.getManager();
	}

	public ElementObj createElement(String str) {
		String[] splitStrings = str.split(",");
		this.setX(Integer.parseInt(splitStrings[0]));
		this.setY(Integer.parseInt(splitStrings[1]));
		//从加载器的imgMap静态图片映射中获取当前飞机机身图片列表
		bossImgList=GameLoad.imgMap.get(splitStrings[2]);
		//从机身图片列表里取出默认状态的第一张图片（默认向右飞）
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
			else {	//防止下标越界，并且可以循环切换图片
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
		//初始化炸弹出现的位置
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
