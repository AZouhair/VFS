package Core;

import java.util.ArrayList;

public class VFSVisitor implements Visitor {

	private Errors errorInstance = Errors.getInstance();
	private int index;
	private ArrayList<String> pathNodes;
	private String target;
	private String command;
	private boolean targetReached;
	private VFSNode targetNode;
	private boolean noExecute = false;

	public VFSVisitor(ArrayList<String> pathNodes, String command) {
		this.pathNodes = pathNodes;
		this.index = 0;
		this.target = pathNodes.get(pathNodes.size() - 1);
		this.command = command;
		this.targetReached = false;
		this.targetNode = null;
	}

	public boolean isNoExecute() {
		return noExecute;
	}

	public boolean isTargetReached() {
		return targetReached;
	}

	public VFSNode getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(VFSNode targetNode) {
		this.targetNode = targetNode;
	}

	@Override
	public void print(File file) {
		if (file.getName().equals(target)) {
			this.targetReached = true;
			this.targetNode = file;
		} else {
			System.out.println(errorInstance.getErrorMessage(command, 3));
		}

	}

	private void auxiliaryCheck(Directory directory) {
		/*
		 * If we reach a leaf directory, and the next element in the path is
		 * .(current directory) or .. (parent directory) we have to return to
		 * the parent nodes.
		 */
		if (pathNodes.get(index).equals(".")) {
			index++;
			directory.accept(this);
			index--;
		} else if (pathNodes.get(index).equals("..")) {
			index++;
			if (Permission.checkPermissions(directory.getParent(), "x"))
				directory.getParent().accept(this);
			else {
				System.out.println(errorInstance.getErrorMessage(command, 6));
				noExecute = true;
				return;
			}
			index--;
		}
	}

	@Override
	public void print(Directory directory) {
		/*
		 * The index indicates the current file system node in the path from
		 * source to target. If the path is absolute, then the index 0 indicates
		 * the current directory(root) and it must be incremented in order to
		 * indicate the next node.
		 */
		if (directory.getName().equals("/") && index == 0 && pathNodes.get(0).equals("/"))
			index++;

		/*
		 * If the index is equal to the size of the path, then we have examined
		 * all the nodes on that path.
		 */
		if (index == pathNodes.size()) {
			targetReached = true;
			this.targetNode = directory;
		} else if (directory.getChildNodes().size() == 0) {
			auxiliaryCheck(directory);
		} else {
			/*
			 * If the current directory is not an empty one, then we search the
			 * next element from the path in its child nodes.
			 */
			auxiliaryCheck(directory);
			
			if(noExecute)
				return;
			
			for (VFSNode node : directory.getChildNodes()) {
				if (node.getName().equals(pathNodes.get(index))) {
					index++;
					if (node.getType().equals("directory")) {
						if (Permission.checkPermissions(node, "x")) {
							node.accept(this);

						} else {
							System.out.println(errorInstance.getErrorMessage(command, 6));
							noExecute = true;
							return;
						}

					} else
						node.accept(this);
					index--;

				}
			}

		}
	}

}
