package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAgeable;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.MangrovePropagule;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;
import static de.atlascore.block.data.type.CoreSapling.NBT_STAGE;

public class CoreMangrovePropagule extends CoreAgeable implements MangrovePropagule {

	protected static final CharKey
	NBT_HANGING = CharKey.literal("hanging");
	
	private boolean waterlogged;
	private boolean hanging;
	private int stage;
	
	public CoreMangrovePropagule(Material material) {
		super(material, 3);
	}

	@Override
	public int getMaxStage() {
		return 1;
	}

	@Override
	public int getStage() {
		return stage;
	}

	@Override
	public void setStage(int stage) {
		if (1 < stage || stage < 0) 
			throw new IllegalArgumentException("Stage is not between 0 and 1: " + stage);
		this.stage = stage;
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
	public boolean isHanging() {
		return hanging;
	}

	@Override
	public void setHanging(boolean hanging) {
		this.hanging = hanging;
	}
	
	@Override
	public MangrovePropagule clone() {
		return (MangrovePropagule) super.clone();
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+(waterlogged?0:1)+stage*2+(hanging?0:4)+getAge()*8;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getStage() > 0) 
			writer.writeIntTag(NBT_STAGE, getStage());
		if (waterlogged)
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
		if (hanging)
			writer.writeByteTag(NBT_HANGING, true);
	}

}
