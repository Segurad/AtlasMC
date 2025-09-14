package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Lightable;
import de.atlasmc.node.block.data.Waterlogged;

public interface Candle extends Waterlogged, Lightable {
	
	int getCandles();
	
	int getMaxCandles();
	
	void setCandles(int candles);

}
