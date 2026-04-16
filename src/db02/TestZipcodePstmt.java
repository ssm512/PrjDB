package db02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestZipcodePstmt {

	// db 연결 문자열
	private	static	String	driver		=	"oracle.jdbc.OracleDriver";
	private	static	String	url			=	"jdbc:oracle:thin:@localhost:1521:xe";
	private	static	String	dbuid		=	"hr";
	private	static	String	dbpwd		=	"1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection	conn				=	DriverManager.getConnection(url, dbuid, dbpwd);
	//	System.out.println(conn); // oracle.jdbc.driver.T4CConnection@58134517
		
		String				sql			=	""; // oracle은 sql 안에 ;가 들어가면 안된
		sql +=  "SELECT nvl(D.department_name, '부서없음') 	부서명, "; 
		sql +=  "E.first_name|| ' ' || E.last_name 			직원명, " ;
		sql +=  "E.phone_number 							전화번호 ";
		sql +=  "FROM departments D  RIGHT OUTER JOIN employees E ON D.department_id = E.department_id ";
		sql +=  "WHERE E.department_id = ? ";// perpared statment 인자를 ?를 통해 전달 할 수 있음
		System.out.println(sql);
		PreparedStatement	pstmt		=	conn.prepareStatement( sql );	
		pstmt.setInt(1, 80); // 첫번째 ?의 값을 설정, 작은 ''을 붙이려면 setString으로 
		ResultSet			rs			=	pstmt.executeQuery(); // perparedstatment 시에는 rs에 sql을 넣는게 아님
		String				fmt			=	"%s, %s, %s";
		
		//while (rs.next() != false) {} // 같은 의미
		//while (rs.next() == true ) {} // 같은 의미
		while (rs.next()) { // 같은 의미
			String			dname		=	rs.getString("부서명");
			String			ename		=	rs.getString("직원명");
			String			phone		=	rs.getString("전화번호");
			String			msg			=	String.format(fmt, dname, ename, phone);
			System.out.println(msg);
		} // while 문 end
		
		rs.close();
		pstmt.close();
		conn.close();

	} // main end

} // class TestZipcodePstmt end
