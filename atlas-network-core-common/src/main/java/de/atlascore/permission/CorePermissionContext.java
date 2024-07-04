package de.atlascore.permission;

import de.atlasmc.permission.PermissionContext;

public class CorePermissionContext extends CorePermissionHolder implements PermissionContext {
	
	private final String key;
	private final String context;
	
	public CorePermissionContext(String key, String context) {
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

}
