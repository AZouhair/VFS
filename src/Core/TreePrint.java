package Core;

public class TreePrint implements Visitor {

	private Integer indentLevel;

	public TreePrint() {
		this.indentLevel = 1;
	}

	@Override
	public void print(File file) {
		System.out.print("|_");
		for (int i = 0; i < indentLevel; i++)
			System.out.print("___ ");

		System.out.println(file.getName() + " " + file.getType()
				+ file.getPermission() + "   " + file.getOwner().getName());
	}

	@Override
	public void print(Directory directory) {
		System.out.print("|_");
		for (int i = 0; i < indentLevel ; i++)
			System.out.print("__ ");

		System.out.println(directory.getName() + " " + directory.getType()
				+ directory.getPermission() + " "
				+ directory.getOwner().getName());

		for (VFSNode node : directory.getChildNodes()) {
			indentLevel++;
			node.accept(this);
			indentLevel--;
		}

	}

}
