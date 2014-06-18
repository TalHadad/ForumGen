package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.Forum;
import Server.Forum.ModeratorInfo;
import Server.Forum.Policy;
import Server.Forum.Security;
import Server.Forum.SubForum;
import Server.Posts.FollowPost;
import Server.Users.Member;

public class ModeratorsHandler extends DataBaseInterface{

	//To init Forums list in server
	public Vector<ModeratorInfo> getModerators(String forumName, String subforumName) {
		Vector<ModeratorInfo> moderators = new Vector<ModeratorInfo>();

		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");			
			try {
				Statement statement = connect.createStatement();
				try {
					ResultSet resultSet = statement.executeQuery("SELECT * FROM ForumSysDB.Moderators WHERE ForumName="+forumName+",SFName="+subforumName+";");
					while (resultSet.next()) {
						String UserName = resultSet.getString("UserName");
						String ForumName = resultSet.getString("ForumName");
						String SFName = resultSet.getString("SFName");
						String ApointerUserName = resultSet.getString("ApointerUserName");
						Date ApointDate = resultSet.getDate("ApointDate");

						ModeratorInfo moderatorInfo = new ModeratorInfo(ApointerUserName,UserName,ApointDate);
						moderators.add(moderatorInfo);
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

		return moderators;
	}



	//SubForum.removeModerator
	public boolean deleteModerator(String forumName,String subforumName) {


		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
				Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

				try{
					PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Moderators WHERE ForumName= ?, SFName= ? ;");
					try{
						preparedStatement = connect.prepareStatement("DELETE FROM ForumSysDB.Moderators WHERE ForumName= ?, SFName= ? ;");
						preparedStatement.setString(1,forumName);
						preparedStatement.setString(2,subforumName);
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
