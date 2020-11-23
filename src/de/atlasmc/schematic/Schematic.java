package de.atlasmc.schematic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.schematic.filter.Filter;
import de.atlasmc.world.Region;

public class Schematic {

	private SchematicBlock[][][] blocks;
	private SimpleLocation sl;
	private int rotation = 0;

	public Schematic(int widhtx, int height, int widhtz) {
		blocks = new SchematicBlock[widhtx][height][widhtz];
		sl = new SimpleLocation(0, 0, 0);
	}
	
	public Schematic(Region region, Location center) {
		if (region == null) throw new IllegalArgumentException("Region can not be null");
		if (center == null) throw new IllegalArgumentException("Location can not be null");
		blocks = new SchematicBlock[(int) (region.getWidhtX() + 1)][(int) (region.getHeight() + 1)][(int) (region.getWidhtZ() + 1)];
		sl = new SimpleLocation(region.getMinX(), region.getMinY(), region.getMinZ()).sub(center);
		int lx = 0;
		int ly = 0;
		int lz = 0;
		final Location loc = new Location(center.getWorld(), 0, 0, 0);
		for (int x = (int) region.getMinX(); x <= region.getMaxX(); x++) {
			for (int y = (int) region.getMinY(); y <= region.getMaxY(); y++) {
				for (int z = (int) region.getMinZ(); z <= region.getMaxZ(); z++) {
					loc.setX(x);
					loc.setY(y);
					loc.setZ(z);
					blocks[lx][ly][lz] = new SchematicBlock(loc.getBlock());
					lz++;
				}
				lz = 0;
				ly++;
			}
			ly = 0;
			lx++;
		}
	}

	public void setObject(SchematicBlock block, SimpleLocation loc) {
		blocks[loc.getBlockX()][loc.getBlockY()][loc.getBlockZ()] = block;
	}

	public void setObject(SchematicBlock block, Location loc) {
		blocks[loc.getBlockX()][loc.getBlockY()][loc.getBlockZ()] = block;
	}
	
	public void setObject(SchematicBlock block, int x, int y, int z) {
		blocks[x][y][z] = block;
	}

	public SchematicObject getObject(Location loc) {
		return getObject(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public SchematicObject getObject(SimpleLocation loc) {
		return getObject(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public SchematicObject getObject(int x, int y, int z) {
		return blocks[x][y][z];
	}

	public static Schematic getShematic(Region region, Location center) {
		return new Schematic(region, center);
	}

	public void mirrorY() {
		Thread t = new Thread(() -> {
			final SchematicObject[][][] new_blocks = new SchematicObject[blocks.length][blocks[0].length][blocks[0][0].length];
			sl.setY((sl.getY() == 0 ? blocks[0].length : 0));
			final int lenX = blocks.length;
			final int lenY = blocks[0].length;
			final int lenZ = blocks[0][0].length;
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						SchematicObject block = blocks[x][y][z];
						new_blocks[x][blocks[0].length - y - 1][z] = block;
					}
				}
			}
		});
		t.start();
	}

	public void mirrorX() {
		Thread t = new Thread(() -> {
			final SchematicObject[][][] new_blocks = new SchematicObject[blocks.length][blocks[0].length][blocks[0][0].length];
			sl.setY((sl.getY() == 0 ? blocks[0].length : 0));
			final int lenX = blocks.length;
			final int lenY = blocks[0].length;
			final int lenZ = blocks[0][0].length;
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						SchematicObject block = blocks[x][y][z];
						new_blocks[blocks.length - x - 1][y][z] = block;
					}
				}
			}
		});
		t.start();
	}

	public void mirrorZ() {
		Thread t = new Thread(() -> {
			final SchematicObject[][][] new_blocks = new SchematicObject[blocks.length][blocks[0].length][blocks[0][0].length];
			sl.setY((sl.getY() == 0 ? blocks[0].length : 0));
			final int lenX = blocks.length;
			final int lenY = blocks[0].length;
			final int lenZ = blocks[0][0].length;
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						SchematicObject block = blocks[x][y][z];
						new_blocks[x][y][blocks[0][0].length - z - 1] = block;
					}
				}
			}
		});
		t.start();
	}

	public void rotate(int rotation) {
		setRotation(this.rotation + rotation);
	}

	public void setRotation(int rotation) {
		this.rotation = rotation%360;
	}

	public int getRotation() {
		return rotation;
	}

	public Schematic place(Location loc) {
		return place(loc, true);
	}

	public Schematic place(Location loc, boolean air) {
		if (rotation == 0) {
			final Schematic shem = new Schematic(getWidhtX(), getHeight(), getWidhtZ());
			shem.setSourceLocation(sl.clone());
			final SimpleLocation lloc = sl.clone().add(loc);
			final Location exact = loc.clone();
			final int lenX = blocks.length;
			final int lenY = blocks[0].length;
			final int lenZ = blocks[0][0].length;
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						SchematicObject block = blocks[x][y][z];
						if (block == null) {
							if (air == false) continue;
							block = new SchematicBlock(Material.AIR);
						}
						if (block.isAir() && air == false) continue;
						exact.setX(x);
						exact.setY(y);
						exact.setZ(z);
						exact.add(lloc.getX(), lloc.getY(), lloc.getZ());
						shem.setObject(new SchematicBlock(exact.getBlock()), x, y, z);
						block.place(exact);
					}
				}
			}
			return shem;
		} else return place(loc, air, rotation);
	}

	public Schematic place(Location loc, int rotation) {
		return place(loc, true, rotation);
	}

	public Schematic place(Location loc, boolean air, int rotation) {
		final Schematic shem = new Schematic(getWidhtX(), getHeight(), getWidhtZ());
		shem.setSourceLocation(sl.clone());
		shem.setRotation(rotation);
		final SimpleLocation sexact = new SimpleLocation(0, 0, 0);
		final Location exact = loc.clone();
		final int lenX = blocks.length;
		final int lenY = blocks[0].length;
		final int lenZ = blocks[0][0].length;
		for (int x = 0; x < lenX; x++) {
			for (int y = 0; y < lenY; y++) {
				for (int z = 0; z < lenZ; z++) {
					SchematicObject block = blocks[x][y][z];
					if (block == null) {
						if (air == false) continue;
						block = new SchematicBlock(Material.AIR);
					}
					sexact.setLocation(x, y, z).add(sl).rotate(rotation).add(loc);
					exact.setX(sexact.getX());
					exact.setY(sexact.getY());
					exact.setZ(sexact.getZ());
					if (block.isAir()) {
						if (air == true) {
							shem.setObject(new SchematicBlock(exact.getBlock()), x, y, z);
							block.place(exact);
						} else
							continue;
					} else {
						shem.setObject(new SchematicBlock(exact.getBlock()), x, y, z);
						block.place(exact);
					}
				}
			}
		}
		return shem;
	}

	public int getHeight() {
		return blocks[0].length;
	}

	public int getWidhtX() {
		return blocks.length;
	}

	public int getWidhtZ() {
		return blocks[0][0].length;
	}

	public SimpleLocation getSourceLocation() {
		return sl.clone();
	}
	
	public SimpleLocation getSourLocation(SimpleLocation loc) {
		return loc.setLocation(sl);
	}

	public void setSourceLocation(SimpleLocation loc) {
		sl = loc;
	}

	public void replace(Material from, Material to) {
		replace(from, new Material[] { to });
	}

	public void replace(Material from, Material[] to) {
		replace(Arrays.asList(from), to);
	}

	public void replace(List<Material> from, Material[] to) {
		final Thread t = new Thread(() -> {
			final int lenX = blocks.length;
			final int lenY = blocks[0].length;
			final int lenZ = blocks[0][0].length;
			final Random r = new Random();
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						SchematicBlock block = blocks[x][y][z];
						if (block == null) {
							if (from.contains(Material.AIR)) { 
								block = new SchematicBlock(Material.AIR);
								blocks[x][y][z] = block;
							} else continue;
						} else if (from.contains(block.getType())) {
							final int ri = r.nextInt(100);
							block.setType(to[ri]);
						}
					}
				}
			}
		});
		t.start();
	}

	public double getVolume() {
		return getWidhtX() * getHeight() * getWidhtZ();
	}

	public void filter(Filter filter) {
		blocks = (SchematicBlock[][][]) filter.apply(blocks);
	}

	public void set(final Material mat) {
		final Thread t = new Thread(() -> {
			final int lenX = blocks.length;
			final int lenY = blocks[0].length;
			final int lenZ = blocks[0][0].length;
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						SchematicBlock block = blocks[x][y][z];
						if (block == null) blocks[x][y][z] = new SchematicBlock(mat); else
						block.setType(mat);
					}
				}
			}
		});
		t.start();
	}
}
