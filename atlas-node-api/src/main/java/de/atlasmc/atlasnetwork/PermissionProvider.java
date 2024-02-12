package de.atlasmc.atlasnetwork;

import java.util.List;

import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.concurrent.future.Future;

public interface PermissionProvider {

	Future<List<PermissionGroup>> getGroups(String... names);

	Future<PermissionGroup> getGroup(String name, boolean load);

	Future<PermissionHandler> getHandler(AtlasPlayer player);

}
