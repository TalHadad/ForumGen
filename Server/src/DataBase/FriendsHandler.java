package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.SubForum;
import Server.Posts.OpeningPost;
import Server.Users.Member;

public class FriendsHandler extends DataBaseInterface{

	//To init Forums list in server
	public Vector<String> getFriends(String forumname, String userName) {
		Vector<String> friends = new Vector<String>();
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("SELECT UserName2 FROM ForumSysDB.Members WHERE UserName1=?,ForumName=?;");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			try{
				preparedStatement.setString(1, userName);
				preparedStatement.setString(2, forumname);
				ResultSet resultSet=preparedStatement.executeQuery();
				//Extract data from result set
				while(resultSet.next()){
					//Retrieve by column name
					
					String UserName2 = resultSet.getString("UserName2");
					friends.add(UserName2);
				}
			}finally{
				preparedStatement.close();
			}
			}finally{
				connect.close();
			}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
			}
	catch (Exception e) {
		System.out.println(e.getMessage());
		return null;
	}
		return friends;
	}	
		
}		

