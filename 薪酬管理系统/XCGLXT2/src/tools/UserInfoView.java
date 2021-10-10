package tools;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.UIManager;

import dao.UserDao;
import modleItem.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class UserInfoView extends JPanel {
	
	
	private JTextField userId;
	private JTextField userName;
	
	private JTextField sex;
	private JTextField department;
	private JTextField post;	
	private JTextField professionalTitle;
	private JTextField mTOA_State;
	private JTextField baseAllowanceState ;
	private JTextField dutyAllowanceState ;
	private JTextField telAllowanceState;

	public UserInfoView(User user) {
		
		
		setLayout(null);
		//UIManager.put("TextField.inactiveForeground", new Color(0, 0, 0));  //一定要在对象创建之前
		
		
		JLabel label = new JLabel("\u59D3\u540D\uFF1A");
		label.setBounds(60, 166, 54, 15);
		add(label);
		
		JLabel label_1 = new JLabel("\u6027\u522B\uFF1A");
		label_1.setBounds(60, 229, 54, 15);
		add(label_1);
		
		JLabel label_2 = new JLabel("\u804C\u79F0\u7EA7\u522B\uFF1A");
		label_2.setBounds(319, 98, 92, 15);
		add(label_2);
		
		JLabel label_3 = new JLabel("\u804C\u52A1\uFF1A");
		label_3.setBounds(60, 355, 54, 15);
		add(label_3);		

		userName = new JTextField(user.getUserName());
		userName.setEnabled(false);
		userName.setBounds(116, 162, 128, 21);		
		userName.setColumns(10);		
		add(userName);
		
		post = new JTextField(user.getPost());
		post.setEnabled(false);
		post.setBounds(116, 354, 128, 21);
		add(post);
		post.setColumns(10);
		
		JLabel label_4 = new JLabel("\u90E8\u95E8\uFF1A");
		label_4.setBounds(60, 292, 54, 15);
		add(label_4);
		
		
		department =  new JTextField(user.getDepartment());		
		department.setEnabled(false);
		
		department.setBounds(116, 290, 128, 21);		
		add(department);
		
		
		sex = new JTextField(user.getSex());
		sex.setForeground(Color.BLACK);
		sex.setEnabled(false);		
		sex.setBounds(116, 226, 128, 21);
		add(sex);
		
	    professionalTitle = new JTextField(user.getProfessionalTitle());
	    professionalTitle.setEnabled(false);
		professionalTitle.setBounds(449, 95, 130, 21);
		add(professionalTitle);
		
		JLabel label_5 = new JLabel("\u7F16\u53F7\uFF1A");
		label_5.setBounds(60, 103, 54, 15);
		add(label_5);
		
		userId = new JTextField(user.getUserId());
		userId.setForeground(Color.BLACK);
		userId.setEnabled(false);
		userId.setBounds(116, 98, 130, 21);		
		add(userId);
		userId.setColumns(10);
		
		JLabel label_6 = new JLabel("\u7EE9\u6548\u7EDF\u8BA1\u8EAB\u4EFD\uFF1A");
		label_6.setBounds(319, 161, 130, 15);
		add(label_6);
		
		mTOA_State = new JTextField(user.getmTOA_State());
		mTOA_State.setEnabled(false);
		mTOA_State.setBounds(449, 160, 130, 21);
		add(mTOA_State);
		
		JLabel label_7 = new JLabel("\u751F\u6D3B\u6D25\u8D34\u8EAB\u4EFD\uFF1A");
		label_7.setBounds(319, 224, 130, 15);
		add(label_7);
		
		baseAllowanceState = new JTextField(user.getBaseAllowanceState());
		baseAllowanceState.setEnabled(false);
		baseAllowanceState.setBounds(449, 223, 130, 21);
		add(baseAllowanceState);
		
		JLabel label_8 = new JLabel("\u804C\u52A1\u6D25\u8D34\u8EAB\u4EFD\uFF1A");
		label_8.setBounds(319, 287, 130, 15);
		add(label_8);
		
		dutyAllowanceState = new JTextField(user.getDutyAllowanceState());
		dutyAllowanceState.setEnabled(false);
		dutyAllowanceState.setBounds(449, 286, 130, 21);
		add(dutyAllowanceState);
		
		JLabel label_9 = new JLabel("\u7535\u8BDD\u8865\u8D34\u8EAB\u4EFD\uFF1A");
		label_9.setBounds(319, 350, 130, 15);
		add(label_9);
		
		 telAllowanceState = new JTextField(user.getTelAllowanceState());
		 telAllowanceState.setEnabled(false);
		telAllowanceState.setBounds(449, 349, 130, 21);
		add(telAllowanceState);
		
		JLabel label_11 = new JLabel("\u7528 \u6237 \u4FE1 \u606F");
		label_11.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		label_11.setBounds(233, 30, 166, 40);
		add(label_11);

	}
}
