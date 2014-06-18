package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.Forum;
import Server.Forum.ModeratorInfo;
import Server.Forum.Policy;
import Server.Forum.QuestionAnswerPair;
import Server.Forum.Security;
import Server.Forum.SubForum;
import Server.Posts.FollowPost;
import Server.Posts.OpeningPost;
import Server.Users.Member;

public class MembersHandler extends DataBaseInterface{

	private SecurityHandler securityHandler;
	private FriendsHandler friendsHandler;
	
	
	public MembersHandler() {
		super();
		this.securityHandler = new SecurityHandler();
		this.friendsHandler = new FriendsHandler();
		
	}

	//Forum.regiserToForum
	public boolean addMember(String forumName ,String userName, String userPassword, String eMail,QuestionAnswerPair questionAnswerPair,String MemType,boolean IsAdmin,boolean IsPending){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			
			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("INSERT INTO ForumSysDB.Members (UserName,ForumName,Email,MemType,IsAdmin,IsPending) "
					+"VALUES (?, ?, ?, ?, ?, ?);");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,forumName);
			preparedStatement.setString(3,eMail);
			preparedStatement.setString(4,MemType);
			preparedStatement.setBoolean(5,IsAdmin);
			preparedStatement.setBoolean(6,IsPending);
			preparedStatement.executeUpdate();
			
			this.securityHandler.addSecurity(userName, forumName, userPassword, questionAnswerPair);

		} catch (Exception e) {
			System.out.println("faild to execute query");
			return false;
		} finally {
			//      close();
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				System.out.println("faild to close connection with data base");
				return false;
			}
			
		}
		return true;
	}
	
	//Forum.addAdmin
	public boolean setAdmin (String forumName ,String userName){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("update ForumSysDB.Members set IsAdmin = ? where UserName= ?, ForumName= ? ;");
			
			// parameters start with 1
			preparedStatement.setBoolean(1, true);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,forumName);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.out.println("faild to execute query");
			return false;
		} finally {
			//      close();
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				System.out.println("faild to close connection with data base");
				return false;
			}
			
		}
		return true;
	}
	
	//To init Forums list in server
	public Vector<Member> getMembers(Forum forum) {
		Vector<Member> members = new Vector<Member>();
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM ForumSysDB.Members WHERE ForumName="+forum.getName()+";");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				
				String UserName = resultSet.getString("UserName");
				String MemType = resultSet.getString("MemType");
				String eMail = resultSet.getString("Email");
				boolean IsPending = resultSet.getBoolean("IsPending"); 
				Vector<String> friends = this.friendsHandler.getFriends(forum.getName(),UserName);
				
				Member member = new Member(UserName,forum,eMail);
				member.setType(MemType);
				member.setFriends(friends);
				if (IsPending)
					member.setStatus(Configuration.Configuration.PENDING);
				
				members.add(member);
			}
		} catch (Exception e) {
			System.out.println("faild to execute query");
			return null;
		} finally {
			//      close();
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				System.out.println("faild to close connection with data base");
				return null;
			}
			
		}
		return members;
	}

	//To init Forums list in server
	public Vector<String> getAdmins(String forumname) {
		Vector<String> admins = new Vector<String>();
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT UserName FROM ForumSysDB.Members WHERE ForumName="+forumname+",IsAdmin=true;");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				
				String UserName = resultSet.getString("UserName");
				admins.add(UserName);
				
			}
		} catch (Exception e) {
			System.out.println("faild to execute query");
			return null;
		} finally {
			//      close();
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				System.out.println("faild to close connection with data base");
				return null;
			}
			
		}
		return admins;
	}
	
	//ForumsHandler.deleteForum
	public boolean deleteForum(String forumName) {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// remove
			preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Members WHERE ForumName= ? ;");
			preparedStatement.setString(1,forumName);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.out.println("faild to execute query");
			return false;
		} finally {
			//      close();
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				System.out.println("faild to close connection with data base");
				return false;
			}
			
		}
		return true;
	}
}
