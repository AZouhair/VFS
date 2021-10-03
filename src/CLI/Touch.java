package CLI;

import Core.Directory;
import Core.File;
import Core.VFSNode;
import Core.Errors;
import Core.Shell;
import Core.Permission;
import Core.User;

public class Touch extends FileCommand{

	public Touch(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Shell terminalInstance = Shell.getInstance();
		Errors errorInstance = Errors.getInstance();
		
		if(commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);
		
		String nodeName = processArguments(commandArguments);

		if (nodeName == null)
			return;
		
		if (!Permission.checkPermissions(targetNode, "w")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 5));
			return;
		}
		
		boolean exists = false;
		
		for (VFSNode childNode : ((Directory) targetNode).getChildNodes()) {
			if (childNode.getName().equals(nodeName)) {
				switch (childNode.getType()) {
					case "file":
						System.out.println(errorInstance.getErrorMessage(getFullCommand(), 7));
						exists = true;
						break;

					case "directory":
						System.out.println(errorInstance.getErrorMessage(getFullCommand(), 1));
						exists = true;
						break;

					default:
						break;
				}
			}
		}		

		if (exists)
			return;

		User activeUser = terminalInstance.getActiveUser();
		File newFile = new File(nodeName,activeUser,Permission.getImplicitPermissions());
		newFile.setParent((Directory) targetNode);
		((Directory) targetNode).getChildNodes().add(newFile);
		activeUser.getOwnedNodes().add(newFile);		
	}
}
