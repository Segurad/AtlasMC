package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FallingBlock;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFallingBlock extends CoreEntity implements FallingBlock {
	
	protected static final MetaDataField<Long> 
	META_SPAWN_POS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0L, MetaDataType.POSITION);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	protected static final NBTFieldSet<CoreFallingBlock> NBT_FIELDS;
	
	protected static final CharKey
	NBT_BLOCK_STATE = CharKey.literal("BlockState"),
	NBT_NAME = CharKey.literal("Name"),
	NBT_PROPERTIES = CharKey.literal("Properties"),
	NBT_DROP_ITEM = CharKey.literal("DropItem"),
	NBT_FALL_HURT_AMOUNT = CharKey.literal("FallHurtAmount"),
	NBT_FALL_HURT_MAX = CharKey.literal("FallHurtMax"),
	NBT_HURT_ENTITIES = CharKey.literal("HurtEntities"),
	NBT_TILE_ENTITY_DATA = CharKey.literal("TileEntityData");
	// TODO unused NBT_TIME = "Time";
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_BLOCK_STATE, (holder, reader) -> {
			reader.readNextEntry();
			BlockType type = null;
			BlockData data = null;
			while (reader.getType() != TagType.TAG_END) {
				final CharSequence value = reader.getFieldName();
				if (NBT_NAME.equals(value))
					type = BlockType.get(reader.readStringTag());
				else if (NBT_PROPERTIES.equals(value))
					if (type == null)
						reader.skipTag();
					else {
						data = type.createBlockData();
						reader.readNextEntry();
						data.fromNBT(reader);
					}
				else
					reader.skipTag();
			}
			reader.skipTag();
			if (data != null)
				holder.setBlockData(data);
			else if (type != null)
				holder.setBlockDataType(type);
		});
		NBT_FIELDS.setField(NBT_DROP_ITEM, (holder, reader) -> {
			holder.setDropItems(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_FALL_HURT_AMOUNT, (holder, reader) -> {
			holder.setBaseDamage(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_FALL_HURT_MAX, (holder, reader) -> {
			holder.setMaxDamage(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_HURT_ENTITIES, (holder, reader) -> {
			holder.setHurtEntities(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_TILE_ENTITY_DATA, (holder, reader) -> {
			reader.readNextEntry();
			TileEntity tile = TileEntity.getFromNBT(reader);
			if (tile == null) {
				while (reader.getType() != TagType.TAG_END)
					reader.skipTag();
				reader.readNextEntry();
			} else tile.fromNBT(reader);
			holder.setTileEntity(tile);
		});
	}
	
	private boolean canHurtEntities;
	private boolean canDropItem;
	private float baseDamage;
	private int maxDamage = -1;
	private BlockData data;
	private TileEntity tile;
	
	public CoreFallingBlock(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreFallingBlock> getFieldSetRoot() {
		return NBT_FIELDS;
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SPAWN_POS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean canHurtEntities() {
		return canHurtEntities;
	}

	@Override
	public BlockData getBlockData() {
		return data;
	}

	@Override
	public boolean canDropItems() {
		return canDropItem;
	}

	@Override
	public BlockType getBlockType() {
		return data == null ? tile == null ? null : tile.getType() : data.getType();
	}

	@Override
	public void setDropItems(boolean drop) {
		this.canDropItem = drop;
	}

	@Override
	public void setHurtEntities(boolean hurtEntities) {
		this.canHurtEntities = hurtEntities;
	}

	@Override
	public void setBlockData(BlockData data) {
		this.data = data;
	}

	@Override
	public void setBlockDataType(BlockType type) {
		if (type == null)
			this.data = null;
		else
			this.data = type.createBlockData();
	}

	@Override
	public void setBaseDamage(float damage) {
		this.baseDamage = damage;
	}

	@Override
	public float getBaseDamage() {
		return baseDamage;
	}

	@Override
	public void setMaxDamage(int damage) {
		this.maxDamage = damage;
	}

	@Override
	public int getMaxDamage() {
		return maxDamage;
	}

	@Override
	public void setTileEntity(TileEntity tile) {
		this.tile = tile;
	}

	@Override
	public TileEntity getTileEntity() {
		return tile;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getBlockData() != null) {
			BlockData data = getBlockData();
			writer.writeCompoundTag(NBT_BLOCK_STATE);
			if (systemData)
				writer.writeStringTag(NBT_NAME, data.getType().getNamespacedKeyRaw());
			else
				writer.writeStringTag(NBT_NAME, data.getType().getClientKey().toString());
			writer.writeCompoundTag(NBT_PROPERTIES);
			data.toNBT(writer, systemData);
			writer.writeEndTag();
			writer.writeEndTag();
		}
		if (canDropItems())
			writer.writeByteTag(NBT_DROP_ITEM, true);
		writer.writeFloatTag(NBT_FALL_HURT_AMOUNT, getBaseDamage());
		writer.writeIntTag(NBT_FALL_HURT_MAX, getMaxDamage());
		if (canHurtEntities())
			writer.writeByteTag(NBT_HURT_ENTITIES, true);
		if (getTileEntity() != null) {
			writer.writeCompoundTag(NBT_TILE_ENTITY_DATA);
			getTileEntity().toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}
	
}
