package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Ageable;
import de.atlasmc.node.block.data.Hangable;
import de.atlasmc.node.block.data.Waterlogged;

public interface MangrovePropagule extends Ageable, Sapling, Waterlogged, Hangable {
	
	MangrovePropagule clone();

}
