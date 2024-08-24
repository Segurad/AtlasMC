package de.atlasmc.permission;

import java.util.Map;

/**
 * Stores information abound the current context of the holder.
 * For example the current Server or ServerGroup.
 * Based on this information {@link Permission}s will be set via {@link PermissionContext}
 */
public interface ContextProvider {
	
	Map<String, String> getContext();
	
	String set(String key, String context);
	
	boolean removeContext(String key, String context);
	
	String removeContext(String key);
	
	String getContext(String key);
	
	boolean hasChangedContext();
	
	void changedContext();

}
