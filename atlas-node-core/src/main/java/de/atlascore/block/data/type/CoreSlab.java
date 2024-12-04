package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Slab;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSlab extends CoreBlockData implements Slab {

	protected static final NBTFieldContainer<CoreSlab> NBT_FIELDS;
	
	protected static final CharKey
	TYPE = CharKey.literal("type");
	
	static {
		NBT_FIELDS = CoreBlockData.NBT_FIELDS.fork();
		NBT_FIELDS.setField(TYPE, (holder, reader) -> {
			holder.setType(Type.getByName(reader.readStringTag()));
		});
	}
	
	private boolean waterlogged;
	private Type type;
	
	public CoreSlab(Material material) {
		super(material);
		type = Type.BOTTOM;
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
		return super.getStateID()+
				(waterlogged?0:1)+
				type.ordinal()*2;
	}

	@Override
	protected NBTFieldContainer<? extends CoreSlab> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getType() != Type.BOTTOM) 
			writer.writeStringTag(TYPE, getType().name().toLowerCase());
		if (isWaterlogged()) 
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true); 
	}
	
}
