package element;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import controller.GameThread;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @˵�����ڱ��࣬�幥��������ɱ
 * 
 * @author lao
 *
 */
public class GuardEnemy extends ElementObj {

	private ElementManager em;

	private long time = 0L;

	private long instanceTime = 0L;

	private long interval = 0L;

	// ������˲���
	private int randomWaves;

	private int instanceImgX;

	private boolean firstInstance = true;

	// �Ƿ�̶�����
	private boolean fixed = false;

	// ���˵Ķ���,Ĭ��Ϊ�볡״̬���볡״̬���κζ�������������ƶ�
	private String action = "stand";
	// ���˹���״̬��Ĭ��Ϊ��
	// ����ͼƬ��
	private List<ImageIcon> enemyImgs;
	// ���ڿ���ͼƬ
	int i = 0;

	public GuardEnemy() {
		em = ElementManager.getManager();
		randomWaves = 1 + new Random().nextInt(5);
		// System.out.println(randomWaves);
	}

	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}

	@Override
	public ElementObj createElement(String str) {

		// ��ȡ��ǰ��ͼ����
		GameMap gameMap = null;
		List<ElementObj> gameMaps = em.getElementsByKey(GameElement.MAPS);
		if (gameMaps.get(0) instanceof GameMap) {

			gameMap = (GameMap) gameMaps.get(0);
		}
		// ����ʵ����ʱ�����ڱ����ϵ�x�����꣬���ڿ����Ƿ�̶���Ļ
		this.instanceImgX = gameMap.getImgx();

		String[] splits = str.split(",");

		// ��������
		this.setX(Integer.parseInt(splits[0]));
		this.setY(Integer.parseInt(splits[1]));

		// �Ӽ�������imgMap��̬ͼƬӳ���л�ȡ��ǰ����ͼƬ�б�
		enemyImgs = GameLoad.imgMap.get(splits[2]);
		// �ٴ�����ͼƬ�б���ȡ��Ĭ��״̬�ĵ�һ��ͼƬ
		ImageIcon img0 = enemyImgs.get(0);
		// ���ÿ��
		this.setW(img0.getIconWidth());
		this.setH(img0.getIconHeight());
		// ����ͼƬ
		this.setIcon(img0);

		return this;
	}

	@Override
	public void die(int i) {

		if (GameThread.gameTime - time > interval) {
			instanceTime = GameThread.gameTime;
			interval = 1000;
			if (randomWaves == 0) {
				List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
				enemies.remove(this);
			} else {
				add();
				randomWaves--;
			}
		}
	}

	@Override
	public boolean pk(ElementObj obj) {
		return false;
	}

	private void add() {
		String infantryEnemyString = "infantry_enemy_";
		int num = 5 + new Random().nextInt(6);
		for (int j = 0; j < num; j++) {
			ElementObj infantryEnemyObj = GameLoad.getObj("infantry_enemy");
			ElementObj infantryEnemy = infantryEnemyObj.createElement(infantryEnemyString);

			em.addElement(infantryEnemy, GameElement.ENEMY);
		}
	}

	/**
	 * ���˵��ƶ��Ƚ����⣬����
	 */
	@Override
	protected void updateImage(long gameTime) {

		// ��ȡ��ǰ��ͼ����
		GameMap gameMap = null;
		List<ElementObj> gameMaps = em.getElementsByKey(GameElement.MAPS);
		if (gameMaps.get(0) instanceof GameMap) {

			gameMap = (GameMap) gameMaps.get(0);
		}

		if (gameTime - time > 20) {

			switch (action) {
			case "stand":
				int delta = this.instanceImgX - gameMap.getImgx();
				this.setX(this.getX() + delta);
				// ����instanceImgX,��ֹ��������޸ĵ��˵�λ��
				this.instanceImgX = gameMap.getImgx();
				// ���˴��ұ߽��봰��һ���ľ����̶�����
				if (this.getX() <= 650 && !fixed) { // ִֻ��һ��
					this.fixed = true;
					this.action = "scared";
				}
				break;
			case "attack":
				break;
			case "scared":
				if (gameTime - time >= 20) {
					if (i >= 11) {
						this.setX(this.getX() + 35);// ������ͼƬ�ſ�ʼ�ƶ�
					}
					time = gameTime;
				}
				i++;
				if (i >= 16) {
					i = 11;
				}
				this.setIcon(enemyImgs.get(i));

				// �ܳ�����
				if (this.getX() > 800) {
					this.setLive(false);
				}
				break;

			}
		}
	}

	public boolean isFixed() {
		return this.fixed;
	}

}
