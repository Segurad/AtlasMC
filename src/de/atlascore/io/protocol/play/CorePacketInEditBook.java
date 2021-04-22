package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInEditBook;
import io.netty.buffer.ByteBuf;

public class CorePacketInEditBook extends AbstractPacket implements PacketInEditBook {

	private ItemStack book;
	private boolean isSigning;
	private int hand;
	
	public CorePacketInEditBook() {
		super(CoreProtocolAdapter.VERSION);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		book = readSlot(in);
		isSigning = in.readBoolean();
		hand = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeSlot(book, out);
		out.writeBoolean(isSigning);
		writeVarInt(hand, out);
	}

	@Override
	public ItemStack getBook() {
		return book;
	}

	@Override
	public boolean isSigning() {
		return isSigning;
	}

	@Override
	public int getHand() {
		return hand;
	}

}
