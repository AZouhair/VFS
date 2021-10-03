package CLI;

import Core.Directory; 
import Core.VFSNode;
import Core.Errors;
import Core.Shell;
import Core.Permission;
import Core.User;

public class Mkdir extends FileCommand {

	private static int attempts = 0;

	public Mkdir(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Shell terminalInstance = Shell.getInstance();
		Errors errorInstance = Errors.getInstance();
		
		if (commandArguments.equals("/") && attempts == 0) {
			attempts++;
			return;
		} else if (commandArguments.equals("/")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 1));
			return;
		}

		if(commandArguments.endsWith("/"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);
		
		String nodeName = processArguments(commandArguments);

		if (nodeName == null)
			return;
		
		boolean existsAlready = false;
		
		for (VFSNode childNode : ((Directory) targetNode).getChildNodes()) {
			if (childNode.getName().equals(nodeName)) {
				switch (childNode.getType()) {
					case "file":
						System.out.println(errorInstance.getErrorMessage(getFullCommand(), 3));
						existsAlready = true;
						break;

					case "directory":
						System.out.println(errorInstance.getErrorMessage(getFullCommand(), 1));
						existsAlready = true;
						break;
				}
			}
		}

		if (!Permission.checkPermissions(targetNode, "w")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 5));
			return;
		}

		if (existsAlready)
			return;

		User activeUser = terminalInstance.getActiveUser();
		Directory newDirectory = new Directory(nodeName, activeUser,
				Permission.getImplicitPermissions());
		newDirectory.setParent((Directory) targetNode);
		((Directory) targetNode).getChildNodes().add(newDirectory);
		activeUser.getOwnedNodes().add(newDirectory);

	}
}
