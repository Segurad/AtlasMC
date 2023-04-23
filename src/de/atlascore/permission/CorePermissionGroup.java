package de.atlascore.permission;

import java.util.Collection;

import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHolder;

public class CorePermissionGroup extends CorePermissionContextHolder implements PermissionGroup {

	private volatile int power = 0;
	private PermissionHolder permissions;
	
	public CorePermissionGroup() {
		this.permissions = new CorePermissionHolder();
	}
	
	@Override
	public Collection<PermissionGroup> getGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionGroup getHighestGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPermissionGroup(PermissionGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePermissionGroup(PermissionGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Permission> getPermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPermission(String permission, boolean allowWildcards) {
		// TODO Auto-generated method stub
		return null;
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
	public int getPower() {
		return power;
	}

}
