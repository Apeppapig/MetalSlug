package show;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @˵�� ��Ϸ���� ��Ҫʵ�ֵĹ��ܣ��رգ���ʾ�������С��
 * @author renjj
 * @����˵��   ��ҪǶ�����,�������̵߳ȵ�
 * @����˵��  swing awt  �����С����¼�û��ϴ�ʹ������Ĵ�����ʽ��
 * 
 * @���� 1.���󶨵�����
 *       2.������
 *       3.��Ϸ���߳�����
 *       4.��ʾ����
 */
public class GameJFrame extends JFrame{
	public static int GameX = 800;//��Ϸ���ڵĳ��� 
	public static int GameY = 480;//��Ϸ���ڵĿ��
	private JPanel jPanel =null; //����ʵ�ֵ����
	private KeyListener keyListener=null;//���̼���
	private MouseMotionListener mouseMotionListener=null; //������
	private MouseListener mouseListener=null;
	private Thread thead=null;  //��Ϸ���߳�
	
	public GameJFrame() {
		init();
	}
	public void init() {
		this.setSize(GameX, GameY); //���ô����С
		this.setTitle("�Ͻ�ͷ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�����˳����ҹر�
		this.setLocationRelativeTo(null);//��Ļ������ʾ
		setResizable(false);
		//�����Լ�����ƴ��ڵ���������
	}
	
	/*���岼��: ���Դ浵��������ʹ��button   �������չ��*/
	public void addButton() {
//		this.setLayout(manager);//���ָ�ʽ��������ӿؼ�
	}	
	
	/**
	 * ��������
	 */
	public void start() {
		if(jPanel!=null) {
			this.add(jPanel);
		}
		if(keyListener !=null) {
			this.addKeyListener(keyListener);
		}
		if(thead !=null) {
			thead.start();//�����߳�
		}
		this.setVisible(true);//��ʾ����
//		���jp �� runnable�� ����ʵ����� 
//		�������ж��޷�������� instanceof�ж�Ϊ false ��ô jpanelû��ʵ��runnable�ӿ�
		if(this.jPanel instanceof Runnable) {
//			�Ѿ��������ж���ǿ������ת���������
//			new Thread((Runnable)this.jPanel).start();
			Runnable run=(Runnable)this.jPanel;
			Thread th=new Thread(run);
			th.start();// 
			System.out.println("�Ƿ�����");
		}
		
	}
	
	/*setע�룺�ȴ��ѧϰssm ͨ��set����ע�������ļ��ж�ȡ������;�������ļ�
	 * �е����ݸ�ֵΪ�������
	 * ����ע�룺��Ҫ��Ϲ��췽��
	 * spring ��ioc ���ж�����Զ����ɣ�����
	 * */
	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}
	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}
	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}
	public void setThead(Thread thead) {
		this.thead = thead;
	}
}
