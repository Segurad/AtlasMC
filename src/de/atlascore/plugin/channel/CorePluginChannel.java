package de.atlascore.plugin.channel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.channel.ChannelListener;
import de.atlasmc.plugin.channel.PluginChannel;
import de.atlasmc.plugin.channel.PluginChannelException;
import de.atlasmc.plugin.channel.PluginChannelHandler;

public class CorePluginChannel implements PluginChannel {
	
	private final CoreAbstractPluginChannelHandler handler;
	private final NamespacedKey name;
	private final ChannelDirection direction;
	private final List<ChannelListener> listener;
	private volatile boolean unregistered;
	
	public CorePluginChannel(CoreAbstractPluginChannelHandler handler, NamespacedKey name, ChannelDirection direction) {
		this.handler = handler;
		this.name = name;
		this.direction = direction;
		this.listener = new CopyOnWriteArrayList<>();
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return name;
	}
	
	@Override
	public void unregister() {
		if (unregistered)
			return;
		synchronized (this) {
			if (unregistered)
				return;
			unregistered = true;
			handler.unregisterChannel(this);
			for (ChannelListener l : listener)
				l.onUnregisterChannel(this);
			listener.clear();
		}
	}
	
	@Override
	public void addListener(ChannelListener listener) {
		if (unregistered)
			throw new PluginChannelException("Channel closed!");
		this.listener.add(listener);
	}
	
	@Override
	public void removeListener(ChannelListener listener) {
		this.listener.remove(listener);
	}
	
	@Override
	public void sendMessage(byte[] message) {
		if (unregistered)
			throw new PluginChannelException("Channel closed!");
		if (direction == ChannelDirection.IN)
			throw new PluginChannelException("Cannot send messages on inbound channel!");
		handler.sendMessage(this, message);
	}
	
	@Override
	public PluginChannelHandler getHolder() {
		return handler;
	}
	
	@Override
	public ChannelDirection getDirection() {
		return direction;
	}

	@Override
	public void handleMessage(byte[] message) {
		if (unregistered)
			throw new PluginChannelException("Channel closed!");
		if (direction == ChannelDirection.OUT)
			throw new PluginChannelException("Cannot handle messages on outbound channel!");
		for (ChannelListener l : listener)
			l.onMessage(this, message);
	}
 
}
