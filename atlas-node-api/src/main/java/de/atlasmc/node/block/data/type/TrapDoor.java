package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Bisected;
import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Openable;
import de.atlasmc.node.block.data.Powerable;
import de.atlasmc.node.block.data.Waterlogged;

public interface TrapDoor extends Bisected, Directional, Openable, Powerable, Waterlogged {
	
	TrapDoor clone();

}
