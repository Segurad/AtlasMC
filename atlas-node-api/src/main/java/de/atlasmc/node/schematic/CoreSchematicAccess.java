package de.atlasmc.node.schematic;

import de.atlasmc.node.SimpleLocation;

public class CoreSchematicAccess implements SchematicAccess {

	private boolean visible;
	private final SimpleLocation off; // stores the offset and the rotation
	private final Schematic schematic;
	
	public CoreSchematicAccess(Schematic schematic, int offX, int offY, int offZ) {
		this(schematic, offX, offY, offZ, 0);
	}
	
	public CoreSchematicAccess(Schematic schematic, int offX, int offY, int offZ, float rotation) {
		this(schematic, offX, offY, offZ, rotation, true);
	}
	
	public CoreSchematicAccess(Schematic schematic, int offX, int offY, int offZ, float rotation, boolean visible) {
		off = new SimpleLocation(offX, offY, offZ, rotation, 0);
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
	public SimpleLocation getOffset() {
		return off.clone();
	}

	@Override
	public SimpleLocation getOffset(SimpleLocation loc) {
		return off.copyTo(loc);
	}

	@Override
	public void setOffset(SimpleLocation loc) {
		off.set(loc);		
	}

	@Override
	public Schematic getSchematic() {
		return schematic;
	}

}
