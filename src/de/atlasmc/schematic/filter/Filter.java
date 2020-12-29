package de.atlasmc.schematic.filter;

import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.schematic.Schematic;
import de.atlasmc.util.Pair;

public interface Filter {
	public Pair<List<BlockData>, short[][][]> apply(Schematic schematic);
}