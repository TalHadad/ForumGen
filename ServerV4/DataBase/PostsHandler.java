package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.QuestionAnswerPair;
import Server.Forum.SubForum;
import Server.Posts.FollowPost;
import Server.Posts.OpeningPost;

public class PostsHandler extends DataBaseInterface{
	
	
	//SubForum.addOpeningPost
	public boolean addOpeningPost(String forumName ,String SFName, String userName,String id, String title, String content){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			
			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("INSERT INTO ForumSysDB.Posts (id, SFName, Content, Title, "
					+"WriterUserName, ForumName, OpenningThreadId) "
					+"VALUES (?, ?, ?, ?, ?, ? , ?);");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1,id);
			preparedStatement.setString(2,SFName);
			preparedStatement.setString(3,content);
			preparedStatement.setString(4,title);
			preparedStatement.setString(5,userName);
			preparedStatement.setString(6,forumName);
			preparedStatement.setString(7,null);
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

	
	//SubForum.deleteThread
	public boolean deleteOpeningPost(String id) {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// remove
			preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Posts WHERE id= ? OR OpenningThreadId= ?;");
			preparedStatement.setString(1,id);
			preparedStatement.setString(2,id);
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
	
	//SubForum.postFollowPost
	public boolean addFollowPost(String forumName ,String SFName, String userName,String id,String OpenningThreadId, String title, String content){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			
			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("INSERT INTO ForumSysDB.Posts (id, SFName, Content, Title, "
					+"WriterUserName, ForumName, OpenningThreadId) "
					+"VALUES (?, ?, ?, ?, ?, ? , ?);");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1,id);
			preparedStatement.setString(2,SFName);
			preparedStatement.setString(3,content);
			preparedStatement.setString(4,title);
			preparedStatement.setString(5,userName);
			preparedStatement.setString(6,forumName);
			preparedStatement.setString(7,OpenningThreadId);
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
	
	//OpeningPost.deleteResponse
	public boolean deleteFollowPost(String id) {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// remove
			preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Posts WHERE id= ?;");
			preparedStatement.setString(1,id);
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
	public Vector<OpeningPost> getOpeningPosts(String forumName, String subforumName) {
		Vector<OpeningPost> openingPosts = new Vector<OpeningPost>();
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT id,Content,Title,WriterUserName FROM ForumSysDB.Posts WHERE ForumName="+forumName+",SFName="+subforumName+",OpenningThreadId<0;");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				String id = resultSet.getString("id");
				String WriterUserName = resultSet.getString("WriterUserName");
				String Content = resultSet.getString("Content");
				String Title = resultSet.getString("Title");
				
				OpeningPost openingPost = new OpeningPost(id, WriterUserName, Title, Content);
				Vector<FollowPost> followPosts = getFollowPosts(forumName,subforumName,id);
				openingPost.setFollowPosts(followPosts);
					
				openingPosts.add(openingPost);
				
				
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
		return openingPosts;
	}

	//To init Forums list in server
	private Vector<FollowPost> getFollowPosts(String forumName, String subforumName, String id) {
		Vector<FollowPost> followPosts = new Vector<FollowPost>();
		Connection connect=null;
		Statement statement=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT id,Content,Title,WriterUserName FROM ForumSysDB.Posts WHERE ForumName="+forumName+",SFName="+subforumName+",OpenningThreadId="+id+";");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				String id2 = resultSet.getString("id");
				String WriterUserName = resultSet.getString("WriterUserName");
				String Content = resultSet.getString("Content");
				String Title = resultSet.getString("Title");
				
				FollowPost followPost = new FollowPost(id2, WriterUserName, Title, Content);
				followPosts.add(followPost);
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
		return followPosts;
	}
	
	

}
