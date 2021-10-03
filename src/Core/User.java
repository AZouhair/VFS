package Core;

import java.util.ArrayList;

public class User {

	private String name;
	private Directory homeDirectory;
	private ArrayList<VFSNode> ownedNodes;

	public User(String name) {
		this.name = name;
		this.ownedNodes = new ArrayList<VFSNode>();
	}

	public User(String name, Directory homeDirectory) {
		this.name = name;
		this.homeDirectory = homeDirectory;
		this.ownedNodes = new ArrayList<VFSNode>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Directory getHomeDirectory() {
		return homeDirectory;
	}

	public void setHomeDirectory(Directory homeDirectory) {
		this.homeDirectory = homeDirectory;
	}

	public ArrayList<VFSNode> getOwnedNodes() {
		return ownedNodes;
	}

	public void setOwnedNodes(ArrayList<VFSNode> ownedNodes) {
		this.ownedNodes = ownedNodes;
	}
}
