package db03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TestTuser {
	// 연결문자열 
	private	static	String	driver		=	"oracle.jdbc.OracleDriver";
	private	static	String	dburl		=	"jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private	static	String	dbuid		=	"sky";
	private	static	String	dbpwd		=	"1234";
	
	static Scanner			in			=	new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// CRUD example, Create, Read, Update, Delete
		do {
			// 화면출력
			System.out.println("===============================");
			System.out.println("            회원정보           ");
			System.out.println("===============================");
			System.out.println("1. 회원 목록");
			System.out.println("2. 회원 조회");
			System.out.println("3. 회원 추가");
			System.out.println("4. 회원 수정");
			System.out.println("5. 회원 삭제");
			System.out.println("Q. 종료");
			
			System.out.println("선택 : ");
			String			choice		=	in.nextLine();
			
			switch (choice) {
			case	"1": // 회원목록 
				break; 
			case	"2": // 회원조회 
				break; 
			case	"3": // 회원추가
				TUserDTO	tuser		=	inputData();
				int			aftcnt		=	addTUser(tuser);
				System.out.println(aftcnt + "건 저장되었습니다.");
				break; 
			case	"4": // 회원수정
				break; 
			case	"5": // 회원삭제
				break; 
			case	"q": // 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				break; 
			}
		} while (true); // 무한반복 : 무한 루프
		

	} // main end
	
	// db에 insert 한다
	private static int addTUser(TUserDTO tuser) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection			conn		=	DriverManager.getConnection(dburl, dbuid, dbpwd);
		
		String				sql			= "";
		sql 							+= "INSERT INTO TUSER VALUES (?, ?, ?) ";
		PreparedStatement 	pstmt		= conn.prepareStatement(sql);	
		pstmt.setString(1, tuser.getUserid());
		pstmt.setString(2, tuser.getUsername());
		pstmt.setString(3, tuser.getEmail());
		int					aftcnt		= pstmt.executeUpdate();
				
		pstmt.close();
		conn.close();
		
		return			aftcnt;
	}

	// oracle db에 저장할 데이터를 키보드로 입력 받는다
	private static TUserDTO inputData() {
		System.out.println("아이디 : ");
		String		userid		=	in.nextLine();
		System.out.println("이름 : ");
		String		username	=	in.nextLine();
		System.out.println("이메일 : ");
		String		email		=	in.nextLine();
		TUserDTO	tuser		=	new	TUserDTO(userid, username, email);
		return		tuser;
	}

} // class TestTuser end
