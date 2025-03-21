package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.Brushable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreBrushable extends CoreBlockData implements Brushable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.DUSTED);
	}
	
	private final int maxDusted;
	private int dusted;
	
	public CoreBrushable(BlockType type) {
		this(type, 3);
	}
	
	public CoreBrushable(BlockType type, int maxDusted) {
		super(type);
		this.maxDusted = maxDusted;
	}

	@Override
	public int getDusted() {
		return dusted;
	}

	@Override
	public int getMaxDusted() {
		return maxDusted;
	}

	@Override
	public int getStateID() {
		return super.getStateID() + dusted;
	}
	
	@Override
	public void setDusted(int dusted) {
		if (dusted < 0 || dusted > maxDusted)
			throw new IllegalArgumentException("Dusted must be between 0 and " + maxDusted + ": " + dusted);
		this.dusted = dusted;
	}
	
	@Override
	public CoreBrushable clone() {
		return (CoreBrushable) super.clone();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
