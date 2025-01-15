package com.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.service.LoginService;

@Path("/genral_controller")
@Produces(MediaType.APPLICATION_JSON)
public class GenralController {
	@Context
	HttpServletResponse res;
	@Context
	HttpServletRequest req;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(String user_login) {
		System.out.println("I am in the login method 83");
		System.out.print("in getMsg2 26 " + user_login);

		// Use Jackson to parse the input JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String message;
		try {
			JsonNode jsonNode = objectMapper.readTree(user_login);
			message = jsonNode.get("username").asText(); // Extract the value of "message"
			System.out.println(jsonNode.get("username").asText());
		} catch (Exception e) {
			
		}

		return new LoginService().userLogin(user_login, res, req);
	}
}