package element;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * @˵�� ����Ԫ�صĻ��ࡣ
 *
 */
public abstract class ElementObj {

	private int x;
	private int y;
	private int w;
	private int h;
	private ImageIcon icon;
	//���и��ֱ�Ҫ��״ֵ̬�����磺�Ƿ�����.
	private boolean live=true; //����״̬ true ������ڣ�false��������
						 // ���Բ���ö��ֵ���������(���棬�����������޵�)
	
	//ע���������¶���һ�������ж�״̬�ı�������Ҫ˼����1.��ʼ�� 2.ֵ�ĸı� 3.ֵ���ж�
	public ElementObj() {	//���������ʵû�����ã�ֻ��Ϊ�̳е�ʱ�򲻱���д��	
		
	}
	
	/**
	 * @˵�� �������Ĺ��췽��; ���������ഫ�����ݵ�����
	 * @param x    ���Ͻ�x����
	 * @param y    ���Ͻ�y����
	 * @param w    w���
	 * @param h    h�߶�
	 * @param icon  ͼƬ
	 */
	public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.icon = icon;
	}
	
	/**
	 * @˵�� ���󷽷�����ʾԪ��
	 * @param g  ���� ���ڽ��л滭
	 */
	public abstract void showElement(Graphics g);
	
	/**
	 * @˵�� ʹ�ø��ඨ����ռ����¼��ķ���
	 * 	         ֻ����Ҫʵ�ּ��̼��������࣬��д�������(Լ��)
	 * @˵�� ��ʽ2 ʹ�ýӿڵķ�ʽ;ʹ�ýӿڷ�ʽ��Ҫ�ڼ������������ת��
	 * @���⻰ Լ��  ����  ���ڴ󲿷ֵ�java��ܶ�����Ҫ�������õ�.
	 *         Լ����������
	 * @param bl   ���������  true�����£�false�����ɿ�
	 * @param key  �������ļ��̵�codeֵ  
	 * @��չ �������Ƿ���Է�Ϊ2��������1�����հ��£�1�������ɿ�(��ͬѧ��չʹ��)
	 */
	public void keyClick(boolean bl,int key) {  //�����������ǿ�Ʊ�����д�ġ�
		//System.out.println("����ʹ��");
	}
	
	/**
	 * @���ģʽ ģ��ģʽ;��ģ��ģʽ�ж��� ����ִ�з������Ⱥ�˳��,������ѡ������д����
	 *        1.�ƶ�  2.��װ  3.�ӵ�����
	 */
	public final void model(long gameTime) {
//		�Ȼ�װ
		updateImage(gameTime);
//		���ƶ�
		move(gameTime);
//		�ٷ����ӵ�
		add(gameTime);
	}
	
	/**
	 * @˵�� �ƶ�����; ��Ҫ�ƶ������࣬����д�������
	 */
	protected void move(long gameTime) {	
		
	}
	
	//��̬������Ƭ
	protected void updateImage(long gameTime) {
		
	}
	
	//long ... arr  �������� ����,����������������� N�� long���͵�����
	protected void add(long gameTime){
		
	}
	
	//��������  ����̳�
	public void die(int i) {  //����Ҳ��һ������
		
	}
	
	public ElementObj createElement(String str) {
		
		return null;
	}
	
	/**
	 * @˵�� ���������� Ԫ�ص���ײ���ζ���(ʵʱ����)
	 * @return
	 */
	public Rectangle getRectangle() {
//		���Խ�������ݽ��д��� 
		return new Rectangle(x,y,w,h);
	}
	
	/**
	 * @˵�� ��ײ������intersect���ཻ����ײ
	 * һ���� this���� һ���Ǵ���ֵ obj
	 * @param obj
	 * @return boolean ����true ˵������ײ������false˵��û����ײ
	 */
	public boolean pk(ElementObj obj) {	
		return this.getRectangle().intersects(obj.getRectangle());
	}
	
	/**
	 * @˵�� ������������֮��ķ�����������ڴ˽��д��ȷ�ϣ�Ѫ��ȷ�ϵȲ�����
	 */
	public void attackNow(ElementObj obj) {
		
	}
		
	/**
	 * ֻҪ�� VO�� POJO ��ҪΪ�������� get��set����
	 */
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public boolean getLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	
}
