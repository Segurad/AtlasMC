package de.atlasmc.core.datarepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import de.atlasmc.datarepository.Repository;
import de.atlasmc.datarepository.RepositoryEntryUpdate;
import de.atlasmc.util.concurrent.future.Future;

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

	protected abstract boolean copyEntry(CoreRepositoryEntry entry, File destination, boolean override) throws IOException;

	protected abstract Future<Boolean> delete(CoreRepositoryEntry entry);

	protected abstract boolean isTouched(CoreRepositoryEntry entry, boolean shallow) throws IOException;

	protected abstract Future<RepositoryEntryUpdate> update(CoreRepositoryEntry entry);
	
}
