package com.pms.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.pms.db.Process;
import com.pms.db.SheetProcess;
import com.pms.db.Sheets;
import com.pms.db.Steps;
import com.pms.util.MessageLog;

public class SheetService {

	public static String showRecentSheets(String request) {
		MessageLog.info("In SheetService showRecentSheets() request= " + request);
		JSONObject response = new JSONObject();
		try {
			JSONArray sheets = new JSONArray();
			Sheets s[] = new Sheets().retrieveAllWhere(" order by sheet_id desc limit 6");
			if (s != null && s.length > 0) {
				for (Sheets s1 : s) {
					sheets.add(s1.getFileName());
				}
			}
			response.put("status", "success");
			response.put("recentSheets", sheets);
		} catch (Exception e) {
			// Handle any unexpected errors
			response.put("status", "error");
			response.put("message", e.getMessage());
		}
		return response.toString();
	}

	public String showSheets(String request) {
		MessageLog.info("In SheetService showSheets() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONArray sheets = new JSONArray();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			String loadType = info.get("load_type") != null ? (String) info.get("load_type") : "";
			if ("singleSheet".equalsIgnoreCase(loadType)) {
				String fileName = info.get("file_name") != null ? (String) info.get("file_name") : "";
				Sheets s[];

				if (!fileName.isEmpty()) {
					s = new Sheets().retrieveAllWhere(" where file_name='" + fileName + "'");
				} else {
					response.put("status", false);
					response.put("message", "sheet_id_or_file_name_required");
					return response.toString();
				}

				if (s != null) {
					JSONArray sheetProcessArr = new JSONArray();
					JSONObject sheetProcessObj = new JSONObject();
					JSONObject sheetDetails = new JSONObject();
					sheetDetails.put("sheet_id", s[0].getSheetId());
					sheetDetails.put("file_name", s[0].getFileName());
					sheetDetails.put("version", s[0].getVersion());
					sheetDetails.put("date", s[0].getDate());
					sheetDetails.put("department", s[0].getDepartment());
					sheetDetails.put("design_no", s[0].getDesignNo());
					sheetDetails.put("floor", s[0].getFloor());
					sheetDetails.put("last_updated_by", s[0].getLastUpdatedBy());
					sheetDetails.put("date_of_last_update", s[0].getDateOfLastUpdate());
					sheets.add(sheetDetails);
					SheetProcess sp[] = new SheetProcess()
							.retrieveAllWhere("where file_name='" + s[0].getFileName() + "' order by sheet_process_id");
					if (sp != null && sp.length > 0) {
						for (SheetProcess spObj : sp) {
							JSONArray stepsArr = new JSONArray();
							JSONObject stepsObj = new JSONObject();
							sheetProcessObj = new JSONObject();
							sheetProcessObj.put("sheet_process_id", spObj.getSheetProcessId());
							sheetProcessObj.put("file_name", spObj.getFileName());
							sheetProcessObj.put("process_name", spObj.getProcessName());
							Steps step[] = new Steps().retrieveAllWhere(
									"where sheet_process_id='" + spObj.getSheetProcessId() + "' order by step_id");
							if (step != null && step.length > 0) {
								for (Steps stepObj : step) {
									stepsObj = new JSONObject();
									stepsObj.put("step_id", stepObj.getStepId());
									stepsObj.put("file_name", stepObj.getFileName());
									stepsObj.put("process_name", stepObj.getProcessName());
									stepsObj.put("subprocess_name", stepObj.getSubprocessName());
									stepsObj.put("step_number", stepObj.getStepNumber());
									stepsObj.put("tool_name", stepObj.getToolName());
									stepsObj.put("tool_spec", stepObj.getToolSpec());
									stepsObj.put("special_instruction", stepObj.getSpecialInstruction());
									stepsObj.put("skill", stepObj.getSkill());
									stepsObj.put("time_minutes", stepObj.getTimeMinutes());
									stepsObj.put("image_url", stepObj.getImageUrl());
									stepsObj.put("sheet_process_id", stepObj.getSheetProcess());
									stepsArr.add(stepsObj);
								}
							}
							sheetProcessObj.put("steps", stepsArr);
							sheetProcessArr.add(sheetProcessObj);
						}
					}
					response.put("status", true);
					response.put("sheets", sheets);
					response.put("sheet_process", sheetProcessArr);
				} else {
					response.put("status", false);
					response.put("message", "sheet_not_found");
				}
			} else if ("all".equalsIgnoreCase(loadType)) {
				Sheets[] allSheets = new Sheets().retrieveAllWhere(" order by sheet_id desc");

				if (allSheets != null && allSheets.length > 0) {
					for (Sheets s : allSheets) {
						JSONObject sheetDetails = new JSONObject();
						sheetDetails.put("sheet_id", s.getSheetId());
						sheetDetails.put("file_name", s.getFileName());
						sheetDetails.put("version", s.getVersion());
						sheetDetails.put("date", s.getDate());
						sheetDetails.put("department", s.getDepartment());
						sheetDetails.put("design_no", s.getDesignNo());
						sheetDetails.put("floor", s.getFloor());
						sheetDetails.put("last_updated_by", s.getLastUpdatedBy());
						sheetDetails.put("date_of_last_update", s.getDateOfLastUpdate());
						sheets.add(sheetDetails);
					}
				}
				response.put("status", true);
				response.put("sheets", sheets);
			} else {
				response.put("status", false);
				response.put("message", "invalid_load_type");
			}
		} catch (Exception e) {
			response = new JSONObject();
			response.put("status", false);
			response.put("message", "exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public static String addSheets(String request) {
		MessageLog.info("In SheetService addSheets() request= " + request);
		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			Sheets s = new Sheets();
			s.setFileName(info.get("file_name") != null ? info.get("file_name").toString().trim() : "");
			if (s.getFileName().isEmpty()) {
				response.put("success", false);
				response.put("msg", "file_name_empty");
				return response.toString();
			}
			Sheets sList[] = s.retrieveAllWhere(" where lower(file_name)='" + s.getFileName().toLowerCase() + "'");
			if (sList != null && sList.length > 0) {
				response.put("success", false);
				response.put("msg", "file_name_already_exist");
				return response.toString();
			}
			s.setVersion(info.get("version") != null ? (String) info.get("version") : "1.0");
			s.setDate(info.get("date") != null ? (String) info.get("date") : "");
			s.setDepartment(info.get("department") != null ? (String) info.get("department") : "");
			s.setDesignNo(info.get("design_no") != null ? (String) info.get("design_no") : "");
			s.setFloor(info.get("floor") != null ? (String) info.get("floor") : "");
			s.setLastUpdatedBy(info.get("last_updated_by") != null ? (String) info.get("last_updated_by") : "");
			s.setDateOfLastUpdate(currentTime);

			if (s.insert()) {
				response.put("success", true);
				response.put("msg", "sheet_added");
			} else {
				response.put("success", false);
				response.put("msg", "failed");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public static String updateSheet(String request) {
		MessageLog.info("In SheetService updateSheet() request= " + request);
		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			Sheets s = new Sheets();
			s.setFileName(info.get("file_name") != null ? info.get("file_name").toString().trim() : "");
			if (s.getFileName().isEmpty()) {
				response.put("success", false);
				response.put("msg", "file_name_empty");
				return response.toString();
			}
			Sheets sList[] = s.retrieveAllWhere(" where lower(file_name)='" + s.getFileName().toLowerCase() + "'");
			if (sList == null || sList.length <= 0) {
				response.put("success", false);
				response.put("msg", "file_name_not_exist");
				return response.toString();
			}
			s.setVersion(info.get("version") != null ? (String) info.get("version") : "1.0");
			s.setDate(info.get("date") != null ? (String) info.get("date") : "");
			s.setDepartment(info.get("department") != null ? (String) info.get("department") : "");
			s.setDesignNo(info.get("design_no") != null ? (String) info.get("design_no") : "");
			s.setFloor(info.get("floor") != null ? (String) info.get("floor") : "");
			s.setLastUpdatedBy(info.get("last_updated_by") != null ? (String) info.get("last_updated_by") : "");
			s.setDateOfLastUpdate(currentTime);

			if (s.update()) {
				response.put("success", true);
				response.put("msg", "sheet_updated");
			} else {
				response.put("success", false);
				response.put("msg", "failed");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String addStep(String request) {
		MessageLog.info("In SheetService addStep() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			Steps step = new Steps();
			step.setFileName(info.get("file_name") != null ? info.get("file_name").toString().trim() : "");
			step.setProcessName(info.get("process_name") != null ? info.get("process_name").toString().trim() : "");
			step.setSubprocessName(
					info.get("subprocess_name") != null ? info.get("subprocess_name").toString().trim() : "");
			step.setStepNumber(
					info.get("step_number") != null ? Integer.parseInt(info.get("step_number").toString()) : 0);
			step.setToolName(info.get("tool_name") != null ? info.get("tool_name").toString().trim() : "");
			step.setToolSpec(info.get("tool_spec") != null ? info.get("tool_spec").toString().trim() : "");
			step.setSpecialInstruction(
					info.get("special_instruction") != null ? info.get("special_instruction").toString().trim() : "");
			step.setSkill(info.get("skill") != null ? info.get("skill").toString().trim() : "");
			step.setTimeMinutes(
					info.get("time_minutes") != null ? Integer.parseInt(info.get("time_minutes").toString()) : 0);
			step.setImageUrl(info.get("image_url") != null ? info.get("image_url").toString().trim() : "");
			step.setSheetProcess(Long.parseLong(info.get("sheet_process_id").toString()));

			if (step.insert()) {
				response.put("success", true);
				response.put("msg", "step_added");
			} else {
				response.put("success", false);
				response.put("msg", "failed");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String updateStep(String request) {
		MessageLog.info("In SheetService addStep() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			Steps step = new Steps();
			step.setFileName(info.get("file_name") != null ? info.get("file_name").toString().trim() : "");
			step.setProcessName(info.get("process_name") != null ? info.get("process_name").toString().trim() : "");
			step.setSubprocessName(
					info.get("subprocess_name") != null ? info.get("subprocess_name").toString().trim() : "");
			step.setStepNumber(
					info.get("step_number") != null ? Integer.parseInt(info.get("step_number").toString()) : 0);
			step.setToolName(info.get("tool_name") != null ? info.get("tool_name").toString().trim() : "");
			step.setToolSpec(info.get("tool_spec") != null ? info.get("tool_spec").toString().trim() : "");
			step.setSpecialInstruction(
					info.get("special_instruction") != null ? info.get("special_instruction").toString().trim() : "");
			step.setSkill(info.get("skill") != null ? info.get("skill").toString().trim() : "");
			step.setTimeMinutes(
					info.get("time_minutes") != null ? Integer.parseInt(info.get("time_minutes").toString()) : 0);
			step.setImageUrl(info.get("image_url") != null ? info.get("image_url").toString().trim() : "");
			step.setSheetProcess(Long.parseLong(info.get("sheet_process_id").toString()));
			step.setStepId(Long.parseLong(info.get("step_id").toString()));
			Steps[] s = step.retrieveAllWhere("where step_id='" + step.getStepId() + "'");
			if (s == null || s.length <= 0) {
				response = new JSONObject();
				response.put("success", false);
				response.put("msg", "step_id_not_exist");
				return response.toString();
			}

			if (step.update()) {
				response.put("success", true);
				response.put("msg", "step_updated");
			} else {
				response.put("success", false);
				response.put("msg", "failed");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String deleteSheet(String request) {
		MessageLog.info("In SheetService deleteSheet() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			Sheets sheet = new Sheets();
			sheet.setSheetId(Long.parseLong(info.get("sheet_id").toString()));
			sheet.setFileName(info.get("file_name") != null ? info.get("file_name").toString().trim() : "");
			if (sheet.getFileName().isEmpty()) {
				response = new JSONObject();
				response.put("success", false);
				response.put("msg", "file_name_empty");
				return response.toString();
			}
			Sheets[] s = sheet.retrieveAllWhere("where sheet_id='" + sheet.getSheetId() + "' and lower(file_name)='"
					+ sheet.getFileName().toLowerCase() + "'");
			if (s == null || s.length <= 0) {
				response = new JSONObject();
				response.put("success", false);
				response.put("msg", "sheet_not_exist");
				return response.toString();
			}

			Steps step = new Steps();
			SheetProcess sheetProcess = new SheetProcess();
			Steps[] stepList = step
					.retrieveAllWhere("where lower(file_name)='" + sheet.getFileName().trim().toLowerCase() + "'");
			SheetProcess[] sheetProcessList = sheetProcess
					.retrieveAllWhere("where lower(file_name)='" + sheet.getFileName().trim().toLowerCase() + "'");
			if (sheet.delete(sheetProcessList, stepList)) {
				response.put("success", true);
				response.put("msg", "sheet_deleted");
			} else {
				response.put("success", false);
				response.put("msg", "failed");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String deleteStep(String request) {
		MessageLog.info("In SheetService deleteStep() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			Steps step = new Steps();
			step.setStepId(Long.parseLong(info.get("step_id").toString()));
			Steps[] s = step.retrieveAllWhere("where step_id='" + step.getStepId() + "'");
			if (s == null || s.length <= 0) {
				response = new JSONObject();
				response.put("success", false);
				response.put("msg", "step_not_exist");
				return response.toString();
			}
			if (step.delete()) {
				response.put("success", true);
				response.put("msg", "step_deleted");
			} else {
				response.put("success", false);
				response.put("msg", "failed");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String addSheetProcess(String request) {
		MessageLog.info("In SheetService addStep() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			SheetProcess sp = new SheetProcess();
			sp.setFileName(info.get("file_name") != null ? info.get("file_name").toString().trim() : "");
			sp.setProcessName(info.get("process_name") != null ? info.get("process_name").toString().trim() : "");
			if (sp.getFileName().isEmpty() || sp.getProcessName().isEmpty()) {
				response = new JSONObject();
				response.put("success", false);
				response.put("msg", "process_file_empty");
				return response.toString();
			}
			Sheets[] s = new Sheets().retrieveAllWhere("where lower(file_name)='" + sp.getFileName() + "'");
			Process[] p = new Process()
					.retrieveAllWhere("where lower(process_name)='" + sp.getProcessName().toLowerCase() + "'");
			if (s == null || s.length <= 0 || p == null || p.length <= 0) {
				response = new JSONObject();
				response.put("success", false);
				response.put("msg", "process_or_sheet_not_exist");
				return response.toString();
			}
			if (sp.insert()) {
				response.put("success", true);
				response.put("msg", "sheet_process_added");
			} else {
				response.put("success", false);
				response.put("msg", "failed");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}

	public String deleteSheetProcess(String request) {
		MessageLog.info("In SheetService deleteSheetProcess() request= " + request);
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			SheetProcess sheet_process = new SheetProcess();
			sheet_process.setSheetProcessId(Long.parseLong(info.get("sheet_process_id").toString()));

			Steps step = new Steps();
			SheetProcess sp[] = sheet_process
					.retrieveAllWhere("where sheet_process_id='" + sheet_process.getSheetProcessId() + "'");
			if (sp == null || sp.length <= 0) {
				response = new JSONObject();
				response.put("success", false);
				response.put("msg", "sheet_process_not_exist");
				return response.toString();
			}
			Steps[] stepList = step
					.retrieveAllWhere("where sheet_process_id='" + sheet_process.getSheetProcessId() + "'");
			if (sheet_process.delete(stepList)) {
				response.put("success", true);
				response.put("msg", "sheet_process_deleted");
			} else {
				response.put("success", false);
				response.put("msg", "failed");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();

	}
}