package db03;
/*
아이디			문자(20)		필수입력	중복방지
이름			문자(30)		필수입력	
이메일			문자(320)		필수입력	중복방지
 */
public class TUserDTO { // oracle select한걸 들고와서 사용하기 위해 만든거임
	//fields
	private	String	userid;
	private	String	username;
	private String	email;
	//constructor
	public TUserDTO()	{}
	public TUserDTO(String userid, String username, String email) {
		this.userid = userid;
		this.username = username;
		this.email = email;
	}
	//Getter Setter
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	//toString
	@Override
	public String toString() {
		return "TUser [userid=" + userid + ", username=" + username + ", email=" + email + "]";
	}
} // class TUser end
