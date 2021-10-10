package viewItem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import dao.LoginDao;
import dao.UserDao;
import modleItem.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField userName;
	private JPasswordField userPsw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("\u6B22\u8FCE\u767B\u9646");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 444, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");
		label.setBounds(55, 31, 81, 21);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6 \u7801\uFF1A");
		label_1.setBounds(55, 93, 81, 21);
		contentPane.add(label_1);
		
		userName = new JTextField();
		userName.setBounds(151, 28, 145, 27);
		contentPane.add(userName);
		userName.setColumns(10);
		
		userPsw = new JPasswordField();
		userPsw.setBounds(151, 90, 145, 27);
		contentPane.add(userPsw);
		
		JButton textFile = new JButton("\u767B\u9646");			
		textFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = userName.getText();
				String psw = new String( userPsw.getPassword()).trim();
				
				User user = new LoginDao().checkUser(name, psw);
				
				if(user!=null)
				{					
					MainFram  fram = new MainFram(user);
					fram.setLocationRelativeTo(null);
					fram.setVisible(true);
				}
				
				else
				{
					JOptionPane.showMessageDialog(null, "该用户不存在！");
				}
			}
		});
		
		textFile.setBounds(108, 152, 69, 29);
		contentPane.add(textFile);
		
		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
				button_1.setBounds(215, 152, 81, 29);
		contentPane.add(button_1);
	}
}
