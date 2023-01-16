package de.atlasmc.plugin.messenger;

/**
 * Listener for {@link PluginChannel}
 */
public interface ChannelListener {
	
	public void onMessage(PluginChannel channel, byte[] message);
	
	public void onUnregisterChannel(PluginChannel channel);

}
