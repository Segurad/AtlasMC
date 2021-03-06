package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInAdvancementTab;
import io.netty.buffer.ByteBuf;

public class CorePacketInAdvancementTab extends AbstractPacket implements PacketInAdvancementTab {

	public CorePacketInAdvancementTab() {
		super(CoreProtocolAdapter.VERSION);
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
	public Action getAction() {
		return action == 0 ? Action.OPEN : Action.CLOSE;
	}

	@Override
	public String getTabID() {
		return tabID;
	}
	
	

}
