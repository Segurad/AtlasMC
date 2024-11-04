package de.atlasmc.plugin.channel;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import de.atlasmc.NamespacedKey;
import io.netty.buffer.ByteBuf;

public class SimplePluginChannel implements PluginChannel {
	
	private final AbstractPluginChannelHandler handler;
	private final NamespacedKey name;
	private final Collection<ChannelListener> listener;
	private volatile boolean unregistered;
	
	public SimplePluginChannel(AbstractPluginChannelHandler handler, NamespacedKey name) {
		this.handler = handler;
		this.name = name;
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
	public PluginChannelHandler getHolder() {
		return handler;
	}

	@Override
	public void handleMessage(ByteBuf message) {
		if (unregistered)
			throw new PluginChannelException("Channel closed!");
		if (listener.isEmpty())
			return;
		for (ChannelListener l : listener)
			l.onMessage(this, message);
	}

	@Override
	public void sendMessage(ByteBuf message) {
		if (unregistered)
			throw new PluginChannelException("Channel closed!");
		handler.sendMessage(this, message);
	}
	
	@Override
	public void sendMessage(byte[] message) {
		if (unregistered)
			throw new PluginChannelException("Channel closed!");
		handler.sendMessage(this, message);
	}
 
}
