package com.pms.controller;

import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.service.SheetService;

@Path("/sheets")
public class SheetController {

	@POST
	@Path("/recent")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRecentSheets() {
		return new SheetService().showSheets();
	}

	@POST
	@Path("/addSheets")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addSheets(String request) {
		return new SheetService().addSheets(request);
	}
}