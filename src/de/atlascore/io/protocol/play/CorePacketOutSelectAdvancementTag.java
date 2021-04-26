package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSelectAdvancementTab;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSelectAdvancementTag extends AbstractPacket implements PacketOutSelectAdvancementTab {

	private String tabid;
	
	public CorePacketOutSelectAdvancementTag() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSelectAdvancementTag(String tabid) {
		this();
		this.tabid = tabid;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		boolean has = in.readBoolean();
		if (has)
			tabid = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeBoolean(tabid != null);
		if (tabid != null)
			writeString(tabid, out);
	}

	@Override
	public boolean hasTabID() {
		return tabid != null;
	}

	@Override
	public String getTabID() {
		return tabid;
	}

}
