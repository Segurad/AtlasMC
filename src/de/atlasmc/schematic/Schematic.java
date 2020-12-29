package de.atlasmc.schematic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.schematic.filter.Filter;
import de.atlasmc.util.Pair;
import de.atlasmc.world.Region;

public class Schematic {

	private SchematicSection[][][] mappings;
	private final List<BlockData> pallet;
	private SimpleLocation sl;
	private int rotation = 0;

	public Schematic(int widhtx, int height, int widhtz) {
		this(widhtx, height, widhtz, Material.AIR.createBlockData());
	}
	
	public Schematic(int widhtx, int height, int widhtz, BlockData defaultData) {
		mappings = new short[widhtx][height][widhtz];
		sl = new SimpleLocation(0, 0, 0);
		pallet = new ArrayList<>();
		pallet.add(defaultData);
	}
	
	public Schematic(Region region, Location center) {
		this(region, center, Material.AIR.createBlockData());
	}
	
	public Schematic(Region region, Location center, BlockData defaulBlockData) {
		if (region == null) throw new IllegalArgumentException("Region can not be null");
		if (center == null) throw new IllegalArgumentException("Location can not be null");
		mappings = new short[(int) (region.getWidhtX() + 1)][(int) (region.getHeight() + 1)][(int) (region.getWidhtZ() + 1)];
		pallet = new ArrayList<>();
		pallet.add(defaulBlockData);
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
					mappings[lx][ly][lz] = new SchematicBlock(loc.getBlock());
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
		mappings[loc.getBlockX()][loc.getBlockY()][loc.getBlockZ()] = block;
	}

	public void setObject(SchematicBlock block, Location loc) {
		mappings[loc.getBlockX()][loc.getBlockY()][loc.getBlockZ()] = block;
	}
	
	public void setObject(SchematicBlock block, int x, int y, int z) {
		mappings[x][y][z] = block;
	}

	public SchematicObject getObject(Location loc) {
		return getObject(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public SchematicObject getObject(SimpleLocation loc) {
		return getObject(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public SchematicObject getObject(int x, int y, int z) {
		return mappings[x][y][z];
	}

	public static Schematic getShematic(Region region, Location center) {
		return new Schematic(region, center);
	}

	public void mirrorY() {
		Thread t = new Thread(() -> {
			final short[][][] new_blocks = new short[mappings.length][mappings[0].length][mappings[0][0].length];
			sl.setY((sl.getY() == 0 ? mappings[0].length : 0));
			final int lenX = Math.round(mappings.length/2);
			final int lenY = Math.round(mappings[0].length/2);
			final int lenZ = Math.round(mappings[0][0].length/2);
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						final short low = mappings[x][y][z];
						final short high = mappings[x][mappings[0].length - y - 1][z];
						mappings[x][y][z] = high;
						mappings[x][mappings[0].length - y - 1][z] = low;
					}
				}
			}
		});
		t.start();
	}

	public void mirrorX() {
		Thread t = new Thread(() -> {
			sl.setY((sl.getY() == 0 ? mappings[0].length : 0));
			final int lenX = Math.round(mappings.length/2);
			final int lenY = Math.round(mappings[0].length/2);
			final int lenZ = Math.round(mappings[0][0].length/2);
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						final short low = mappings[x][y][z];
						final short high = mappings[mappings.length - x - 1][y][z];
						mappings[x][y][z] = high;
						mappings[mappings.length - x - 1][y][z] = low;
					}
				}
			}
		});
		t.start();
	}

	public void mirrorZ() {
		Thread t = new Thread(() -> {
			sl.setY((sl.getY() == 0 ? mappings[0].length : 0));
			final int lenX = Math.round(mappings.length/2);
			final int lenY = Math.round(mappings[0].length/2);
			final int lenZ = Math.round(mappings[0][0].length/2);
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						new_blocks[x][y][mappings[0][0].length - z - 1] = mappings[x][y][z];
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
			final int lenX = mappings.length;
			final int lenY = mappings[0].length;
			final int lenZ = mappings[0][0].length;
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						SchematicObject block = mappings[x][y][z];
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
		final int lenX = mappings.length;
		final int lenY = mappings[0].length;
		final int lenZ = mappings[0][0].length;
		for (int x = 0; x < lenX; x++) {
			for (int y = 0; y < lenY; y++) {
				for (int z = 0; z < lenZ; z++) {
					SchematicObject block = mappings[x][y][z];
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
		return mappings[0].length;
	}

	public int getWidhtX() {
		return mappings.length;
	}

	public int getWidhtZ() {
		return mappings[0][0].length;
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
			final int lenX = mappings.length;
			final int lenY = mappings[0].length;
			final int lenZ = mappings[0][0].length;
			final Random r = new Random();
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						SchematicBlock block = mappings[x][y][z];
						if (block == null) {
							if (from.contains(Material.AIR)) { 
								block = new SchematicBlock(Material.AIR);
								mappings[x][y][z] = block;
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
		Pair<List<BlockData>, short[][][]> result = filter.apply(this);
		this.pallet = result.getValue1();
		this.mappings = result.getValue2();
	}

	public void set(final Material mat) {
		set(mat.createBlockData());
	}
	
	public void set(final BlockData mat) {
		final Thread t = new Thread(() -> {
			pallet.clear();
			pallet.add(mat);
			final int lenX = mappings.length;
			final int lenY = mappings[0].length;
			final int lenZ = mappings[0][0].length;
			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						mappings[x][y][z] = 0;
					}
				}
			}
		});
		t.start();
	}

	public short[][][] getMapings() {
		return mappings;
	}
}
