package com.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.service.LoginService;

@Path("/myresource")
public class MyResource {

	@Path("/getMsg")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getIt() {
		return "Got it!";
	}

	@Path("/getMsg2")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIt2(String input) {
		ObjectMapper objectMapper = new ObjectMapper();
		String message;
		try {
			JsonNode jsonNode = objectMapper.readTree(input);
			message = jsonNode.get("message").asText(); // Extract the value of "message"
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Invalid JSON input\"}").build();
		}
		String jsonResponse = "{\"response\":\"" + message + "\"}";
		return Response.ok(jsonResponse).build();
	}
}