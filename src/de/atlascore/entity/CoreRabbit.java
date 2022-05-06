package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Rabbit;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreRabbit extends CoreAgeableMob implements Rabbit {

	protected static final MetaDataField<Integer>
	META_RABBIT_TYPE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.INT);

	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	protected static final CharKey
	// NBT_MORE_CARROT_TICKS = "MoreCarrotTicks", TODO unnecessary 
	// NBT_OWNER = "Owner", TODO unnecessary
	NBT_RABBIT_TYPE = CharKey.of("RabbitType");
	
	static {
		NBT_FIELDS.setField(NBT_RABBIT_TYPE, (holder, reader) -> {
			if (holder instanceof Rabbit) {
				((Rabbit) holder).setRabbitType(Type.getByID(reader.readIntTag()));
			} else reader.skipTag();
		});
	}
	
	public CoreRabbit(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_RABBIT_TYPE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public Type getRabbitType() {
		return Type.getByID(metaContainer.getData(META_RABBIT_TYPE));
	}

	@Override
	public void setRabbitType(Type type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		metaContainer.get(META_RABBIT_TYPE).setData(type.getID());		
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_RABBIT_TYPE, getRabbitType().getID());
	}

}
