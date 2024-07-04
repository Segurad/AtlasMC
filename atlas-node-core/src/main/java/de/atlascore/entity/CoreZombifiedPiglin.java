package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ZombifiedPiglin;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreZombifiedPiglin extends CoreZombie implements ZombifiedPiglin {

	protected static final CharKey
	NBT_ANGER = CharKey.literal("Anger"),
	NBT_HURT_BY = CharKey.literal("HurtBy"); // used to set the flag angry 
	
	static {
		NBT_FIELDS.setField(NBT_ANGER, (holder, reader) -> {
			if (holder instanceof ZombifiedPiglin) {
				((ZombifiedPiglin) holder).setAngerTime(reader.readShortTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ANGER, (holder, reader) -> {
			if (holder instanceof ZombifiedPiglin)
				((ZombifiedPiglin) holder).setAngry(true);
			reader.skipTag();
		});
	}
	
	private int angerTime;
	private boolean angry;
	
	public CoreZombifiedPiglin(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public void setAngerTime(int ticks) {
		this.angerTime = ticks;
	}

	@Override
	public int getAngerTime() {
		return angerTime;
	}

	@Override
	public boolean isAngry() {
		return angry;
	}

	@Override
	public void setAngry(boolean angry) {
		this.angry = angry;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isAngry())
			writer.writeUUID(NBT_HURT_BY, AtlasUtil.ZERO_UUID);
		writer.writeShortTag(NBT_ANGER, getAngerTime());
	}

}
