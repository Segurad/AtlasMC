package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutCloseWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCloseWindow extends AbstractPacket implements PacketOutCloseWindow {

	private byte windowID;
	
	public CorePacketOutCloseWindow() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutCloseWindow(byte windowID) {
		this();
		this.windowID = windowID;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
	}
	
	@Override
	public byte getWindowID() {
		return windowID;
	}

}
