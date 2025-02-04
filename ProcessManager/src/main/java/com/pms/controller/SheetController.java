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
	@Consumes(MediaType.APPLICATION_JSON)
	public String getRecentSheets(String request) {
		return new SheetService().showRecentSheets(request);
	}

	@POST
	@Path("/add_sheets")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addSheet(String request) {
		return new SheetService().saveSheet(request);
	}

	@POST
	@Path("/showSheets")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String showSheets(String request) {
		return new SheetService().showSheets(request);
	}

	@POST
	@Path("/addStep")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addStep(String request) {
		return new SheetService().addStep(request);
	}

	@POST
	@Path("/updateStep")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateStep(String request) {
		return new SheetService().updateStep(request);
	}

	@POST
	@Path("/deleteSheet")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteSheet(String request) {
		return new SheetService().deleteSheet(request);
	}

	@POST
	@Path("/deleteStep")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteStep(String request) {
		return new SheetService().deleteStep(request);
	}

	@POST
	@Path("/addSheetProcess")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addSheetProcess(String request) {
		return new SheetService().addSheetProcess(request);
	}

	@POST
	@Path("/deleteSheetProcess")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteSheetProcess(String request) {
		return new SheetService().deleteSheetProcess(request);
	}
}
