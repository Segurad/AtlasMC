package de.atlasmc.schematic.filter;

import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.schematic.SchematicBlock;
import de.atlasmc.schematic.SchematicObject;

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
	public SchematicObject[][][] apply(SchematicObject[][][] objects) {
		return apply(objects, new SimpleLocation(0, 0, 0), new SimpleLocation(widhtx, height, widhtz), type);
	}

	protected SchematicObject[][][] apply(SchematicObject[][][] blocks, SimpleLocation start,
			SimpleLocation end, Material material) {
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
