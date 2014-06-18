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
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM ForumSysDB.Securities WHERE ForumName='"+forumName+"';");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				String UserName = resultSet.getString("UserName");
				String UPassWord = resultSet.getString("UPassWord");
				String ReminderQues = resultSet.getString("ReminderQues");
				String ReminderAns = resultSet.getString("ReminderAns");
				
				QuestionAnswerPair questionAnswerPair = new QuestionAnswerPair (ReminderQues,ReminderAns);
				security.addUserSecurity(UserName,forumName, UPassWord, questionAnswerPair);

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
		return security;
	}


	
	//UserSecurity.setPassword
	public boolean setPassword (String memberName, String forumName, String newPass){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("update ForumSysDB.Securities set UPassWord = ? where ForumName = ?, UserName= ?;");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1,newPass);
			preparedStatement.setString(2,forumName);
			preparedStatement.setString(2,memberName);
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

	//Security.addUserSecurity**NOT
	//MembersHandler.regiserToForum
	//MembersHandler.addMember**NOT
	public boolean addSecurity(String userName, String forumName,
			String userPassword, QuestionAnswerPair questionAnswerPair) {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			
			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("INSERT INTO Securities (UserName,ForumName,UPassWord,ReminderQues,ReminderAns) "+
			"VALUES (?, ?, ?, ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,forumName);
			preparedStatement.setString(3,userPassword);
			preparedStatement.setString(4,questionAnswerPair.getQuestions());
			preparedStatement.setString(5,questionAnswerPair.getAnswers());
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


	
	//ForumsHandler.deleteForum
	public boolean deleteForum(String forumName) {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// remove
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.Securities where ForumName= ? ; ");
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