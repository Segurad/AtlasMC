package de.atlasmc.master;

import java.util.List;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;

public interface PermissionManager {
	
	Permission createPermission(String permission);
	
	Permission createPermission(String permission, int value);
	
	PermissionContext createContext(String key, String value);
	
	PermissionGroup createGroup(String name);
	
	PermissionGroup getGroup(String name);
	
	PermissionGroup getGroup(String name, boolean load);
	
	boolean removeGroup(String name);
	
	PermissionHandler getHandler(AtlasPlayer player);

	List<PermissionGroup> getGroups(String... names);

}
