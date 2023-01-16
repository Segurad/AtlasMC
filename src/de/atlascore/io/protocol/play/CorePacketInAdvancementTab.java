package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInAdvancementTab;
import de.atlasmc.io.protocol.play.PacketInAdvancementTab.Action;
import io.netty.buffer.ByteBuf;

public class CorePacketInAdvancementTab extends PacketIO<PacketInAdvancementTab> {

	@Override
	public void read(PacketInAdvancementTab packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setAction(Action.getByID(in.readInt()));
		packet.setTabID(readString(in));
	}

	@Override
	public void write(PacketInAdvancementTab packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeInt(packet.getAction().getID());
		writeString(packet.getTabID(), out);
	}
	
	@Override
	public PacketInAdvancementTab createPacketData() {
		return new PacketInAdvancementTab();
	}
	
}
