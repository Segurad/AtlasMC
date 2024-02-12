package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TechnicalPiston;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTechnicalPiston extends CoreDirectional6Faces implements TechnicalPiston {

	protected static final ChildNBTFieldContainer<CoreTechnicalPiston> NBT_FIELDS;
	
	protected static final CharKey
	TYPE = CharKey.literal("type");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreDirectional6Faces.NBT_FIELDS);
		NBT_FIELDS.setField(TYPE, (holder, reader) -> {
			holder.setType(Type.getByName(reader.readStringTag()));
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
		return getMaterial().getBlockStateID()+getFaceValue()*2+type.ordinal();
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreTechnicalPiston> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getType() != Type.NORMAL) writer.writeStringTag(TYPE, getType().name().toLowerCase());
	}

}
