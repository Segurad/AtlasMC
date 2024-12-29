package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SpectralArrow;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSpectralArrow extends CoreAbstractArrow implements SpectralArrow {

	protected static final NBTFieldContainer<CoreSpectralArrow> NBT_FIELDS;
	
	protected static final CharKey
	NBT_DURATION = CharKey.literal("Duration");
	
	static {
		NBT_FIELDS = CoreAbstractProjectile.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_DURATION, (holder, reader) -> {
			holder.setDuration(reader.readIntTag());
		});
	}
	
	private int duration;
	
	public CoreSpectralArrow(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreSpectralArrow> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public void setDuration(int ticks) {
		this.duration = ticks;
	}

	@Override
	public int getDuration() {
		return duration;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_DURATION, getDuration());
	}

}
