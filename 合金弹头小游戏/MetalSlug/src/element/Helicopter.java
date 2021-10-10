package element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import element.ElementObj;
import manager.ElementManager;
import manager.GameElement;
import manager.GameLoad;

public class Helicopter extends ElementObj{
	
	private boolean right=true;//控制飞机向右飞
	private boolean left=false;//控制飞机向左飞
	private int moveNum=2;//移动速度值
	private long boomTime=0L;//控制投放炸弹时间
	private long imgTime=0L;//控制图片切换时间
	private List<ImageIcon> planeImgList;//飞机图片列表
	private List<ImageIcon> screwImgList;//螺旋桨图片列表
	private ImageIcon planeImg;//机身图片
	private ImageIcon screwImg;//螺旋桨图片
	private String planeState=null;//记录飞机当前状态
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
		//从加载器的imgMap静态图片映射中获取当前飞机机身图片列表
		planeImgList=GameLoad.imgMap.get(splitStrings[2]);
		//从加载器的imgMap静态图片映射中获取当前螺旋桨图片列表
		screwImgList=GameLoad.imgMap.get(splitStrings[3]);
		//从机身图片列表里取出默认状态的第一张图片（默认向右飞）
		planeImg=planeImgList.get(0);	
		//从螺旋桨列表里取出第一张图
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
	 * 飞机必须重写以下三个方法updateImage(),move(),add()
	 */
	protected void updateImage(long gameTime) {	//重写
		//螺旋桨随时间不断的做旋转运动
		if (gameTime-imgTime>5) {
			imgTime=gameTime;
			if (i<=screwImgList.size()-1) {
				screwImg=(screwImgList.get(i++));	
			}
			else {	//防止下标越界，并且可以循环切换图片
				i=0;
			}
		}
	}
	
	protected void move(long gameTime) {	//重写
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
	
	//重写(在这里加入炸弹(需要新建一个炸弹类)，参考PlayerBody和PlayerBullet这两个代码)
	protected void add(long gameTime){	
		if (gameTime-boomTime>150) {
			boomTime=gameTime;//控制炸弹的生成间隔
			ElementObj bombObj = new Bomb().createElement(createBomb());
			em.addElement(bombObj, GameElement.BOMB);
		}
	}

	private String createBomb() {
		int x = this.getX();
		int y = this.getY();
		//初始化炸弹出现的位置
		x+=90;
		y+=50;
		return "x:" + x + ",y:" + y 
				+ ",pic1:"+"helicopter_bombfall"+",pic2:"+"helicopter_bombexplode";
	}
	
}
