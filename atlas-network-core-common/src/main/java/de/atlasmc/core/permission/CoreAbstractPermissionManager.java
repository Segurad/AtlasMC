package de.atlasmc.core.permission;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import de.atlasmc.cache.Caching;
import de.atlasmc.cache.MapCache;
import de.atlasmc.network.PermissionManager;
import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.map.key.CharKey;

public abstract class CoreAbstractPermissionManager implements PermissionManager {
	
	protected final MapCache<String, PermissionGroup> groups;
	protected final MapCache<UUID, PermissionHandler> handlers;
	protected final MapCache<Integer, PermissionContext> contexts;
	private final MapCache<Collection<PermissionGroup>, CoreGroupSetPermission> groupSetCache;
	private final Set<PermissionGroup> defaultGroups;
	private final Collection<PermissionGroup> defaultGroupsView;
	
	public CoreAbstractPermissionManager() {
		this.groups = new MapCache<>();
		this.handlers = new MapCache<>();
		this.contexts = new MapCache<>();
		this.groupSetCache = new MapCache<>();
		this.defaultGroups = new CopyOnWriteArraySet<>();
		this.defaultGroupsView = Collections.unmodifiableCollection(defaultGroups);
		Caching.register(groups);
		Caching.register(contexts);
		Caching.register(handlers);
		Caching.register(groupSetCache);
	}
	
	protected void updateGroup(PermissionGroup group) {
		boolean containsDefault = defaultGroups.contains(group);
		synchronized (groupSetCache) { // update all cached sets containing the group
			for (Entry<Collection<PermissionGroup>, CoreGroupSetPermission> entry : groupSetCache.entrySet()) {
				Collection<PermissionGroup> keySet = entry.getKey();
				if (!keySet.contains(group))
					continue;
				CoreGroupSetPermission oldGroupSet = entry.getValue();
				CoreGroupSetPermission newGroupSet = buildGroupSet(keySet);
				oldGroupSet.update(newGroupSet);
			}
		}
		if (group.isDefault() != containsDefault) { // update defaults
			synchronized (defaultGroups) {
				containsDefault = defaultGroups.contains(group);
				if (group.isDefault() != containsDefault) {
					if (containsDefault) {
						defaultGroups.remove(group);
					} else {
						defaultGroups.add(group);
					}
					// TODO notify existing default handlers
				}
			}
		}
		
	}
	
	CoreGroupSetPermission getDefaultGroupSetPermissions() {
		return getGroupSetPermissions(defaultGroups);
	}
	
	CoreGroupSetPermission getGroupSetPermissions(Collection<PermissionGroup> groups) {
		Set<PermissionGroup> key;
		if (groups instanceof Set<PermissionGroup> set) {
			key = set;
		} else { 
			key = new CopyOnWriteArraySet<>(groups);
		}
		CoreGroupSetPermission handler = groupSetCache.get(key);
		if (handler != null)
			return handler;
		synchronized (groupSetCache) {
			handler = groupSetCache.get(groups);
			if (handler != null)
				return handler;
			handler = buildGroupSet(groups);
			groupSetCache.put(Set.copyOf(key), handler);
		}
		return handler;
	}
	
	private CoreGroupSetPermission buildGroupSet(Collection<PermissionGroup> groups) {
		PermissionGroup[] groupsArr = new PermissionGroup[groups.size()];
		groups.toArray(groupsArr);
		Arrays.sort(groupsArr, PermissionGroup.SORT_BY_POWER);
		Map<CharKey, Permission> effectivePermissions = new HashMap<>();
		Map<String, Map<String, PermissionContext>> effectiveContexts = new HashMap<>();
		ContextProvider context = new CoreContextProvider();
		for (PermissionGroup group : groups) {
			if (group instanceof CorePermissionGroup coreGroup) {
				addPermissions(coreGroup.permissions(), effectivePermissions);
			} else {
				addPermissions(group.getPermissions(), effectivePermissions);
			}
			addPermissionContext(group.getPermissionContexts(), effectiveContexts);
			addContext(group.getContext(), context);
		}
		return new CoreGroupSetPermission(effectiveContexts, effectivePermissions, context);
	}
	
	private void addContext(ContextProvider from, ContextProvider to) {
		Map<String, String> toMap = to.getContext();
		for (Entry<String, String> entry : from.getContext().entrySet()) {
			toMap.putIfAbsent(entry.getKey(), entry.getValue());
		}
	}
	
	private void addPermissionContext(Collection<PermissionContext> contexts, Map<String, Map<String, PermissionContext>> effectiveContexts) {
		for (PermissionContext ctx : contexts) {
			String key = ctx.getContextKey();
			Map<String, PermissionContext> inner = effectiveContexts.get(key);
			if (inner == null)
				effectiveContexts.put(key, inner = new HashMap<>());
			String value = ctx.getContext();
			CorePermissionContext effectiveCtx = (CorePermissionContext) inner.get(value);
			if (effectiveCtx == null)
				inner.put(value, new CorePermissionContext(-1, key, value));
			if (ctx instanceof CorePermissionContext coreCtx) {
				addPermissions(coreCtx, effectiveCtx.permissions());
			} else {
				addPermissions(ctx.getPermissions(), effectiveCtx.permissions());
			}
		}
	}
	
	@Override
	public Collection<PermissionGroup> getDefaultGroups() {
		return defaultGroupsView;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	private void addPermissions(Collection<Permission> perms, Map<CharKey, Permission> permissions) {
		for (Permission perm : perms) {
			if (permissions.containsKey(perm.permission()))
				continue;
			permissions.put(CharKey.of(perm.permission()), perm);
		}
	}
	
	private void addPermissions(CorePermissionHolder holder, Map<CharKey, Permission> permissions) {
		Map<CharKey, Permission> perms = holder.permissions();
		for (Entry<CharKey, Permission> entry : perms.entrySet()) {
			permissions.putIfAbsent(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public Permission createPermission(String permission) {
		return createPermission(permission, 0);
	}

	@Override
	public Permission createPermission(String permission, int value) {
		return new CorePermission(permission, value);
	}

	@Override
	public PermissionContext getContext(int id) {
		return contexts.get(id);
	}

	@Override
	public PermissionGroup getGroup(String name) {
		return groups.get(name);
	}

	@Override
	public PermissionHandler getHandler(UUID uuid) {
		return handlers.get(uuid);
	}

	@Override
	public Collection<PermissionGroup> getGroups() {
		return groups.values();
	}

	@Override
	public Collection<PermissionHandler> getHandlers() {
		return handlers.values();
	}

}
