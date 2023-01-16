package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Painting.Motive;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSpawnPainting;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnPainting extends PacketIO<PacketOutSpawnPainting> {

	@Override
	public void read(PacketOutSpawnPainting packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		long most = in.readLong();
		long least = in.readLong();
		packet.setUUID(new UUID(most, least));
		packet.setMotiv(Motive.getByID(readVarInt(in)));
		packet.setPosition(in.readLong());
		int directionID = in.readByte();
		BlockFace face = null;
		switch (directionID) {
		case 0: 
			face = BlockFace.SOUTH;
			break;
		case 1: 
			face = BlockFace.WEST;
			break;
		case 2: 
			face = BlockFace.NORTH;
			break;
		case 3: 
			face = BlockFace.EAST;
			break;
		default: 
			face = BlockFace.SOUTH;
			break;
		}
		packet.setDirection(face);
	}

	@Override
	public void write(PacketOutSpawnPainting packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		UUID uuid = packet.getUUID();
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeVarInt(packet.getMotiv().getID(), out);
		out.writeLong(packet.getPosition());
		int directionID = 0;
		BlockFace face = packet.getDirection();
		switch (face) {
		case NORTH: 
			directionID = 2;
			break;
		case SOUTH: 
			directionID = 0; 
			break;
		case WEST: 
			directionID = 1; 
			break;
		case EAST: 
			directionID = 3; 
			break;
		default: 
			directionID = 0; 
			break;
		}
		out.writeByte(directionID);
	}

	@Override
	public PacketOutSpawnPainting createPacketData() {
		return new PacketOutSpawnPainting();
	}

}
