package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutWorldBorder;
import de.atlasmc.io.protocol.play.PacketOutWorldBorder.BorderAction;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutWorldBorder extends PacketIO<PacketOutWorldBorder> {

	@Override
	public void read(PacketOutWorldBorder packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		BorderAction action = BorderAction.getByID(readVarInt(in));
		packet.setAction(action);
		switch(action) {
		case SET_SIZE:
			packet.setNewDiameter(in.readDouble());
			break;
		case LERP_SIZE:
			packet.setOldDiameter(in.readDouble());
			packet.setNewDiameter(in.readDouble());
			packet.setSpeed(readVarLong(in));
			break;
		case SET_CENTER:
			packet.setX(in.readDouble());
			packet.setZ(in.readDouble());
			break;
		case INITIALIZE:
			packet.setX(in.readDouble());
			packet.setZ(in.readDouble());
			packet.setOldDiameter(in.readDouble());
			packet.setNewDiameter(in.readDouble());
			packet.setSpeed(readVarLong(in));
			packet.setPortalBoundary(readVarInt(in));
			packet.setWarnBlocks(readVarInt(in));
			packet.setWarnTime(readVarInt(in));
			break;
		case SET_WARNING_TIME:
			packet.setWarnTime(readVarInt(in));
			break;
		case SET_WARNING_BLOCKS:
			packet.setWarnBlocks(readVarInt(in));
			break;
		}
	}

	@Override
	public void write(PacketOutWorldBorder packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		BorderAction action = packet.getAction();
		writeVarInt(action.getID(), out);
		switch(action) {
		case SET_SIZE:
			out.writeDouble(packet.getNewDiameter());
			break;
		case LERP_SIZE:
			out.writeDouble(packet.getOldDiameter());
			out.writeDouble(packet.getNewDiameter());
			writeVarLong(packet.getSpeed(), out);
			break;
		case SET_CENTER:
			out.writeDouble(packet.getX());
			out.writeDouble(packet.getZ());
			break;
		case INITIALIZE:
			out.writeDouble(packet.getX());
			out.writeDouble(packet.getZ());
			out.writeDouble(packet.getOldDiameter());
			out.writeDouble(packet.getNewDiameter());
			writeVarLong(packet.getSpeed(), out);
			writeVarInt(packet.getPortalBoundary(), out);
			writeVarInt(packet.getWarnBlocks(), out);
			writeVarInt(packet.getWarnTime(), out);
			break;
		case SET_WARNING_TIME:
			writeVarInt(packet.getWarnTime(), out);
			break;
		case SET_WARNING_BLOCKS:
			writeVarInt(packet.getWarnBlocks(), out);
			break;
		}
	}
	
	@Override
	public PacketOutWorldBorder createPacketData() {
		return new PacketOutWorldBorder();
	}

}
