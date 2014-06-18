package DataBase;
import java.sql.*;
import java.util.Vector;

import Server.Forum.*;
import Server.Users.Member;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ForumsHandler extends DataBaseInterface{
	
	SecurityHandler securityHandler;
	MembersHandler membersHandler;
	SubForumsHandler subForumsHandler;
	
	

	public ForumsHandler() {
		super();
		this.securityHandler = new SecurityHandler();
		this.membersHandler = new MembersHandler();
		this.subForumsHandler = new SubForumsHandler();
	}

	//ForumGen.createForum
	public boolean addForum(String ForumName, boolean HasEmailConfirmPolicy, boolean HasExtandedDeletePolicy, 
			int MinMonthsForMod, int MinPostsForMod, boolean OnlyApointerCanRemoveMod, 
			boolean CanRemoveSingleMod,  int MonthsPasswordValidFor){
		
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			
			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("INSERT INTO ForumSysDB.Forums (ForumName, HasEmailConfirmPolicy, HasExtandedDeletePolicy,"
					+ " MinMonthsForMod, MinPostsForMod, OnlyApointerCanRemoveMod, CanRemoveSingleMod,"
					+" MonthsPasswordValidFor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1,ForumName);
			preparedStatement.setBoolean(2,HasEmailConfirmPolicy);
			preparedStatement.setBoolean(3,HasExtandedDeletePolicy);
			preparedStatement.setInt(4,MinMonthsForMod);
			preparedStatement.setInt(5,MinPostsForMod);
			preparedStatement.setBoolean(6,OnlyApointerCanRemoveMod);
			preparedStatement.setBoolean(7,CanRemoveSingleMod);
			preparedStatement.setInt(8,MonthsPasswordValidFor);
			preparedStatement.executeUpdate();
			

			System.out.println("######## Forum added seccessfuly");

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

	//ForumGen
	public boolean deleteForum (String forumName){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// remove
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.Forums where ForumName= ? ; ");
			preparedStatement.setString(1,forumName);
			preparedStatement.executeUpdate();
			

			System.out.println("########### Forum deleted seccessfuly");

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
	public Vector<Forum> getForums(){
		Vector<Forum> forums = new Vector<Forum>();
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from ForumSysDB.Forums");
			
			//Extract data from result set
			while(resultSet.next()){
				//Retrieve by column name
				String forumName = resultSet.getString("ForumName");
				boolean hasEmailConfirmPolicy = resultSet.getBoolean("HasEmailConfirmPolicy");
				boolean hasExtandedDeletePolicy = resultSet.getBoolean("HasExtandedDeletePolicy"); 
				boolean onlyApointerCanRemoveMod = resultSet.getBoolean("OnlyApointerCanRemoveMod");
				boolean canRemoveSingleMod = resultSet.getBoolean("CanRemoveSingleMod");
				int minMonthsForMod = resultSet.getInt("MinMonthsForMod");
				int minPostsForMod = resultSet.getInt("MinPostsForMod");
				String InteractiveNotifyingPolicys = resultSet.getString("InteractiveNotifyingPolicys");
				int monthsPasswordValidFor = resultSet.getInt("MonthsPasswordValidFor");

				Security security = this.securityHandler.getSecurity(forumName);
				Policy policy = new Policy (hasEmailConfirmPolicy, hasExtandedDeletePolicy,minPostsForMod ,minMonthsForMod , onlyApointerCanRemoveMod,canRemoveSingleMod,monthsPasswordValidFor,InteractiveNotifyingPolicys);
				Forum forum = new Forum(forumName,null, null, policy,null, security, null);
				
				Vector<SubForum> subForums = this.subForumsHandler.getSubForums(forum);
				forum.setSubForums(subForums);
				
				Vector<Member> members = this.membersHandler.getMembers(forum);
				forum.setMembers(members);
				
				Vector<String> admins = this.membersHandler.getAdmins(forumName);
				forum.setAdmins(admins);
				
				Vector<String> statuses = new Vector<String>();
				forum.setStatuses(statuses);
			
				forums.add(forum);
				
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
		return forums;

	}
	
	//Forum
	public boolean setName (String oldForumName,String newForumName){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("update ForumSysDB.Forums set ForumName = ? where ForumName = ?;");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setString(1,newForumName);
			preparedStatement.setString(2,oldForumName);
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
	
	//Forum
	public boolean setPolicy (String forumName, Policy policy){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			// preparedStatements can use variables and are more efficient
			preparedStatement =  connect.prepareStatement("update ForumSysDB.Forums set HasEmailConfirmPolicy= ? "
			+"HasExtandedDeletePolicy= ? MinMonthsForMod= ? MinPostsForMod= ? OnlyApointerCanRemoveMod= ? CanRemoveSingleMod= ? "
			+"MonthsPasswordValidFor= ? where ForumName = ?;");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			preparedStatement.setBoolean(1, policy.hasEmailConfirmation());
			preparedStatement.setBoolean(2, policy.getExtendedDeletionPolicy());
			preparedStatement.setInt(3, policy.getMinSeniorityMonths());
			preparedStatement.setInt(4, policy.getMinPostForModerator());
			preparedStatement.setBoolean(5, policy.getOnlyApointAdministratorCanRemoveModerators());
			preparedStatement.setBoolean(6, policy.getCanRemoveSingleModerators());
			preparedStatement.setInt(7, policy.getMonthsPasswordValidFor());
			preparedStatement.setString(8,forumName);
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
