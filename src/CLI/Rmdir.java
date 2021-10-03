package CLI;

import Core.Directory;
import Core.VFSNode;
import Core.Errors;
import Core.Shell;
import Core.Permission;

public class Rmdir extends FileCommand {

	public Rmdir(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Shell terminalInstance = Shell.getInstance();
		Errors errorInstance = Errors.getInstance();
		String copy = commandArguments;
		if (commandArguments.endsWith("/") && !commandArguments.equals("/"))
			copy = commandArguments.substring(0, commandArguments.length() - 1);

		if (copy.equals(".") || copy.equals("..")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 13));
			return;
		}

		if (copy.equals("/..")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 13));
			return;
		}

		String nodeName = processArguments(copy);

		if (nodeName == null)
			return;

		boolean found = false;
		Directory toRemove = null;

		for (VFSNode childNode : ((Directory) targetNode).getChildNodes()) {
			if (childNode.getName().equals(nodeName)) {
				switch (childNode.getType()) {
					case "file":
						System.out.println(errorInstance.getErrorMessage(getFullCommand(), 3));
						return;

					case "directory":
						found = true;
						toRemove = (Directory) childNode;
						break;

					default:
						break;
				}
			}
		}

		if (found) {
			VFSNode auxiliary = terminalInstance.getCurrentDirectory();
			while (!auxiliary.equals(terminalInstance.getRootDirectory())) {
				if (toRemove.equals(auxiliary)) {
					System.out.println(errorInstance.getErrorMessage(getFullCommand(), 13));
					return;
				}
				auxiliary = auxiliary.getParent();
			}
		}

		if (found && toRemove.getChildNodes().size() != 0) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 14));
			return;
		}

		if (found && !Permission.checkPermissions(targetNode, "w")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 5));
			return;
		}

		if (!found) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 2));
			return;
		}

		((Directory) targetNode).getChildNodes().remove(toRemove);
		toRemove.getOwner().getOwnedNodes().remove(toRemove);
		toRemove.setParent(null);
		toRemove = null;
	}

}
