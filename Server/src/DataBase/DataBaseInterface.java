package DataBase;
import java.sql.*;

public class DataBaseInterface {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/ForumSysDB";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "1234";

	/*protected Connection connect;
	protected Statement statement;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;*/

	public DataBaseInterface() {

	}
	/*
	public Connection getConnect() {
		return connect;
	}

	public void setConnect(Connection connect) {
		this.connect = connect;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public void closeResultSet() {
		try {
			this.resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void closeConnect() {
		try {
			this.connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void closePreparedStatement() {
		try {
			this.preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void closeStatement() {
		try {
			this.statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

	public void printForumList() {
		System.out.println("~~~~~~~~~ forums ~~~~~~~~~~~~~~~~~");
			// this will load the MySQL driver, each DB has its own driver
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			// setup the connection with the DB.
			try {
				Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");			
				try {
					Statement statement = connect.createStatement();
					try {
						ResultSet resultSet = statement.executeQuery("select * from ForumSysDB.Forums");
						while (resultSet.next()) {
							// it is possible to get the columns via name
							// also possible to get the columns via the column number
							// which starts at 1
							//      // e.g., resultSet.getSTring(2);

							String ForumName = resultSet.getString("ForumName");
							boolean HasEmailConfirmPolicy = resultSet.getBoolean("HasEmailConfirmPolicy");
							boolean HasExtandedDeletePolicy =resultSet.getBoolean("HasExtandedDeletePolicy");
							int MinMonthsForMod = resultSet.getInt("MinMonthsForMod");
							int MinPostsForMod = resultSet.getInt("MinPostsForMod");
							boolean OnlyApointerCanRemoveMod = resultSet.getBoolean("OnlyApointerCanRemoveMod");
							boolean CanRemoveSingleMod = resultSet.getBoolean("CanRemoveSingleMod");
							int MonthsPasswordValidFor = resultSet.getInt("MonthsPasswordValidFor");

							//Retrieve by column name
							System.out.println(ForumName+" "+HasEmailConfirmPolicy+" "+HasExtandedDeletePolicy+" "+MinMonthsForMod+
									" "+MinPostsForMod+" "+OnlyApointerCanRemoveMod+" "+CanRemoveSingleMod+" "+MonthsPasswordValidFor);
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
			}
	}

	public void clearAllData(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try{
		// setup the connection with the DB.
		Connection connect=DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
		try {
			// remove
			PreparedStatement preparedStatement = connect.prepareStatement("delete from ForumSysDB.Forums; ");
			try{
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.SubForums; ");
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.Members; ");
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.Posts; ");
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.Moderators; ");
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.Friends; ");
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("delete from ForumSysDB.Securities; ");
			preparedStatement.executeUpdate();
			

			System.out.println("########### All data was deleted");
		} finally {
			preparedStatement.close();
		}
		} finally {
		connect.close();
		}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		}
}
		
