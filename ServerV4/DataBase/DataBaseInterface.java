package DataBase;
import java.sql.*;

public class DataBaseInterface {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/ForumSysDB";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "1234";

	protected Connection connect;
	protected Statement statement;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;

	public DataBaseInterface() {
		this.connect=null;
		this.statement=null;
		this.preparedStatement=null;
		this.resultSet=null;
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
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ForumSysDB?user=root&password=1234");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from ForumSysDB.Forums");
			while (this.resultSet.next()) {
				// it is possible to get the columns via name
				// also possible to get the columns via the column number
				// which starts at 1
				//      // e.g., resultSet.getSTring(2);

				String ForumName = this.resultSet.getString("ForumName");
				boolean HasEmailConfirmPolicy = this.resultSet.getBoolean("HasEmailConfirmPolicy");
				boolean HasExtandedDeletePolicy = this.resultSet.getBoolean("HasExtandedDeletePolicy");
				int MinMonthsForMod = this.resultSet.getInt("MinMonthsForMod");
				int MinPostsForMod = this.resultSet.getInt("MinPostsForMod");
				boolean OnlyApointerCanRemoveMod = this.resultSet.getBoolean("OnlyApointerCanRemoveMod");
				boolean CanRemoveSingleMod = this.resultSet.getBoolean("CanRemoveSingleMod");
				int MonthsPasswordValidFor = this.resultSet.getInt("MonthsPasswordValidFor");

				//Retrieve by column name
				System.out.println(ForumName+" "+HasEmailConfirmPolicy+" "+HasExtandedDeletePolicy+" "+MinMonthsForMod+
						" "+MinPostsForMod+" "+OnlyApointerCanRemoveMod+" "+CanRemoveSingleMod+" "+MonthsPasswordValidFor);
			}


		} catch (Exception e) {
			System.out.println("faild to execute query");
		} finally {
			//      close();
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				System.out.println("faild to close connection with data base");
			}

		}
	}
}
