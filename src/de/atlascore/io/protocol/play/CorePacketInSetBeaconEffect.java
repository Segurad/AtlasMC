package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetBeaconEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetBeaconEffect extends AbstractPacket implements PacketInSetBeaconEffect {

	public CorePacketInSetBeaconEffect() {
		super(CoreProtocolAdapter.VERSION);
	}

	private int primeffect,secondeffect;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		primeffect = readVarInt(in);
		secondeffect = readVarInt(in);	
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(primeffect, out);
		writeVarInt(secondeffect, out);
	}

	@Override
	public int getPrimaryEffect() {
		return primeffect;
	}

	@Override
	public int getSecondaryEffect() {
		return secondeffect;
	}

}
