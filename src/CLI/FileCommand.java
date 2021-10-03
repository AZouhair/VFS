package CLI;

import Core.Directory;
import Core.Errors;
import Core.Shell;

public abstract class FileCommand extends Command {

	public FileCommand(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}
	
	protected String processArguments(String commandArguments) {
		Shell terminalInstance = Shell.getInstance();
		Errors errorInstance = Errors.getInstance();
		
		int lastDelimiter = commandArguments.lastIndexOf('/');
		boolean shortPath = false;		
		String pathToCheck = null;
		String nodeName = null;		

		if (!commandArguments.contains("/")) {
			targetNode = terminalInstance.getCurrentDirectory();
			nodeName = commandArguments;
			shortPath = true;
		}

		if (!shortPath) {
			pathToCheck = commandArguments.substring(0, lastDelimiter);
			nodeName = commandArguments.substring(lastDelimiter + 1);
		}

		if (pathToCheck != null && pathToCheck.isEmpty())
			pathToCheck = "/";

		if (!shortPath && Directory.checkPath(pathToCheck, this)) {
			return null;
		}
		
		if(targetNode.getType().equals("file")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 3));
			return null;
		}
		
		return nodeName;
	}

}
