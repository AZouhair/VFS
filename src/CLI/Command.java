package CLI;

import Core.VFSNode;

public abstract class Command implements CommandInterface {

	protected String commandName;
	protected String commandArguments;
	protected VFSNode targetNode;

	public void setTargetNode(VFSNode targetNode) {
		this.targetNode = targetNode;
	}

	public Command(String commandName, String commandArguments) {
		super();
		this.commandName = commandName;
		this.commandArguments = commandArguments;
	}
	
	public String getFullCommand() {
		return commandName + " " + commandArguments;
	}

}
