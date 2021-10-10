package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;

public class InfantryBullet extends ElementObj{
	
	private ElementManager em;
	//子弹移动方向
	private String direction;
	
	private long time = 0L;

	public InfantryBullet() {
		em = ElementManager.getManager();
	}
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), null);
	}
	
	@Override
	public ElementObj createElement(String str) {
		String[] splits = str.split(",");
		switch(splits[2]) {
		case "left":
			ImageIcon image1 = new ImageIcon("image/enemy/bullet/bullet00.png");
			this.setIcon(image1);
			this.direction = "left";
			break;
		case "right":
			ImageIcon image2 = new ImageIcon("image/enemy/bullet/bullet01.png");
			this.setIcon(image2);
			this.direction = "right";
			break;
		}
		this.setX(Integer.parseInt(splits[0]));
		this.setY(Integer.parseInt(splits[1]));
		return this;
	}
	
	@Override
	protected void updateImage(long gameTime) {
		if(gameTime - time > 20) {
			switch (direction) {
			case "left":
				this.setX(getX()-3);
				break;
	
			case "right":
				this.setX(getX()+3);
				break;
			}
		}
		
		if(this.getX() <=0 || this.getX() >= 800) {
			this.setLive(false);
		}
	}
	
	@Override
	public void die(int i) {
		List<ElementObj> bullets = em.getElementsByKey(GameElement.INFANTRY_BULLET);
		bullets.remove(this);
	}
}
