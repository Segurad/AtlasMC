package de.atlascore.world;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

public class CorePaletteEntry {
	
	private BlockData data;
	private short count;
	
	public CorePaletteEntry(BlockData data) {
		this.data = data;
	}
	
	public short getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = (short) count;
	}
	
	public short incrementCount() {
		return ++count;
	}
	
	public short decrementCount() {
		return --count;
	}
	
	public BlockData getData() {
		return data;
	}
	
	public void setData(BlockData data) {
		this.data = data;
	}

	public Material getMaterial() {
		return data.getMaterial();
	}

}
