package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

/**
 * @˵�� ������
 * @author DELL
 *
 */
public class Hostage extends ElementObj{
	
	private boolean right=false;
	private boolean left=false;
	private int recordX;
	private int recordY;
	private String stateString=null;
	//��ǰԪ�ع�����
	private ElementManager em;
	//��ǰ������Ƭ�б�
	private List<ImageIcon> hostageImgList;
	private List<ElementObj> hostageList;
	//��ǰ�����Ƿ���
	private boolean isRescue=false;//rescue��״̬�ı���GameThread����ı�
	//��ǰ�����Ƿ��Ѿ������ǵ���(trueΪ�Ѿ�����falseΪû��)
	private boolean isgiven=false;
	private long bindTime=5L;		//���ڿ������ʱ���ʱͼƬ�仯�ٶ�
	private long rescueTime=3L;		//���ڿ������ʸ�����ʱͼƬ�仯�ٶ�
	private long giveTime=5L;		//���ڿ������ʵ������ʱͼƬ�仯�ٶ�
	private long fleeTime=0L;		//���ڿ������������ֳ�ʱͼƬ�仯�ٶ�
	private int i=0;

	public Hostage() {
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
		hostageImgList=GameLoad.imgMap.get(splitStrings[2]);
		//�ٴ�����ͼƬ�б���ȡ��Ĭ��״̬�ĵ�һ��ͼƬ
		ImageIcon icon2=hostageImgList.get(0);	
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}
	
	@Override
	public void keyClick(boolean bl, int key) {
		if (bl) {// ����
			switch (key) { // ��ô�Ż� �������˼��;�Ӽ����������������û�취����һ��
			case 37:
				this.right = false;
				this.left = true;	
				break;
			case 39:
				this.left = false;
				this.right = true;
				break;
			}
		} else {//�ɿ�����
			switch (key) {
			case 37:
				this.left = false;
				break;
			case 39:
				this.right = false;
				break;
			}
		}
	}
	
	/**
	 * �����ڻ�ȣ������ߣ�����ȹ����е�ͼƬ����
	 */
	@Override
	protected void updateImage(long gameTime) {
		//������ʻ�δ���ȣ����ϸ�������ͼƬ��
		if (this.getLive()&&(!this.getRescue())&&(!this.getGiven())) {	
			stateString="hostage_bind";
			hostageImgList=GameLoad.imgMap.get(stateString);
			if (gameTime-bindTime>20) {
				bindTime=gameTime;
				if (i<=hostageImgList.size()-1) {
					this.setIcon(hostageImgList.get(i++));
				}
				else {
					i=0;
				}
			}
		}
		//���ʵõ���Ԯ������δ������ߣ���δ��������
		//rescue��״̬�ı���GameThread�����Ϊ��
		if (this.getLive()&&this.getRescue()&&(!this.getGiven())) {	
			stateString="hostage_rescue";
			hostageImgList=GameLoad.imgMap.get(stateString);
			if (gameTime-rescueTime>25) {
				rescueTime=gameTime;
				if (i<=hostageImgList.size()-1) {
					this.setIcon(hostageImgList.get(i++));
				}
				else {
					this.setGiven(true);//�ɰ��һϵ��ͼƬ������Ϻ󣬰ѵ������״̬��Ϊ��
				}
			}	
		}
		//���ʵ������
		if (this.getLive()&&this.getRescue()&&this.getGiven()) {
			stateString="hostage_give";
			hostageImgList=GameLoad.imgMap.get(stateString);
			if (gameTime-giveTime>20) {
				giveTime=gameTime;
				if (i<=hostageImgList.size()-1) {
					this.setIcon(hostageImgList.get(i++));
				}
				else {
					this.setLive(false);
					this.setRescue(false);
				}
			}
		}
		if ((!this.getLive())) {	//������������
//			this.setRescue(false);
			stateString="hostage_runaway";
			hostageImgList=GameLoad.imgMap.get(stateString);
			if (gameTime-fleeTime>10) {
				fleeTime=gameTime;
				if (i<=hostageImgList.size()-1) {
					this.setIcon(hostageImgList.get(i++));
				}
				else {
					i=0;
				}
			}	
		}
	}
	
	@Override
	protected void move(long gameTime) {
		if (this.left) {

		}
		if (this.right&&this.getX()<1400) { // �������ת�ɴ�������
//			this.setX(this.getX()-2);
		}
	}
	
	/**
	 * ���ʻ�ȣ������ǵ���
	 */
	@Override
	protected void add(long gameTime) {
		//������ʴ��ڸ������״̬�������ɵ���
		if ((!this.getLive())&&(!this.getRescue())&&this.getGiven()) {	
			this.recordX=this.getX();
			this.recordY=this.getY();
			ElementObj playerProp=new PlayerProp().createElement(createProp());
			em.addElement(playerProp, GameElement.PLAYER_PROP);
			this.setGiven(false);
		}
		
	}
	
	private String createProp() {
		int x = recordX;
		int y = recordY;
		//��ʼ�����߳��ֵ�λ��
		x+=10;
		y+=40;
		return "x:" + x + ",y:" + y + ",pic:"+"player_prop";
	}
	
	@Override
	public void attackNow(ElementObj obj) {
		if (obj instanceof PlayerBody||obj instanceof PlayerLegs) {
			this.setRescue(true);
		}
	}

	/**
	 * ���ʸ����ǵ��ߺ������ƶ���ֱ���뿪��ͼ��߽������ʼ�����remove
	 */
	@Override
	public void die(int i) {
		if ((!this.getLive())&&this.getX()>=0) {
			this.setX(this.getX() - 1);
		}
		if (this.getX()<0) {
			hostageList=em.getElementsByKey(GameElement.HOSTAGE);
			hostageList.remove(i);
		}
	}

	//Getter��Setter
	public boolean getRescue() {
		return isRescue;
	}

	public void setRescue(boolean isRescue) {
		this.isRescue = isRescue;
	}

	public boolean getGiven() {
		return isgiven;
	}

	public void setGiven(boolean isgiven) {
		this.isgiven = isgiven;
	}

	
}
