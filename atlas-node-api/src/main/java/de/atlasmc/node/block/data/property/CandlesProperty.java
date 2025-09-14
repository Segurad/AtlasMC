package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Candle;

class CandlesProperty extends AbstractIntProperty {

	public CandlesProperty() {
		super("candles");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Candle candle)
			candle.setCandles(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Candle candle)
			return candle.getCandles();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Candle;
	}

}
