package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Painting;
import de.atlasmc.entity.Painting.Motive;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSpawnPainting;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnPainting extends AbstractPacket implements PacketOutSpawnPainting {

	private int id, motiv, direction;
	private UUID uuid;
	private long loc;
	
	public CorePacketOutSpawnPainting() {
		super(0x03, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSpawnPainting(Painting painting) {
		this();
		id = painting.getID();
		uuid = painting.getUUID();
		loc = MathUtil.toPosition(painting.getX(), painting.getY(), painting.getZ());
		motiv = painting.getMotive().ordinal();
		BlockFace face = painting.getAttachedFace();
		switch (face) {
		case NORTH: direction = 2; break;
		case SOUTH: direction = 0; break;
		case WEST: direction = 1; break;
		case EAST: direction = 3; break;
		default: direction = 0; break;
		}
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = readVarInt(in);
		long most = in.readLong();
		long least = in.readLong();
		uuid = new UUID(most, least);
		motiv = readVarInt(in);
		loc = in.readLong();
		direction = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(id, out);
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeVarInt(motiv, out);
		out.writeLong(loc);
		out.writeByte(direction);
	}

	@Override
	public int getEntityID() {
		return id;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public Motive getMotiv() {
		return Motive.getByID(motiv);
	}

	@Override
	public long getPosition() {
		return loc;
	}

	@Override
	public BlockFace getDirection() {
		switch (direction) {
		case 0: return BlockFace.SOUTH;
		case 1: return BlockFace.WEST;
		case 2: return BlockFace.NORTH;
		case 3: return BlockFace.EAST;
		default: return BlockFace.SOUTH;
		}
	}

}
