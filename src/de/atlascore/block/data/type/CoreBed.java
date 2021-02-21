package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Bed;
import de.atlasmc.util.Validate;

public class CoreBed extends CoreDirectional4Faces implements Bed {

	private boolean occupied;
	private Part part;
	
	public CoreBed(Material material) {
		super(material);
		part = Part.FOOT;
	}

	@Override
	public boolean isOccupied() {
		return occupied;
	}

	@Override
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	@Override
	public Part getPart() {
		return part;
	}

	@Override
	public void setPart(Part part) {
		Validate.notNull(part, "Part can not be null!");
		this.part = part;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue(getFacing())*4+(occupied?0:2)+part.ordinal();
	}

}
