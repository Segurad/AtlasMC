package de.atlasmc.permission;

/**
 * A PermissionContext is a {@link Permissible} bound to a certain value.
 * With a PermissionContext permission can be set based on certain this value.
 * A context is a combination of its key and the value.
 * There may be more than on PermissionContext that use the same key but not the same value
 * For example some permission are only required on some Servers or in some ServerGroups.
 * @see PermissionContextProvider
 */
public interface PermissionContext extends PermissionHolder {
	
	/**
	 * Returns the key of this context
	 * @return key
	 */
	String getContextKey();
	
	/**
	 * Context is a grouping of permission
	 * @return context
	 */
	String getContext();

}
