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
			System.out.println("���ݿ��������سɹ���");
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		try {
			con =DriverManager.getConnection(dbUrl, dbUser, dbPsw);
			System.out.println("���ݿ����ӳɹ���");
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		return con;
	}

}
