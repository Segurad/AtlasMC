package de.atlascore.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.permission.PermissionHolder;

public class CorePermissionHandler extends CorePermissionContextHolder implements PermissionHandler {

	private static final PermissionContext[] EMPTY = {};
	
	private final int id;
	private final CorePermissionGroupHolder groups;
	private final PermissionHolder permissions;
	private final ContextProvider contextProvider;
	private final ContextProvider tempProvider;
	
	private final CoreAbstractPermissionManager manager;
	private CoreGroupSetPermission groupSet;
	private PermissionContext[] activeContexts = EMPTY;
	private PermissionContext[] activeGroupContexts = EMPTY;
	
	public CorePermissionHandler(int id, CoreAbstractPermissionManager manager) {
		this.id = id;
		groups = new CorePermissionGroupHolder();
		permissions = new CorePermissionHolder();
		contextProvider = new CorePermissionHandlerContextProvider(this);
		tempProvider = new CorePermissionHandlerContextProvider(this);
		this.manager = manager;
	}
	
	void onContextChange(String key, String oldValue, String newValue) {
		PermissionContext oldCtx = null;
		PermissionContext newCtx = null;
		PermissionContext oldGroupCtx = null;
		PermissionContext newGroupCtx = null;
		Map<String, PermissionContext> ctxs = permContext.get(key);
		if (ctxs != null) {
			oldCtx = ctxs.get(oldValue);
			newCtx = ctxs.get(newValue);
		}
		CoreGroupSetPermission groupSet = this.groupSet;
		if (groupSet != null) {
			Map<String, PermissionContext> groupCtxs = groupSet.permissionContexts.get(key);
			if (groupCtxs != null) {
				oldGroupCtx = groupCtxs.get(oldValue);
				newGroupCtx = groupCtxs.get(newValue);
			}
		}
		if (oldCtx == null && newCtx == null && oldGroupCtx == null && newGroupCtx == null)
			return; // no changes everything is null
		if (oldCtx == newCtx && oldGroupCtx == newGroupCtx)
			return; // no changes everything is equal
		synchronized (this) {
			activeContexts = updateContexts(oldCtx, newCtx, activeContexts);
			activeGroupContexts = updateContexts(oldGroupCtx, newGroupCtx, activeGroupContexts);
		}
	}
	
	private PermissionContext[] updateContexts(PermissionContext oldCtx, PermissionContext newCtx, PermissionContext[] oldContexts) {
		PermissionContext[] newContexts = null;
		if (oldCtx == null) {
			if (newCtx != null) { // new ctx active
				newContexts = Arrays.copyOf(oldContexts, oldContexts.length + 1);
				newContexts[newContexts.length - 1] = newCtx;
			}
		} else if (newCtx == null) { // ctx no longer active
			int length = oldContexts.length - 1;
			if (length < 1)
				return EMPTY; // avoid creating empty arrays
			PermissionContext[] newActive = new PermissionContext[oldContexts.length - 1];
			int i = 0;
			for (PermissionContext ctx : oldContexts) {
				if (ctx == oldCtx)
					continue;
				newActive[i] = ctx;
			}
		} else if (oldCtx != newCtx) { // ctx has changed
			newContexts = oldContexts;
			final int length = newContexts.length;
			for (int i = 0; i < length; i++) {
				if (newContexts[i] != oldCtx)
					continue;
				newContexts[i] = newCtx;
				break;
			}
		} else { // no changes
			newContexts = oldContexts;
		}
		return newContexts;
	}
	
	public int getID() {
		return id;
	}

	@Override
	public Collection<Permission> getPermissions() {
		return permissions.getPermissions();
	}

	@Override
	public void setPermission(String permission, int value) {
		permissions.setPermission(permission, value);
	}

	@Override
	public void setPermission(Permission permission) {
		permissions.setPermission(permission);
	}

	@Override
	public void removePermission(String permission) {
		permissions.removePermission(permission);
	}

	@Override
	public void removePermission(Permission permission) {
		permissions.removePermission(permission);
	}

	@Override
	public Collection<PermissionGroup> getGroups() {
		return this.groups.getGroups();
	}	

	@Override
	public PermissionGroup getHighestGroup() {
		return this.groups.getHighestGroup();
	}

	@Override
	public void addPermissionGroup(PermissionGroup group) {
		this.groups.addPermissionGroup(group);
	}

	@Override
	public void removePermissionGroup(PermissionGroup group) {
		this.groups.removePermissionGroup(group);
	}

	@Override
	public boolean hasGroupsChanged() {
		return groups.hasGroupsChanged();
	}

	@Override
	public synchronized void groupsChanged() {
		CoreGroupSetPermission groupSet = manager.getGroupSetPermissions(groups.getGroups());
		ArrayList<PermissionContext> context = new ArrayList<>(5);
		ArrayList<PermissionContext> groupContext = new ArrayList<>(5);
		groups.groupsChanged();
		getActiveContexts(tempProvider.getContext(), context, groupContext);
		getActiveContexts(contextProvider.getContext(), context, groupContext);
		getActiveContexts(groupSet.context.getContext(), context, groupContext);
		if (!context.isEmpty()) {
			activeContexts = (PermissionContext[]) context.toArray();
		} else {
			activeContexts = EMPTY;
		}
		if (!groupContext.isEmpty()) {
			activeGroupContexts = (PermissionContext[]) context.toArray();
		} else {
			activeContexts = EMPTY;
		}
		this.groupSet = groupSet;
	}
	
	private void getActiveContexts(Map<String, String> context, List<PermissionContext> contexts, List<PermissionContext> groupContexts) {
		if (context.isEmpty())
			return;
		for (Entry<String, String> entry : context.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			Map<String, PermissionContext> ctxs = permContext.get(key);
			if (ctxs != null) {
				PermissionContext ctx = ctxs.get(value);
				if (ctx != null) {
					contexts.add(ctx);
				}
			}
			Map<String, PermissionContext> groupCtxs = groupSet.permissionContexts.get(key);
			if (groupCtxs != null) {
				PermissionContext ctx = groupCtxs.get(value);
				if (ctx != null) {
					groupContexts.add(ctx);
				}
			}
		}
	}

	@Override
	public boolean hasChangedPermissions() {
		return permissions.hasChangedPermissions();
	}

	@Override
	public void changedPermissions() {
		permissions.changedPermissions();
	}

	@Override
	public Permission getPermission(CharSequence permission) {
		Permission perm = permissions.getPermission(permission);
		if (perm != null)
			return perm;
		final PermissionContext[] active = activeContexts;
		for (PermissionContext ctx : active) {
			if (ctx == null)
				break;
			perm = ctx.getPermission(permission);
			if (perm != null)
				return perm;
		}
		final CoreGroupSetPermission groupSet = this.groupSet;
		if (groupSet == null)
			return null;
		perm = groupSet.permissions.get(permission);
		if (perm != null)
			return perm;
		final PermissionContext[] activeGroups = activeGroupContexts;
		for (PermissionContext ctx : activeGroups) {
			if (ctx == null)
				break;
			perm = ctx.getPermission(permission);
			if (perm != null)
				return perm;
		}
		return null;
	}

	@Override
	public ContextProvider getTempContext() {
		return tempProvider;
	}

	@Override
	public ContextProvider getContext() {
		return contextProvider;
	}

	@Override
	public Permission getHandlerPermission(CharSequence permission) {
		return permissions.getPermission(permission);
	}

	@Override
	public String getContext(String key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		String value = tempProvider.getContext(key);
		if (value != null)
			return value;
		value = contextProvider.getContext(key);
		if (value != null)
			return value;
		CoreGroupSetPermission groupSet = this.groupSet;
		if (groupSet == null)
			return null;
		return groupSet.context.getContext(key);
	}

}
