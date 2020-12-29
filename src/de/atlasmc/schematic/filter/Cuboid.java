package de.atlasmc.schematic.filter;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.schematic.Schematic;
import de.atlasmc.util.Pair;

public class Cuboid implements Filter {

	protected int widhtx = 1;
	protected int height = 1;
	protected int widhtz = 1;
	protected Material type = Material.AIR;

	public Cuboid(int widhtx, int height, int widhtz, Material type) {
		this.widhtx = widhtx;
		this.height = height;
		this.widhtz = widhtz;
		this.type = type;
	}

	@Override
	public Pair<List<BlockData>, short[][][]> apply(Schematic schematic) {
		return apply(schematic, new SimpleLocation(0, 0, 0), new SimpleLocation(widhtx, height, widhtz), type);
	}

	protected Pair<List<BlockData>, short[][][]> apply(Schematic schematic, SimpleLocation start, SimpleLocation end, Material material) {
		final short[][][] data = schematic.getMapings();
		for (int x = start.getBlockX(); x < end.getBlockX(); x++) {
			for (int y = start.getBlockY(); x < end.getBlockY(); y++) {
				for (int z = start.getBlockZ(); x < end.getBlockZ(); z++) {
					SchematicObject block = blocks[x][y][z];
					if (block instanceof SchematicBlock) ((SchematicBlock) block).setType(material);
				}
			}
		}
		return blocks;
	}

}
