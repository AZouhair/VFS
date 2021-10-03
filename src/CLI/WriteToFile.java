package CLI;

import Core.Directory; 
import Core.File;
import Core.VFSNode;
import Core.Errors;
import Core.Permission;

public class WriteToFile extends FileCommand {

	public WriteToFile(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Errors errorInstance = Errors.getInstance();

		if(commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);

		int spacePosition = commandArguments.indexOf(" ");
		String path = commandArguments.substring(0, spacePosition);
		String content = commandArguments.substring(spacePosition + 1);
		String nodeName = processArguments(path);

		if (nodeName == null)
			return;
		
		File toWrite = null;
		boolean found = false;

		for (VFSNode childNode : ((Directory) targetNode).getChildNodes()) {
			if (childNode.getName().equals(nodeName)) {
				switch (childNode.getType()) {
					case "file":
						toWrite = (File) childNode;
						found = true;
						break;

					case "directory":
						System.out.println(errorInstance.getErrorMessage(getFullCommand(), 1));
						return;

					default:
						break;
				}
			}
		}

		if (!found) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 11));
			return;
		}

		if (!Permission.checkPermissions(toWrite, "w")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 5));
			return;
		}

		(toWrite).setContent(content);
	}

}
