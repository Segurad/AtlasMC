package de.atlasmc.node.block.data;

public interface AnaloguePowerable extends BlockData {
	
	public int getMaxPower();
	
	public int getPower();
	
	public void setPower(int power);

}
