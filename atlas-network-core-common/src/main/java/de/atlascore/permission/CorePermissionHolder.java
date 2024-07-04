package de.atlascore.permission;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionHolder;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.map.key.ImmutableCharKey;

public class CorePermissionHolder implements PermissionHolder {
	
	private volatile Map<CharKey, Permission> permissions;

	@Override
	public Collection<Permission> getPermissions() {
		Map<?, Permission> map = permissions;
		if (map == null)
			return List.of();
		return map.values();
	}

	@Override
	public Permission getPermission(String permission, boolean allowWildcards) {
		Map<?, Permission> map = permissions;
		if (map == null)
			return null;
		Permission perm = map.get(permission);
		if (perm != null)
			return perm;
		if (!allowWildcards)
			return null;
		int lastDot = permission.lastIndexOf('.');
		if (lastDot == -1)
			return null;
		WildcardKey key = new WildcardKey(permission, lastDot);
		do {
			perm = map.get(key);
			if (perm != null)
				return perm;
		} while (key.trimDown());
		return null;
	}

	@Override
	public void setPermission(String permission, int value) {
		setPermission(new CorePermission(permission, value));
	}

	@Override
	public void setPermission(Permission permission) {
		Map<CharKey, Permission> map = permissions;
		if (map == null) {
			synchronized (this) {
				map = permissions;
				if (map == null) {
					map = new ConcurrentHashMap<>();
					permissions = map;
				}
			}
		}
		map.put(new ImmutableCharKey(permission.permission()), permission);
	}

	@Override
	public void removePermission(String permission) {
		Map<?, Permission> map = permissions;
		if (map == null)
			return;
		map.remove(map);
	}

	@Override
	public void removePermission(Permission permission) {
		removePermission(permission.permission());
	}
	
	private static class WildcardKey extends CharKey {
		
		private final char[] buf;
		private int length = 0;
		
		public WildcardKey(CharSequence permission, int lastDot) {
			final int permLength = lastDot+1;
			length = permLength;
			// check if wildcard is present
			if (permission.charAt(length-1) != '*') {
				length += 1;
			}
			buf = new char[permLength + 2];
			for (int i = 0; i < permLength; i++)
				buf[i] = permission.charAt(i);
			// append wildcard if not present
			if (length != permLength) {
				buf[length-1] = '*';
			}
		}
		
		/**
		 * Trims the current wild card string down to the next element
		 * @return true if success
		 */
		public boolean trimDown() {
			// i: length = out of bounds, -1 = '*', -2 = '.', -3 must be non dot char
			// 0 may not be a dot
			boolean trimed = false;
			for (int i = length-4; i > 0; i--) {
				if (buf[i] != '.')
					continue;
				buf[i+1] = '*';
				length = i + 2;
				trimed = true;
				break;
			}
			return trimed;
		}

		@Override
		public int length() {
			return length;
		}

		@Override
		public char charAt(int index) {
			if (index >= length)
				throw new ArrayIndexOutOfBoundsException(index);
			return buf[index];
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return new ImmutableCharKey(buf, 0, length);
		}

		@Override
		protected char[] getBuf() {
			return buf;
		}
		
	}

}
