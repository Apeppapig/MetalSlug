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
	 * ѧԺ�쵼
�������ϵ
�����������
��ѧ��ѧ��
ѧԺ�칫��
ѧԺʵ������
��ְ������Ա
	 */
	
	JTree tree;	
	public UserTree() {
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("���ѧԺ"); 
		
		DefaultMutableTreeNode dep1 = new DefaultMutableTreeNode("ѧԺ�쵼");
		DefaultMutableTreeNode dep2 = new DefaultMutableTreeNode("�������ϵ");
		DefaultMutableTreeNode dep3 = new DefaultMutableTreeNode("�����������");
		DefaultMutableTreeNode dep4 = new DefaultMutableTreeNode("ѧԺ�칫��");
		DefaultMutableTreeNode dep5 = new DefaultMutableTreeNode("ѧԺʵ������");
		DefaultMutableTreeNode dep6 = new DefaultMutableTreeNode("��������Ա");	
		
		UserDao userDao = new UserDao();
		Vector<User> vectors = new Vector<>();
		List list = new ArrayList();
		vectors = userDao.getUsers(list);
		for(User x:vectors)
		{
			switch(x.getDepartment())
			{
			case "ѧԺ�쵼":
				  dep1.add(new DefaultMutableTreeNode(x.getUserName()));
				  break;
			case "�������ϵ":
				dep2.add(new DefaultMutableTreeNode(x.getUserName()));
				break;	
			case "�����������":
				dep3.add(new DefaultMutableTreeNode(x.getUserName()));
				break;
			case "ѧԺ�칫��":
				dep4.add(new DefaultMutableTreeNode(x.getUserName()));
				break;
			case "ѧԺʵ������":
				dep5.add(new DefaultMutableTreeNode(x.getUserName()));
				break;
			case "��������Ա":
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
			leftNodeSelected();//��
		}
	}
	
	public abstract void leftNodeSelected(); //��
	

}


