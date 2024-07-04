package de.atlascore.permission;

import de.atlasmc.permission.Permission;

public class CorePermission implements Permission {

	private final String permission;
	private final int value;
	
	public CorePermission(String permission, int value) {
		this.permission = permission;
		this.value = value;
	}
	
	@Override
	public String permission() {
		return permission;
	}

	@Override
	public int value() {
		return value;
	}

}
