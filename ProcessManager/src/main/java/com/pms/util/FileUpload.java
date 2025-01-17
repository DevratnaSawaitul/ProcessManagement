package com.pms.util;

import java.net.URISyntaxException;

public class FileUpload {
	public static String filepath() {
		String os_Name = System.getProperty("os.name");
		String path = null;
		try {
			path = FileUpload.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			if (os_Name.contains("Windows") || os_Name.contains("Window"))
				path = path.substring(1, path.indexOf("WEB-INF")).replace("/", "\\");
			else
				path = path.substring(0, path.indexOf("WEB-INF"));
		} catch (URISyntaxException e) {
			// e.printStackTrace();
			MessageLog.printError(e);
		}
		return path;

	}
}
