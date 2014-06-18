package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.Forum;
import Server.Forum.Policy;
import Server.Forum.QuestionAnswerPair;
import Server.Forum.Security;
import Server.Forum.SubForum;
import Server.Posts.FollowPost;
import Server.Posts.OpeningPost;
import Server.Users.Member;

public class PostsHandler extends DataBaseInterface{
	
	
	//SubForum.addOpeningPost
	public boolean addOpeningPost(String forumName ,String SFName, String userName,String id, String title, String content){
		
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

		
		try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("INSERT INTO ForumSysDB.Posts (id, SFName, Content, Title, "
					+"WriterUserName, ForumName, OpenningThreadId) "
					+"VALUES (?, ?, ?, ?, ?, ? , ?);");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			try{
				preparedStatement.setString(1,id);
				preparedStatement.setString(2,SFName);
				preparedStatement.setString(3,content);
				preparedStatement.setString(4,title);
				preparedStatement.setString(5,userName);
				preparedStatement.setString(6,forumName);
				preparedStatement.setString(7,null);
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
	
	
	//SubForum.deleteThread
	public boolean deleteOpeningPost(String id) {
		
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
				PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Posts WHERE id= ? OR OpenningThreadId= ?;");
			try{
				preparedStatement.setString(1,id);
				preparedStatement.setString(2,id);
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
		

	
	//SubForum.postFollowPost
	public boolean addFollowPost(String forumName ,String SFName, String userName,String id,String OpenningThreadId, String title, String content){
		
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("INSERT INTO ForumSysDB.Posts (id, SFName, Content, Title, "
					+"WriterUserName, ForumName, OpenningThreadId) "
					+"VALUES (?, ?, ?, ?, ?, ? , ?);");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			try{
				preparedStatement.setString(1,id);
				preparedStatement.setString(2,SFName);
				preparedStatement.setString(3,content);
				preparedStatement.setString(4,title);
				preparedStatement.setString(5,userName);
				preparedStatement.setString(6,forumName);
				preparedStatement.setString(7,OpenningThreadId);
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
		
	
	//OpeningPost.deleteResponse
	public boolean deleteFollowPost(String id) {
		
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
				PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Posts WHERE id= ?;");
			try{
				preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Posts WHERE id= ?;");
				preparedStatement.setString(1,id);
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
	public Vector<OpeningPost> getOpeningPosts(String forumName, String subforumName) {
		Vector<OpeningPost> openingPosts = new Vector<OpeningPost>();
		
		
		
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
					ResultSet resultSet = statement.executeQuery("SELECT id,Content,Title,WriterUserName FROM ForumSysDB.Posts WHERE ForumName="+forumName+",SFName="+subforumName+",OpenningThreadId<0;");
					while (resultSet.next()) {
						String id = resultSet.getString("id");
						String WriterUserName = resultSet.getString("WriterUserName");
						String Content = resultSet.getString("Content");
						String Title = resultSet.getString("Title");
						
						OpeningPost openingPost = new OpeningPost(id, WriterUserName, Title, Content);
						Vector<FollowPost> followPosts = getFollowPosts(forumName,subforumName,id);
						openingPost.setFollowPosts(followPosts);
							
						openingPosts.add(openingPost);
						
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
		
		return openingPosts;
}


	//To init Forums list in server
	private Vector<FollowPost> getFollowPosts(String forumName, String subforumName, String id) {
		Vector<FollowPost> followPosts = new Vector<FollowPost>();
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
					ResultSet resultSet = statement.executeQuery("SELECT id,Content,Title,WriterUserName FROM ForumSysDB.Posts WHERE ForumName="+forumName+",SFName="+subforumName+",OpenningThreadId="+id+";");
					while (resultSet.next()) {
						String id2 = resultSet.getString("id");
						String WriterUserName = resultSet.getString("WriterUserName");
						String Content = resultSet.getString("Content");
						String Title = resultSet.getString("Title");
						
						FollowPost followPost = new FollowPost(id2, WriterUserName, Title, Content);
						followPosts.add(followPost);
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
		
		return followPosts;
	}}
