package com.pms.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.pms.db.Skills;
import com.pms.db.Subprocesses;
import com.pms.db.Process;
import com.pms.db.Tools;
import com.pms.util.MessageLog;

public class OrganizationService {

	public String getProcess(String request) {
		MessageLog.info("In OrganizationService getProcess() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray processJsonArray = new JSONArray();
		try {
			JSONObject processJsonObj = new JSONObject();
			Process[] processes = null;
			JSONObject info = (JSONObject) parser.parse(request);
			String loadType = info.get("load_type") != null ? (String) info.get("load_type") : "";
			if ("active".equalsIgnoreCase(loadType)) {
				processes = new Process().retrieveAllWhere(" where active='true'");

				if (processes != null && processes.length > 0) {
					for (Process s1 : processes) {
						processJsonObj = new JSONObject();
						processJsonObj.put("process_name", s1.getProcessName());
						processJsonObj.put("process_id", s1.getProcessId());
						processJsonObj.put("active", s1.getActive());
						processJsonArray.add(processJsonObj);
					}
					response.put("status", true);
					response.put("processes", processJsonArray);
				} else {
					response.put("status", false);
					response.put("message", "no_process_found");
				}
			} else if ("all".equalsIgnoreCase(loadType)) {
				processes = new Process().retrieveAllWhere(" order by process_id desc");
				if (processes != null && processes.length > 0) {
					for (Process s1 : processes) {
						processJsonObj = new JSONObject();
						processJsonObj.put("process_name", s1.getProcessName());
						processJsonObj.put("process_id", s1.getProcessId());
						processJsonObj.put("active", s1.getActive());
						processJsonArray.add(processJsonObj);
					}
				}
				response.put("status", true);
				response.put("processes", processJsonArray);
			} else {
				response.put("status", false);
				response.put("message", "invalid_load_type");
			}
		} catch (Exception e) {
			response = new JSONObject();
			response.put("status", false);
			response.put("message", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String getSubProcess(String request) {
		MessageLog.info("In OrganizationService getSubProcess() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray subProcess = new JSONArray();
		try {
			JSONObject subProcessDetails = new JSONObject();
			Subprocesses[] subprocesses = null;
			JSONObject info = (JSONObject) parser.parse(request);
			String loadType = info.get("load_type") != null ? (String) info.get("load_type") : "";
			if ("process_related".equalsIgnoreCase(loadType)) {
				String process = info.get("process") != null ? (String) info.get("process") : "";
				if (!process.isEmpty()) {
					subprocesses = new Subprocesses()
							.retrieveAllWhere(" where process_name='" + process + "' and active='true'");
				} else {
					response.put("status", false);
					response.put("message", "process_name_required");
					return response.toString();
				}

				if (subprocesses != null && subprocesses.length > 0) {
					for (Subprocesses s1 : subprocesses) {
						subProcessDetails = new JSONObject();
						subProcessDetails.put("process_name", s1.getProcessName());
						subProcessDetails.put("subprocess_name", s1.getSubprocessName());
						subProcessDetails.put("subprocess_id", s1.getSubprocessId());
						subProcessDetails.put("active", s1.getActive());
						subProcess.add(subProcessDetails);
					}
					response.put("status", true);
					response.put("sub_process", subProcess);
				} else {
					response.put("status", false);
					response.put("message", "no_sub_process_found");
				}
			} else if ("all".equalsIgnoreCase(loadType)) {
				subprocesses = new Subprocesses().retrieveAllWhere(" order by subprocess_id desc");
				if (subprocesses != null && subprocesses.length > 0) {
					for (Subprocesses s : subprocesses) {
						subProcessDetails = new JSONObject();
						subProcessDetails.put("process_name", s.getProcessName());
						subProcessDetails.put("subprocess_name", s.getSubprocessName());
						subProcessDetails.put("subprocess_id", s.getSubprocessId());
						subProcessDetails.put("active", s.getActive());
						subProcess.add(subProcessDetails);
					}
				}
				response.put("status", true);
				response.put("sub_process", subProcess);
			} else {
				response.put("status", false);
				response.put("message", "invalid_load_type");
			}
		} catch (Exception e) {
			response = new JSONObject();
			response.put("status", false);
			response.put("message", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String getTools(String request) {
		MessageLog.info("In OrganizationService getTools() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray toolsJSONArray = new JSONArray();
		try {
			JSONObject toolsJSONObj = new JSONObject();
			Tools[] tools = null;
			JSONObject info = (JSONObject) parser.parse(request);
			String loadType = info.get("load_type") != null ? (String) info.get("load_type") : "";
			if ("sub_process_related".equalsIgnoreCase(loadType)) {
				String sub_process = info.get("sub_process") != null ? (String) info.get("sub_process") : "";
				if (!sub_process.isEmpty()) {
					tools = new Tools()
							.retrieveAllWhere(" where sub_process='" + sub_process + "' and active='true'");
				} else {
					response.put("status", false);
					response.put("message", "sub_process_required");
					return response.toString();
				}

				if (tools != null && tools.length > 0) {
					for (Tools s1 : tools) {
						toolsJSONObj = new JSONObject();
						toolsJSONObj.put("tool_name", s1.getToolName());
						toolsJSONObj.put("sub_process", s1.getSubProcess());
						toolsJSONObj.put("tool_id", s1.getToolId());
						toolsJSONObj.put("active", s1.getActive());
						toolsJSONArray.add(toolsJSONObj);
					}
					response.put("status", true);
					response.put("tools", toolsJSONArray);
				} else {
					response.put("status", false);
					response.put("message", "no_tools_found_related_to_subprocess");
				}
			} else if ("all".equalsIgnoreCase(loadType)) {
				tools = new Tools().retrieveAllWhere(" order by tool_id desc");
				if (tools != null && tools.length > 0) {
					for (Tools s1 : tools) {
						toolsJSONObj = new JSONObject();
						toolsJSONObj.put("tool_name", s1.getToolName());
						toolsJSONObj.put("sub_process", s1.getSubProcess());
						toolsJSONObj.put("tool_id", s1.getToolId());
						toolsJSONObj.put("active", s1.getActive());
						toolsJSONArray.add(toolsJSONObj);
					}
				}
				response.put("status", true);
				response.put("tools", toolsJSONArray);
			} else {
				response.put("status", false);
				response.put("message", "invalid_load_type");
			}
		} catch (Exception e) {
			response = new JSONObject();
			response.put("status", false);
			response.put("message", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String getSkills(String request) {
		MessageLog.info("In OrganizationService getSkills() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray skillsJSONArray = new JSONArray();
		try {
			JSONObject skillsJSONObj = new JSONObject();
			Skills[] skills = null;
			JSONObject info = (JSONObject) parser.parse(request);
			String loadType = info.get("load_type") != null ? (String) info.get("load_type") : "";
			if ("active".equalsIgnoreCase(loadType)) {
				skills = new Skills().retrieveAllWhere(" where active='true'");

				if (skills != null && skills.length > 0) {
					for (Skills s1 : skills) {
						skillsJSONObj = new JSONObject();
						skillsJSONObj.put("skill_name", s1.getSkillName());
						skillsJSONObj.put("skill_id", s1.getSkillId());
						skillsJSONObj.put("active", s1.getActive());
						skillsJSONArray.add(skillsJSONObj);
					}
					response.put("status", true);
					response.put("skills", skillsJSONArray);
				} else {
					response.put("status", false);
					response.put("message", "no_active_skills_found");
				}
			} else if ("all".equalsIgnoreCase(loadType)) {
				skills = new Skills().retrieveAllWhere(" order by skill_id desc");
				if (skills != null && skills.length > 0) {
					for (Skills s1 : skills) {
						skillsJSONObj = new JSONObject();
						skillsJSONObj.put("skill_name", s1.getSkillName());
						skillsJSONObj.put("skill_id", s1.getSkillId());
						skillsJSONObj.put("active", s1.getActive());
						skillsJSONArray.add(skillsJSONObj);
					}
				}
				response.put("status", true);
				response.put("skills", skillsJSONArray);
			} else {
				response.put("status", false);
				response.put("message", "invalid_load_type");
			}
		} catch (Exception e) {
			response = new JSONObject();
			response.put("status", false);
			response.put("message", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}
}
