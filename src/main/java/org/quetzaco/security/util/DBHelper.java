package org.quetzaco.security.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

	
	//TODO init connection
	public static Connection getConnection() throws SQLException{
		Connection conn = null;

		return conn;
	}

	public static void cleanup(Connection con, Statement callStat) throws  Exception {
		
	}

	public static void cleanup(Connection con, CallableStatement callStat)
			throws Exception {
		
	}

	public static void cleanup(Connection con, CallableStatement callStat,
			ResultSet rst)  {
	}

	// Added to Close the Connection - Rajan Sandanam
	public static void cleanup(Connection con, PreparedStatement ps)
			 {
	}

	public static void cleanup(Connection con, PreparedStatement ps1,
			PreparedStatement ps2) throws Exception {
	}

	public static void cleanup(Connection con, PreparedStatement ps,
			ResultSet rst)  {
	}

}
