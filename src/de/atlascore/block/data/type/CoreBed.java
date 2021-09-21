package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Bed;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBed extends CoreDirectional4Faces implements Bed {

	private boolean occupied;
	private Part part;
	
	protected static final String 
	OCCUPIED = "occupied",
	PART = "part";
	
	static {
		NBT_FIELDS.setField(OCCUPIED, (holder, reader) -> {
			if (Bed.class.isInstance(holder)) {
				((Bed) holder).setOccupied(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(PART, (holder, reader) -> {
			if (Bed.class.isInstance(holder)) {
				((Bed) holder).setPart(Part.getByName(reader.readStringTag()));
			} else reader.skipTag();
		});
	}
	
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
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(OCCUPIED, occupied);
		writer.writeStringTag(PART, part.name().toLowerCase());
	}

}
