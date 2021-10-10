package element;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @说明：步兵类 
 * 步兵进场时直接跑向会在x轴坐标取值范围（100，200）或者（600，700）的范围内的坐标，
 * 这取决于敌人是从左进场还是从右进场
 * 进场完毕后，进入自动移动、攻击等状态
 *
 * @author lao
 *
 */
public class InfantryEnemy extends ElementObj {
	
	private ElementManager em;

	// 步兵状态图片集
	private List<ImageIcon> imageList;

	// 敌人书否处于进场状态，用于区分敌人的进场坐标, 默认为true
	private boolean coming = true;
	//动作 用于组合actionAndDirection
	private String action = "run";
	//方向 用于组合actionAndDirection
	private String direction="";
	// 敌人动作字符串
	private String actionAndDirection = action + "_" + direction;
	// 敌人随机位置
	int randomPosition;
	//随机攻击次数
	int randomTime;
	//时间控制
	private long time = 0L;
	//图片控制
	private int i = 0;
	
	boolean flag = true;
	
	public InfantryEnemy() {
		em = ElementManager.getManager();
		Random random = new Random();
		String[] leftOrRight = new String[] { "left", "right" };

		int num = random.nextInt(2);
		actionAndDirection = actionAndDirection + leftOrRight[num];
		switch (actionAndDirection) {
		// 从窗口右往左跑的敌人设置刚进场时的x轴坐标在（600，700）之间
		case "run_left":
			randomPosition = 600 + random.nextInt(100);
			break;
		// 从窗口左往右跑的敌人设置刚进场时的x轴坐标在（100，200）之间
		case "run_right":
			randomPosition = 100 + random.nextInt(100);
		}

	}

	@Override
	public void showElement(Graphics g) {

		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), null);
	}

	@Override
	public ElementObj createElement(String str) {
		// 设置坐标
		switch (actionAndDirection) {
			case "run_left":
				this.setX(800);
				break;
			case "run_right":
				this.setX(0);
				break;
		}
		this.setY(355);

		// 从加载器的imgMap静态图片映射中获取当前身体图片列表
		imageList = GameLoad.imgMap.get(str+actionAndDirection);
		// 再从身体图片列表里取出默认状态的第一张图片
		ImageIcon img0 = imageList.get(0);

		// 设置宽高
		this.setW(img0.getIconWidth());
		this.setH(img0.getIconHeight());
		// 设置图片
		this.setIcon(img0);
		return this;
	}
	
	private void add(String direction){
		int x = this.getX();
		int y = this.getY() + this.getIcon().getIconHeight()/3;
		String infantryBulletString = x + "," + y + "," +direction;
		ElementObj infantryBulletObj = GameLoad.getObj("infantry_bullet");
		ElementObj infantryEnemy = infantryBulletObj.createElement(infantryBulletString);
		
		em.addElement(infantryEnemy, GameElement.INFANTRY_BULLET);
	}

	@Override
	protected void updateImage(long gameTime) {
		
		if(coming) {
			switch (actionAndDirection) {
			case "run_left":
				if(gameTime - time > 20) {
					time = gameTime;
					i++;
					this.setIcon(imageList.get(i));
					if(this.getX() > this.randomPosition) {
						this.setX(this.getX() - 10); 	//往左走
					}else {
						this.coming = false;
						action = "attack";
						randomTime = new Random().nextInt(4);
						imageList = GameLoad.imgMap.get("infantry_enemy_attack_left");
						i = 0;
						this.setIcon(imageList.get(i));
					}
					
					if(i >= imageList.size() - 1) {
						i = 0;
					}
				}
				findDirection();
				break;
			case "run_right":
				if(gameTime - time > 20) {
					time = gameTime;
					i++;
					this.setIcon(imageList.get(i));
					if(this.getX()  < this.randomPosition) {
						this.setX(this.getX() + 10); 	//往右走
					}else {
						//进场完毕，进入攻击状态
						this.coming = false;
						action = "attack";
						randomTime = new Random().nextInt(4);
						imageList = GameLoad.imgMap.get("infantry_enemy_attack_right");
						i = 0;
						this.setIcon(imageList.get(i));
					}
					
					if(i >= imageList.size() - 1) {
						i = 0;
					}
				}
				findDirection();
				break;
			}
		}else {
			switch (actionAndDirection) {
			case "run_left":
				if(gameTime - time > 20) {
					time = gameTime;
					i++;
					this.setIcon(imageList.get(i));
					if(this.getX() - this.randomPosition > 10) {
						this.setX(this.getX() - 10); 	//往左走
					}else if(this.getX() - this.randomPosition < -10){
						this.setX(this.getX() + 10); 	//往右走
					}else {
						//到达随机位置，进入攻击状态
						action = "attack";
						randomTime = new Random().nextInt(4);
						findDirection();
						imageList = GameLoad.imgMap.get("infantry_enemy_" + action + "_" + direction);
						i = 0;
						this.setIcon(imageList.get(i));
					}
					
					if(i >= imageList.size() - 1) {
						i = 0;
					}
				}
				findDirection();
				break;
			case "run_right":
				if(gameTime - time > 20) {
					time = gameTime;
					i++;
					this.setIcon(imageList.get(i));
					if(this.getX() - this.randomPosition > 10) {
						this.setX(this.getX() - 10); 	//往左走
					}else if(this.getX() - this.randomPosition < -10){
						this.setX(this.getX() + 10); 	//往右走
					}else {
						//到达随机位置，进入攻击状态
						action = "attack";
						randomTime = new Random().nextInt(4);
						findDirection();
						imageList = GameLoad.imgMap.get("infantry_enemy_" + action + "_" + direction);
						i = 0;
						this.setIcon(imageList.get(i));
					}
					
					if(i >= imageList.size() - 1) {
						i = 0;
					}
				}
				findDirection();
				break;
	
			case "attack_left":
				if(gameTime - time > 40) {
					time = gameTime;
					i++;
					this.setIcon(imageList.get(i));
					if(i == 2) {
						add("left");
						randomTime--;
					}
					if(i >= imageList.size() - 1) {
						i = 0;
					}
					findDirection();
					if(randomTime < 0) {
						i = 0;
						action = "run";
						//在自身100范围内随机移动
						randomPosition = this.getX() - 100 + new Random().nextInt(200);
						imageList = GameLoad.imgMap.get("infantry_enemy_" + action + "_" + direction);
					}
				}
				break;
			case "attack_right":
				if(gameTime - time > 40) {
					time = gameTime;
					i++;
					this.setIcon(imageList.get(i));
					if(i == 2) {
						add("right");
						randomTime--;
					}
					if(i >= imageList.size() - 1) {
						i = 0;
					}
					
					findDirection();
					
					if(randomTime < 0) {
						i = 0;
						action = "run";
						//在自身100范围内随机移动
						randomPosition = this.getX() - 100 + new Random().nextInt(200);
						imageList = GameLoad.imgMap.get("infantry_enemy_" + action + "_" + direction);
					}
				}
				break;
			case "die":
				if(gameTime - time > 20) {
					time = gameTime;
					i++;
					this.setIcon(imageList.get(i));
				}
			}
		}
	}
	
	@Override
	public boolean pk(ElementObj obj) {
		if(obj instanceof PlayerBullet) {
			return super.pk(obj);
		}
		return false;
	}
	
	@Override
	public void die(int i) {
		if(flag) {
			flag = false;	
			this.i = 0;
			actionAndDirection = "die";
			System.out.println("change");
			imageList = GameLoad.imgMap.get("die_" + direction);
		}
		if(i >= 4) {
			List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
			enemies.remove(this);
		}
	}
	
	/**
	 * 找到玩家的方向
	 * @return
	 */
	private void findDirection() {
		List<ElementObj> players = em.getElementsByKey(GameElement.PLAYER);
		
		if(this.getX() <= players.get(0).getX()) {	//敌人的位置在玩家的左边
			this.direction = "right";						//朝向右
		}else {
			this.direction = "left";	//朝向左
		}
		//获取对应方向的图片
		imageList = GameLoad.imgMap.get("infantry_enemy_" + action + "_" + direction);
		actionAndDirection = action + "_" + direction;
	}

}
