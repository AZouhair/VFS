package CLI;

import Core.Directory;
import Core.Errors;
import Core.Shell;
import Core.Permission;
import Core.User;

public class AddUser extends Command {

	public AddUser(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {

		Errors errorInstance = Errors.getInstance();
		
		Shell shellInstance = Shell.getInstance();
		String activeUserName = shellInstance.getActiveUser().getName();

		String username = commandArguments;

		/*
		 *  Root user should be the only one allowed to add other users
		 */
		if (!activeUserName.equals("root")) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 10));
			return;
		}

		/*
		 * New username should not collide with an existing user
		 */
		for (User user : shellInstance.getRegisteredUsers())
			if (user.getName().equals(username)) {
				System.out.println(errorInstance.getErrorMessage(getFullCommand(), 9));
				return;
			}

		User newUser = new User(username);
		Directory homeDirectory = new Directory(username, newUser,
				Permission.getImplicitPermissions());
		homeDirectory.setParent(shellInstance.getRootDirectory());
		newUser.setHomeDirectory(homeDirectory);
		newUser.getOwnedNodes().add(homeDirectory);
		shellInstance.getRegisteredUsers().add(newUser);
		shellInstance.getRootDirectory().getChildNodes().add(homeDirectory);
	}

}
