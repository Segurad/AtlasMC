package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Chicken;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreChicken extends CoreAgeableMob implements Chicken {

	protected static final CharKey
	NBT_EGG_LAY_TIME = CharKey.literal("EggLayTime");
	// NBT_IS_CHICKEN_JOCKEY = "IsChickenJockey"; TODO unnecessary value
	
	static {
		NBT_FIELDS.setField(NBT_EGG_LAY_TIME, (holder, reader) -> {
			if (holder instanceof Chicken) {
				((Chicken) holder).setEggLayTime(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	private int eggLayTime = -1;
	
	public CoreChicken(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public void setEggLayTime(int time) {
		this.eggLayTime = time;
	}

	@Override
	public int getEggLayTime() {
		return eggLayTime;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_EGG_LAY_TIME, getEggLayTime());
	}

}
