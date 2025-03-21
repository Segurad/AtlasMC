package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Bed;

public class CoreBed extends CoreDirectional4Faces implements Bed {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				BlockDataProperty.OCCUPIED,
				BlockDataProperty.PART);
	}
	
	private boolean occupied;
	private Part part;
	
	public CoreBed(BlockType type) {
		super(type);
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
		if (part == null) 
			throw new IllegalArgumentException("Part can not be null!");
		this.part = part;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+getFaceValue()*4+(occupied?0:2)+part.ordinal();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
