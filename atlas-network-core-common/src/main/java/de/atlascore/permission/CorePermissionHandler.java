package de.atlascore.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.ConcurrentLinkedList;

public class CorePermissionHandler extends CorePermissionGroupHolder implements PermissionHandler {

	private static final PermissionContext[] EMPTY = {};
	
	private final UUID uuid;
	private final ContextProvider contextProvider;
	private final ContextProvider tempProvider;
	
	private final CoreAbstractPermissionManager manager;
	private CoreGroupSetPermission groupSet;
	private PermissionContext[] activeContexts = EMPTY;
	
	public CorePermissionHandler(UUID uuid, CoreAbstractPermissionManager manager) {
		this.uuid = uuid;
		contextProvider = new CorePermissionHandlerContextProvider(this);
		tempProvider = new CorePermissionHandlerContextProvider(this);
		this.manager = manager;
	}
	
	void onContextChange(String key, String oldValue, String newValue) {
		PermissionContext oldCtx = null;
		PermissionContext newCtx = null;
		CoreGroupSetPermission groupSet = this.groupSet;
		if (groupSet != null) {
			Map<String, PermissionContext> groupCtxs = groupSet.permissionContexts.get(key);
			if (groupCtxs != null) {
				oldCtx = groupCtxs.get(oldValue);
				newCtx = groupCtxs.get(newValue);
			}
		}
		if (oldCtx == null && newCtx == null)
			return; // no changes everything is null
		if (oldCtx == newCtx)
			return; // no changes everything is equal
		synchronized (this) {
			PermissionContext[] oldActive = this.activeContexts;
			PermissionContext[] newContexts = null;
			if (oldCtx == null) {
				if (newCtx != null) { // new ctx active
					newContexts = Arrays.copyOf(oldActive, oldActive.length + 1);
					newContexts[newContexts.length - 1] = newCtx;
				}
			} else if (newCtx == null) { // ctx no longer active
				int length = oldActive.length - 1;
				if (length < 1) {
					activeContexts = EMPTY;
					return; // avoid creating empty arrays
				}
				PermissionContext[] newActive = new PermissionContext[oldActive.length - 1];
				int i = 0;
				for (PermissionContext ctx : oldActive) {
					if (ctx == oldCtx)
						continue;
					newActive[i] = ctx;
				}
			} else if (oldCtx != newCtx) { // ctx has changed
				newContexts = oldActive;
				final int length = newContexts.length;
				for (int i = 0; i < length; i++) {
					if (newContexts[i] != oldCtx)
						continue;
					newContexts[i] = newCtx;
					break;
				}
			} else { // no changes
				newContexts = oldActive;
			}
			activeContexts = newContexts;
		}
	}
	
	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public synchronized void groupsChanged() {
		ConcurrentLinkedList<PermissionGroup> groups = getGroups();
		CoreGroupSetPermission groupSet = null;
		if (groups.isEmpty()) {
			groupSet = manager.getGroupSetPermissions(getGroups());
		} else {
			groupSet = manager.getDefaultGroupSetPermissions();
		}
		activeContexts = getActiveContexts(groupSet);
		this.groupSet.handlers.remove(this);
		groupSet.handlers.add(this);
		this.groupSet = groupSet;
	}
	
	public void groupSetUpdated(CoreGroupSetPermission groupSet) {
		if (this.groupSet != groupSet) {
			groupSet.handlers.remove(this);
			return;
		}
		synchronized (this) {
			if (this.groupSet != groupSet) {
				groupSet.handlers.remove(this);
				return;
			}
			activeContexts = getActiveContexts(groupSet);
		}
	}
	
	private PermissionContext[] getActiveContexts(CoreGroupSetPermission groupSet) {
		ArrayList<PermissionContext> groupContext = new ArrayList<>(5);
		super.groupsChanged();
		getActiveContexts(tempProvider.getContext(), groupContext);
		getActiveContexts(contextProvider.getContext(), groupContext);
		getActiveContexts(groupSet.context.getContext(), groupContext);
		if (!groupContext.isEmpty()) {
			return (PermissionContext[]) groupContext.toArray();
		} else {
			return EMPTY;
		}
	}
	
	private void getActiveContexts(Map<String, String> context, List<PermissionContext> groupContexts) {
		if (context.isEmpty())
			return;
		for (Entry<String, String> entry : context.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
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
	public Permission getPermission(CharSequence permission) {
		final CoreGroupSetPermission groupSet = this.groupSet;
		if (groupSet == null)
			return null;
		Permission perm = groupSet.permissions.get(permission);
		if (perm != null)
			return perm;
		final PermissionContext[] activeGroups = activeContexts;
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
