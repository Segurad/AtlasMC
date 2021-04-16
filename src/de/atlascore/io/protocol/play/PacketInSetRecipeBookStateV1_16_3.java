package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInSetRecipeBookState;
import io.netty.buffer.ByteBuf;

public class PacketInSetRecipeBookStateV1_16_3 extends AbstractPacket implements PacketInSetRecipeBookState {

	public PacketInSetRecipeBookStateV1_16_3() {
		super(0x1F, V1_16_3.version);
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
	public int BookID() {
		return bookID;
	}

	@Override
	public boolean BookOpen() {
		return bookopen;
	}

	@Override
	public boolean FilterActive() {
		return filteractive;
	}

}
