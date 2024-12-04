package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.BigDripleaf;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBigDripleaf extends CoreDirectional4Faces implements BigDripleaf {

	protected static final NBTFieldContainer<CoreBigDripleaf> NBT_FIELDS;
	
	protected static final CharKey
	NBT_TILT = CharKey.literal("tilt");
	
	static {
		NBT_FIELDS = CoreDirectional4Faces.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_TILT, (holder, reader) -> {
			Tilt tilt = Tilt.getByName(reader.readStringTag());
			holder.setTilt(tilt);
		});
	}
	
	private Tilt tilt;
	private boolean waterlogged;
	
	public CoreBigDripleaf(Material material) {
		super(material);
		tilt = Tilt.NONE;
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
	public Tilt getTilt() {
		return tilt;
	}

	@Override
	public void setTilt(Tilt tilt) {
		if (tilt == null)
			throw new IllegalArgumentException("Tilt can not be null!");
		this.tilt = tilt;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (waterlogged?0:1)+tilt.getID()*2+getFaceValue()*8;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (tilt != Tilt.NONE)
			writer.writeStringTag(NBT_TILT, tilt.getName());
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBlockData> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public CoreBigDripleaf clone() {
		return (CoreBigDripleaf) super.clone();
	}

}
