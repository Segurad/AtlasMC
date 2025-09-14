package de.atlasmc.core.permission;

import de.atlasmc.permission.PermissionContext;

public class CorePermissionContext extends CorePermissionHolder implements PermissionContext {
	
	private final int id;
	private final String key;
	private final String context;
	
	public CorePermissionContext(int id, String key, String context) {
		this.id = id;
		this.key = key;
		this.context = context;
	}

	@Override
	public String getContextKey() {
		return key;
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public int getID() {
		return id;
	}

}
