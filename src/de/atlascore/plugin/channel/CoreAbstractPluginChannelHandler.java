package de.atlascore.plugin.channel;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.channel.PluginChannel;
import de.atlasmc.plugin.channel.PluginChannelException;
import de.atlasmc.plugin.channel.PluginChannelHandler;
import de.atlasmc.plugin.channel.PluginChannel.ChannelDirection;

public abstract class CoreAbstractPluginChannelHandler implements PluginChannelHandler {

	private ConcurrentHashMap<String, PluginChannel> channels;
	
	@Override
	public final synchronized PluginChannel registerChannel(NamespacedKey channelName, ChannelDirection direction) {
		if (channels.get(channelName.toString()) != null)
			throw new PluginChannelException("Channel with name already present: " + channelName.toString());
		PluginChannel channel = createChannel(channelName, direction);
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
	 * Internal methods to create a {@link PluginChannel}
	 * @param channelName
	 * @param direction
	 * @return
	 */
	protected PluginChannel createChannel(NamespacedKey channelName, ChannelDirection direction) {
		return new CorePluginChannel(this, channelName, direction);
	}
	
	/**
	 * Internal method to remove channel from channel list.
	 * Will not call {@link PluginChannel#unregister()}
	 * @param channel
	 */
	protected final synchronized void unregisterChannel(PluginChannel channel) {
		channels.remove(channel.getNamespacedName(), channel);
	}

}
