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
	
	private static final String InteractiveNotifyingPolicys = null;
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
			boolean CanRemoveSingleMod,  int MonthsPasswordValidFor, String InteractiveNotifyingPolicys){
		
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("INSERT INTO ForumSysDB.Forums (ForumName, HasEmailConfirmPolicy, HasExtandedDeletePolicy,"
					+ " MinMonthsForMod, MinPostsForMod, OnlyApointerCanRemoveMod, CanRemoveSingleMod,"
					+" MonthsPasswordValidFor,InteractiveNotifyingPolicys) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
			// parameters start with 1
			try{
			preparedStatement.setString(1,ForumName);
			preparedStatement.setBoolean(2,HasEmailConfirmPolicy);
			preparedStatement.setBoolean(3,HasExtandedDeletePolicy);
			preparedStatement.setInt(4,MinMonthsForMod);
			preparedStatement.setInt(5,MinPostsForMod);
			preparedStatement.setBoolean(6,OnlyApointerCanRemoveMod);
			preparedStatement.setBoolean(7,CanRemoveSingleMod);
			preparedStatement.setInt(8,MonthsPasswordValidFor);
			preparedStatement.setString(9,InteractiveNotifyingPolicys);
			preparedStatement.executeUpdate();
			System.out.println("######## Forum added seccessfuly");
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
		
	//ForumGen
	public boolean deleteForum (String forumName){
		
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
				PreparedStatement preparedStatement = connect.prepareStatement("delete from ForumSysDB.Forums where ForumName= ? ; ");
			try{
				preparedStatement.setString(1,forumName);
				preparedStatement.executeUpdate();
			System.out.println("######## Forum deleted seccessfuly");
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
	public Vector<Forum> getForums(){
		Vector<Forum> forums = new Vector<Forum>();
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
					ResultSet resultSet = statement.executeQuery("select * from ForumSysDB.Forums");
					while (resultSet.next()) {
						String ForumName = resultSet.getString("ForumName");
						boolean HasEmailConfirmPolicy = resultSet.getBoolean("HasEmailConfirmPolicy");
						boolean HasExtandedDeletePolicy =resultSet.getBoolean("HasExtandedDeletePolicy");
						int MinMonthsForMod = resultSet.getInt("MinMonthsForMod");
						int MinPostsForMod = resultSet.getInt("MinPostsForMod");
						boolean OnlyApointerCanRemoveMod = resultSet.getBoolean("OnlyApointerCanRemoveMod");
						boolean CanRemoveSingleMod = resultSet.getBoolean("CanRemoveSingleMod");
						int MonthsPasswordValidFor = resultSet.getInt("MonthsPasswordValidFor");

						Security security = this.securityHandler.getSecurity(ForumName);
						Policy policy = new Policy (HasEmailConfirmPolicy, HasExtandedDeletePolicy,MinPostsForMod ,
								MinMonthsForMod , OnlyApointerCanRemoveMod,CanRemoveSingleMod,MonthsPasswordValidFor,InteractiveNotifyingPolicys);
						Forum forum = new Forum(ForumName,null, null, policy,null, security, null);
						
						Vector<SubForum> subForums = this.subForumsHandler.getSubForums(forum);
						forum.setSubForums(subForums);
						
						Vector<Member> members = this.membersHandler.getMembers(forum);
						forum.setMembers(members);
						
						Vector<String> admins = this.membersHandler.getAdmins(ForumName);
						forum.setAdmins(admins);
						
						Vector<String> statuses = new Vector<String>();
						forum.setStatuses(statuses);
					
						forums.add(forum);
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
		
		return forums;
}

	
	//Forum
	public boolean setName (String oldForumName,String newForumName){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("update ForumSysDB.Forums set ForumName = ? where ForumName = ?;");
			try{
				preparedStatement.setString(1,newForumName);
				preparedStatement.setString(2,oldForumName);
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
	//Forum
	public boolean setPolicy (String forumName, Policy policy){
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			try{
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");

			try{
			// preparedStatements can use variables and are more efficient
			PreparedStatement preparedStatement =  connect.prepareStatement("update ForumSysDB.Forums set HasEmailConfirmPolicy= ? "
					+"HasExtandedDeletePolicy= ? MinMonthsForMod= ? MinPostsForMod= ? OnlyApointerCanRemoveMod= ? CanRemoveSingleMod= ? "
					+"MonthsPasswordValidFor= ? InteractiveNotifyingPolicys= ? where ForumName = ?;");
			try{
				preparedStatement.setBoolean(1, policy.hasEmailConfirmation());
				preparedStatement.setBoolean(2, policy.getExtendedDeletionPolicy());
				preparedStatement.setInt(3, policy.getMinSeniorityMonths());
				preparedStatement.setInt(4, policy.getMinPostForModerator());
				preparedStatement.setBoolean(5, policy.getOnlyApointAdministratorCanRemoveModerators());
				preparedStatement.setBoolean(6, policy.getCanRemoveSingleModerators());
				preparedStatement.setInt(7, policy.getMonthsPasswordValidFor());
				preparedStatement.setString(8,policy.getInteractiveNotifyingPolicys());
				preparedStatement.setString(9,forumName);
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		