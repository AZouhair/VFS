package Core;


public class Permission {

	public static String getImplicitPermissions() {
		String implicitPermissions = "rwx---";
		return implicitPermissions;
	}

	public static String getRootDirectoryPermissions() {
		String rootDirectoryPermissions = "rwxr-x";
		return rootDirectoryPermissions;
	}

	public static boolean checkPermissions(VFSNode node, String toCheck) {
		boolean Valid = false;
		Shell terminalInstance = Shell.getInstance();
		User activeUser = terminalInstance.getActiveUser();
		String ownerPermissions = node.getPermission().substring(0, 3);
		String othersPermissions = node.getPermission().substring(3);
		
		if (activeUser.getName().equals("root"))
			Valid = true;
		else {
			if (node.getOwner().equals(activeUser)) {
				if (ownerPermissions.contains(toCheck))
					Valid = true;
			} else if (othersPermissions.contains(toCheck))
				Valid = true;
		}

		return Valid;
	}

	public static String setPermissions(Integer newPermissionCode) {
		/*
		 * We calculate the binary representation of the permission code.
		 */
		String ownerPermission = String
				.format("%3s", Integer.toBinaryString(newPermissionCode / 10)).replace(" ", "0");
		String othersPermission = String
				.format("%3s", Integer.toBinaryString(newPermissionCode % 10)).replace(" ", "0");

		/*
		 * We replace the binary digits with the corresponding permissions.
		 */
		char[] permissions = { 'r', 'w', 'x' };
		char[] ownerArray = ownerPermission.toCharArray();
		char[] othersArray = othersPermission.toCharArray();

		for (int i = 0; i < ownerPermission.length(); i++)
			if (ownerArray[i] == '1')
				ownerArray[i] = permissions[i];
			else
				ownerArray[i] = '-';

		for (int i = 0; i < othersPermission.length(); i++)
			if (othersArray[i] == '1')
				othersArray[i] = permissions[i];
			else
				othersArray[i] = '-';

		return new String(ownerArray).concat(new String(othersArray));
	}

}
