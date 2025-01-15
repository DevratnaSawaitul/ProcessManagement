package com.pms.controller;

import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/sheets")
public class SheetResource {

	@GET
	@Path("/recent")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRecentSheets() {
		try {
			// Create a response object
			Map<String, Object> response = new HashMap<>();
			List<String> sheets = Arrays.asList("Sheet 1", "Sheet 2", "Sheet 3", "Sheet 4", "Sheet 5", "Sheet 6", "Sheet 7");
			//List<String> sheets = Arrays.asList("Sheet 1", "Sheet 2", "Sheet 3", "Sheet 4", "Sheet 5");
			//List<String> sheets = Arrays.asList("Sheet 1", "Sheet 2", "Sheet 3", "Sheet 4", "Sheet 5", "Sheet 6");

			// Add data to the response object
			response.put("status", "success");
			response.put("recentSheets", sheets);

			// Convert the response object to a JSON string
			ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
			return objectMapper.writeValueAsString(response);
		} catch (Exception e) {
			// Handle any unexpected errors
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("status", "error");
			errorResponse.put("message", e.getMessage());
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.writeValueAsString(errorResponse);
			} catch (Exception jsonException) {
				// Fallback if error serialization fails
				return "{\"status\":\"error\",\"message\":\"Unexpected error occurred\"}";
			}
		}
	}
}
