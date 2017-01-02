package com.youtube.rest.inventory;

import javax.ws.rs.Path;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.Schema308tube;

/**
 * This class is used to manage computer parts inventory. It is a improvement to
 * V1_inventory.java
 * 
 * @author 308tube
 */
@Path("/v5/inventory/")
public class V5_inventory {

	/**
	 * This method will allow you to insert data the PC_PARTS table. This is a
	 * example of using the Jackson Processor
	 * 
	 * Note: If you look, this method addPcParts using the same URL as a GET
	 * method returnBrandParts. We can do this because we are using different
	 * HTTP methods for the same URL string.
	 * 
	 * @param incomingData
	 *            - must be in JSON format
	 * @return String
	 * @throws Exception
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
			MediaType.APPLICATION_JSON })
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception {

		String returnString = null;
		JSONArray jsonArray = new JSONArray(); //not needed
		Schema308tube dao = new Schema308tube();

		try {
			System.out.println("incomingData: " + incomingData);

			/*
			 * ObjectMapper is from Jackson Processor framework
			 * http://jackson.codehaus.org/
			 * 
			 * Using the readValue method, you can parse the json from the http
			 * request and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper();
			ItemEntry1 itemEntry = mapper.readValue(incomingData,
					ItemEntry1.class);

			int http_code = dao.insertIntoPC_PARTS(itemEntry.PC_PARTS_PK,
					itemEntry.PC_PARTS_TITLE, itemEntry.PC_PARTS_CODE,
					itemEntry.PC_PARTS_MAKER, itemEntry.PC_PARTS_AVAIL,
					itemEntry.PC_PARTS_DESC);

			if (http_code == 200) {
				returnString = jsonArray.toString();
				//returnString = "Item inserted";
			} else {
				return Response.status(500).entity("Unable to process Item")
						.build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(returnString).build();
	}

}

/*
 * This is a class used by the addPcParts method. Used by the Jackson Processor
 * 
 * Note: for re-usability you should place this in its own package.
 */
class ItemEntry1 {
	public String PC_PARTS_PK;
	public String PC_PARTS_TITLE;
	public String PC_PARTS_CODE;
	public String PC_PARTS_MAKER;
	public String PC_PARTS_AVAIL;
	public String PC_PARTS_DESC;
}
