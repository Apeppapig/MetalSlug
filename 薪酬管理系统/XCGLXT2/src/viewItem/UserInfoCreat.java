package viewItem;


import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import dao.UserDao;
import modleItem.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class UserInfoCreat extends JPanel {
	
	private JTextField userId;
	private JTextField userName;	
	
	
	private JComboBox sex;
	private JComboBox department;
	private JTextField post;	
	private JComboBox professionalTitle;
	private JComboBox mTOA_State;
	private JComboBox baseAllowanceState ;
	private JComboBox dutyAllowanceState ;
	private JComboBox telAllowanceState;
	private JComboBox loginAuthority;

	public UserInfoCreat() {
		setLayout(null);	
		
		JLabel label = new JLabel("\u59D3\u540D\uFF1A");
		label.setBounds(60, 166, 54, 15);
		add(label);
		
		JLabel label_1 = new JLabel("\u6027\u522B\uFF1A");
		label_1.setBounds(60, 229, 54, 15);
		add(label_1);
		
		JLabel label_2 = new JLabel("\u804C\u79F0\u7EA7\u522B\uFF1A");
		label_2.setBounds(319, 98, 76, 15);
		add(label_2);
		
		JLabel label_3 = new JLabel("\u804C\u52A1\uFF1A");
		label_3.setBounds(60, 355, 54, 15);
		add(label_3);
		
		userName = new JTextField();
		userName.setBounds(116, 162, 128, 21);
		add(userName);
		userName.setColumns(10);
		
		post = new JTextField();
		post.setBounds(116, 354, 128, 21);
		add(post);
		post.setColumns(10);
		
		JLabel label_4 = new JLabel("\u90E8\u95E8\uFF1A");
		label_4.setBounds(60, 292, 54, 15);
		add(label_4);
		
		department = new JComboBox();
		department.setModel(new DefaultComboBoxModel(new String[] {"\u5B66\u9662\u9886\u5BFC", "\u8F6F\u4EF6\u5DE5\u7A0B\u7CFB", "\u8BA1\u7B97\u673A\u57FA\u7840\u90E8", "\u6570\u5B66\u6559\u5B66\u90E8", "\u5B66\u9662\u529E\u516C\u5BA4", "\u5B66\u9662\u5B9E\u9A8C\u4E2D\u5FC3"}));
		department.setBounds(116, 290, 128, 21);
		add(department);
		
		sex = new JComboBox();
		sex.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u7537", "\u5973"}));
		sex.setBounds(116, 226, 128, 21);
		add(sex);
		
	    professionalTitle = new JComboBox();
		professionalTitle.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u6B63\u9AD8-\u6B63\u5904", "\u526F\u9AD8-\u526F\u5904", "\u4E2D\u7EA7-\u6B63\u79D1", "\u521D\u7EA7-\u526F\u79D1", "\u65E0"}));
		professionalTitle.setBounds(405, 95, 139, 21);
		add(professionalTitle);
		
		JLabel label_5 = new JLabel("\u7F16\u53F7\uFF1A");
		label_5.setBounds(60, 103, 54, 15);
		add(label_5);
		
		userId = new JTextField();
		userId.setBounds(116, 98, 128, 21);
		add(userId);
		userId.setColumns(10);
		
		JLabel label_6 = new JLabel("\u7EE9\u6548\u7EDF\u8BA1\u8EAB\u4EFD\uFF1A");
		label_6.setBounds(319, 161, 92, 15);
		add(label_6);
		
		mTOA_State = new JComboBox();
		mTOA_State.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u9662\u957F", "\u4E66\u8BB0", "\u526F\u9662\u957F", "\u526F\u4E66\u8BB0", "\u5DE5\u4F1A\u4E3B\u5E2D", "\u9662\u957F\u52A9\u7406", "\u5B66\u672F\u59D4\u4E3B\u4EFB\u59D4\u5458", "\u7CFB\u4E3B\u4EFB-\u517C\u804C", "\u90E8\u4E3B\u4EFB-\u517C\u804C", "\u884C\u653F", "\u6559\u8F85", "\u4E13\u4EFB\u6559\u5E08", "\u65E0"}));
		mTOA_State.setBounds(405, 158, 139, 21);
		add(mTOA_State);
		
		JLabel label_7 = new JLabel("\u751F\u6D3B\u6D25\u8D34\u8EAB\u4EFD\uFF1A");
		label_7.setBounds(319, 224, 92, 15);
		add(label_7);
		
		baseAllowanceState = new JComboBox();
		baseAllowanceState.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u9662\u957F\u4E66\u8BB0", "\u526F\u9662\u957F-\u4E66\u8BB0", "\u5DE5\u4F1A\u4E3B\u5E2D", "\u7CFB\u90E8\u4E3B\u4EFB", "\u529E\u516C\u5BA4\u4E3B\u4EFB", "\u56E2\u59D4\u4E66\u8BB0", "\u5B66\u672F\u59D4\u4E3B\u4EFB\u59D4\u5458", "\u9662\u957F\u52A9\u7406", "\u529E\u7CFB\u90E8\u526F\u4E3B\u4EFB", "\u672C\u79D1\u751F\u8F85\u5BFC\u5458", "\u6559\u5B66\u79D8\u4E66\u6559\u52A1", "\u6559\u6388", "\u526F\u6559\u6388", "\u8BB2\u5E08", "\u521D\u7EA7", "\u65E0"}));
		baseAllowanceState.setBounds(405, 221, 139, 21);
		add(baseAllowanceState);
		
		JLabel label_8 = new JLabel("\u804C\u52A1\u6D25\u8D34\u8EAB\u4EFD\uFF1A");
		label_8.setBounds(319, 287, 92, 15);
		add(label_8);
		
		dutyAllowanceState = new JComboBox();
		dutyAllowanceState.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u9662\u957F\u4E66\u8BB0 ", "\u526F\u9662\u957F-\u4E66\u8BB0", "\u5DE5\u4F1A\u4E3B\u5E2D ", "\u529E\u516C\u5BA4\u4E3B\u4EFB", "\u56E2\u59D4\u4E66\u8BB0 ", "\u7EE7\u6559\u529E\u4E3B\u4EFB", "\u5404\u4E2D\u5FC3\u4E3B\u4EFB  ", "\u5404\u4E2D\u5FC3\u526F\u4E3B\u4EFB", "\u5404\u529E\u526F\u4E3B\u4EFB", "\u672C\u79D1\u751F\u8F85\u5BFC\u5458", "\u672C\u79D1\u751F\u73ED\u4E3B\u4EFB", "\u6559\u5B66\u79D8\u4E66", "\u79D1\u7814\u79D8\u4E66", "\u515A\u52A1\u79D8\u4E66", "\u515A\u652F\u90E8\u4E66\u8BB0", "\u5DE5\u4F1A\u59D4\u5458", "\u5B66\u751F\u79D1\u7814\u8D1F\u8D23\u4EBA ", "\u5B66\u9662\u7F51\u7AD9\u7EF4\u62A4 ", "\u5FAE\u4FE1\u516C\u4F17\u53F7\u7EF4\u62A4", "\u65E0"}));
		dutyAllowanceState.setBounds(405, 284, 139, 21);
		add(dutyAllowanceState);
		
		JLabel label_9 = new JLabel("\u7535\u8BDD\u8865\u8D34\u8EAB\u4EFD\uFF1A");
		label_9.setBounds(319, 350, 92, 15);
		add(label_9);
		
		 telAllowanceState = new JComboBox();
		telAllowanceState.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u9662\u957F\u4E66\u8BB0", "\u526F\u9662\u957F\u4E66\u8BB0", "\u9662\u957F\u52A9\u7406", "\u5DE5\u4F1A\u4E3B\u5E2D", "\u529E\u516C\u5BA4\u4E3B\u4EFB", "\u56E2\u59D4\u4E66\u8BB0", "\u5B66\u672F\u59D4\u4E3B\u4EFB\u59D4\u5458", "\u5404\u7CFB\u90E8\u95E8\u4E3B\u4EFB", "\u5404\u90E8\u95E8\u526F\u4E3B\u4EFB", "\u672C\u79D1\u751F\u8F85\u5BFC\u5458", "\u515A\u52A1\u79D8\u4E66", "\u6559\u52A1\u5458", "\u6559\u5B66\u79D8\u4E66", "\u79D1\u7814\u79D8\u4E66", "\u65E0"}));
		telAllowanceState.setBounds(405, 347, 139, 21);
		add(telAllowanceState);
		
		JLabel label_10 = new JLabel("\u7528\u6237\u7CFB\u7EDF\u767B\u5F55\u8EAB\u4EFD\uFF08\u7528\u6237\u6743\u9650\uFF09\uFF1A");
		label_10.setBounds(64, 400, 180, 15);
		add(label_10);
		
		loginAuthority = new JComboBox();
		loginAuthority.setModel(new DefaultComboBoxModel(new String[] {"\u6559\u5E08", "\u8D22\u52A1\u5458", "\u8D22\u52A1\u4E3B\u7BA1\u9886\u5BFC"}));
		loginAuthority.setBounds(254, 397, 107, 21);
		add(loginAuthority);
		
		JLabel label_11 = new JLabel("\u6DFB\u52A0\u65B0\u7528\u6237");
		label_11.setFont(new Font("宋体", Font.PLAIN, 22));
		label_11.setBounds(217, 28, 216, 40);
		add(label_11);	
		
		JButton creatUserBt = new JButton("\u521B \u5EFA \u7528 \u6237");		
		creatUserBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				user.setUserId(userId.getText());
				user.setUserName(userName.getText());
				user.setUserPsw("abc123456");
				user.setSex(sex.getSelectedItem().toString());
				user.setDepartment(department.getSelectedItem().toString());
				user.setPost(post.getText());
				user.setProfessionalTitle(professionalTitle.getSelectedItem().toString());
				user.setmTOA_State(mTOA_State.getSelectedItem().toString());
				user.setBaseAllowanceState(baseAllowanceState.getSelectedItem().toString());
				user.setDutyAllowanceState(dutyAllowanceState.getSelectedItem().toString());
				user.setTelAllowanceState(telAllowanceState.getSelectedItem().toString());
				user.setLoginAuthority(loginAuthority.getSelectedItem().toString());
				
				UserDao userDao = new UserDao();
				int n = userDao.addUser(user);
				
				if(n==1)
				{
					JOptionPane.showMessageDialog(null, "添加用户成功", "提示信息", JOptionPane.OK_OPTION);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "添加用户不成功", "提示信息", JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
		});
		

		creatUserBt.setBounds(252, 448, 143, 23);
		add(creatUserBt);
	}
	
	

}
