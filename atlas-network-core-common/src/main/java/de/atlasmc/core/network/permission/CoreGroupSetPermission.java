package de.atlasmc.core.network.permission;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.network.permission.ContextProvider;
import de.atlasmc.network.permission.PermissionContext;
import de.atlasmc.permission.Permission;
import de.atlasmc.util.map.key.CharKey;

/**
 * The group set is a aggregations of groups combining their permissions, contextual, permissions and context
 */
public class CoreGroupSetPermission {
	
	Map<String, Map<String, PermissionContext>> permissionContexts;
	Map<CharKey, Permission> permissions;
	ContextProvider context;
	
	/**
	 * Contains all handlers currently using this set
	 */
	final Set<CorePermissionHandler> handlers;

	public CoreGroupSetPermission(Map<String, Map<String, PermissionContext>> effectiveContexts,
			Map<CharKey, Permission> effectivePermissions, ContextProvider context) {
		this.permissionContexts = effectiveContexts;
		this.permissions = effectivePermissions;
		this.context = context;
		this.handlers = ConcurrentHashMap.newKeySet();
	}

	public synchronized void update(CoreGroupSetPermission newGroupSet) {
		this.permissions = newGroupSet.permissions;
		this.context = newGroupSet.context;
		this.permissionContexts = newGroupSet.permissionContexts;
		for (CorePermissionHandler handler : handlers) {
			handler.onGroupSetUpdated(this);
		}
	}

}
