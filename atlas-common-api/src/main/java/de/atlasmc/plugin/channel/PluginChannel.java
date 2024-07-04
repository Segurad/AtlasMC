package de.atlasmc.plugin.channel;

import de.atlasmc.NamespacedKey.Namespaced;
import io.netty.buffer.ByteBuf;

public interface PluginChannel extends Namespaced {
	
	void unregister();
	
	void addListener(ChannelListener listener);
	
	void removeListener(ChannelListener listener);
	
	/**
	 * Sends a message to the holder of this channel
	 * @param message to send
	 */
	void sendMessage(ByteBuf message);
	
	/**
	 * Sends a message to the holder of this channel
	 * @param message to send
	 */
	void sendMessage(byte[] message);
	
	/**
	 * In bound message of the holder of this channel
	 * @param message to handle
	 */
	void handleMessage(ByteBuf message);
	
	/**
	 * Returns the handler of this channel
	 * @return handler
	 */
	PluginChannelHandler getHolder();

}
