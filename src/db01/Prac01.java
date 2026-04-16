package db01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Prac01 {

	private	static	String	driver	=	"oracle.jdbc.OracleDriver";
	private	static	String	dburl	=	"jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private	static	String	dbuid	=	"hr";
	private	static	String	dbpwd	=	"1234";
	// 위 4줄만 바꾸어 주면 oracle db에서 다른 db를 사용할 수 있음
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// hr 스키마에서 부서명, 직원이름, 전화번호 출력
		Class.forName(driver); 
		Connection	conn	=	DriverManager.getConnection(dburl, dbuid, dbpwd);
		
		Statement	stmt	=	conn.createStatement();
		String		sql		=	"SELECT D.department_name	DNAME, E.first_name || ' ' || E.last_name  ENAME, E.phone_number PHONE " + 
								" FROM departments D JOIN employees E ON D.department_id = E.department_id"; 
		//System.out.println(sql);
		ResultSet	rs		=	stmt.executeQuery(sql);
		System.out.println("부서명, 직원이름, 전화번호");
		while( rs.next() != false ) {
			System.out.print(rs.getString("DNAME") + ',');
			System.out.print(rs.getString("ENAME") + ',');
			System.out.print(rs.getString("PHONE"));
			System.out.println();
		}// while 문 end
		
		rs.close();
		stmt.close();
		conn.close();

	} // main end

} // class Prac01 end
