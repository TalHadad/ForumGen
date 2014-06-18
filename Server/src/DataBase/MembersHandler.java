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

	public boolean addMember(String forumName ,String userName, String userPassword, String eMail,QuestionAnswerPair questionAnswerPair,String MemType,boolean IsAdmin,boolean IsPending){

		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
				Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

				try{
					// preparedStatements can use variables and are more efficient
					PreparedStatement preparedStatement =  connect.prepareStatement("INSERT INTO ForumSysDB.Members (UserName,ForumName,Email,MemType,IsAdmin,IsPending) "
							+"VALUES (?, ?, ?, ?, ?, ?);");

					try{
						preparedStatement.setString(1,userName);
						preparedStatement.setString(2,forumName);
						preparedStatement.setString(3,eMail);
						preparedStatement.setString(4,MemType);
						preparedStatement.setBoolean(5,IsAdmin);
						preparedStatement.setBoolean(6,IsPending);
						preparedStatement.executeUpdate();

						this.securityHandler.addSecurity(userName, forumName, userPassword, questionAnswerPair);
					}finally{
						preparedStatement.close();
					}
				}finally{
					connect.close();
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}



	//Forum.addAdmin
	public boolean setAdmin (String forumName ,String userName){

		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
				Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

				try{
					// preparedStatements can use variables and are more efficient
					PreparedStatement preparedStatement =  connect.prepareStatement("update ForumSysDB.Members set IsAdmin = ? where UserName= ?, ForumName= ? ;");
					try{
						preparedStatement.setBoolean(1, true);
						preparedStatement.setString(2,userName);
						preparedStatement.setString(3,forumName);
						preparedStatement.executeUpdate();

					}finally{
						preparedStatement.close();
					}
				}finally{
					connect.close();
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}



	//To init Forums list in server
	public Vector<Member> getMembers(Forum forum) {
		Vector<Member> members = new Vector<Member>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		// setup the connection with the DB.
		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");			
			try {
				Statement statement = connect.createStatement();
				try {
					ResultSet resultSet = statement.executeQuery("SELECT * FROM ForumSysDB.Members WHERE ForumName="+forum.getName()+";");
					while (resultSet.next()) {
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
				} finally {
				statement.close();
				}
				} finally {
				connect.close();
				}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		return null;
		}
		return members;
}
		
	
	//To init Forums list in server
	public Vector<String> getAdmins(String forumname) {
		Vector<String> admins = new Vector<String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		// setup the connection with the DB.
		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");			
			try {
				Statement statement = connect.createStatement();
				try {
					ResultSet resultSet = statement.executeQuery("SELECT UserName FROM ForumSysDB.Members WHERE ForumName="+forumname+",IsAdmin=true;");
					while (resultSet.next()) {
						
						String UserName = resultSet.getString("UserName");
						admins.add(UserName);

						}
				} finally {
				statement.close();
				}
				} finally {
				connect.close();
				}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		return admins;
}
		

	//ForumsHandler.deleteForum
	public boolean deleteForum(String forumName) {
		
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
				PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Members WHERE ForumName= ? ;");		
			try{
				preparedStatement.setString(1,forumName);
				preparedStatement.executeUpdate();
			}finally{
				preparedStatement.close();
			}
			}finally{
				connect.close();
			}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			}
	catch (Exception e) {
		System.out.println(e.getMessage());
		return false;
	}
		return true;
	}
		
}
