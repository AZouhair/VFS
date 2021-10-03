package Core;

import java.util.ArrayList;

import CLI.Command;
import CLI.InputParser;

public class Shell {

	private static Shell INSTANCE = null;

	private User root;
	private Directory rootDirectory;
	private User activeUser;
	private ArrayList<User> registeredUsers;
	private Directory currentDirectory;

	private Shell() {
		root = new User("root");
		rootDirectory = new Directory("/", root, Permission.getRootDirectoryPermissions());
		rootDirectory.setParent(rootDirectory);
		root.setHomeDirectory(rootDirectory);
		activeUser = root;
		registeredUsers = new ArrayList<>();
		registeredUsers.add(root);
		currentDirectory = rootDirectory;
	}

	public static Shell getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Shell();
		return INSTANCE;
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}

	public ArrayList<User> getRegisteredUsers() {
		return registeredUsers;
	}

	public Directory getRootDirectory() {
		return rootDirectory;
	}

	public Directory getCurrentDirectory() {
		return currentDirectory;
	}

	public void setCurrentDirectory(Directory currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	public void ShellResponse(String input) {

		Command command;
		command = InputParser.createCommand(input);
		command.executeCommand();

		TreePrint treePrint = new TreePrint();

		System.out.println("\nVFS Tree: \n");
		rootDirectory.accept(treePrint);
	}

}
