package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutOpenSignEditor;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenSignEditor extends AbstractPacket implements PacketOutOpenSignEditor {

	private long pos;
	
	public CorePacketOutOpenSignEditor() {
		super(CoreProtocolAdapter.VERSION);
	}

	public CorePacketOutOpenSignEditor(long position) {
		this();
		this.pos = position;
	}
	
	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
	}

	@Override
	public long getPosition() {
		return pos;
	}

}
