package element;

import java.awt.Graphics;
import java.util.List;
import javax.swing.ImageIcon;

import manager.GameLoad;

/**
 * @˵�� �����°�����
 * @author DELL
 *
 */
public class PlayerLegs extends ElementObj{
	
	private boolean left = false; // ��
	private boolean up = false; // ��
	private boolean right = false;// ��
	private boolean down = false; // ��
	private boolean walk=false;	//����״̬
	private boolean jump=false;	//��Ծ״̬
	private boolean squat=false;	//����״̬
	//��Ϊվ����ֹ״̬staticֻ��һ��ͼƬ������������״̬��Ϊfalseʱ�ͼ���
	private long imgtime=0L;		//���ڿ���ͼƬ�仯�ٶ�
	private int i=0;
	// ר��������¼��ǰ��������ķ���,Ĭ����right
	private String direction = "right";
	//��¼��ǰ���ǵĴ���״̬
	private String stateString=null;
	//��ǰ������Ƭ�б�
	private List<ImageIcon> legList;

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
		legList = GameLoad.imgMap.get(splitStrings[2]);
		ImageIcon icon2=legList.get(0);
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}

	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// ����
			switch (key) {
			case 37://��
				this.right = false;
				this.direction = "left";
				this.walk=true;
				this.left = true;
				break;
//			case 38:
//				this.right = false;
//				this.left = false;
//				this.down = false;
//				this.up = true;
//				this.direction = "up";
//				break;
			case 39://��
				this.left = false;
				this.direction = "right";
				this.walk=true;
				this.right = true;
				break;
			case 40://��
				this.jump=false;
				this.squat = true;
				break;
//			case 65:
//				break;// ��������״̬
			case 83:
				this.down=false;
				this.jump=true;
				
				break;
			}
		} else {//�ɿ�
			switch (key) {
			case 37:
				this.left = false;
				this.walk=false;
				break;
//			case 38:
//				this.up = false;
//				break;
			case 39:
				this.walk=false;
				this.right = false;
				break;
			case 40:
				this.squat = false;
				break;
//			case 65:
//				break;// �رչ���״̬
			case 83:
				this.jump=false;
				break;
			}
		}
	}
	
	@Override
	protected void move(long gameTime) {
		if (this.left && this.getX() > 50) {
			this.setX(getX()-2);
		}
		if (this.jump && this.getY() > 293) {
			this.setY(this.getY() - 5);
		}
		if (this.right && this.getX() < 800 - 80) { // �������ת�ɴ�������
			this.setX(getX() + 2);
		}
		if (!this.jump && this.getY() < 393) {
			this.setY(getY() + 5);
		}
	}
	
	@Override
	protected void updateImage(long gameTime) {
		buildStateString();
		legList=GameLoad.imgMap.get(stateString);
		if (gameTime-imgtime>5) {
			imgtime=gameTime;
			if (i<=legList.size()-1) {
				this.setIcon(legList.get(i++));
			}
			else {
				i=0;
			}
		}
	}
	
	private void buildStateString() {
		// �ȸ����Ȳ�״̬�����ַ���
		if (this.direction.equals("left")) {// ����ɫ������
			if (jump) {// ����ɫ��ǰΪ����״̬
				stateString = "player_leftleg_jump";
			} else {// ����ɫ��ǰΪ������״̬
				System.out.println(squat + "+" + walk);
				if (squat) {// ����ɫ��ǰΪ����״̬
					if (walk) {// ����ɫ��ǰΪ�߶�״̬
						stateString = "player_leftleg_squatwalk";
					} else {// ����ɫ��ǰΪ��ֹ״̬
						stateString = "player_leftleg_squatstatic";
					}
				} else {// ����ɫ��ǰΪվ��״̬
					if (walk) {// ����ɫ��ǰΪ�߶�״̬
						stateString = "player_leftleg_standwalk";
					} else {// ����ɫ��ǰΪ��ֹ״̬
						stateString = "player_leftleg_standstatic";
					}
				}
			}

		} else if (this.direction.equals("right")) {// ����ɫ������
			if (jump) {// ����ɫ��ǰΪ����״̬
				stateString = "player_rightleg_jump";
			} else {// ����ɫ��ǰΪ������״̬
				if (squat) {// ����ɫ��ǰΪ����״̬
					if (walk) {// ����ɫ��ǰΪ�߶�״̬
						stateString = "player_rightleg_squatwalk";
					} else {// ����ɫ��ǰΪ��ֹ״̬
						stateString = "player_rightleg_squatstatic";
					}
				} else {// ����ɫ��ǰΪվ��״̬
					if (walk) {// ����ɫ��ǰΪ�߶�״̬
						stateString = "player_rightleg_standwalk";
					} else {// ����ɫ��ǰΪ��ֹ״̬
						stateString = "player_rightleg_standstatic";
					}
				}
			}
		}
	}
}
