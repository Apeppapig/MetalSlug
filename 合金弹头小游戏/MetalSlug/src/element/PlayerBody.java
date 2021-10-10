package element;

import java.awt.Graphics;
import java.util.List;
import javax.swing.ImageIcon;

import element.ElementObj;
import element.PlayerBullet;
import manager.ElementManager;
import manager.GameElement;

import manager.GameLoad;

/**
 * @˵�� ����Ϊ�����ϰ�����
 * @author DELL
 *
 */
public class PlayerBody extends ElementObj{
	
	private boolean left = false; // ��
	private boolean up = false; // ��
	private boolean right = false;// ��
	private boolean down = false;//��
	private boolean walk=false;	//����״̬
	private boolean jump=false;	//��Ծ״̬
	private boolean attack = false;//����״̬ true ���� falseֹͣ
	private int jumpHeight=5;	//move����һ�Σ����ǵ���Ծ�߶�
	private int i=0;
	private int bulletNum=0;
	private long imgTime=0;		//���ڿ���ͼƬ�仯�ٶ�
	private long bulletTime=0;	//���ڿ����ӵ�����
	//��¼��ǰ���ǵĳ�������/����/���ң���Ĭ��Ϊ����
	private String direction="right";
	//��¼��ǰ���ǵ��ϰ���״̬
	private String stateString=null;
	//���ǵ�������ʶ��Ĭ��Ϊ��ǹ��
	private String weaponString="pistol";
	//��ǰ������Ƭ�б�
	private List<ImageIcon> bodyList;
	private ElementManager em;
	
	public PlayerBody() {
		em=ElementManager.getManager();
	}

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	
	@Override
	public ElementObj createElement(String str) {
		String[] splitStrings = str.split(",");
		this.setX(Integer.parseInt(splitStrings[0]));
		this.setY(Integer.parseInt(splitStrings[1]));
		//�Ӽ�������imgMap��̬ͼƬӳ���л�ȡ��ǰ����ͼƬ�б�
		bodyList=GameLoad.imgMap.get(splitStrings[2]);
		System.out.println(GameLoad.imgMap.get(splitStrings[2]));
		//�ٴ�����ͼƬ�б���ȡ��Ĭ��״̬�ĵ�һ��ͼƬ
		ImageIcon icon2=bodyList.get(0);	
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}

	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// ����
			switch (key) { // ��ô�Ż� �������˼��;�Ӽ����������������û�취����һ��
			case 37://��
				this.right = false;
				this.left = true;
				if (!this.jump&&this.direction.equals("right")) {//Ϊ��ʹ�ϰ���ƥ���°���
					this.setX(this.getX()-50);
				}
				this.walk=true;
				this.direction = "left";
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
				if (!this.jump&&this.direction.equals("left")) {//Ϊ��ʹ�ϰ���ƥ���°���
					this.setX(this.getX()+50);
				}
				this.walk=true;
				this.direction = "right";
				break;
			case 40://��
//				this.right = false;
//				this.left = false;
				this.jump=false;//
				this.up = false;//
				this.down = true;
				if (this.direction.equals("right")) {
					this.setY(this.getY()+7);
					this.setX(this.getX()-5);
				}
				if (this.direction.equals("left")) {
					this.setY(this.getY()+7);
					this.setX(this.getX()+5);
				}
				//this.direction = "down";
				break;
			case 65:	// ��������״̬
				
				this.attack = true;
				break;
			case 83:	//������Ծ״̬
				this.down=false;
				this.jump=true;	//
				this.setW(65);	//��Ϊ�����������������ߵ�ͼƬ��С��ͬ
				this.setH(55);	//����Ҫ�����³ߴ�
				if (direction.equals("right")) {
					this.setX(getX()-15);
				}
				if (direction.equals("left")) {
					this.setX(getX()+35);
				}
				break;
			}
		} else {//�ɿ�����
			switch (key) {
			case 37://��
				this.left = false;
				break;
			case 38://��
				this.up = false;
				break;
			case 39://��
				this.right = false;
				break;
			case 40://��
				this.down = false;
				if (this.direction.equals("right")) {
					this.setY(this.getY()-7);
					this.setX(this.getX()+5);
				}
				if (this.direction.equals("left")) {
					this.setY(this.getY()-7);
					this.setX(this.getX()-5);
				}
				break;
			case 65:// �رչ���״̬
				this.attack = false;
				break;
			case 83:
				this.jump=false;	
				this.setW(85);
				this.setH(55);
				if (direction.equals("right")) {
					this.setX(getX()+15);
				}
				if (direction.equals("left")) {
					this.setX(getX()-35);
				}
				break;
			}
		}
	}
	
	/**
	 * ��̬�ı䵱ǰ��ҵ�����ͼƬ
	 */
	@Override
	protected void updateImage(long gameTime) {	
		//1.�����ɵ�ǰ�ϰ���״̬�ַ���
		if (this.getBulletNum()==0) {
			this.setWeaponString("pistol");
			this.setBulletNum(0);
		}
		buildStateString();
		bodyList=GameLoad.imgMap.get(stateString);
		//2.�ٱ�������״̬�µ�ͼƬ�б�����Ϊ���Ǹ���ͼƬ
		if (gameTime-imgTime>5) {
			imgTime=gameTime;
			if (i<=bodyList.size()-1) {
				this.setIcon(bodyList.get(i++));
			}
			else {	//��ֹ�±�Խ�磬���ҿ���ѭ���л�ͼƬ
				i=0;
			}
		}
	}
	
	@Override
	protected void move(long gameTime) {	
		
		if (this.left && this.getX() > 0) {
			this.setX(this.getX() - 2);
		}
		if (this.jump && this.getY() > 245) {
			this.setY(this.getY() - jumpHeight);
		}
		if (this.right && this.getX() < 800 - 80) { // �������ת�ɴ�������
			this.setX(this.getX() + 2);
		}
		if (!this.jump &&this.getY()<350) {
			this.setY(this.getY() + jumpHeight);
		}
		
	}

	/**
	 * �����ӵ�/����
	 */
	@Override
	protected void add(long gameTime) {
		if (this.attack&&gameTime-bulletTime>25) {
			bulletTime=gameTime;//�����ӵ������ɼ��
//			this.attack = false;// ��һ�Σ�����һ���ӵ���ƴ����(Ҳ�������ӱ���bulletTime������)
			ElementObj playerBullet = new PlayerBullet().createElement(createBullet());
			em.addElement(playerBullet, GameElement.PLAYER_BULLET);
			// ���Ҫ�����ӵ��ٶȵȵȣ�����Ҫ�����д
		}
		
	}
	
	@Override
	public void attackNow(ElementObj obj) {
		if (obj instanceof PlayerProp) {
			this.setWeaponString("rifle");
			this.setBulletNum(50);//�õ���ǹ����50���ӵ�
		}
	}
	
	private String createBullet() {
		// {x:3,y:5,f:up,t:A} ģ��json��ʽ(�����������Ϸ��ͨ���������ͨ�Ż�ȡ��JSON�ַ���֮������������)
		int x = this.getX();
		int y = this.getY();
		// �ӵ��ڷ���ʱ����Ѿ�����̶��Ĺ켣�����Լ���Ŀ�꣬�޸�json��ʽ
		if (this.getWeaponString().equals("pistol")) {
			switch (this.direction) { 
			case "up":
				x += 20;
				break;
			// һ�㲻��д20����ֵ��һ�������ͼƬ��С������ʾ��С������ʹ��ͼƬ��С��������
			case "left":
				y += 20;
				break;
			case "right":
				x += 80;
				y += 20;
				break;
			case "down":
				y += 50;
				x += 20;
				break;
			}
		}
		else if (this.getWeaponString().equals("rifle")) {
			bulletNum--;
			switch (this.direction) { 
			case "up":
				x += 20;
				break;
			// һ�㲻��д20����ֵ��һ�������ͼƬ��С������ʾ��С������ʹ��ͼƬ��С��������
			case "left":
				x-=30;
				y += 20;
				break;
			case "right":
				x += 80;
				y += 20;
				break;
			case "down":
				y += 50;
				x += 20;
				break;
			}
		}
		return "x:" + x + ",y:" + y + ",f:" + this.direction+
				",pic:"+"player_"+this.weaponString+this.direction+"_bullet";
	}
	
	private void buildStateString() {
		if (direction.equals("left")) {
			if (jump) {
				stateString = "player_leftbody_"+this.weaponString+"jump";
			} else {
				if (attack) {
					stateString = "player_leftbody_"+this.getWeaponString()+"attack";
				} else {
					stateString = "player_leftbody_"+this.getWeaponString()+"walk";
				}
			}
		} else if (direction.equals("right")) {
			if (jump) {
				stateString = "player_rightbody_"+this.weaponString+"jump";
			} else {
				if (attack) {
					stateString = "player_rightbody_"+this.getWeaponString()+"attack";
				} else {
					stateString = "player_rightbody_"+this.getWeaponString()+"walk";
				}
			}
		}
	}

	public String getWeaponString() {
		return weaponString;
	}

	public void setWeaponString(String weaponString) {
		this.weaponString = weaponString;
	}

	public int getBulletNum() {
		return bulletNum;
	}

	public void setBulletNum(int bulletNum) {
		this.bulletNum = bulletNum;
	}
}
