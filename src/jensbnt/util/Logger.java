package jensbnt.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Logger {

	private static List<String> current_logs;
	private static Path logs_path = Paths.get(System.getProperty("user.home")).resolve("GTSport/Logs");
	
	public Logger() {
		current_logs = new ArrayList<>();
		
		/* Make Log directory */
		if(!Files.exists(logs_path)) {
			try {
				Files.createDirectories(logs_path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveLogs() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Path newPath = logs_path.resolve(timestamp.toString().replaceAll(" ", "_").replaceAll(":", "-").replace(".", "_") + ".txt");
		try {
			Files.createFile(newPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try(BufferedWriter writer = Files.newBufferedWriter(newPath)) {
			for(String log : current_logs) {
				writer.write(log + "\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addLog(String log) {
		System.out.println("[LOG] " + log);
		current_logs.add("[LOG] " + log);
	}
	
	public static void addErrorLog(String log) {
		System.out.println("[Error] " + log);
		current_logs.add("[Error] " + log);
	}
}
