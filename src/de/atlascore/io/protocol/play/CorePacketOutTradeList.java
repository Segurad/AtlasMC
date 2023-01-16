package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.entity.Merchant.MerchantRecipe;
import de.atlasmc.inventory.ItemStack;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutTradeList;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTradeList extends PacketIO<PacketOutTradeList> {

	@Override
	public void read(PacketOutTradeList packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(readVarInt(in));
		final int size = in.readByte();
		List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>(size);
		NBTNIOReader reader = new NBTNIOReader(in);
		for (int i = 0; i < size; i++) {
			ItemStack in1 = readSlot(in, reader);
			ItemStack out = readSlot(in, reader);
			boolean hasin2 = in.readBoolean();
			ItemStack in2 = null;
			if (hasin2) in2 = readSlot(in, reader);
			int trades = in.readInt();
			int maxtrades = in.readInt();
			int xp = in.readInt();
			int specialPrice = in.readInt();
			float priceMulti = in.readFloat();
			int demand = in.readInt();
			
			MerchantRecipe t = new MerchantRecipe(in1, out);
			t.setInputItem2(in2);
			t.setTrades(trades);
			t.setMaxTrades(maxtrades);
			t.setXP(xp);
			t.setSpecialPrice(specialPrice);
			t.setPriceMultiplier(priceMulti);
			t.setDemand(demand);
			
			recipes.add(t);
		}
		reader.close();
		packet.setTrades(recipes);
		packet.setLevel(readVarInt(in));
		packet.setExperience(readVarInt(in));
		packet.setRegular(in.readBoolean());
		packet.setCanRestock(in.readBoolean());
	}

	@Override
	public void write(PacketOutTradeList packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getWindowID(), out);
		out.writeByte(packet.getTrades().size());
		NBTNIOWriter writer = new NBTNIOWriter(out);
		for (MerchantRecipe t : packet.getTrades()) {
			writeSlot(t.getInputItem1(), out, writer);
			writeSlot(t.getOutputItem(), out, writer);
			out.writeBoolean(t.hasInputItem2());
			if (t.hasInputItem2())
				writeSlot(t.getInputItem2(), out, writer);
			out.writeBoolean(t.isDisabled());
			out.writeInt(t.getTrades());
			out.writeInt(t.getMaxTrades());
			out.writeInt(t.getXP());
			out.writeInt(t.getSpecialPrice());
			out.writeFloat(t.getPriceMultiplier());
			out.writeInt(t.getDemand());
		}
		writer.close();
		writeVarInt(packet.getLevel(), out);
		writeVarInt(packet.getExperience(), out);
		out.writeBoolean(packet.isRegular());
		out.writeBoolean(packet.canRestock());
	}
	
	@Override
	public PacketOutTradeList createPacketData() {
		return new PacketOutTradeList();
	}

}
