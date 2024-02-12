package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface BrewingStand extends BlockData {
	
	public boolean hasBottle0();
	public void setBottle0(boolean has);
	public boolean hasBottle1();
	public void setBottle1(boolean has);
	public boolean hasBottle2();
	public void setBottle2(boolean has);

}
