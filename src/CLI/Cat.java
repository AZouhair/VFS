package CLI;

import Core.Directory; 
import Core.File;
import Core.VFSNode;
import Core.Errors;
import Core.Permission;

public class Cat extends FileCommand {

	public Cat(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Errors errorInstance = Errors.getInstance();

		if (commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0, commandArguments.length() - 1);

		String nodeName = processArguments(commandArguments);

		if (nodeName == null)
			return;
		
		boolean found = false;
		File toRead = null;

		for (VFSNode childNode : ((Directory) targetNode).getChildNodes()) {
			if (childNode.getName().equals(nodeName)) {
				switch (childNode.getType()) {
					case "file":
						toRead = (File) childNode;
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

		if (!Permission.checkPermissions(toRead, "r")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 4));
			return;
		}

		System.out.println((toRead).getContent());
	}

}
