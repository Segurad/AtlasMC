package de.atlasmc.core.datarepository;

import java.util.UUID;

import de.atlasmc.datarepository.Repository;

public abstract class CoreAbstractRepository implements Repository {

	private final String name;
	private final UUID uuid;
	protected final boolean readonly;
	
	public CoreAbstractRepository(String name, UUID uuid, boolean readonly) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		this.name = name;
		this.uuid = uuid;
		this.readonly = readonly;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public boolean isReadOnly() {
		return readonly;
	}
	
}
