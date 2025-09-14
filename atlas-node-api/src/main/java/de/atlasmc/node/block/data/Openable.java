package de.atlasmc.node.block.data;

public interface Openable extends BlockData {
	
	boolean isOpen();
	
	void setOpen(boolean open);

}
