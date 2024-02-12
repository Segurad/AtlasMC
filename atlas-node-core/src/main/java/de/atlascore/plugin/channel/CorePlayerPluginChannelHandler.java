package de.atlascore.plugin.channel;

import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutPluginMessage;
import de.atlasmc.plugin.channel.PluginChannel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CorePlayerPluginChannelHandler extends CoreAbstractPluginChannelHandler {

	private final PlayerConnection con;
	
	public CorePlayerPluginChannelHandler(PlayerConnection con) {
		this.con = con;
	}
	
	@Override
	protected void sendMessage(PluginChannel channel, byte[] message) {
		sendMessage(channel, Unpooled.wrappedBuffer(message));
	}

	@Override
	protected void sendMessage(PluginChannel channel, ByteBuf message) {
		PacketOutPluginMessage packet = new PacketOutPluginMessage();
		packet.channel = channel.getNamespacedKey();
		packet.data = message;
		con.sendPacked(packet);
	}

}
