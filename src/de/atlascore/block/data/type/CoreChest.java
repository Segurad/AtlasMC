package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Chest;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreChest extends CoreDirectional4Faces implements Chest {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	TYPE = "type";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreDirectional4Faces.NBT_FIELDS);
		NBT_FIELDS.setField(TYPE, (holder, reader) -> {
			if (holder instanceof Chest)
			((Chest) holder).setType(Type.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private boolean waterlogged;
	private Type type;
	
	public CoreChest(Material material) {
		super(material);
		type = Type.SINGLE;
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
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
		return getMaterial().getBlockID()+
				getFaceValue()*6+
				type.ordinal()*2+
				(waterlogged?0:1);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getType() != Type.SINGLE) writer.writeStringTag(TYPE, getType().name().toLowerCase());
	}

}
