package de.atlascore.datarepository;

import java.io.File;
import java.util.UUID;

public class CoreLocalRepository extends CoreAbstractLocalRepository {
	
	public CoreLocalRepository(String name, UUID uuid, File dir) {
		super(name, uuid, dir, false);
	}

}
