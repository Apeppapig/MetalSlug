package viewItem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;

import modleItem.User;




public class MainFram extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainFram frame = new MainFram();
//					//frame.add();
//					frame.setVisible(true);
//					frame.setLocationRelativeTo(null);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	
	
	public MainFram(User user) {
		setTitle("\u7A0B\u5E8F\u4E3B\u754C\u9762");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 635);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel infoView = new JPanel();		
		tabbedPane.addTab("\u7528\u6237\u4FE1\u606F", null, infoView, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\u5DE5\u8D44\u67E5\u8BE2", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("\u5DE5\u8D44\u53D1\u653E", null, panel_2, null);	
		
		JPanel panel_3 = new UserManagerFrame();
		tabbedPane.addTab("\u7528\u6237\u7BA1\u7406", null, panel_3, null);
		
		
		
		
	}

}
