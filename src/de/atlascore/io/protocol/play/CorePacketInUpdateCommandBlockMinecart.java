package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInUpdateCommandBlockMinecart;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateCommandBlockMinecart extends AbstractPacket implements PacketInUpdateCommandBlockMinecart {

	public CorePacketInUpdateCommandBlockMinecart() {
		super(0x27, CoreProtocolAdapter.VERSION);
	}
	
	private int entityID;
	private String cmd;
	private boolean trackoutput;

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		cmd = readString(in);
		trackoutput = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		writeString(cmd, out);
		out.writeBoolean(trackoutput);
	}

	@Override
	public int EntityID() {
		return entityID;
	}

	@Override
	public String Command() {
		return cmd;
	}

	@Override
	public boolean TrackOutput() {
		return trackoutput;
	}

}
