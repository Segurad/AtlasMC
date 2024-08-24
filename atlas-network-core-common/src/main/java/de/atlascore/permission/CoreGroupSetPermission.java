package de.atlascore.permission;

import java.util.Map;

import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.util.map.key.CharKey;

public class CoreGroupSetPermission {
	
	final Map<String, Map<String, PermissionContext>> permissionContexts;
	final Map<CharKey, Permission> permissions;
	final ContextProvider context;

	public CoreGroupSetPermission(Map<String, Map<String, PermissionContext>> effectiveContexts,
			Map<CharKey, Permission> effectivePermissions, ContextProvider context) {
		this.permissionContexts = effectiveContexts;
		this.permissions = effectivePermissions;
		this.context = context;
	}

}
