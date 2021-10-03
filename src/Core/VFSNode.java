package Core;

public abstract class VFSNode {
	// node on the VFS Tree can either be a file or a directory
	protected String name;
	protected User owner;
	protected String permission;
	protected String type;
	protected Directory parent;

	public VFSNode(String name, User owner, String permission) {
		super();
		this.name = name;
		this.owner = owner;
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Directory getParent() {
		return parent;
	}

	public void setParent(Directory parent) {
		this.parent = parent;
	}

	public abstract void accept(Visitor v);
}
