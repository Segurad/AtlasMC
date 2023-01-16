package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInClickWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketInClickWindow extends PacketIO<PacketInClickWindow> {

	@Override
	public void read(PacketInClickWindow packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setSlot(in.readShort());
		packet.setButton(in.readByte());
		packet.setAction(in.readShort());
		packet.setMode(readVarInt(in));
		packet.setClickedItem(readSlot(in));
	}

	@Override
	public void write(PacketInClickWindow packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.getWindowID());
		out.writeShort(packet.getSlot());
		out.writeByte(packet.getButton());
		out.writeShort(packet.getAction());
		writeVarInt(packet.getMode(), out);
		writeSlot(packet.getClickedItem(), out);
	}

	@Override
	public PacketInClickWindow createPacketData() {
		return new PacketInClickWindow();
	}

}
