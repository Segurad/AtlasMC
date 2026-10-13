package de.atlasmc.network.permission;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.permission.Permission;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;

public interface PermissionManager {
	
	/**
	 * Creates a new permission with the given string value of 1
	 * @param permission
	 * @return permission instance
	 */
	@NotNull
	default Permission createPermission(@NotNull CharSequence permission) {
		return createPermission(permission, 1);
	}
	
	/**
	 * Creates a new permission with the given string and value
	 * @param permission
	 * @param value
	 * @return permission instance
	 */
	@NotNull
	Permission createPermission(@NotNull CharSequence permission, int value);
	
	/**
	 * Creates a new permission context with the given key and value
	 * @param key
	 * @param value
	 * @return permission context
	 */
	@NotNull
	Future<PermissionContext> createContext(@NotNull String key, @NotNull String value);
	
	/**
	 * Returns the loaded context with the given id or null of not loaded
	 * @param id
	 * @return future context
	 */
	@Nullable
	PermissionContext getContext(int id);
	
	/**
	 * Returns a future that returns the context or null if not present
	 * @param id
	 * @return future context
	 */
	@NotNull
	Future<PermissionContext> loadContext(int id);
	
	/**
	 * Deletes the given context the future value will be true if successfully
	 * @param id
	 * @return future success
	 */
	@NotNull
	Future<Boolean> deleteContext(int id);
	
	/**
	 * Saves the given context the future value will be true if successfully
	 * @param context
	 * @return future success
	 */
	@NotNull
	Future<Boolean> saveContext(PermissionContext context);
	
	/**
	 * Creates a new group with the given name or load a existing group with this name
	 * @param name
	 * @return future group
	 */
	@NotNull
	Future<PermissionGroup> createGroup(String name);
	
	/**
	 * Returns the loaded group with the given name or null if not loaded
	 * @param name
	 * @return group
	 */
	@Nullable
	PermissionGroup getGroup(String name);
	
	/**
	 * Returns a future that returns the group or null if not present
	 * @param name
	 * @return future group
	 */
	@NotNull
	Future<PermissionGroup> loadGroup(String name);
	
	/**
	 * Saves the given group the future value will be true if successfully
	 * @param group
	 * @return future success
	 */
	@NotNull
	Future<Boolean> saveGroup(PermissionGroup group);
	
	/**
	 * Deletes the given group the future value will be true if successfully
	 * @param name
	 * @return future success
	 */
	@NotNull
	Future<Boolean> deleteGroup(String name);
	
	/**
	 * Returns the loaded handler with the given uuid or null if not loaded
	 * @param uuid
	 * @return handler
	 */
	@Nullable
	PermissionHandler getHandler(UUID uuid);
	
	/**
	 * Returns a future that returns the handler or null if not present
	 * @param uuid
	 * @return future handler
	 */
	@NotNull
	Future<PermissionHandler> loadHandler(UUID uuid);
	
	/**
	 * Saves the given handler the future value will be true if successfully
	 * @param handler
	 * @return future success
	 */
	@NotNull
	Future<Boolean> saveHandler(PermissionHandler handler);
	
	/**
	 * Creates a new handler with the given name or load a existing handler with the given uuid
	 * @param uuid
	 * @return future handler
	 */
	@NotNull
	Future<PermissionHandler> createHandler(UUID uuid);
	
	@NotNull
	Future<Boolean> deleteHandler(UUID uuid);

	/**
	 * Returns a collection of all loaded groups
	 * @return groups
	 */
	@NotNull
	Collection<PermissionGroup> getGroups();

	/**
	 * Returns a collection of all loaded handlers
	 * @return handlers
	 */
	@NotNull
	Collection<PermissionHandler> getHandlers();
	
	@NotNull
	Collection<PermissionGroup> getDefaultGroups();
	
	@NotNull
	Future<Collection<PermissionGroup>> loadDefaultGroups();
	
}
