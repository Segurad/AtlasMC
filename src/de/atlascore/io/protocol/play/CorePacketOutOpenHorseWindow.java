package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutOpenHorseWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenHorseWindow extends AbstractPacket implements PacketOutOpenHorseWindow {

	private byte windowID;
	private int slots, entityID;
	
	public CorePacketOutOpenHorseWindow() {
		super(0x1E, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutOpenHorseWindow(byte windowID, int slots, int entityID) {
		this();
		this.windowID = windowID;
		this.slots = slots;
		this.entityID = entityID;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		slots = readVarInt(in);
		entityID = in.readInt();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		writeVarInt(slots, out);
		out.writeInt(entityID);
	}

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public int getSlots() {
		return slots;
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

}
