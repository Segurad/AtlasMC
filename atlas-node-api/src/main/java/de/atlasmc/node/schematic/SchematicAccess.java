package de.atlasmc.node.schematic;

import de.atlasmc.node.Location;

/**
 * Represents a wrapping of a {@link Schematic} used within Schematics as Parts
 */
public interface SchematicAccess {
	
	public boolean isVisible();
	
	public void setVisible(boolean visible);
	
	public Location getOffset();
	
	public Location getOffset(Location loc);
	
	public void setOffset(Location loc);
	
	public Schematic getSchematic();

}
