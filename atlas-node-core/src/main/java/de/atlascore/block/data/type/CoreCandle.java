package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Candle;

public class CoreCandle extends CoreWaterlogged implements Candle {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, 
				BlockDataProperty.CANDLES,
				BlockDataProperty.LIT);
	}
	
	private int candles;
	private boolean lit;
	
	public CoreCandle(BlockType type) {
		super(type);
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
	public int getStateID() {
		return super.getStateID() + (lit?0:2) + (candles-1)*4;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
