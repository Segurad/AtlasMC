package de.atlasmc;

import java.util.UUID;

public interface NameUUIDDispatcher {

	public void updateName(UUID uuid, String name);
	public String getName(UUID uuid);
	public UUID getUUID(String name);
	public void create(UUID uuid, String name);
	public boolean containsName(String name);
	public boolean containsUUID(UUID uuid);
	
}
