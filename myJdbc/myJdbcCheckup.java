// for MySql, run with
//   java -classpath mysql-connector-java-8.0.14.jar;. JdbcCheckup
//                                    (use : instead of ; on UNIX)
import java.io.IOException;
import java.sql.*;

class myJdbcCheckup {
	public static void main(String args[]) {
		// Prompt the user for connect information
		String user = "root";
		String password = "";
		String database = "sandwichdb";
		String host = "localhost";
		String port = "3306";
		String connStr = "jdbc:mysql://" + host + ":" + port + "/" + database;
		System.out.println("using connection string: " + connStr);
		System.out.print("Connecting to the database...");
		System.out.flush();
		Connection conn = null;
		// Connect to the database
		// Use finally clause to close connection
		try {
			conn = DriverManager.getConnection(connStr, user, password);
			System.out.println("connected.");
		} catch (SQLException e) {
			System.out.println("Problem with JDBC Connection\n");
			printSQLException(e);
			System.exit(0);
		} finally {
			// Close the connection, if it was obtained, no matter what happens
			// above or within called methods
			if (conn != null) {
				try {
					conn.close(); // this also closes the Statement and
									// ResultSet, if any
				} catch (SQLException e) {
					System.out.println("Problem with closing JDBC Connection\n");
					printSQLException(e);
					System.exit(1);
				}
			}
		}
	}
	// print out all exceptions connected to e by nextException or getCause
	static void printSQLException(SQLException e) {
		// SQLExceptions can be delivered in lists (e.getNextException)
		// Each such exception can have a cause (e.getCause, from Throwable)
		while (e != null) {
			System.out.println("SQLException Message:" + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			Throwable t = e.getCause();
			while (t != null) {
				System.out.println("SQLException Cause:" + t);
				t = t.getCause();
			}
			e = e.getNextException();
		}
	}
}
