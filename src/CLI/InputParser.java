package CLI;

public class InputParser {

	private static InputParser INSTANCE = null;

	public static InputParser getInstance() {
		return (INSTANCE == null) ? new InputParser() : INSTANCE;
	}

	public static Command createCommand(String line) {

		int spacePosition = line.indexOf(" ");
		String commandName = (spacePosition != -1)? line.substring(0, spacePosition) : line;
		String commandArguments = (spacePosition != -1)? line.substring(spacePosition + 1) : ".";
		return switch (commandName) {
			case "add_user" -> new AddUser(commandName, commandArguments);
			case "cat" -> new Cat(commandName, commandArguments);
			case "cd" -> new Cd(commandName, commandArguments);
			case "change_user" -> new ChangeUser(commandName, commandArguments);
			case "chmod" -> new Chmod(commandName, commandArguments);
			case "delete_user" -> new DeleteUser(commandName, commandArguments);
			case "help" -> new Help(commandName, commandArguments);
			case "ls" -> new Ls(commandName, commandArguments);
			case "mkdir" -> new Mkdir(commandName, commandArguments);
			case "rm" -> new Rm(commandName, commandArguments);
			case "rmdir" -> new Rmdir(commandName, commandArguments);
			case "touch" -> new Touch(commandName, commandArguments);
			case "write_to_file" -> new WriteToFile(commandName, commandArguments);
			default -> throw new IllegalArgumentException("Command has not been recognized, try 'help' " +
					"for a list of available commands!");
		};
	}

}
