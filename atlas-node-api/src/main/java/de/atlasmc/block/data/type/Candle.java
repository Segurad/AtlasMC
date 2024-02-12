package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Lightable;
import de.atlasmc.block.data.Waterlogged;

public interface Candle extends Waterlogged, Lightable {
	
	int getCandles();
	
	int getMaxCandles();
	
	void setCandles(int candles);

}
