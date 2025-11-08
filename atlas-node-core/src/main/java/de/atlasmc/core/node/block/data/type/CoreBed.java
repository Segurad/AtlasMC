package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Bed;

public class CoreBed extends CoreDirectional4Faces implements Bed {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				PropertyType.OCCUPIED,
				PropertyType.PART);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
