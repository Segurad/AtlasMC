package de.atlasmc.plugin.channel;

import java.util.Collection;
import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface PluginChannelManager {
	
	PluginChannel registerChannel(NamespacedKey channel);
	
	@NotNull
	Collection<PluginChannel> getChannels();
	
	@Nullable
	PluginChannel getChannel(String name);
	
	@Nullable
	PluginChannel getChannel(NamespacedKey channel);
	
	void unregisterAll();

}
