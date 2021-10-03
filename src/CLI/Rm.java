package CLI;

import Core.Directory;
import Core.VFSNode;
import Core.Errors;
import Core.Shell;
import Core.Permission;

public class Rm extends FileCommand {

	public Rm(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Shell terminalInstance = Shell.getInstance();
		Errors errorInstance = Errors.getInstance();
		String recursive = null;
		String path = null;
		
		if(commandArguments.endsWith("/") && !commandArguments.equals("-r /"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);

		if (commandArguments.contains("-r ")) {
			int spacePosition = commandArguments.indexOf(" ");
			recursive = commandArguments.substring(0, spacePosition);
			path = commandArguments.substring(spacePosition + 1);			
		} else {
			path = commandArguments;
		}

		if (recursive != null && (path.equals(".") || path.equals("..") || path.equals("/"))) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 13));
			return;
		}

		String nodeName = processArguments(path);

		if (nodeName == null)
			return;

		boolean found = false;
		VFSNode toRemove = null;

		for (VFSNode childNode : ((Directory) targetNode).getChildNodes()) {
			if (childNode.getName().equals(nodeName)) {
				switch (childNode.getType()) {
					case "file":
						found = true;
						toRemove = childNode;
						break;

					case "directory":
						if (recursive == null) {
							System.out.println(errorInstance.getErrorMessage(getFullCommand(), 1));
							return;
						} else
							found = true;
						toRemove = childNode;
						break;

					default:
						break;
				}
			}
		}

		if (recursive == null && !found) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 11));
			return;
		}

		if (recursive != null && found) {

			VFSNode auxiliary = terminalInstance.getCurrentDirectory();
			while (!auxiliary.equals(terminalInstance.getRootDirectory())) {
				if (toRemove.equals(auxiliary)) {
					System.out.println(errorInstance.getErrorMessage(getFullCommand(), 13));
					return;
				}
				auxiliary = auxiliary.getParent();
			}
		}

		if (recursive != null && !found) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 12));
			return;
		}

		if (!Permission.checkPermissions(targetNode, "w")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 5));
			return;
		}

		((Directory) targetNode).getChildNodes().remove(toRemove);
		toRemove.getOwner().getOwnedNodes().remove(toRemove);
		toRemove.setParent(null);
		toRemove = null;

	}

}
