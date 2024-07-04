package de.atlasmc.plugin.channel;

import java.util.Collection;
import de.atlasmc.NamespacedKey;

public interface PluginChannelHandler {
	
	PluginChannel registerChannel(NamespacedKey channel);
	
	Collection<PluginChannel> getChannels();
	
	PluginChannel getChannel(String name);
	
	PluginChannel getChannel(NamespacedKey channel);
	
	void unregisterAll();

}
