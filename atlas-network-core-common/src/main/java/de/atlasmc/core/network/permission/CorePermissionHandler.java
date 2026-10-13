package de.atlasmc.core.network.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.network.permission.ContextProvider;
import de.atlasmc.network.permission.PermissionContext;
import de.atlasmc.network.permission.PermissionGroup;
import de.atlasmc.network.permission.PermissionHandler;
import de.atlasmc.permission.Permission;
import de.atlasmc.util.ConcurrentLinkedList;

public class CorePermissionHandler extends CorePermissionGroupHolder implements PermissionHandler {

	private static final PermissionContext[] EMPTY = {};
	
	private final int id;
	private final UUID uuid;
	private final ContextProvider contextProvider;
	private final ContextProvider tempProvider;
	
	private final CoreAbstractPermissionManager manager;
	private volatile CoreGroupSetPermission groupSet;
	/**
	 * Array storing active permission contexts this should be treated as copy on write.
	 * If changes to the array are made a new copy should be set.
	 * Changes must be made synchronous using mutex this
	 */
	private volatile PermissionContext[] activeContexts = EMPTY;
	
	public CorePermissionHandler(int id, UUID uuid, CoreAbstractPermissionManager manager) {
		this.id = id;
		this.uuid = Objects.requireNonNull(uuid, "uuid");
		this.manager = Objects.requireNonNull(manager, "manager");
		this.contextProvider = new CorePermissionHandlerContextProvider(this);
		this.tempProvider = new CorePermissionHandlerContextProvider(this);
	}
	
	/**
	 * Used to notify this handler that a context has changed by a context provider
	 * @param key
	 * @param oldValue
	 * @param newValue
	 */
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
		if (oldCtx == newCtx)
			return;
		rebuildActivePermissionContexts(oldCtx, newCtx);
	}
	
	private synchronized void rebuildActivePermissionContexts(PermissionContext oldCtx, PermissionContext newCtx) {
		if (oldCtx == newCtx)
			return;
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
			newContexts = oldActive.clone();
			final int length = newContexts.length;
			for (int i = 0; i < length; i++) {
				if (newContexts[i] != oldCtx)
					continue;
				newContexts[i] = newCtx;
				break;
			}
		}
		activeContexts = newContexts;
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
		super.groupsChanged();
	}
	
	/**
	 * Notifies this handler that the a permission set has been updated
	 * @param groupSet
	 */
	public synchronized void onGroupSetUpdated(CoreGroupSetPermission groupSet) {
		if (this.groupSet != groupSet) {
			groupSet.handlers.remove(this);
			return;
		}
		activeContexts = getActiveContexts(groupSet);
	}
	
	/**
	 * Aggregates the active permissions contexts in order of relevants.
	 * The most significant context is the temp context followed by the persistent context and group context
	 * @param groupSet
	 * @return
	 */
	private PermissionContext[] getActiveContexts(CoreGroupSetPermission groupSet) {
		ArrayList<PermissionContext> groupContext = new ArrayList<>(5);
		super.groupsChanged();
		getActiveContexts(tempProvider.getContext(), groupContext);
		getActiveContexts(contextProvider.getContext(), groupContext);
		getActiveContexts(groupSet.context.getContext(), groupContext);
		if (!groupContext.isEmpty()) {
			return groupContext.toArray(new PermissionContext[groupContext.size()]);
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
			return null; // if no group set is present there can be no permission
		// direct group set lookup
		Permission perm = groupSet.permissions.get(permission);
		if (perm != null)
			return perm;
		// context lookup
		final PermissionContext[] activeGroups = activeContexts;
		final int length = activeGroups.length;
		for (int i = 0; i < length; i++) {
			final PermissionContext ctx = activeContexts[i];
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
		Objects.requireNonNull(key, "key");
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

	@Override
	public int getID() {
		return id;
	}

}
