package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.Forum;
import Server.Forum.Policy;
import Server.Forum.QuestionAnswerPair;
import Server.Forum.Security;
import Server.Forum.SubForum;
import Server.Posts.OpeningPost;
import Server.Users.Member;

public class SecurityHandler extends DataBaseInterface{

	
	//To init Forums list in server
	public Security getSecurity(String forumName) {
		Security security = new Security();
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
					ResultSet resultSet = statement.executeQuery("SELECT * FROM ForumSysDB.Securities WHERE ForumName='"+forumName+"';");
					while(resultSet.next()){
						//Retrieve by column name
						String UserName = resultSet.getString("UserName");
						String UPassWord = resultSet.getString("UPassWord");
						String ReminderQues = resultSet.getString("ReminderQues");
						String ReminderAns = resultSet.getString("ReminderAns");
						
						QuestionAnswerPair questionAnswerPair = new QuestionAnswerPair (ReminderQues,ReminderAns);
						security.addUserSecurity(UserName,forumName, UPassWord, questionAnswerPair);

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
		
		return security;
}
		
	
	//UserSecurity.setPassword
	public boolean setPassword (String memberName, String forumName, String newPass){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("update ForumSysDB.Securities set UPassWord = ? where ForumName = ?, UserName= ?;");
			try{
				preparedStatement.setString(1,newPass);
				preparedStatement.setString(2,forumName);
				preparedStatement.setString(2,memberName);
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
	//Security.addUserSecurity**NOT
	//MembersHandler.regiserToForum
	//MembersHandler.addMember**NOT
	public boolean addSecurity(String userName, String forumName,
			String userPassword, QuestionAnswerPair questionAnswerPair) {

		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("INSERT INTO Securities (UserName,ForumName,UPassWord,ReminderQues,ReminderAns) "+
					"VALUES (?, ?, ?, ?, ?)");
			try{
				preparedStatement.setString(1,userName);
				preparedStatement.setString(2,forumName);
				preparedStatement.setString(3,userPassword);
				preparedStatement.setString(4,questionAnswerPair.getQuestions());
				preparedStatement.setString(5,questionAnswerPair.getAnswers());
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