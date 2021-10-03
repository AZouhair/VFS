package Core;

public class File extends VFSNode {
	
	private String content;

	public File(String name, User owner, String permission) {
		super(name, owner, permission);
		this.type = "file";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void accept(Visitor v) {
		v.print(this);
	}

}