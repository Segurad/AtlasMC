package de.atlascore.permission;

import java.util.Map;

import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.map.key.CharKey;

public class CoreGroupSetPermission {
	
	Map<String, Map<String, PermissionContext>> permissionContexts;
	Map<CharKey, Permission> permissions;
	ContextProvider context;
	
	/**
	 * Contains all handlers currently using this set
	 */
	final ConcurrentLinkedList<CorePermissionHandler> handlers;

	public CoreGroupSetPermission(Map<String, Map<String, PermissionContext>> effectiveContexts,
			Map<CharKey, Permission> effectivePermissions, ContextProvider context) {
		this.permissionContexts = effectiveContexts;
		this.permissions = effectivePermissions;
		this.context = context;
		this.handlers = new ConcurrentLinkedList<>();
	}

	public synchronized void update(CoreGroupSetPermission newGroupSet) {
		this.permissions = newGroupSet.permissions;
		this.context = newGroupSet.context;
		this.permissionContexts = newGroupSet.permissionContexts;
		for (CorePermissionHandler handler : handlers) {
			handler.groupSetUpdated(this);
		}
	}

}
