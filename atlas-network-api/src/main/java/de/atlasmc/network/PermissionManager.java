package de.atlasmc.network;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;

public interface PermissionManager {
	
	/**
	 * Creates a new permission with the given string value of 0
	 * @param permission
	 * @return permission instance
	 */
	@NotNull
	Permission createPermission(String permission);
	
	/**
	 * Creates a new permission with the given string and value
	 * @param permission
	 * @param value
	 * @return permission instance
	 */
	@NotNull
	Permission createPermission(String permission, int value);
	
	/**
	 * Creates a new permission context with the given key and value
	 * @param key
	 * @param value
	 * @return permission context
	 */
	Future<PermissionContext> createContext(String key, String value);
	
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
	Future<PermissionContext> loadContext(int id);
	
	/**
	 * Deletes the given context the future value will be true if successfully
	 * @param id
	 * @return future success
	 */
	Future<Boolean> deleteContext(int id);
	
	/**
	 * Saves the given context the future value will be true if successfully
	 * @param context
	 * @return future success
	 */
	Future<Boolean> saveContext(PermissionContext context);
	
	/**
	 * Creates a new group with the given name or load a existing group with this name
	 * @param name
	 * @return future group
	 */
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
	Future<PermissionGroup> loadGroup(String name);
	
	/**
	 * Saves the given group the future value will be true if successfully
	 * @param group
	 * @return future success
	 */
	Future<Boolean> saveGroup(PermissionGroup group);
	
	/**
	 * Deletes the given group the future value will be true if successfully
	 * @param name
	 * @return future success
	 */
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
	Future<PermissionHandler> loadHandler(UUID uuid);
	
	/**
	 * Saves the given handler the future value will be true if successfully
	 * @param handler
	 * @return future success
	 */
	Future<Boolean> saveHandler(PermissionHandler handler);
	
	/**
	 * Creates a new handler with the given name or load a existing handler with the given uuid
	 * @param uuid
	 * @return future handler
	 */
	Future<PermissionHandler> createHandler(UUID uuid);
	
	Future<Boolean> deleteHandler(UUID uuid);

	/**
	 * Returns a collection of all loaded groups
	 * @return groups
	 */
	Collection<PermissionGroup> getGroups();

	/**
	 * Returns a collection of all loaded handlers
	 * @return handlers
	 */
	Collection<PermissionHandler> getHandlers();
	
	Collection<PermissionGroup> getDefaultGroups();
	
	Future<Collection<PermissionGroup>> loadDefaultGroups();
	
}
