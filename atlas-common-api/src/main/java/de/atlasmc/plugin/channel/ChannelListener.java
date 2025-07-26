package de.atlasmc.plugin.channel;

import io.netty.buffer.ByteBuf;

/**
 * Listener for {@link PluginChannel}
 */
@FunctionalInterface
public interface ChannelListener {
	
	void onMessage(PluginChannel channel, ByteBuf message);
	
	default void onUnregisterChannel(PluginChannel channel) {
		// override as needed
	}

}
