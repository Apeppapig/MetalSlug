package tools;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChooseImage extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	MyCanvas myCanvas =null;//图片容器
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseImage frame = new ChooseImage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChooseImage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u6587\u4EF6\u540D\uFF1A");
		label.setBounds(28, 49, 54, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(76, 46, 267, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("\u9009\u62E9\u56FE\u7247");
		button.addActionListener(this);
		button.setBounds(341, 45, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u4E0A\u4F20\u56FE\u7247");
		button_1.setBounds(210, 396, 93, 23);
		contentPane.add(button_1);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
			
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new MyFileFilter(".jpg"));
		jfc.setDialogTitle("选择图像文件");
		int result = jfc.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION)
		{
			File f = jfc.getSelectedFile();
			
			if(accept(f)) //检查文件格式
			{
			  textField.setText(f.getAbsolutePath());			
			  URL imgURL = null ;
			  Image img;
			  try {
				    imgURL=f.toURI().toURL();
			       } catch (MalformedURLException e) {
				  // TODO 自动生成的 catch 块
				    e.printStackTrace();
			       }			
			 
				 img = Toolkit.getDefaultToolkit().getImage(imgURL);
				 myCanvas = new MyCanvas(img);
				 myCanvas.setBounds(80,85,300,200);					
				 contentPane.add(myCanvas);	
				 contentPane.repaint();
				 
				 
			  }
			else
			{
				 JOptionPane.showMessageDialog(null, "文件格式不对，请选择jpg文件！");
			}			
			
		}			
	}
		

	
	private boolean accept(File f) //设置文件的类型检查
	{
		boolean acceptAble = false;
		String fileName = f.getName();
		int i =fileName.lastIndexOf(".");
		if(i>0&&i<fileName.length()-1)
		{
			String fileType = fileName.substring(i+1,fileName.length());
		
			if(fileType.equalsIgnoreCase("jpg")||fileType.equalsIgnoreCase("jpeg"))
			{
				acceptAble = true;
			}
		}		
		return acceptAble;			
	}
	
	class MyCanvas extends JPanel {
		Image img;		
	    public MyCanvas(Image img)
	    {
	    	this.repaint();
	    	this.img = img;
	    	
	    }
		
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(img, 0, 0,300,200,this); // 显示图片
		}
	}
}
