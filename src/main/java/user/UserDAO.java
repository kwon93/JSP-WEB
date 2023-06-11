package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//DAO란 데이터베이스에 접근해서 데이터를 가져오는 클래스 (데이터 접근 객체)
public class UserDAO {
	private Connection conn; // 커넥션은 데이터베이스에게 접근을 해주는 객체를 의미한다.
	private PreparedStatement pstmt;
	private	ResultSet rs;
	
	public UserDAO() {
		try {
			
			String dbURL = "jdbc:mysql://localhost:3306/BBS"; //나의 컴퓨터 3306포트로 데이터베이스 BBS로 접속하게함.
			String dbID = "root"; // sql 아이디
			String dbPassword = "rnjs73030862"; //sql 비밀번호
			Class.forName("com.mysql.jdbc.Driver"); //드라이버란 sql에 접속할 수 있도록 매개체 역할을하는 라이브러리.
			conn = DriverManager.getConnection(dbURL,dbID,dbPassword); //conn 객체안에 접속된 정보가 담기게된다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) { //매개변수로 들어온 userID를
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);//pstmt.setString을 통해서 위에 물음표로 넘어간다.
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) { //위에 SQL 쿼리문 수행으로 나온 패스워드결과가 매개변수 패스워드와 같다면
					return 1; //로그인 성공.
				}else {
					return -0; // 비번 불일치.
				}
			}
			return -1; //아이디가 없음
			
		} catch (Exception e) {
			e.setStackTrace(null);
		}
		return -2; // -1이란 데이터베이스 오류를 뜻함.
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (? , ? , ? , ? ,?);";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}
}
