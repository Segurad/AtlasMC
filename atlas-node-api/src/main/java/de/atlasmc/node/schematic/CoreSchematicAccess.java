package de.atlasmc.node.schematic;

import de.atlasmc.node.Location;

public class CoreSchematicAccess implements SchematicAccess {

	private boolean visible;
	private final Location off; // stores the offset and the rotation
	private final Schematic schematic;
	
	public CoreSchematicAccess(Schematic schematic, int offX, int offY, int offZ) {
		this(schematic, offX, offY, offZ, 0);
	}
	
	public CoreSchematicAccess(Schematic schematic, int offX, int offY, int offZ, float rotation) {
		this(schematic, offX, offY, offZ, rotation, true);
	}
	
	public CoreSchematicAccess(Schematic schematic, int offX, int offY, int offZ, float rotation, boolean visible) {
		off = new Location(offX, offY, offZ, rotation, 0);
		this.visible = visible;
		this.schematic = schematic;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public Location getOffset() {
		return off.clone();
	}

	@Override
	public Location getOffset(Location loc) {
		return off.copyTo(loc);
	}

	@Override
	public void setOffset(Location loc) {
		off.set(loc);		
	}

	@Override
	public Schematic getSchematic() {
		return schematic;
	}

}
