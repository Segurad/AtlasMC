package de.atlasmc.permission;

import java.util.Collection;

/**
 * Stores {@link Permission}s
 */
public interface PermissionHolder {
	
	public Collection<Permission> getPermissions();
	
	public Permission getPermission(String permission);
	
	public void addPermission(String permission, int value, boolean negate);
	
	public void addPermission(String permission, int value);
	
	public void addPermission(String permission);
	
	public void addPermission(Permission permission);
	
	public void removePermission(String permission);
	
	public void removePermission(Permission permission);
 
}
