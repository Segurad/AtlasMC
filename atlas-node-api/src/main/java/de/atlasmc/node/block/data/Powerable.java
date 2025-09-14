package de.atlasmc.node.block.data;

public interface Powerable extends BlockData {
	
	public boolean isPowered();
	public void setPowered(boolean powered);

}
