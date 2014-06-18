package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.ModeratorInfo;
import Server.Posts.FollowPost;

public class ModeratorsHandler extends DataBaseInterface{

	//To init Forums list in server
	public Vector<ModeratorInfo> getModerators(String forumName, String subforumName) {
		Vector<ModeratorInfo> moderators = new Vector<ModeratorInfo>();
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM ForumSysDB.Moderators WHERE ForumName="+forumName+",SFName="+subforumName+";");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				String UserName = resultSet.getString("UserName");
				String ForumName = resultSet.getString("ForumName");
				String SFName = resultSet.getString("SFName");
				String ApointerUserName = resultSet.getString("ApointerUserName");
				Date ApointDate = resultSet.getDate("ApointDate");
				
				ModeratorInfo moderatorInfo = new ModeratorInfo(ApointerUserName,UserName,ApointDate);
				moderators.add(moderatorInfo);
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
		return moderators;
	}
		
	//SubForum.removeModerator
	public boolean deleteModerator(String forumName,String subforumName) {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// remove
			preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Moderators WHERE ForumName= ?, SFName= ? ;");
			preparedStatement.setString(1,forumName);
			preparedStatement.setString(2,subforumName);
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
