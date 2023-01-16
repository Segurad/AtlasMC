package de.atlasmc.plugin.channel;

import de.atlasmc.NamespacedKey.Namespaced;

public interface PluginChannel extends Namespaced {
	
	public void unregister();
	
	public void addListener(ChannelListener listener);
	
	public void removeListener(ChannelListener listener);
	
	public void sendMessage(byte[] message);
	
	public void handleMessage(byte[] message);
	
	public PluginChannelHandler getHolder();
	
	public ChannelDirection getDirection();
	
	public enum ChannelDirection {
		OUT,
		IN,
		UNI;
	}

}
