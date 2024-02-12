package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetTitleAnimationTimes;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetTitleAnimationTimes implements PacketIO<PacketOutSetTitleAnimationTimes> {

	@Override
	public void read(PacketOutSetTitleAnimationTimes packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setFadeIn(in.readInt());
		packet.setStay(in.readInt());
		packet.setFadeOut(in.readInt());
	}

	@Override
	public void write(PacketOutSetTitleAnimationTimes packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.getFadeIn());
		out.writeInt(packet.getStay());
		out.writeInt(packet.getFadeOut());
	}

	@Override
	public PacketOutSetTitleAnimationTimes createPacketData() {
		return new PacketOutSetTitleAnimationTimes();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetTitleAnimationTimes.class);
	}

}
