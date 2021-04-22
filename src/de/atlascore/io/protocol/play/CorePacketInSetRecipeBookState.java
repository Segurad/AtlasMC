package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetRecipeBookState;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetRecipeBookState extends AbstractPacket implements PacketInSetRecipeBookState {

	public CorePacketInSetRecipeBookState() {
		super(CoreProtocolAdapter.VERSION);
	}

	private int bookID;
	private boolean bookopen,filteractive;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		bookID = readVarInt(in);
		bookopen = in.readBoolean();
		filteractive = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(bookID, out);
		out.writeBoolean(bookopen);
		out.writeBoolean(filteractive);
	}

	@Override
	public int getBookID() {
		return bookID;
	}

	@Override
	public boolean getBookOpen() {
		return bookopen;
	}

	@Override
	public boolean getFilterActive() {
		return filteractive;
	}

}
