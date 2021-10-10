package viewItem;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class UserManagerFrame extends JPanel {

	/**
	 * Create the panel.
	 */
	JSplitPane splitPane;
	
	public UserManagerFrame() {
		setLayout(new BorderLayout(0, 0));
		
	//	JSplitPane splitPane = new JSplitPane();
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(new myTree());
		splitPane.setRightComponent(new UserInfoCreat());
		add(splitPane, BorderLayout.CENTER);

	}
	
	class myTree extends UserTree{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			
			splitPane.setLeftComponent(new myTree());	
			
		}

		@Override
		public void leftNodeSelected() {
			// TODO 自动生成的方法存根
			
			System.out.println("叶子节点被选中了！");
			
			
		}
	}

		
}
