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
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT UserName2 FROM ForumSysDB.Members WHERE UserName1="+userName+",ForumName="+forumname+";");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				
				String UserName2 = resultSet.getString("UserName2");
				friends.add(UserName2);
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
		return friends;
	}
}
