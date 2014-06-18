package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.Forum;
import Server.Forum.ModeratorInfo;
import Server.Forum.Policy;
import Server.Forum.Security;
import Server.Forum.SubForum;
import Server.Posts.OpeningPost;
import Server.Users.Member;

public class SubForumsHandler extends DataBaseInterface{
	
	private PostsHandler postsHandler;
	private MembersHandler membersHandler;
	private ModeratorsHandler moderatorsHandler;
	
	public SubForumsHandler() {
		super();
		this.postsHandler = new PostsHandler();
		this.membersHandler = new MembersHandler();
		this.moderatorsHandler = new ModeratorsHandler();
	}
	
	//Forum.addSubForum
	public boolean addSubForum (String SFName,String SFsubject,String ForumName){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			
			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("INSERT INTO SubForums (SFName,SFsubject,ForumName) "+
			"VALUES (?, ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1,SFName);
			preparedStatement.setString(2,SFsubject);
			preparedStatement.setString(3,ForumName);
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
	public Vector<SubForum> getSubForums(Forum forum) {
		Vector<SubForum> subForums = new Vector<SubForum>();
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM ForumSysDB.SubForum WHERE ForumName="+forum.getName()+";");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				String SFName = resultSet.getString("SFName");
				String SFsubject = resultSet.getString("SFsubject");

				SubForum subForum = new SubForum(forum,SFName,SFsubject);
				Vector<OpeningPost> openingPosts = this.postsHandler.getOpeningPosts(forum.getName(),SFName);
				Vector<ModeratorInfo> moderatorInfo = this.moderatorsHandler.getModerators(forum.getName(),SFName);
				subForum.setOpeningPosts(openingPosts);
				subForum.setModerators(moderatorInfo);
				subForums.add(subForum);
				
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
		return subForums;
	}

	//ForumsHandler.deleteForum
	public boolean deleteForum(String forumName) {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// remove
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.SubForums where ForumName= ? ; ");
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