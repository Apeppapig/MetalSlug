package tools;

import modleItem.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

import dao.BaseDao;

public class UserDao {

	private User user = null;
	

	public User getUser(String userId) {
		List<String> list = new ArrayList<>();
		list.add(userId);
		Vector<String> v = null;
		String sql = "select * from user where userId =?";
		try {
			v = BaseDao.select(sql, list);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(v!=null)
		{
		user = new User();
		user.setUserId(v.get(0));
		user.setUserName(v.get(1));
		user.setUserPsw(v.get(2));
		user.setSex(v.get(3));
		user.setDepartment(v.get(4));
		user.setPost(v.get(5));
		user.setProfessionalTitle(v.get(6));
		user.setmTOA_State(v.get(7));
		user.setBaseAllowanceState(v.get(8));
		user.setDutyAllowanceState(v.get(9));
		user.setTelAllowanceState(v.get(10));
		user.setLoginAuthority(v.get(11));		
		}		
		return user;
	}
	//添加新方法 应付名字查询
	public User getUserByName(String userName) {
		List<String> list = new ArrayList<>();
		list.add(userName);
		Vector<String> v = null;
		String sql = "select * from user where userName =?";
		try {
			v = BaseDao.select(sql, list);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(v!=null)
		{
		user = new User();
		user.setUserId(v.get(0));
		user.setUserName(v.get(1));
		user.setUserPsw(v.get(2));
		user.setSex(v.get(3));
		user.setDepartment(v.get(4));
		user.setPost(v.get(5));
		user.setProfessionalTitle(v.get(6));
		user.setmTOA_State(v.get(7));
		user.setBaseAllowanceState(v.get(8));
		user.setDutyAllowanceState(v.get(9));
		user.setTelAllowanceState(v.get(10));
		user.setLoginAuthority(v.get(11));		
		}		
		return user;
	}
	

	public Vector<User> getUsers(List userIdList) {
		
		Vector<User> users = new Vector<>();
		Vector<Vector<String>> vectors = new Vector<>();
		String sql = "select * from user";
		List<String> list = new ArrayList<>();
		try {
			vectors = BaseDao.selectAll(sql, list);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		for(Vector<String> x :vectors)
		{
			User user = new User();
			user.setUserId(x.get(0));
			user.setUserName(x.get(1));
			user.setUserPsw(x.get(2));
			user.setSex(x.get(3));
			user.setDepartment(x.get(4));
			user.setPost(x.get(5));
			user.setProfessionalTitle(x.get(6));
			user.setmTOA_State(x.get(7));
			user.setBaseAllowanceState(x.get(8));
			user.setDutyAllowanceState(x.get(9));
			user.setTelAllowanceState(x.get(10));
			user.setLoginAuthority(x.get(11));
			
			users.add(user);
		}
				
		return users;
	}

	public int addUser(User user) {			
		
		 List<String> list = new ArrayList<>();
		 list.add(user.getUserId());
		 list.add(user.getUserName());
		 list.add(user.getUserPsw());
		 list.add(user.getSex());
		 list.add(user.getDepartment());
		 list.add( user.getPost());
		 list.add(user.getmTOA_State());
		 list.add(user.getBaseAllowanceState());
		 list.add( user.getDutyAllowanceState());
		 list.add(user.getTelAllowanceState());
		 list.add(user.getProfessionalTitle());
		 list.add(user.getLoginAuthority());		 	
		
		 String sql1 ="userId,userName,psw,sex,department,post,mTAO_State,baseAllowanceState,dutyAllowanceState,telAllowanceState,professionalTitle,loginAuthority";
		 String sql = "insert into user("+sql1+") values (?,?,?,?,?,?,?,?,?,?,?,?)";
		 
		 return BaseDao.insert(sql, list);
	}

	public int updateUser(User user) {
		List<String> list = new ArrayList<>();
		list.add(user.getUserName());
		list.add(user.getUserPsw());
		list.add(user.getSex());
		 list.add(user.getDepartment());
		 list.add( user.getPost());
		 list.add(user.getmTOA_State());
		 list.add(user.getBaseAllowanceState());
		 list.add( user.getDutyAllowanceState());
		 list.add(user.getTelAllowanceState());
		 list.add(user.getProfessionalTitle());
		 list.add(user.getLoginAuthority());
		 
		 list.add(user.getUserId());
		 
		 String sql1 = "userName=?,psw=?,sex=?,department=?,post=?,mTAO_State=?,baseAllowanceState=?,dutyAllowanceState=?,telAllowanceState=?,professionalTitle=?,loginAuthority=?"; 
		 String sql = "update user set "+ sql1+"where UserId=?";
		 
		 int updateCount =0;
		 try {
			updateCount = BaseDao.update(sql, list);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		 return updateCount;
	}

	private User setUser(Vector userAttributes) {
		return null;
	}

	private List getUserAttributesList(User user) {
				
		
		 return null;
	}

}
