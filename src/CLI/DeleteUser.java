package CLI;

import Core.VFSNode;
import Core.Errors;
import Core.Shell;
import Core.User;

public class DeleteUser extends Command {

	public DeleteUser(String commandName, String commandArguments) {
		super(commandName, commandArguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCommand() {
		// TODO Auto-generated method stub
		Shell terminalInstance = Shell.getInstance();
		Errors errorInstance = Errors.getInstance();

		String username = commandArguments;
		User toBeDeleted = null;
		String activeUserName = terminalInstance.getActiveUser().getName();
		Boolean existsUser = false;

		/*
		 * Only root can delete existing users.
		 */
		if (!activeUserName.equals("root")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 10));
			return;
		}

		for (User user : terminalInstance.getRegisteredUsers())
			if (user.getName().equals(username)) {
				existsUser = true;
				toBeDeleted = user;
			}

		if (!existsUser) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 8));
			return;
		}

		/*
		 * The nodes owned by the user that will be deleted must change
		 * their owner. The new owner will be the first user added by root.
		 */
		for (VFSNode node : toBeDeleted.getOwnedNodes()) {
			node.setOwner(terminalInstance.getRegisteredUsers().get(1));
		}
		
		terminalInstance.getRegisteredUsers().remove(toBeDeleted);
		toBeDeleted = null;
	}

}
