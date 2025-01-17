package com.pms.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.pms.db.Sheets;
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

	public static String addSheets(String request) {
		MessageLog.info("In SheetService addSheets() request= " + request);
		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			Sheets s = new Sheets();
			s.setFileName(info.get("file_name") != null ? (String) info.get("file_name") : "");
			Sheets sList[] = s.retrieveAllWhere(" where file_name='" + s.getFileName() + "'");
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
				response.put("msg", "sheet_add_fail");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("msg", "some exception");
			MessageLog.printError(e);
		}
		return response.toString();
	}
}