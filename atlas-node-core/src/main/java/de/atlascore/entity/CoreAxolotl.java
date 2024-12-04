package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Axolotl;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAxolotl extends CoreAgeableMob implements Axolotl {

	protected static final NBTFieldContainer<CoreAxolotl> NBT_FIELDS;
	
	private static final CharKey
	NBT_FROM_BUCKET = CharKey.literal("FromBucket"),
	NBT_VARIANT = CharKey.literal("Variant");
	
	static {
		NBT_FIELDS = CoreAgeableMob.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_FROM_BUCKET, (holder, reader) -> {
			holder.setFromBucket(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_VARIANT, (holder, reader) -> {
			holder.setVariant(Variant.getByID(reader.readIntTag()));
		});
	}
	
	private Variant variant;
	private boolean fromBucket;
	
	public CoreAxolotl(EntityType type, UUID uuid) {
		super(type, uuid);
		variant = Variant.LUCY;
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreAxolotl> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public Variant getVariant() {
		return variant;
	}

	@Override
	public void setVariant(Variant variant) {
		if (variant == null)
			throw new IllegalArgumentException("Variant can not be null!");
		this.variant = variant;
	}

	@Override
	public boolean isFromBucket() {
		return fromBucket;
	}

	@Override
	public void setFromBucket(boolean bucket) {
		this.fromBucket = bucket;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(NBT_FROM_BUCKET, fromBucket);
		writer.writeIntTag(NBT_VARIANT, variant.getID());
	}

}
