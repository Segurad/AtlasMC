package de.atlasmc.block.data;

public interface Bed extends Directional {
	
	public boolean isOccupied();
	public void setOccupied(boolean occupied);
	public Part getPart();
	public void setPart(Part part);
	
	public static enum Part {
		HEAD,
		FOOT
	}

}
