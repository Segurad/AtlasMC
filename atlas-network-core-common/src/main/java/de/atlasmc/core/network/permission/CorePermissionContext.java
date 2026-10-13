package de.atlasmc.core.network.permission;

import java.util.Objects;

import de.atlasmc.network.permission.PermissionContext;

public class CorePermissionContext extends CorePermissionHolder implements PermissionContext {
	
	private final int id;
	private final String key;
	private final String context;
	
	public CorePermissionContext(int id, String key, String context) {
		this.id = id;
		this.key = Objects.requireNonNull(key, "key");
		this.context = Objects.requireNonNull(context, "context");
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
