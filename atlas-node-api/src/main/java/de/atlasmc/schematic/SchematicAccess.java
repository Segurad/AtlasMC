package de.atlasmc.schematic;

import de.atlasmc.SimpleLocation;

/**
 * Represents a wrapping of a {@link Schematic} used within Schematics as Parts
 */
public interface SchematicAccess {
	
	public boolean isVisible();
	
	public void setVisible(boolean visible);
	
	public SimpleLocation getOffset();
	
	public SimpleLocation getOffset(SimpleLocation loc);
	
	public void setOffset(SimpleLocation loc);
	
	public Schematic getSchematic();

}
