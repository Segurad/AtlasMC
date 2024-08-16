package de.atlasmc.atlasnetwork;

import java.util.List;

import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.concurrent.future.Future;

public interface PermissionManager {

	Future<List<PermissionGroup>> getGroups(String... names);

	Future<PermissionGroup> getGroup(String name);

	Future<PermissionHandler> getHandler(AtlasPlayer player);
	
	PermissionGroup getCachedGroup(String name);
	
	List<PermissionGroup> getCachedGroups(String... names);
	
	PermissionHandler getCachedHandler(AtlasPlayer player);

}
