package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInClickWindowButton;
import io.netty.buffer.ByteBuf;

public class PacketInClickWindowButtonV1_16_3 extends AbstractPacket implements PacketInClickWindowButton {

	public PacketInClickWindowButtonV1_16_3() {
		super(0x08, V1_16_3.version);
	}

	private byte windowID, buttonID;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		buttonID = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		out.writeByte(buttonID);
	}

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public byte getButtonID() {
		return buttonID;
	}

}
