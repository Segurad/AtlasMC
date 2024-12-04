package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.SculkShrieker;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSculkShrieker extends CoreWaterlogged implements SculkShrieker {

	protected static final NBTFieldContainer<CoreSculkShrieker> NBT_FIELDS;
	
	protected static final CharKey 
	NBT_CAN_SUMMON = CharKey.literal("can_summon"),
	NBT_SHRIEKING = CharKey.literal("shrieking");
	
	static {
		NBT_FIELDS = CoreWaterlogged.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_CAN_SUMMON, (holder, reader) -> {
			holder.canSummon = reader.readByteTag() == 1;
		});
		NBT_FIELDS.setField(NBT_SHRIEKING, (holder, reader) -> {
			holder.shrieking = reader.readByteTag() == 1;
		});
	}
	
	private boolean canSummon;
	private boolean shrieking;
	
	public CoreSculkShrieker(Material material) {
		super(material);
	}

	@Override
	public boolean canSummon() {
		return canSummon;
	}

	@Override
	public void setCanSummon(boolean canSummon) {
		this.canSummon = canSummon;
	}

	@Override
	public boolean isShrieking() {
		return shrieking;
	}

	@Override
	public void setShrieking(boolean shrieking) {
		this.shrieking = shrieking;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (shrieking?0:2) + (canSummon?0:4);
	}
	
	@Override
	public SculkShrieker clone() {
		return (SculkShrieker) super.clone();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (canSummon)
			writer.writeByteTag(NBT_CAN_SUMMON, true);
		if (shrieking)
			writer.writeByteTag(NBT_SHRIEKING, true);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBlockData> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
