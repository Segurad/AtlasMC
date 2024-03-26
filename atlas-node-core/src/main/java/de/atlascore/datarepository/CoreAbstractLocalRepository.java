package de.atlascore.datarepository;
import de.atlasmc.datarepository.LocalRepository;

public abstract class CoreAbstractLocalRepository implements LocalRepository {
	
	public CoreAbstractLocalRepository() {}

	@Override
	public boolean isReadOnly() {
		return false;
	}
	
}
