package de.atlasmc.plugin.channel;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import io.netty.buffer.ByteBuf;

public abstract class AbstractPluginChannelHandler implements PluginChannelHandler {

	private Map<String, PluginChannel> channels;
	
	public AbstractPluginChannelHandler() {
		channels = new ConcurrentHashMap<>();
	}
	
	@Override
	public final synchronized PluginChannel registerChannel(NamespacedKey channelName) {
		if (channels.get(channelName.toString()) != null)
			throw new PluginChannelException("Channel with name already present: " + channelName.toString());
		PluginChannel channel = createChannel(channelName);
		channels.put(channelName.toString(), channel);
		return channel;
	}

	@Override
	public final Collection<PluginChannel> getChannels() {
		return channels.values();
	}

	@Override
	public PluginChannel getChannel(String name) {
		return channels.get(name);
	}

	@Override
	public PluginChannel getChannel(NamespacedKey channel) {
		return channels.get(channel.toString());
	}

	@Override
	public synchronized void unregisterAll() {
		for (PluginChannel channel : channels.values()) {
			channel.unregister();
		}
	}
	
	/**
	 * Method that handles out bound messages for a channel
	 * @param channel
	 * @param message
	 */
	protected abstract void sendMessage(PluginChannel channel, byte[] message);
	
	/**
	 * Method that handles out bound messages for a channel
	 * @param channel
	 * @param message
	 */
	protected abstract void sendMessage(PluginChannel channel, ByteBuf message);
	
	/**
	 * Internal methods to create a {@link PluginChannel}
	 * @param channelName
	 * @param direction
	 * @return
	 */
	protected PluginChannel createChannel(NamespacedKey channelName) {
		return new SimplePluginChannel(this, channelName);
	}
	
	/**
	 * Internal method to remove channel from channel list.
	 * Will not call {@link PluginChannel#unregister()}
	 * @param channel
	 * @return true if channel was removed
	 */
	protected final synchronized boolean unregisterChannel(PluginChannel channel) {
		return channels.remove(channel.getNamespacedKey().toString(), channel);
	}

}
