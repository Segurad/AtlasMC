package de.atlascore.plugin.channel;

import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutPluginMessage;
import de.atlasmc.plugin.channel.PluginChannel;

public class CorePlayerPluginChannelHandler extends CoreAbstractPluginChannelHandler {

	private final PlayerConnection con;
	
	public CorePlayerPluginChannelHandler(PlayerConnection con) {
		this.con = con;
	}
	
	@Override
	protected void sendMessage(PluginChannel channel, byte[] message) {
		PacketOutPluginMessage packet = new PacketOutPluginMessage();
		packet.setIdentifier(channel.getNamespacedName());
		packet.setData(message);
		con.sendPacked(packet);
	}

}
