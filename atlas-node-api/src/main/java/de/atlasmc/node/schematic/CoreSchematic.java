package de.atlasmc.node.schematic;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3i;

public class CoreSchematic implements Schematic {
	
	private final SchematicSection[] sections; // ordered by y > z > x
	private List<SchematicAccess> schematics;
	private final Vector3i off;
	private final int lengthX;
	private final int lengthZ;
	private final int height;
	
	/**
	 * Creates a new CoreSchamtic instance with no contents
	 * @param off the offset added when placed
	 */
	public CoreSchematic(Vector3i off) {
		this(off, 0, 0, 0);
	}
	
	/**
	 * Creates a new CoreSchematic instance<br>
	 * if one of the size parameters is >= 0 the schematic does not have block contents
	 * but still can act as a container for schematics
	 * @param off the offset added when placed
	 * @param lengthX
	 * @param height
	 * @param lengthZ 
	 */
	public CoreSchematic(Vector3i off, int lengthX, int height, int lengthZ) {
		this.off = new Vector3i(off);
		this.lengthX = lengthX;
		this.lengthZ = lengthZ;
		this.height = height;
		sections = lengthX == 0 || lengthZ == 0 || height == 0 ? 
				null : new SchematicSection[lengthX>>4*lengthZ>>4*height>>4];
	}
	
	@Override
	public SchematicSection getSection(int x, int y, int z) {
		return sections[getIndex(x, y, z)];
	}
	
	protected int getIndex(int x, int y, int z) {
		return (y >> 4) * (z >> 4 * lengthX >> 4) + (x >> 4);
	}

	@Override
	public int getHight() {
		return height;
	}

	@Override
	public int getLengthX() {
		return lengthX;
	}

	@Override
	public int getLengthZ() {
		return lengthZ;
	}

	@Override
	public Vector3i getOffset() {
		return new Vector3i(off);
	}

	@Override
	public Vector3i getOffset(Vector3i loc) {
		off.set(loc);
		return loc;
	}

	@Override
	public void setOffset(Vector3i loc) {
		off.set(loc);
	}

	@Override
	public List<SchematicAccess> getSchematics() {
		return schematics == null ? List.of() : schematics;
	}

	@Override
	public SchematicAccess addSchematic(Schematic schematic, int offX, int offY, int offZ, float rotation, boolean visible) {
		if (schematics == null) 
			schematics = new ArrayList<>();
		SchematicAccess access = new CoreSchematicAccess(schematic, offX, offY, offZ, rotation, visible);
		schematics.add(access);
		return access;
	}

}