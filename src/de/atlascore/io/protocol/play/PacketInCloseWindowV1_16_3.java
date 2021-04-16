package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInCloseWindow;
import io.netty.buffer.ByteBuf;

public class PacketInCloseWindowV1_16_3 extends AbstractPacket implements PacketInCloseWindow {

	public PacketInCloseWindowV1_16_3() {
		super(0x0A, V1_16_3.version);
	}

	private byte windowID;
	
	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
	}


}
