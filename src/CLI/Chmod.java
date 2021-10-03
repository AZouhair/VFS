package CLI;

import Core.Directory; 
import Core.VFSNode;
import Core.Errors;
import Core.Permission;

public class Chmod extends FileCommand {

	public Chmod(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Errors errorInstance = Errors.getInstance();

		if (commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0, commandArguments.length() - 1);

		int spacePosition = commandArguments.indexOf(" ");
		int permissionCode = Integer.parseInt(commandArguments.substring(0, spacePosition));
		String path = commandArguments.substring(spacePosition + 1);
		String nodeName = processArguments(path);

		if (nodeName == null)
			return;

		boolean found = false;
		VFSNode toChangePermission = null;

		for (VFSNode childNode : ((Directory) targetNode).getChildNodes()) {
			if (childNode.getName().equals(nodeName)) {
				found = true;
				toChangePermission = childNode;
			}
		}

		if (!found) {
			System.out.println(Errors.getInstance().getErrorMessage(getFullCommand(), 12));
			return;
		}

		if (!Permission.checkPermissions(toChangePermission, "w")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 5));
			return;
		}

		toChangePermission.setPermission(Permission.setPermissions(permissionCode));

	}

}
