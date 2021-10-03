package Core;

import java.util.ArrayList; 
import java.util.StringTokenizer;

import CLI.Command;

public class Directory extends VFSNode {

	private ArrayList<VFSNode> childNodes;

	public Directory(String name, User owner, String permission) {
		super(name, owner, permission);
		childNodes = new ArrayList<>();
		this.type = "directory";
	}

	public ArrayList<VFSNode> getChildNodes() {
		return childNodes;
	}

	public static boolean checkPath(String path, Command command) {
		ArrayList<String> pathNodes = new ArrayList<>();
		Shell terminalInstance = Shell.getInstance();

		boolean isAbsolute = false;
		boolean isValid = false;
		/*
		 * If the path starts with /, then the path is absolute.
		 */
		if ("/".equals(path.substring(0, 1))) {
			pathNodes.add("/");
			isAbsolute = true;
		}

		StringTokenizer st = new StringTokenizer(path, "/");
		/*
		 * We create an array that contains all the nodes of the path.
		 */
		while (st.hasMoreTokens()) {
			pathNodes.add(st.nextToken());
		}

		VFSVisitor v = new VFSVisitor(pathNodes, command.getFullCommand());

		if (isAbsolute)
			v.print(terminalInstance.getRootDirectory());
		else
			v.print(terminalInstance.getCurrentDirectory());

		if (!v.isTargetReached() && !v.isNoExecute()) {
			if (command.getFullCommand().contains("ls") )
				System.out.println(
						Errors.getInstance().getErrorMessage(command.getFullCommand(), 12));
			else
				System.out.println(
						Errors.getInstance().getErrorMessage(command.getFullCommand(), 2));
		} else if (v.isTargetReached()) {
			isValid = true;
			command.setTargetNode(v.getTargetNode());
		}

		return !isValid;
	}

	@Override
	public void accept(Visitor v) {
		v.print(this);
	}


}
