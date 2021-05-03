package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface Bed extends Directional {
	
	public boolean isOccupied();
	public void setOccupied(boolean occupied);
	public Part getPart();
	public void setPart(Part part);
	
	public static enum Part {
		HEAD,
		FOOT;

		public static Part getByName(String name) {
			Part[] parts = values();
			for (Part part : parts) {
				if (part.name().equalsIgnoreCase(name)) return part;
			}
			return null;
		}
	}

}
