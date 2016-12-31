package com.youtube.dao;

import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;

public class Oracle308tube {
	private static DataSource Oracle308tube = null;
	private static Context context = null;

	public static DataSource Oracle308tubeConn() throws Exception {

		if (Oracle308tube != null) {

			return Oracle308tube;
		}

		try {

			if (context == null) {
				context = new InitialContext();
			}

			Oracle308tube = (DataSource) context.lookup("308tubeOracle"); // jndi

		} catch (Exception e) {

			e.printStackTrace();
		}

		return Oracle308tube;
	}
	
	/**
	 * This method will return the connection to the Oracle 308tube schema
	 * Note that the scope is protected which means only java class in the
	 * dao package can use this method.
	 * 
	 * @return Connection to 308tube Oracle database.
	 */
	protected static Connection oraclePcPartsConnection() {
		Connection conn = null;
		try {
			conn = Oracle308tubeConn().getConnection();
			return conn;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}