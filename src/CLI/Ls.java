package CLI;

import Core.Directory; 
import Core.VFSNode;
import Core.Errors;
import Core.Permission;

public class Ls extends Command {

	public Ls(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Errors errorInstance = Errors.getInstance();
		
		if(commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);

		if (Directory.checkPath(commandArguments, this)) {
			return;
		}

		if (!Permission.checkPermissions(targetNode, "r")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 4));
			return;
		}

		switch (targetNode.getType()) {
			case "file":
				System.out.println(targetNode.getName() + " " + targetNode.getType()
						+ targetNode.getPermission() + " " + targetNode.getOwner().getName());
				break;

			case "directory":
				for (VFSNode node : ((Directory) targetNode).getChildNodes())
					System.out.println(node.getName() + " " + node.getType()
							+ node.getPermission() + " " + node.getOwner().getName());
				break;
			default:
				break;
		}

	}

}
