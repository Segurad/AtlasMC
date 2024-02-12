package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Brushable;
import de.atlasmc.util.map.key.CharKey;

public class CoreBrushable extends CoreBlockData implements Brushable {

	public static final CharKey NBT_DUSTED = CharKey.literal("dusted");
	
	private final int maxDusted;
	private int dusted;
	
	public CoreBrushable(Material material) {
		this(material, 3);
	}
	
	public CoreBrushable(Material material, int maxDusted) {
		super(material);
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

}
