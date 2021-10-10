package element;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @˵���������� 
 * ��������ʱֱ���������x������ȡֵ��Χ��100��200�����ߣ�600��700���ķ�Χ�ڵ����꣬
 * ��ȡ���ڵ����Ǵ���������Ǵ��ҽ���
 * ������Ϻ󣬽����Զ��ƶ���������״̬
 *
 * @author lao
 *
 */
public class InfantryEnemy extends ElementObj {
	
	private ElementManager em;

	// ����״̬ͼƬ��
	private List<ImageIcon> imageList;

	// ��������ڽ���״̬���������ֵ��˵Ľ�������, Ĭ��Ϊtrue
	private boolean coming = true;
	//���� �������actionAndDirection
	private String action = "run";
	//���� �������actionAndDirection
	private String direction="";
	// ���˶����ַ���
	private String actionAndDirection = action + "_" + direction;
	// �������λ��
	int randomPosition;
	//�����������
	int randomTime;
	//ʱ�����
	private long time = 0L;
	//ͼƬ����
	private int i = 0;
	
	boolean flag = true;
	
	public InfantryEnemy() {
		em = ElementManager.getManager();
		Random random = new Random();
		String[] leftOrRight = new String[] { "left", "right" };

		int num = random.nextInt(2);
		actionAndDirection = actionAndDirection + leftOrRight[num];
		switch (actionAndDirection) {
		// �Ӵ����������ܵĵ������øս���ʱ��x�������ڣ�600��700��֮��
		case "run_left":
			randomPosition = 600 + random.nextInt(100);
			break;
		// �Ӵ����������ܵĵ������øս���ʱ��x�������ڣ�100��200��֮��
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
		// ��������
		switch (actionAndDirection) {
			case "run_left":
				this.setX(800);
				break;
			case "run_right":
				this.setX(0);
				break;
		}
		this.setY(355);

		// �Ӽ�������imgMap��̬ͼƬӳ���л�ȡ��ǰ����ͼƬ�б�
		imageList = GameLoad.imgMap.get(str+actionAndDirection);
		// �ٴ�����ͼƬ�б���ȡ��Ĭ��״̬�ĵ�һ��ͼƬ
		ImageIcon img0 = imageList.get(0);

		// ���ÿ��
		this.setW(img0.getIconWidth());
		this.setH(img0.getIconHeight());
		// ����ͼƬ
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
						this.setX(this.getX() - 10); 	//������
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
						this.setX(this.getX() + 10); 	//������
					}else {
						//������ϣ����빥��״̬
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
						this.setX(this.getX() - 10); 	//������
					}else if(this.getX() - this.randomPosition < -10){
						this.setX(this.getX() + 10); 	//������
					}else {
						//�������λ�ã����빥��״̬
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
						this.setX(this.getX() - 10); 	//������
					}else if(this.getX() - this.randomPosition < -10){
						this.setX(this.getX() + 10); 	//������
					}else {
						//�������λ�ã����빥��״̬
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
						//������100��Χ������ƶ�
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
						//������100��Χ������ƶ�
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
	 * �ҵ���ҵķ���
	 * @return
	 */
	private void findDirection() {
		List<ElementObj> players = em.getElementsByKey(GameElement.PLAYER);
		
		if(this.getX() <= players.get(0).getX()) {	//���˵�λ������ҵ����
			this.direction = "right";						//������
		}else {
			this.direction = "left";	//������
		}
		//��ȡ��Ӧ�����ͼƬ
		imageList = GameLoad.imgMap.get("infantry_enemy_" + action + "_" + direction);
		actionAndDirection = action + "_" + direction;
	}

}
