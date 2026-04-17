package solo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			TUserDTO		tuser		= null;
			int				aftcnt      ;
			//String			upid		;
			switch (choice) {
			case	"1": // 회원목록 
				ArrayList<TUserDTO>	userList	=	getTUserList(); // userList는 길이가 정해지지않은 arraylist이고, 그안에는 TUserDTO type이 들어가는 거임 
				displayList(userList);
				break; 
			case	"2": // 회원조회(아이디로 조회) 
				System.out.println("조회할 아이디를 입력하세요 : ");
				String		uid			=	in.nextLine();
				tuser		=	getTUser(uid);
				//System.out.println(tuser.toString());
				display(tuser);
				break; 
			case	"3": // 회원추가
				tuser		=	inputData();
				aftcnt		=	addTUser(tuser);
				System.out.println(aftcnt + "건 저장되었습니다.");
				break; 
			case	"4": // 회원수정, 3항목 모두 수정, if 문으로 항목별로 수정 사항이 있는지 확인하고 하나씩 수정, 입력시 수정 없는 항목은 enter 
				/*
				System.out.println("수정할 아이디를 입력하세요 : ");
				upid		=	in.nextLine();
				tuser		=	inputData();
				aftcnt		=	updateTUser(tuser);
				System.out.println(aftcnt + "건 수정되었습니다.");
				*/
				break; 
			case	"5": // 회원삭제
				System.out.println("삭제할 아이디를 입력하세요 : ");
				String		did		=	in.nextLine();
				aftcnt	=	deleteUser(did);
				System.out.println(aftcnt + "건 삭제되었습니다.");
				break; 
			case	"q": // 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				break; 
			}
		} while (true); // 무한반복 : 무한 루프
		

	} // main end

	//-----------------------------------------------------------------------
	// 4. 회원 수정,id로 where문 조건 대입, if 문으로 항목별로 수정 사항이 있는지 확인하고 하나씩 수정, 입력시 수정 없는 항목은 enter
	// UPDATE  TUSER SET email = 'SKY1@naver.com' WHERE   userid = (?)
	/*
	private static int updateTUser(TUserDTO tuser) throws ClassNotFoundException, SQLException {
		// 입력된 정보 확인
		
		Class.forName(driver);
		Connection			conn		=	DriverManager.getConnection(dburl, dbuid, dbpwd);
		if (tuser.getUserid() != null) {
			String				sql			= "";
			sql 							+= "UPDATE  TUSER SET userid = (?) WHERE   userid = (?) ";
			PreparedStatement 	pstmt		= conn.prepareStatement(sql);	
			pstmt.setString(1, tuser.getUserid());
			//pstmt.setString(2, upid);
		}
		String				sql			= "";
		sql 							+= "UPDATE  TUSER SET userid = (?) WHERE   userid = (?) ";
		PreparedStatement 	pstmt		= conn.prepareStatement(sql);	
		pstmt.setString(1, tuser.getUserid());
		pstmt.setString(2, tuser.getUsername());
		pstmt.setString(3, tuser.getEmail());
		int					aftcnt		= pstmt.executeUpdate();
				
		pstmt.close();
		conn.close();
		
		return			aftcnt;

	}
	*/
	//-----------------------------------------------------------------------
	// 1. 목록 조회 db에서
	private static ArrayList<TUserDTO> getTUserList() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection	conn				=	DriverManager.getConnection(dburl, dbuid, dbpwd);
		String			sql				=	" SELECT * FROM TUSER ";
		sql								+=	" ORDER BY USERID ASC ";
		PreparedStatement	pstmt		=	conn.prepareStatement(sql);
		ResultSet			rs			=	pstmt.executeQuery();
		
		ArrayList<TUserDTO>	userList	=	new	ArrayList<>();
		
		while (rs.next()) {
			String		userid			=	rs.getString("userid");
			String		username		=	rs.getString("username");
			String		email			=	rs.getString("email");
			TUserDTO	tuser			=	new	TUserDTO(userid, username, email);
			userList.add(tuser);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return userList;
	} // getTUserList() end
	
	// 2. 입력받은 아이디로 db에서 한 줄을 조회한다
	private static TUserDTO getTUser(String uid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection	conn		=	DriverManager.getConnection(dburl, dbuid, dbpwd);
		String			sql		=	"";
		
		sql						+=	"SELECT * FROM TUSER WHERE USERID = ? ";
		PreparedStatement	pstmt		=	conn.prepareStatement(sql);
		pstmt.setString(1, uid.toUpperCase());
		
		ResultSet		rs		=	pstmt.executeQuery();
		TUserDTO		tuser = null	; // 변수생성이 조건문 밖이고, 조건문안에서 변수인자라 들어가는 구조이면 변수생성시 초기화 무조건 해줘야 됨 
		if (rs.next()) { // 해당 자료가 있는 경우
			String		userid			=	rs.getString("userid");
			String		username		=	rs.getString("username");
			String		email			=	rs.getString("email");
			tuser						=	new	TUserDTO(userid, username, email);	
		} else { // 해당 자료가 없는 경우 : primary key
			
		} // else end
		
		rs.close();
		pstmt.close();
		conn.close();
		return tuser;
	} // getTUser() end

	// 3. db에 insert 한다
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
	} // addTUser() end
	
	// 5. 회원 삭제
		private static int deleteUser(String	did) throws ClassNotFoundException, SQLException {
			Class.forName(driver);
			Connection			conn		=	DriverManager.getConnection(dburl, dbuid, dbpwd);
			
			String				sql			= "";
			sql 							+= "DELETE FROM TUSER WHERE UPPER(userid) = (?) ";
			PreparedStatement 	pstmt		= conn.prepareStatement(sql);	
			pstmt.setString(1, did.toUpperCase());
			int					aftcnt		= pstmt.executeUpdate();
					
			pstmt.close();
			conn.close();
			
			return			aftcnt;
		}
	//-----------------------------------------------------------------------
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
	} //inputData() end
	
	// TUser한줄을 출력한다
	private static void display(TUserDTO tuser) {
		if (tuser == null) {
			System.out.println("조회한 자료가 없습니다.");
		} // if end
		else {
			String		msg		=	String.format("%s %s %s",
			tuser.getUserid(), tuser.getUsername(), tuser.getEmail());
			System.out.println(msg);
		} // else end
		System.out.print("Press enter key..");
		in.nextLine();
	} // display() end

	// 전체 목록을 출력한다
	private static void displayList(ArrayList<TUserDTO> userList) {
		
		if (userList.size()==0) {
			System.out.println("조회한 자료가 없습니다.");
			return;
		}
		
		String			fmt			=	"";
		String			msg			=	"";
		for (TUserDTO tuser : userList) {
			String		userid		=	tuser.getUserid();
			String		username	=	tuser.getUsername();
			String		email		=	tuser.getEmail();
			
			msg	=	"""
					%s %s %s
					""".formatted(userid, username, email); // java template 문자열
					
			System.out.print(msg);
		}
		System.out.print("Press enter key..");
		in.nextLine();
	} // displayList() end

} // class TestTuser end
