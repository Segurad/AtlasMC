package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutWindowProperty;
import io.netty.buffer.ByteBuf;

public class CorePacketOutWindowProperty extends AbstractPacket implements PacketOutWindowProperty {

	private byte windowID;
	private int property, value;
	
	public CorePacketOutWindowProperty() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutWindowProperty(byte windowID, int property, int value) {
		this();
		this.property = property;
		this.windowID = windowID;
		this.value = value;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		property = in.readShort();
		value = in.readShort();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		out.writeShort(property);
		out.writeShort(value);
	}

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public int getProperty() {
		return property;
	}

	@Override
	public int getValue() {
		return value;
	}

}
