package de.atlasmc.permission;

import java.util.Map;

/**
 * Stores information abound the current context of the holder.
 * For example the current Server or ServerGroup.
 * Based on this information {@link Permission}s will be set via {@link PermissionContext}
 */
public interface PermissionContextProvider {
	
	public Map<String, String> getContext();
	
	public void setContext(String key, String context);
	
	public void removeContext(String key, String context);
	
	public String getContext(String key);

}
