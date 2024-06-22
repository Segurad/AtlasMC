package de.atlascore.datarepository;

import java.io.File;

public class CoreLocalRepository extends CoreAbstractLocalRepository {
	
	public CoreLocalRepository(String name, File dir) {
		super(name, dir, false);
	}

}
