package CLI;

import Core.Errors;
import Core.Shell;
import Core.User;

public class ChangeUser extends Command {

	public ChangeUser(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		Shell terminalInstance = Shell.getInstance();
		Errors errorInstance = Errors.getInstance();

		String username = commandArguments;
		User toChange = null;
		Boolean existsUser = false;
		
		for (User user : terminalInstance.getRegisteredUsers())
			if (user.getName().equals(username)) {
				existsUser = true;
				toChange = user;
			}

		if (!existsUser) {
			System.out.println(errorInstance.getErrorMessage(getFullCommand(), 8));
			return;
		}
		
		terminalInstance.setActiveUser(toChange);
		terminalInstance.setCurrentDirectory(toChange.getHomeDirectory());
	}

}
