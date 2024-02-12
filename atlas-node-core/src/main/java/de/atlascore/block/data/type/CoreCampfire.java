package de.atlascore.block.data.type;

import de.atlasmc.Material;
import de.atlasmc.block.data.type.Campfire;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreLightable;
import de.atlascore.block.data.CoreWaterlogged;

public class CoreCampfire extends CoreDirectional4Faces implements Campfire {

	protected static final CharKey
	SIGNALE_FIRE = CharKey.literal("signal_fire");
	
	static {
		NBT_FIELDS.setField(SIGNALE_FIRE, (holder, reader) -> {
			if (holder instanceof Campfire)
			((Campfire) holder).setSignalFire(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean lit, waterlogged, signalFire;
	
	public CoreCampfire(Material material) {
		super(material);
		lit = true;
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
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
	public boolean isSignalFire() {
		return signalFire;
	}

	@Override
	public void setSignalFire(boolean signalFire) {
		this.signalFire = signalFire;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(waterlogged?0:1)+
				(signalFire?0:2)+
				(lit?0:4)+
				getFaceValue()*8;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isLit()) 
			writer.writeByteTag(CoreLightable.NBT_LIT, true);
		if (isWaterlogged()) 
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
		if (isSignalFire()) 
			writer.writeByteTag(SIGNALE_FIRE, true);
	}

}
