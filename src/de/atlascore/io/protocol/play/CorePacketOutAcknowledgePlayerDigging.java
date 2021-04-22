package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutAcknowledgePlayerDigging;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAcknowledgePlayerDigging extends AbstractPacket implements PacketOutAcknowledgePlayerDigging {

	private long pos;
	private int blockstate, status;
	private boolean successful;
	
	public CorePacketOutAcknowledgePlayerDigging() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutAcknowledgePlayerDigging(long pos, int blockstate, int status, boolean successful) {
		this();
		this.pos = pos;
		this.blockstate = blockstate;
		this.status = status;
		this.successful = successful;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		pos = in.readLong();
		blockstate = readVarInt(in);
		status = readVarInt(in);
		successful = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(pos);
		writeVarInt(blockstate, out);
		writeVarInt(status, out);
		out.writeBoolean(successful);
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public int getBlockState() {
		return blockstate;
	}

	@Override
	public boolean isSuccessful() {
		return successful;
	}
	
	

}
