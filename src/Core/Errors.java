package Core;

import java.util.HashMap;

public class Errors {

	private static Errors INSTANCE = null;
	private HashMap<Integer, String> errorMap;

	private Errors() {
		this.errorMap = new HashMap<>();
		errorMap.put(1, "Is a directory");
		errorMap.put(2, "No such directory");
		errorMap.put(3, "Not a directory");
		errorMap.put(4, "No rights to read");
		errorMap.put(5, "No rights to write");
		errorMap.put(6, "No rights to execute");
		errorMap.put(7, "File already exists");
		errorMap.put(8, "User does not exist");
		errorMap.put(9, "User already exists");
		errorMap.put(10, "No rights to change user status");
		errorMap.put(11, "No such file");
		errorMap.put(12, "No such file or directory");
		errorMap.put(13, "Cannot delete parent or current directory");
		errorMap.put(14, "Non empty directory");
	}

	public static Errors getInstance() {
		return (INSTANCE == null) ? new Errors() : INSTANCE;
	}

	public String getErrorMessage(String commandName, int errorNumber) {
		return "code " + errorNumber + ": " + commandName + ": "
				+ errorMap.get(errorNumber);

	}
}
