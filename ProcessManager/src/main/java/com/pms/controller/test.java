package com.pms.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Root resource (exposed at "test" path)
@Path("test")
public class test {

	// Method handling HTTP GET requests and returning a JSON response
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIt() {
		// Create a response object
		ResponseMessage responseMessage = new ResponseMessage("hi there it!");

		// Return the response as JSON
		return Response.ok(responseMessage).build();
	}

	// A simple class to represent the response message (this will be converted to
	// JSON)
	public static class ResponseMessage {
		private String message;

		public ResponseMessage(String message) {
			this.message = message;
		}

		// Getter and setter
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
