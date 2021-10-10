package element;

import java.awt.Graphics;

/**
 * @ËµÃ÷£ºÅÚ±øÀà
 * @author lao
 *
 */
public class ArtilleryEnemy extends ElementObj{

	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), null);
	}

}
