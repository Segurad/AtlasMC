package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterlogged;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Candle;

public class CoreCandle extends CoreWaterlogged implements Candle {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, 
				PropertyType.CANDLES,
				PropertyType.LIT);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
