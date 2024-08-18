package de.atlasmc.permission;

import java.util.Map;

import de.atlasmc.util.Pair;

/**
 * Stores information abound the current context of the holder.
 * For example the current Server or ServerGroup.
 * Based on this information {@link Permission}s will be set via {@link PermissionContext}
 */
public interface ContextProvider {
	
	Map<String, Pair<String, Boolean>> getContext();
	
	String setContext(String key, String context, boolean persistent);
	
	String removeContext(String key, String context);
	
	String getContext(String key);
	
	boolean isPersistent(String key);
	
	void setPersistent(String key, boolean peristent);
	
	boolean hasChangedContext();
	
	void changedContext();

}
