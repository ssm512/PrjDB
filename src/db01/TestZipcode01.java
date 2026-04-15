package db01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class TestZipcode01 {
	// 연결 문자열 : Connection String
	private	static	String driver		=	"oracle.jdbc.OracleDriver";	// 오라클하고 연결하는 class의 이름을 지정
	private	static	String dburl		=	"jdbc:oracle:thin:@localhost:1521:xe";	// @앞에는 driver 이름(버전)임
	private	static	String dbuid		=	"sky";	
	private	static	String dbpwd		=	"1234";	

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver); // 외부의 클래스를 new로 부르는 명령어 new랑 같은 역할
		Connection	conn	=	DriverManager.getConnection(dburl, dbuid, dbpwd);
		
		Statement	stmt	=	conn.createStatement();		
		// 오라클에서 실행할 한줄의 명령을 Statement class의 stmt instance에 담는다
		String		sql		=	"SELECT COUNT(distinct zipcode) cnt FROM ZIPCODE";
		
		ResultSet 	rs		=	stmt.executeQuery(sql);
		// 파일 포인터라고 생각하면 됨
		
		rs.next();
		// rs 안에 거를 한줄 읽어온다
		
		System.out.println(rs.getInt("cnt"));
		
		rs.close();
		stmt.close();
		conn.close();
		
	} // main end

} // class TestZipcode01 end
