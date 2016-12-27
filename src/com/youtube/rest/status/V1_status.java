package com.youtube.rest.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Oracle308tube;

/**
 * This is the root path for our restful api service
 * In the web.xml file, we specified that /api/* need to be in the URL to
 * get to this class.
 * 
 * We are versioning the class in the URL path.  This is the first version v1.
 * Example how to get to the root of this api resource:
 * http://localhost:8080/com.will.rest/api/v1/status
 * 
 * @author 308tube
 *
 */
@Path("/v1/status/")
public class V1_status {
	
	private static final String api_version = "00.01.00"; //version of the api
	
	/**
	 * This method sits at the root of the api.  It will return the name
	 * of this api.
	 * 
	 * @return String - Title of the api
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>Java Web Service</p>";
	}
	
	/**
	 * This method will return the version number of the api
	 * Note: this is nested one down from the root.  You will need to add version
	 * into the URL path.
	 * 
	 * Example:
	 * http://localhost:7001/com.youtube.rest/api/v1/status/version
	 * 
	 * @return String - version number of the api
	 */
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version:</p>" + api_version;
	}
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabasesStatus() throws Exception {

		PreparedStatement query = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;

		try {

			conn = Oracle308tube.oracle308tubeConn().getConnection();
			query = conn
					.prepareStatement("select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') DATETIME "
							+ "from sys.dual");
			ResultSet rs = query.executeQuery();

			while (rs.next()) {

				myString = rs.getString("DATETIME");

			}

			query.close(); // close connection
			returnString = "<p>Database Status</p> "
					+ "<p>Database Date/Time return: " + myString + "</p>";

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			if (conn != null) {
				conn.close();
			}
		}

		return returnString;
	}


}