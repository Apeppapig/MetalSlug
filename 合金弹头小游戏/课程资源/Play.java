package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Play extends ElementObj {
	private ElementManager em = ElementManager.getManager();
	List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
	private boolean leftRun = false;	//����״̬ true-������ false-վ��
	private boolean rightRun = false;	//����״̬ true-������ false-վ��
	private int mv = 3;					//�����ƶ��ٶ�
	private int jh = 5;					//������Ծ�߶�
	private String fx = "right";		//��¼��ǰ��������ķ���Ĭ����right
	private String weapon = "handgun";	//��¼��ǰ���ǵ�������Ĭ��Ϊ��ǹ
	private String upperAction = "attack";//��¼��ǰ�����ϰ���Ķ�����attackhandgun����ǹ��� jump��Ծ
	private String lowerAction = "stand";//��¼��ǰ�����°���Ķ�����standվ�� run�� jump��Ծ squatstand���� squatrun������
	private boolean pkType = false;		//����״̬ true-���� false-ֹͣ
	private boolean jumpType = false;	//��Ծ״̬ true-��Ծ false-վ��
	private boolean squatType = false;  //����״̬ true-���� false-վ��
	private boolean haveShot = false;   //�ӵ�״̬ true-������ӵ� false-δ����ӵ�
	private long attackInterval = 3L;	//���ͼƬ�����ļ��ʱ��(��ͬ���������ͼƬ�����ٶȲ�ͬ����ı���ʱ��)
	private long jumpInterval = 10L;	//��ԾͼƬ�����ļ��ʱ��
	private long actionInterval = 5L;	//����ͼƬ�����ļ��ʱ��
	private long attackImgTime = 0L;	//��һ�����ͼƬ����ʱ��
	private long jumpImgTime = 0L;		//��һ����ԾͼƬ����ʱ��
	private long actionImgTime = 0L;	//��һ�ζ���ͼƬ����ʱ��
	private int upperIndex = 0;			//�ϰ���ͼƬ�б��±�
	private int lowerIndex = 0;			//�°���ͼƬ�б��±�
	private int tempIndex = 0;			//����Ծʱ��������¼����Ծʱ׼�����֮ǰ��upperIndex��ֵ
	private ImageIcon icon2;			//�°���ͼƬ
	private String upperLastState = "attack";//��¼�����ϰ����ǰ״̬(����)��Ĭ��Ϊattack
	private String lowerLastState = "stand";//��¼�����°����ǰ״̬(����)��Ĭ��Ϊstand
	
	public Play() {}
	
	public Play(int x, int y, int w, int h, ImageIcon icon, ImageIcon icon2) {
		super(x, y, w, h, icon);
		this.icon2 = icon2;
		this.setHP(3);//���Ѫ����Ĭ��Ѫ��Ϊ10(�ɸ�)
	}
	
	/**
	 * @˵�� ��ʾ���Ƿ���(��д)
	 * @param g ����
	 */
	@Override
	public void showElement(Graphics g) {
		//�ϰ���
		int x1 = this.getX();
		int y1 = this.getY();
		int w1 = this.getIcon().getIconWidth();
		int h1 = this.getIcon().getIconHeight();
		//�°���(������ϰ�����е���)
		int x2 = x1;
		int y2 = y1+h1-8;
		int w2 = this.icon2.getIconWidth();
		int h2 = this.icon2.getIconHeight();
		switch(this.fx) {//�������ǲ�ͬ��������°���ͼƬ��λ��
			case "right":
				x2 = x1+4;
				break;
			case "left":
				x2 = x1+w1-w2-4;
				break;
			default:
				break;
		}
		if(this.jumpType) {//��Ծʱ�������°���ͼƬ��λ��
			switch(this.fx) {
				case "right":
					x1 -= 6;
					break;
				case "left":
					x1 += 6;
					break;
				default:
					break;
			}
			y2 += 2;
		} else {
			switch(this.fx) {
				case "right":
					x1 += 0;
					x2 += 0;
					break;
				case "left":
					x1 -= 44;
					x2 -= 44;
					break;
				default:
					break;	
			
			}
		}
		if(this.squatType) {//����ʱ�����ϰ���ͼƬ��λ��
			switch(this.fx) {
				case "right":
					x1 -= 4;
					break;
				case "left":
					x1 += 4;
					break;
				default:
					break;
			}
			y1 += 14;
		}
		g.drawImage(this.getIcon().getImage(), x1, y1, w1, h1, null);//�滭�ϰ���
		g.drawImage(this.icon2.getImage(), x2, y2, w2, h2, null);//�滭�°���
	}
	
	/**
	 * @˵�� ���̰�ť��Ӧ�¼�����keyClick(��д)
	 * @param b1 ���̰�ť���»����ɿ� true-���� false-�ɿ�
	 * @param key ���̰�ť��keyCodeֵ
	 */
	@Override
	public void keyClick(boolean b1, int key) {
		if(b1) {
			switch (key) {
				case 65:	//A
					this.leftRun = true; 
					this.fx = "left"; 
					if(!this.jumpType) {
						this.upperAction = "attack";
						this.lowerAction = "run";
						if(this.squatType) {
							this.lowerAction = "squatrun";
						}
					}
					break;
				case 68:	//D
					this.rightRun = true; 
					this.fx = "right"; 
					if(!this.jumpType) {
						this.upperAction = "attack";
						this.lowerAction = "run";
						if(this.squatType) {
							this.lowerAction = "squatrun";
						}
					}
					break;
				case 83:	//S
					if(!jumpType) {
						this.squatType = true;
						this.upperAction = "attack";
						this.lowerAction = "squatstand";
						if(this.leftRun || this.rightRun) {
							this.lowerAction = "squatrun";
						}
					}
					break;
				case 75:	//K
					this.squatType = false;
					this.jumpType = true;//������Ծ״̬
					this.upperAction = "jump";
					this.lowerAction = "jump";
					break;
				case 74:	//J
					this.haveShot = false;//δ�����ӵ�
					this.pkType = true;//��������״̬
					this.upperAction = "attack";
					break;
				default: 
					break;
			}
		} else {
			switch (key) {
				case 65:	//A
					this.leftRun = false; 
					if(!this.rightRun && !this.jumpType && !this.squatType) {
						this.lowerAction = "stand";
					}
					if(squatType && !this.leftRun && !this.rightRun) {
						this.lowerAction = "squatstand";
					}
					break;
				case 68:	//D
					this.rightRun = false; 
					if(!this.leftRun && !this.jumpType && !this.squatType) {
						this.lowerAction = "stand";
					}
					if(squatType && !this.leftRun && !this.rightRun) {
						this.lowerAction = "squatstand";
					}
					break;
				case 83:	//S
					this.squatType = false;
					this.lowerAction = "stand";
					if(this.leftRun || this.rightRun) {
						this.lowerAction = "run";
					}
					break;
				default: 
					break;
			}
		}
	}
	
	/**
	 * @˵�� ���ǻ�װ����(��д)
	 * @param gameTime ��ǰ��Ϸʱ��
	 */
	@Override
	protected void updateImage(long gameTime) {
		String key1 = this.fx+"_upper_"+this.upperAction+this.weapon;
		String key2 = this.fx+"_lower_"+this.lowerAction;
		List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//��ȡ�ϰ���ͼƬ�б�
		List<ImageIcon> listImageIcon2 = GameLoad.imgMap_element.get(key2);//��ȡ�°���ͼƬ�б�
		if(this.pkType && gameTime - this.attackImgTime > this.attackInterval) {
			this.attackImgTime = gameTime;
			switch(this.weapon) {//�������ǲ�ͬ�����������ͼƬ�����ļ��ʱ��
				case "handgun":
					this.attackInterval = 3L;
					break;
				default:
					break;
			}
			if(this.jumpType) {
				tempIndex = this.upperIndex;
			}
			if(this.upperLastState != this.upperAction) {
				this.upperIndex = 0;
				this.upperLastState = this.upperAction;
			}
			this.setIcon(listImageIcon1.get(this.upperIndex++));
			if(this.upperIndex > listImageIcon1.size()-1) {
				this.upperIndex = 0;
				this.pkType = false;
				if(this.jumpType) {
					this.upperAction = "jump";
					key1 = this.fx+"_upper_"+this.upperAction+this.weapon;
				}
			}
		}
		if(this.jumpType && gameTime - this.jumpImgTime > this.jumpInterval) {
			this.jumpImgTime = gameTime;
			if(this.upperLastState != this.upperAction && !this.pkType) {
				this.upperIndex = 0;
				if(this.tempIndex != 0) {
					this.upperIndex = this.tempIndex < listImageIcon1.size()-1 ? this.tempIndex : listImageIcon1.size()-1;
					this.tempIndex = 0;
				}
				this.upperLastState = this.upperAction;
			}
			if(this.lowerLastState != this.lowerAction) {
				this.lowerIndex = 0;
				this.lowerLastState = this.lowerAction;
			}
			if(!this.pkType) {//����״̬���ɱ����
				this.setIcon(listImageIcon1.get(this.upperIndex++));
			}
			this.icon2 = listImageIcon2.get(this.lowerIndex++);
			if(this.upperIndex > listImageIcon1.size()-1 || this.lowerIndex > listImageIcon2.size()-1) {
				this.upperIndex = 0;
				this.lowerIndex = 0;
			}
		}
		if(!this.jumpType && gameTime - this.actionImgTime > this.actionInterval) {
			this.actionImgTime = gameTime;
			if(this.upperIndex != 0 && !this.pkType) {
				this.upperIndex = 0;
				this.upperLastState = this.upperAction;
			}
			if(this.lowerLastState != this.lowerAction) {
				this.lowerIndex = 0;
				this.lowerLastState = this.lowerAction;
			}
			this.setIcon(listImageIcon1.get(this.upperIndex));
			switch(this.lowerAction) {//���������°���Ķ�������ͼƬ
				case "run":
				case "squatrun":
					this.icon2 = listImageIcon2.get(this.lowerIndex++);
					if(this.lowerIndex > listImageIcon2.size()-1) {
						this.lowerIndex = 0;
					}
					break;
				case "stand":
				case "squatstand":
					this.icon2 = listImageIcon2.get(this.lowerIndex);
					break;
				default:
					break;
			}
		}
	}
	
	/**
	 * @˵�� �����ƶ�����(��д)
	 */
	@Override
	protected void move(long gameTime) {
		//���ñ߽磬����������ֻ���ڴ������ƶ�
		if(this.getX() < 0) {
			this.setX(0);
			
		} 
		//��ͼû����ͷ
		if(map.get(0).getX()>-600) {
			if(this.getX() + this.getW() >= 420) {
				this.setX(420 - this.getW());
			}
		}	
		//��ͼ����ͷ
		if(map.get(0).getX()<=-600){
			if(this.getX() + this.getW() >= 800) {
				this.setX(800 - this.getW());
			}
		}
		//��������״̬����x��yֵ
		int distance = this.mv;
		if(this.squatType) {//�����ܲ����ٶ���վ���ܲ���2/3
			distance = this.mv * 1/3;
		}
		//�ƶ�
		if(this.leftRun) {
			this.setX(this.getX() - distance);
		}
		if(this.rightRun) {
			this.setX(this.getX() + distance);
		}
		//��Ծ
		if(this.jumpType) {
			this.setY(this.getY() - this.jh);
			if(this.getY() < 50) {//200Ϊ������Ծ����߸߶�(��Сyֵ)
				this.jh = -this.jh;
			}
			if(this.getY() > 220) {//400Ϊ����վ��ʱ��yֵ
				this.setY(220);
				this.jh = -this.jh;
				this.jumpType = false;
				this.upperAction = "attack";
				if(this.leftRun || this.rightRun) {
					this.lowerAction = "run";
				} else {
					this.lowerAction = "stand";
				}
			}
		}
	}
	
	/**
	 * @˵�� ��ӵ��߷���(��д)
	 * @param gameTime ��ǰ��Ϸʱ��
	 */
	@Override	
	protected void add(long gameTime) {
		if(!this.pkType) {//������Ƿ���״̬��ֱ�ӽ���
			return;
		}
		//����ǰ�ӵ�״̬Ϊfalse����δ�����ӵ��������ӵ�
		if(this.upperIndex == 2 && !this.haveShot) {
			this.haveShot = true;//�޸��ӵ�״̬����ֹ���ɶ���ӵ�(һ�����ӵ���ɼ���)
			ElementObj element = GameLoad.getObj("playfire").createElement(this.fireStr());
			ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
		}
	}
	
	/**
	 * @˵�� ���ɴ����ӵ��������ݵ��ַ�������
	 * @return String ���ش����ӵ��������ݵ��ַ���
	 */
	public String fireStr() {//�����ַ�������
		//{x:3,y:5,fx:up} json��ʽ
		int x = this.getX();
		int y = this.getY();
		int w = 10;//�ӵ����
		int h = 10;//�ӵ��߶�
		//����ʱ���ӵ�λ��
		if(this.squatType) {
			switch(this.fx) {//���ݲ�һ���ķ������ӵ�λ�õ���
				case "left":
					x -= 35;
					y += 28;
					break;
				case "right":
					x += this.getIcon().getIconWidth()-5;
					y += 28;
					break;
				default:
					break;
			}
		} else if(this.jumpType) {//��Ծʱ���ӵ�λ��
			switch(this.fx) {//���ݲ�һ���ķ������ӵ�λ�õ���
				case "left":
					x -= 0;
					y += 14;
					break;
				case "right":
					x += this.getIcon().getIconWidth()-10;
					y += 14;
					break;
				default:
					break;
			}
		} else {//վ������ʱ���ӵ�λ��
			switch(this.fx) {//���ݲ�һ���ķ������ӵ�λ�õ���
				case "left":
					x -= 40;
					y += 14;
					break;
				case "right":
					x += this.getIcon().getIconWidth()-5;
					y += 14;
					break;
				default:
					break;
			}
		}
		return "x:"+x+",y:"+y+",w:"+w+",h:"+h+",fx:"+this.fx;
	}
	
	/**
	 * @˵�� �����ַ����������Ƿ���(��д)
	 * @param str ������������Ҫ������(���ַ�������ʽ: "x,y,fx")
	 */
	@Override
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		this.fx = split[2];
		String key1 = this.fx+"_upper_"+this.upperAction+this.weapon;
		String key2 = this.fx+"_lower_"+this.lowerAction;
		List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);
		List<ImageIcon> listImageIcon2 = GameLoad.imgMap_element.get(key2);
		this.setIcon(listImageIcon1.get(this.upperIndex));
		this.icon2 = listImageIcon2.get(this.lowerIndex);
		int w = this.getW() < this.icon2.getIconWidth() ? this.getW() : this.icon2.getIconWidth();
		int h = this.getH() + this.icon2.getIconHeight();
		this.setW(w);
		this.setH(h);
		this.setHP(3);
		return this;
	}
	
}
