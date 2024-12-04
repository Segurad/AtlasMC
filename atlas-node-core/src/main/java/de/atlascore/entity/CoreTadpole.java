package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Tadpole;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTadpole extends CoreFish implements Tadpole {

	protected static final NBTFieldContainer<CoreTadpole> NBT_FIELDS;
	
	private static final CharKey NBT_AGE = CharKey.literal("Age");
	
	static {
		NBT_FIELDS = CoreFish.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_AGE, (holder, reader) -> {
			holder.setAge(reader.readIntTag());
		});
	}
	
	private int age;
	
	public CoreTadpole(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (age != 0)
			writer.writeIntTag(NBT_AGE, age);
	}

	@Override
	protected NBTFieldContainer<? extends CoreTadpole> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
}
