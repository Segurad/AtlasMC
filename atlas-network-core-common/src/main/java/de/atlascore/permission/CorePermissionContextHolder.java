package de.atlascore.permission;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.PermissionContextHolder;

public class CorePermissionContextHolder implements PermissionContextHolder, Iterable<PermissionContext> {
	
	protected Map<String, Map<String, PermissionContext>> permContext;
	private Collection<PermissionContext> values;
	private boolean changed;
	
	
	@Override
	public Collection<PermissionContext> getPermissionContexts() {
		if (values == null)
			values = new ContextCollection(this);
		return values;
	}

	@Override
	public Collection<PermissionContext> getPermissionContexts(String key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		Map<String, Map<String, PermissionContext>> map = permContext;
		if (map == null)
			return List.of();
		Map<String, PermissionContext> contexts = map.get(key);
		if (contexts == null)
			return List.of();
		return contexts.values();
	}

	@Override
	public PermissionContext getPermissionContext(String key, String context) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (context == null)
			throw new IllegalArgumentException("Context can not be null!");
		Map<String, Map<String, PermissionContext>> map = permContext;
		if (map == null)
			return null;
		Map<String, PermissionContext> contexts = map.get(key);
		if (contexts == null)
			return null;
		return contexts.get(context);
	}

	@Override
	public boolean addPermissionContext(PermissionContext context) {
		if (context == null)
			throw new IllegalArgumentException("Context can not be null!");
		Map<String, PermissionContext> contexts = getInnerMapSafe(context.getContextKey());
		PermissionContext old = contexts.put(context.getContext(), context);
		if (context == old)
			return true;
		changed = true;
		return true;
	}

	@Override
	public boolean removePermissionContext(PermissionContext context) {
		if (context == null)
			throw new IllegalArgumentException("Context can not be null!");
		Map<String, Map<String, PermissionContext>> map = permContext;
		if (map == null)
			return false;
		Map<String, PermissionContext> contexts = map.get(context.getContextKey());
		if (contexts == null)
			return false;
		if (!contexts.remove(context.getContext(), context))
			return false;
		changed = true;
		return true;
	}

	@Override
	public boolean removePermissionContext(String key, String context) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (context == null)
			throw new IllegalArgumentException("Context can not be null!");
		Map<String, Map<String, PermissionContext>> map = permContext;
		if (map == null)
			return false;
		Map<String, PermissionContext> contexts = map.get(key);
		if (contexts == null)
			return false;
		if (contexts.remove(context) == null)
			return false;
		changed = true;
		return true;
	}
	
	private Map<String, Map<String, PermissionContext>> getMainMapSafe() {
		Map<String, Map<String, PermissionContext>> map = permContext;
		if (map != null)
			return map;
		synchronized (this) {
			map = permContext;
			if (map == null)
				map = new ConcurrentHashMap<>(3);
				permContext = map;
			return map;
		}
	}
	
	private Map<String, PermissionContext> getInnerMapSafe(String key) {
		Map<String, Map<String, PermissionContext>> mainMap = getMainMapSafe();
		Map<String, PermissionContext> map = mainMap.get(key);
		if (map != null)
			return map;
		synchronized (this) {
			map = mainMap.get(key);
			if (map == null) {
				map = new ConcurrentHashMap<>(3);
				mainMap.put(key, map);
			}
			return map;
		}
	}
	
	@Override
	public Iterator<PermissionContext> iterator() {
		return new ContextIterator(this);
	}
	
	private static class ContextCollection extends AbstractCollection<PermissionContext> {

		private final CorePermissionContextHolder holder;
		
		public ContextCollection(CorePermissionContextHolder holder) {
			this.holder = holder;
		}
		
		@Override
		public Iterator<PermissionContext> iterator() {
			return new ContextIterator(holder);
		}
		
		@Override
		public boolean add(PermissionContext e) {
			return holder.addPermissionContext(e);
		}

		@Override
		public int size() {
			Map<String, Map<String, PermissionContext>> map = holder.permContext;
			if (map == null)
				return 0;
			int size = 0;
			for (Map<?, ?> values : map.values())
				size += values.size();
			return size;
		}
		
	}
	
	private static class ContextIterator implements Iterator<PermissionContext> {
		
		private CorePermissionContextHolder holder;
		private Iterator<Map<String, PermissionContext>> mainIterator;
		private Iterator<PermissionContext> innerIterator;
		private boolean end;
		
		public ContextIterator(CorePermissionContextHolder holder) {
			this.holder = holder;
		}
		
		@Override
		public boolean hasNext() {
			if (end)
				return false;
			if (innerIterator == null || !innerIterator.hasNext()) {
				if (mainIterator == null) {
					Map<String, Map<String, PermissionContext>> map = holder.permContext;
					if (map == null) {
						end();
						return false;
					}
					mainIterator = map.values().iterator();
				}
				if (!mainIterator.hasNext()) {
					end();
					return false;
				}
				innerIterator = mainIterator.next().values().iterator();
			}
			return true;
		}
		
		private void end() {
			end = true;
			holder = null;
			mainIterator = null;
			innerIterator = null;
		}

		@Override
		public PermissionContext next() {
			if (end)
				throw new NoSuchElementException();
			if (innerIterator == null || !innerIterator.hasNext()) {
				if (mainIterator == null) {
					Map<String, Map<String, PermissionContext>> map = holder.permContext;
					if (map == null) {
						end();
						throw new NoSuchElementException();
					}
					mainIterator = map.values().iterator();
				}
				if (!mainIterator.hasNext()) {
					end();
					throw new NoSuchElementException();
				}
				innerIterator = mainIterator.next().values().iterator();
			}
			return innerIterator.next();
		}
		
	}

	@Override
	public Permission getPermission(CharSequence permission, ContextProvider provider) {
		if (provider == null)
			throw new IllegalArgumentException("ContextProvider can not be null!");
		for (String key : permContext.keySet()) {
			String value = provider.getContext(key);
			if (value == null)
				continue;
			PermissionContext context = getPermissionContext(key, value);
			if (context == null)
				continue;
			Permission perm = context.getPermission(permission);
			if (perm == null)
				continue;
			return perm;
		}
		return null;
	}

	@Override
	public boolean hasChangedPermissionContext() {
		return changed;
	}

	@Override
	public void changedPermissionContexts() {
		changed = false;
	}

}
