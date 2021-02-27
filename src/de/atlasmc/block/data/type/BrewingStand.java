package de.atlasmc.block.data.type;

import java.util.Set;

import de.atlasmc.block.data.BlockData;

public interface BrewingStand extends BlockData {
	
	public Set<Integer> getBottles();
	public int getMaxBottles();
	public boolean hasBottle(int bottle);
	public void setBottle(int bottle, boolean has);

}
