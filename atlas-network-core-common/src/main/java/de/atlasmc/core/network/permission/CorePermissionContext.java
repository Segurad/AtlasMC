package de.atlasmc.core.network.permission;

import java.util.Objects;
import java.util.UUID;

import de.atlasmc.network.permission.PermissionContext;

public class CorePermissionContext extends CorePermissionHolder implements PermissionContext {
	
	private final UUID uuid;
	private final String key;
	private final String context;
	
	public CorePermissionContext(UUID uuid, String key, String context) {
		this.uuid = Objects.requireNonNull(uuid);
		this.key = Objects.requireNonNull(key);
		this.context = Objects.requireNonNull(context);
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
	public UUID getUUID() {
		return uuid;
	}

}
