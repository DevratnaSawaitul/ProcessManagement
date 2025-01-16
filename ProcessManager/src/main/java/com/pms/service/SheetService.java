package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SheetService {
	public static String showSheets() {
		JSONObject response = new JSONObject();
		try {
			JSONArray sheets = new JSONArray();
			sheets.add("Sheet 1");
			sheets.add("Sheet 2");
			sheets.add("Sheet 3");
			sheets.add("Sheet 4");
			sheets.add("Sheet 5");
			sheets.add("Sheet 6");
			sheets.add("Sheet 7");

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
		JSONObject response = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject info = (JSONObject) parser.parse(request);
			response = new JSONObject();
			response.put("success", true);
			response.put("msg", "done");
		} catch (Exception e) {
			response = new JSONObject();
			response.put("success", false);
			response.put("msg", "some exception");
		}
		return response.toString();
	}
}