package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Interaction;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreInteraction extends CoreEntity implements Interaction {

	protected static final MetaDataField<Float> META_WIDTH = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 1f, MetaDataType.FLOAT);
	protected static final MetaDataField<Float> META_HEIGHT = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 1f, MetaDataType.FLOAT);
	protected static final MetaDataField<Boolean> META_RESPONSIVE = new MetaDataField<Boolean>(CoreEntity.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+3;
	
	protected static final NBTFieldSet<CoreInteraction> NBT_FIELDS;
	
	private static final CharKey
	NBT_WIDTH = CharKey.literal("width"),
	NBT_HEIGHT = CharKey.literal("height"),
	NBT_RESPONSE = CharKey.literal("response"),
	NBT_ATTACK = CharKey.literal("attack"),
	NBT_INTERACTION = CharKey.literal("interaction");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_WIDTH, (holder, reader) -> {
			holder.setWidth(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_HEIGHT, (holder, reader) -> {
			holder.setHeight(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_RESPONSE, (holder, reader) -> {
			holder.setResponsive(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_ATTACK, (holder, reader) -> {
			reader.readNextEntry();
			holder.setLastAttack(new PreviousInteraction(reader));
		});
		NBT_FIELDS.setField(NBT_INTERACTION, (holder, reader) -> {
			reader.readNextEntry();
			holder.setLastInteraction(new PreviousInteraction(reader));
		});
	}
	
	private PreviousInteraction lastAttack;
	private PreviousInteraction lastInteraction;
	
	public CoreInteraction(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_WIDTH);
		metaContainer.set(META_HEIGHT);
		metaContainer.set(META_RESPONSIVE);
	}

	@Override
	protected NBTFieldSet<? extends CoreInteraction> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void setWidth(float width) {
		metaContainer.get(META_WIDTH).setData(width);
	}

	@Override
	public float getWidth() {
		return metaContainer.getData(META_WIDTH);
	}

	@Override
	public void setHeight(float height) {
		metaContainer.get(META_HEIGHT).setData(height);
	}

	@Override
	public float getHeight() {
		return metaContainer.getData(META_HEIGHT);
	}

	@Override
	public boolean isResponsive() {
		return metaContainer.getData(META_RESPONSIVE);
	}

	@Override
	public void setResponsive(boolean responsive) {
		metaContainer.get(META_RESPONSIVE).setData(responsive);
	}

	@Override
	public PreviousInteraction getLastAttack() {
		return lastAttack;
	}

	@Override
	public void setLastAttack(PreviousInteraction interaction) {
		this.lastAttack = interaction;
	}

	@Override
	public PreviousInteraction getLastInteraction() {
		return lastInteraction;
	}

	@Override
	public void setLastInteraction(PreviousInteraction interaction) {
		this.lastInteraction = interaction;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		float width = getWidth();
		if (width != 1f)
			writer.writeFloatTag(NBT_WIDTH, width);
		float height = getHeight();
		if (height != 1f)
			writer.writeFloatTag(NBT_HEIGHT, height);
		if (isResponsive())
			writer.writeByteTag(NBT_RESPONSE, true);
		if (!systemData) // end of public data
			return;
		if (lastAttack != null) {
			writer.writeCompoundTag(NBT_ATTACK);
			lastAttack.toNBT(writer, systemData);
			writer.writeEndTag();
		}
		if (lastInteraction != null) {
			writer.writeCompoundTag(NBT_INTERACTION);
			lastInteraction.toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}

}
