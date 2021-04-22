package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.entity.AbstractVillager.Trade;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutTradeList;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTradeList extends AbstractPacket implements PacketOutTradeList {

	private int windowID, level, experience;
	private boolean regular, canRestock;
	private List<Trade> trades;
	
	public CorePacketOutTradeList() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutTradeList(int windowID, List<Trade> trades, int level, int experience, boolean regular, boolean canRestock) {
		this();
		this.windowID = windowID;
		this.trades = new ArrayList<Trade>(trades.size());
		for (Trade t : trades) {
			this.trades.add(t.clone());
		}
		this.level = level;
		this.experience = experience;
		this.regular = regular;
		this.canRestock = canRestock;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = readVarInt(in);
		final int size = in.readByte();
		trades = new ArrayList<Trade>(size);
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
			
			Trade t = new Trade(in1, out);
			t.setInputItem2(in2);
			t.setTrades(trades);
			t.setMaxTrades(maxtrades);
			t.setXP(xp);
			t.setSpecialPrice(specialPrice);
			t.setPriceMultiplier(priceMulti);
			t.setDemand(demand);
			
			this.trades.add(t);
		}
		level = readVarInt(in);
		experience = readVarInt(in);
		regular = in.readBoolean();
		canRestock = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(windowID, out);
		out.writeByte(trades.size());
		NBTNIOWriter writer = new NBTNIOWriter(out);
		for (Trade t : trades) {
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
		writeVarInt(level, out);
		writeVarInt(experience, out);
		out.writeBoolean(regular);
		out.writeBoolean(canRestock);
	}

	@Override
	public int getWindowID() {
		return windowID;
	}

	@Override
	public List<Trade> getTrades() {
		return trades;
	}

	@Override
	public int getVillagerLevel() {
		return level;
	}

	@Override
	public int getExperience() {
		return experience;
	}

	@Override
	public boolean isRegularVillager() {
		return regular;
	}

	@Override
	public boolean getCanRestock() {
		return canRestock;
	}

}
