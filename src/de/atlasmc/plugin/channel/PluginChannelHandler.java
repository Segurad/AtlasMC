package de.atlasmc.plugin.messenger;

import java.util.Collection;
import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.messenger.PluginChannel.ChannelDirection;

public interface PluginChannelHandler {
	
	public PluginChannel registerChannel(NamespacedKey channel, ChannelDirection direction);
	
	public Collection<PluginChannel> getChannels();
	
	public PluginChannel getChannel(String name);
	
	public PluginChannel getChannel(NamespacedKey channel);
	
	public void unregisterAll();

}
