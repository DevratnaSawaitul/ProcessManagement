package com.pms.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pms.service.OrganizationService;

@Path("/org")
public class OrganizationController {
	@POST
	@Path("/show_tools")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getTools(String request) {
		return new OrganizationService().getTools(request);
	}
	
	@POST
	@Path("/show_skills")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getSkills(String request) {
		return new OrganizationService().getSkills(request);
	}
	
	@POST
	@Path("/show_process")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getProcess(String request) {
		return new OrganizationService().getProcess(request);
	}
	
	@POST
	@Path("/show_sub_process")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getSubProcess(String request) {
		return new OrganizationService().getSubProcess(request);
	}
}
