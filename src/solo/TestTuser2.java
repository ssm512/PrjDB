// 모든 라인에 주석달기

package solo; // 이 클래스가 solo 라는 패키지에 속함 

import java.sql.Connection; // DB 연결 클래스 import
import java.sql.DriverManager;	// DB 연결 생성 클래스 import
import java.sql.PreparedStatement; // SQL prepared실행 객체 클래스 import
import java.sql.ResultSet;	// 조회 결과 저장 클래스 import
import java.sql.SQLException;	// db 에러 처리 클래스 import
import java.util.ArrayList;	//arraylist 클래스 import
import java.util.Scanner;	// 키보드 입력 클래스 import

public class TestTuser2 {	// 접근제한자가 public인 클래스 TestTuser2 
	// 연결문자열 
	private	static	String	driver		=	"oracle.jdbc.OracleDriver";	// 현 클래스 내부에서 사용할 수 있는 오라클 드라이버 
	private	static	String	dburl		=	"jdbc:oracle:thin:@127.0.0.1:1521:xe"; // db 접속 주소
	private	static	String	dbuid		=	"sky";
	private	static	String	dbpwd		=	"1234";
	
	static Scanner			in			=	new Scanner(System.in); // 프로그램 전체에서 사용할 키보드 입력 객체
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException { // main 메서드 프로그램의 시작점, 예외 exception처리를 main에서 함
		// CRUD example, Create, Read, Update, Delete
		do { // 무한 반복 시작 
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
			String			choice		=	in.nextLine(); // 사용자의 입력을 받음, 어떤 기능을 수행할지
			TUserDTO		tuser		= null; // TUserDTO type의 tuser instance를 생성하고, 초기화함
			int				aftcnt      ;
			
			switch (choice) { // switch 분기 어느 기능을 수행 할지
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

	//-----------------------------------------------------------------------
	// 1. 목록 조회 db에서
	private static ArrayList<TUserDTO> getTUserList() throws ClassNotFoundException, SQLException { // 목록 전체 조회
		Class.forName(driver);// oracle jdbc driver 로딩
		Connection	conn				=	DriverManager.getConnection(dburl, dbuid, dbpwd); // db 연결 생성
		String			sql				=	" SELECT * FROM TUSER ";
		sql								+=	" ORDER BY USERID ASC "; // sequal 문 작성
		PreparedStatement	pstmt		=	conn.prepareStatement(sql); // sql 문 실행 준비
		ResultSet			rs			=	pstmt.executeQuery(); // select문 실핼 후 결과를 rs instance에 받음
		
		ArrayList<TUserDTO>	userList	=	new	ArrayList<>(); // TUserDTO type의 결과를 담을 arraylist userList를 생성함
		
		while (rs.next()) { // rs의 한행씩을 반복할 거다
			String		userid			=	rs.getString("userid");
			String		username		=	rs.getString("username");
			String		email			=	rs.getString("email"); // 각각의 칼럼의 값들을 꺼냄
			TUserDTO	tuser			=	new	TUserDTO(userid, username, email); //TUserDTO type의 instance tuser를 생성하고 칼럼에서 꺼낸 값들을 받음
			userList.add(tuser); //tuser 값을 userList에 추가함
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return userList; // 결과 반환
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
