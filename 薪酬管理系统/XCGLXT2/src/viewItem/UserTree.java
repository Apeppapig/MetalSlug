package viewItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import modleItem.User;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import dao.UserDao;



public abstract class UserTree extends JPanel implements ActionListener ,TreeSelectionListener {

	/**
	 * Create the panel.
	 * 学院领导
软件工程系
计算机基础部
数学教学部
学院办公室
学院实验中心
离职退休人员
	 */
	
	JTree tree;	
	public UserTree() {
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("软件学院"); 
		
		DefaultMutableTreeNode dep1 = new DefaultMutableTreeNode("学院领导");
		DefaultMutableTreeNode dep2 = new DefaultMutableTreeNode("软件工程系");
		DefaultMutableTreeNode dep3 = new DefaultMutableTreeNode("计算机基础部");
		DefaultMutableTreeNode dep4 = new DefaultMutableTreeNode("学院办公室");
		DefaultMutableTreeNode dep5 = new DefaultMutableTreeNode("学院实验中心");
		DefaultMutableTreeNode dep6 = new DefaultMutableTreeNode("离退休人员");	
		
		UserDao userDao = new UserDao();
		Vector<User> vectors = new Vector<>();
		List list = new ArrayList();
		vectors = userDao.getUsers(list);
		for(User x:vectors)
		{
			switch(x.getDepartment())
			{
			case "学院领导":
				  dep1.add(new DefaultMutableTreeNode(x.getUserName()));
				  break;
			case "软件工程系":
				dep2.add(new DefaultMutableTreeNode(x.getUserName()));
				break;	
			case "计算机基础部":
				dep3.add(new DefaultMutableTreeNode(x.getUserName()));
				break;
			case "学院办公室":
				dep4.add(new DefaultMutableTreeNode(x.getUserName()));
				break;
			case "学院实验中心":
				dep5.add(new DefaultMutableTreeNode(x.getUserName()));
				break;
			case "离退休人员":
				dep6.add(new DefaultMutableTreeNode(x.getUserName()));
				break;
			}
		}
		
		rootNode.add(dep1);
		rootNode.add(dep2);
		rootNode.add(dep3);
		rootNode.add(dep4);
		rootNode.add(dep5);
		rootNode.add(dep6);			
		setLayout(new BorderLayout(0, 0));
		
				
		tree = new JTree(rootNode);			
		tree.addTreeSelectionListener(this);
		this.add(tree,BorderLayout.CENTER);
		
		JButton button = new JButton("\u5237\u65B0");
		button.addActionListener(this);
		add(button, BorderLayout.SOUTH);			
			
		
	}
	
	public void valueChanged(TreeSelectionEvent e)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if(node == null)
		{
			return;
		}
		
		if(node.isLeaf())
		{
			leftNodeSelected();//改
		}
	}
	
	public abstract void leftNodeSelected(); //改
	

}


