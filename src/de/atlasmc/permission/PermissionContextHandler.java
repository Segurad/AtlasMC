package de.atlasmc.permission;

import java.util.Map;
import de.atlasmc.atlasnetwork.server.ServerGroup;

/**
 * Stores information abound the current context of the holder.
 * For example the current {@link Server} or {@link ServerGroup}.
 * Based on this information {@link Permission}s will be set via {@link PermissionContext}
 */
public interface PermissionContextHandler extends Permissible {
	
	public Map<String, String> getContext();
	
	public void setContext(String key, String context);
	
	public void removeContext(String key, String context);
	
	public String getContext(String key);

}
