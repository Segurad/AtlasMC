package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Candle;

class CandlesProperty extends AbstractIntProperty {

	public CandlesProperty() {
		super("candles");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Candle candle)
			candle.setCandles(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Candle candle)
			return candle.getCandles();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Candle;
	}

}
