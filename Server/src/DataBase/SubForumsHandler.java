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
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("INSERT INTO SubForums (SFName,SFsubject,ForumName) "+
					"VALUES (?, ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			try{
				preparedStatement.setString(1,SFName);
				preparedStatement.setString(2,SFsubject);
				preparedStatement.setString(3,ForumName);
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
	public Vector<SubForum> getSubForums(Forum forum) {
		Vector<SubForum> subForums = new Vector<SubForum>();
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
					ResultSet resultSet = statement.executeQuery("SELECT * FROM ForumSysDB.SubForum WHERE ForumName="+forum.getName()+";");
					while (resultSet.next()) {
						String SFName = resultSet.getString("SFName");
						String SFsubject = resultSet.getString("SFsubject");

						SubForum subForum = new SubForum(forum,SFName,SFsubject);
						Vector<OpeningPost> openingPosts = this.postsHandler.getOpeningPosts(forum.getName(),SFName);
						Vector<ModeratorInfo> moderatorInfo = this.moderatorsHandler.getModerators(forum.getName(),SFName);
						subForum.setOpeningPosts(openingPosts);
						subForum.setModerators(moderatorInfo);
						subForums.add(subForum);
						
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
		
		return subForums;
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
				PreparedStatement preparedStatement = connect.prepareStatement("delete from ForumSysDB.SubForums where ForumName= ? ; ");
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