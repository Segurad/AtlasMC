package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInAdvancementTab;
import io.netty.buffer.ByteBuf;

public class PacketInAdvancementTabV1_16_3 extends AbstractPacket implements PacketInAdvancementTab {

	public PacketInAdvancementTabV1_16_3() {
		super(0x22, V1_16_3.version);
	}
	
	private int action;
	private String tabID;

	@Override
	public void read(ByteBuf in) throws IOException {
		action = in.readInt();
		tabID = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(action);
		writeString(tabID, out);
	}

	@Override
	public int Action() {
		return action;
	}

	@Override
	public String TabID() {
		return tabID;
	}
	
	

}
