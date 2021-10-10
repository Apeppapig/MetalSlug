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
 * @说明：哨兵类，五攻击、不可杀
 * 
 * @author lao
 *
 */
public class GuardEnemy extends ElementObj {

	private ElementManager em;

	private long time = 0L;

	private long instanceTime = 0L;

	private long interval = 0L;

	// 随机敌人波数
	private int randomWaves;

	private int instanceImgX;

	private boolean firstInstance = true;

	// 是否固定窗口
	private boolean fixed = false;

	// 敌人的动作,默认为入场状态，入场状态无任何动作，根据相对移动
	private String action = "stand";
	// 敌人攻击状态，默认为否
	// 敌人图片集
	private List<ImageIcon> enemyImgs;
	// 用于控制图片
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

		// 获取当前地图对象
		GameMap gameMap = null;
		List<ElementObj> gameMaps = em.getElementsByKey(GameElement.MAPS);
		if (gameMaps.get(0) instanceof GameMap) {

			gameMap = (GameMap) gameMaps.get(0);
		}
		// 设置实例化时敌人在背景上的x轴坐标，用于控制是否固定屏幕
		this.instanceImgX = gameMap.getImgx();

		String[] splits = str.split(",");

		// 设置坐标
		this.setX(Integer.parseInt(splits[0]));
		this.setY(Integer.parseInt(splits[1]));

		// 从加载器的imgMap静态图片映射中获取当前身体图片列表
		enemyImgs = GameLoad.imgMap.get(splits[2]);
		// 再从身体图片列表里取出默认状态的第一张图片
		ImageIcon img0 = enemyImgs.get(0);
		// 设置宽高
		this.setW(img0.getIconWidth());
		this.setH(img0.getIconHeight());
		// 设置图片
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
	 * 敌人的移动比较特殊，不受
	 */
	@Override
	protected void updateImage(long gameTime) {

		// 获取当前地图对象
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
				// 更换instanceImgX,防止程序持续修改敌人的位置
				this.instanceImgX = gameMap.getImgx();
				// 敌人从右边进入窗口一定的距离后固定背景
				if (this.getX() <= 650 && !fixed) { // 只执行一次
					this.fixed = true;
					this.action = "scared";
				}
				break;
			case "attack":
				break;
			case "scared":
				if (gameTime - time >= 20) {
					if (i >= 11) {
						this.setX(this.getX() + 35);// 第四张图片才开始移动
					}
					time = gameTime;
				}
				i++;
				if (i >= 16) {
					i = 11;
				}
				this.setIcon(enemyImgs.get(i));

				// 跑出窗口
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
