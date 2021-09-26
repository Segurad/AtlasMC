package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TechnicalPiston;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTechnicalPiston extends CoreDirectional6Faces implements TechnicalPiston {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	TYPE = "type";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreDirectional6Faces.NBT_FIELDS);
		NBT_FIELDS.setField(TYPE, (holder, reader) -> {
			if (holder instanceof TechnicalPiston)
			((TechnicalPiston) holder).setType(Type.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private Type type;
	
	public CoreTechnicalPiston(Material material) {
		super(material);
		type = Type.NORMAL;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		if (type == null) throw new IllegalArgumentException("Type can not be null!");
		this.type = type;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue()*2+type.ordinal();
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getType() != Type.NORMAL) writer.writeStringTag(TYPE, getType().name().toLowerCase());
	}

}
