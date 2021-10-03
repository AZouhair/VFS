package CLI;

import Core.Directory;
import Core.Errors;
import Core.Shell;

public class Cd extends Command {

	public Cd(String commandName, String commandArguments) {
		super(commandName, commandArguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCommand() {
		// TODO Auto-generated method stub
		Shell terminalInstance = Shell.getInstance();
		Errors errorInstance = Errors.getInstance();

		if (commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0, commandArguments.length() - 1);

		if (Directory.checkPath(commandArguments, this)) {
			return;
		}

		if (targetNode.getType().equals("file"))
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 3));
		else
			terminalInstance.setCurrentDirectory((Directory) targetNode);
	}

}
