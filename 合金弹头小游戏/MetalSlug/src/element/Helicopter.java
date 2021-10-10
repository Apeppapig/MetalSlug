package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import element.ElementObj;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

public class Helicopter extends ElementObj{
	
	private boolean right=true;//���Ʒɻ����ҷ�
	private boolean left=false;//���Ʒɻ������
	private int moveNum=2;//�ƶ��ٶ�ֵ
	private long boomTime=0L;//����Ͷ��ը��ʱ��
	private long imgTime=0L;//����ͼƬ�л�ʱ��
	private List<ImageIcon> planeImgList;//�ɻ�ͼƬ�б�
	private List<ImageIcon> screwImgList;//������ͼƬ�б�
	private ImageIcon planeImg;//����ͼƬ
	private ImageIcon screwImg;//������ͼƬ
	private String planeState=null;//��¼�ɻ���ǰ״̬
	private int i=0;
	private int x2=0;
	private int y2=0;
	private ElementManager em;
	
	public Helicopter() {
		em=ElementManager.getManager();
	}

	public ElementObj createElement(String str) {
		String[] splitStrings = str.split(",");
		this.setX(Integer.parseInt(splitStrings[0]));
		this.setY(Integer.parseInt(splitStrings[1]));
		//�Ӽ�������imgMap��̬ͼƬӳ���л�ȡ��ǰ�ɻ�����ͼƬ�б�
		planeImgList=GameLoad.imgMap.get(splitStrings[2]);
		//�Ӽ�������imgMap��̬ͼƬӳ���л�ȡ��ǰ������ͼƬ�б�
		screwImgList=GameLoad.imgMap.get(splitStrings[3]);
		//�ӻ���ͼƬ�б���ȡ��Ĭ��״̬�ĵ�һ��ͼƬ��Ĭ�����ҷɣ�
		planeImg=planeImgList.get(0);	
		//���������б���ȡ����һ��ͼ
		screwImg=screwImgList.get(0);
		this.setW(planeImg.getIconWidth());
		this.setH(planeImg.getIconHeight());
		this.setIcon(planeImg);
		return this;
	}
	
	@Override
	public void showElement(Graphics g) {
		int x1=this.getX();
		int y1=this.getY();
		y2=y1;
		if (this.left) {
			x2=this.getX()+20;	
		}
		if (this.right) {
			x2=this.getX()+70;
		}
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
		g.drawImage(this.screwImg.getImage(), 
				x2, y2, 
				this.screwImg.getIconWidth(), this.screwImg.getIconHeight(), null);
	}
	
	/**
	 * �ɻ�������д������������updateImage(),move(),add()
	 */
	protected void updateImage(long gameTime) {	//��д
		//��������ʱ�䲻�ϵ�����ת�˶�
		if (gameTime-imgTime>5) {
			imgTime=gameTime;
			if (i<=screwImgList.size()-1) {
				screwImg=(screwImgList.get(i++));	
			}
			else {	//��ֹ�±�Խ�磬���ҿ���ѭ���л�ͼƬ
				i=0;
			}
		}
	}
	
	protected void move(long gameTime) {	//��д
		if(right&&this.getX()<600) {
			this.setX(this.getX()+moveNum);
		}
		if(this.getX()==600) {
			right=false;
			left=true;
			this.setIcon(planeImgList.get(1));
		}
		if(left&&this.getX()>0) {
			this.setX(this.getX()-moveNum);
		}
		if(this.getX()==0) {
			right=true;
			left=false;
			this.setIcon(planeImgList.get(0));
		}
	}
	
	//��д(���������ը��(��Ҫ�½�һ��ը����)���ο�PlayerBody��PlayerBullet����������)
	protected void add(long gameTime){	
		if (gameTime-boomTime>150) {
			boomTime=gameTime;//����ը�������ɼ��
			ElementObj bombObj = new Bomb().createElement(createBomb());
			em.addElement(bombObj, GameElement.BOMB);
		}
	}

	private String createBomb() {
		int x = this.getX();
		int y = this.getY();
		//��ʼ��ը�����ֵ�λ��
		x+=90;
		y+=50;
		return "x:" + x + ",y:" + y 
				+ ",pic1:"+"helicopter_bombfall"+",pic2:"+"helicopter_bombexplode";
	}
	
}
