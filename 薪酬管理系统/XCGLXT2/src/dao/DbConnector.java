package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
	private Connection con;
	private String dbDriver="com.mysql.jdbc.Driver";
	private String dbUser ="chf123";
	private String dbPsw ="abc123";
	private String dbUrl ="jdbc:mysql://localhost:3306/xcglxt";
	
	public Connection getConnection()
	{
		try {
			Class.forName(dbDriver);
			System.out.println("数据库驱动加载成功！");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		try {
			con =DriverManager.getConnection(dbUrl, dbUser, dbPsw);
			System.out.println("数据库连接成功！");
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return con;
	}

}
