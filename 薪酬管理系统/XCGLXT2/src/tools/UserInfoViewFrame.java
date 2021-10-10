package tools;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JSplitPane;

import viewItem.UserTree;
import modleItem.User;

public class UserInfoViewFrame extends JPanel {

	/**
	 * Create the panel.
	 */
	public UserInfoViewFrame(User user) {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(new MyUserTree());
		splitPane.setRightComponent(new UserInfoView(user));
		add(splitPane, BorderLayout.CENTER);
	}
	
	class MyUserTree extends UserTree{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void leftNodeSelected() {
			// TODO 自动生成的方法存根
			
		}
		
	}
	
	

}
