package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreLightable;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Candle;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCandle extends CoreWaterlogged implements Candle {

	private static final NBTFieldContainer<CoreCandle> NBT_FIELDS;
	
	protected static final CharKey
	NBT_CANDLES = CharKey.literal("candles");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreWaterlogged.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_CANDLES, (holder, reader) -> {
			holder.setCandles(reader.readIntTag());
		});
	}
	
	private int candles;
	private boolean lit;
	
	public CoreCandle(Material material) {
		super(material);
		candles = 1;
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
	}

	@Override
	public int getCandles() {
		return candles;
	}

	@Override
	public int getMaxCandles() {
		return 4;
	}

	@Override
	public void setCandles(int candles) {
		if (candles > 4 || candles < 1) 
			throw new IllegalArgumentException("Candles is not between 1 and 4: " + candles);
		this.candles = candles;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (candles > 1)
			writer.writeIntTag(NBT_CANDLES, candles);
		if (lit)
			writer.writeByteTag(CoreLightable.NBT_LIT, true);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBlockData> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (lit?0:2) + (candles-1)*4;
	}

}
