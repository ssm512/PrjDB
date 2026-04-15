package db01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestZipcode02 {

	private	static	String	driver	=	"oracle.jdbc.OracleDriver";
	private	static	String	dburl	=	"jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private	static	String	dbuid	=	"sky";
	private	static	String	dbpwd	=	"1234";
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver); 
		Connection	conn	=	DriverManager.getConnection(dburl, dbuid, dbpwd); // db랑 연결된거임
		
		Statement	stmt	=	conn.createStatement(); // sql 명령을 담을 객체
		String		sql		=	" SELECT ZIPCODE, SIDO, GUGUN, DONG, nvl(BUNJI, ' ') BJ, SEQ "
				+ " FROM ZIPCODE "
				+ " WHERE DONG LIKE '%롯데백화점%'"
				; // sql문
		System.out.println(sql);
		ResultSet	rs		=	stmt.executeQuery(sql); //sql 문의 결과를 보는 객체 ResultSet임, executeQuery가 ctrl + enter 생각하면 됨
		// rs 안에 위의 sql문 의 결과가 들어가는데
		while( rs.next() != false ) { // rs.next() 가 줄이 있을 경우
			System.out.print(rs.getString(1) + ',');
			System.out.print(rs.getString(2)+ ',');
			System.out.print(rs.getString(3)+ ',');
			System.out.print(rs.getString(4)+ ',');
			System.out.print(rs.getString(5)+ ',');
			System.out.print(rs.getInt(6));
			System.out.println();
			/*
			System.out.print(rs.getString("zipcode") + ',');
			System.out.print(rs.getString("sido")+ ',');
			System.out.print(rs.getString("gugun")+ ',');
			System.out.print(rs.getString("dong")+ ',');
			System.out.print(rs.getString("bj")+ ',');
			System.out.print(rs.getInt("seq"));
			System.out.println();
			*/
		} // while end
		
		rs.close(); // rs도 close 해줘야됨
		stmt.close(); // Statment도 close 해줘야됨
		conn.close(); // conn은 꼭 close 해줘야됨

	} // main end

} // class TestZipcode02 end
