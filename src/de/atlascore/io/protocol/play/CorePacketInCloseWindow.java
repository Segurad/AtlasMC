package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInCloseWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketInCloseWindow extends AbstractPacket implements PacketInCloseWindow {

	public CorePacketInCloseWindow() {
		super(CoreProtocolAdapter.VERSION);
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
