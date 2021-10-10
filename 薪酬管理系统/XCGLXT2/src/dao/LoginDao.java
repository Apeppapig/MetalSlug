package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import modleItem.User;

public class LoginDao {
	
	public User checkUser(String userName,String userPsw)
	{
		User user = null;
		List<String> list = new ArrayList<>();
		list.add(userName);
		list.add(userPsw);		
		String sql = "select * from user where userName=? and psw =?";
		Vector<String> v = null;
		
		try {
			v= BaseDao.select(sql, list);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		if(v.size()>1)
		{
			System.out.println(v.size());
			user = new UserDao().creatUser(v);
		}		
		
		return user;
	}


}
