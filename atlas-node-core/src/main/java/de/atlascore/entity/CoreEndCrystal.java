package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.SimpleLocation;
import de.atlasmc.entity.EndCrystal;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreEndCrystal extends CoreEntity implements EndCrystal {
	
	protected static final MetaDataField<Long>
	META_BEAM_TARGET = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, null, MetaDataType.OPT_POSITION);
	protected static final MetaDataField<Boolean>
	META_SHOW_BOTTOM = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, true, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	protected static final NBTFieldSet<CoreEndCrystal> NBT_FIELDS;
	
	protected static final CharKey
	NBT_BEAM_TARGET = CharKey.literal("BeamTarget"),
	NBT_X = CharKey.literal("X"),
	NBT_Y = CharKey.literal("Y"),
	NBT_Z = CharKey.literal("Z"),
	NBT_SHOW_BOTTOM = CharKey.literal("ShowBottom");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_BEAM_TARGET, (holder, reader) -> {
			reader.readNextEntry();
			int x = 0;
			int y = 0;
			int z = 0;
			while (reader.getType() != TagType.TAG_END) {
				final CharSequence value = reader.getFieldName();
				if (NBT_X.equals(value))
					x = reader.readIntTag();
				else if (NBT_Y.equals(value))
					y = reader.readIntTag();
				else if (NBT_Z.equals(value))
					z = reader.readIntTag();
				else
					reader.skipTag();
			}
			holder.setBeamTarget(x, y, z);
		});
		NBT_FIELDS.setField(NBT_SHOW_BOTTOM, (holder, reader) -> {
			holder.setShowBottom(reader.readByteTag() == 1);
		});
	}
	
	public CoreEndCrystal(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreEndCrystal> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_BEAM_TARGET);
		metaContainer.set(META_SHOW_BOTTOM);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public SimpleLocation getBeamTarget() {
		return hasTarget() ? getBeamTarget(new SimpleLocation()) : null;
	}

	@Override
	public SimpleLocation getBeamTarget(SimpleLocation loc) {
		return hasTarget() ? MathUtil.getLocation(loc, metaContainer.getData(META_BEAM_TARGET)) : null;
	}

	@Override
	public void setBeamTarget(SimpleLocation loc) {
		if (loc == null) {
			resetTarget();
			return;
		}
		metaContainer.get(META_BEAM_TARGET).setData(MathUtil.toPosition(loc));
	}

	@Override
	public void setBeamTarget(int x, int y, int z) {
		metaContainer.get(META_BEAM_TARGET).setData(MathUtil.toPosition(x, y, z));
	}

	@Override
	public boolean getShowBottom() {
		return metaContainer.getData(META_SHOW_BOTTOM);
	}

	@Override
	public void setShowBottom(boolean show) {
		metaContainer.get(META_SHOW_BOTTOM).setData(show);		
	}

	@Override
	public boolean hasTarget() {
		return metaContainer.getData(META_BEAM_TARGET) != null;
	}

	@Override
	public void resetTarget() {
		metaContainer.get(META_BEAM_TARGET).setData(null);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasTarget()) {
			writer.writeCompoundTag();
			long target = metaContainer.getData(META_BEAM_TARGET);
			writer.writeIntTag(NBT_X, MathUtil.getPositionX(target));
			writer.writeIntTag(NBT_Y, MathUtil.getPositionY(target));
			writer.writeIntTag(NBT_Z, MathUtil.getPositionZ(target));
			writer.writeEndTag();
		}
		if (getShowBottom())
			writer.writeByteTag(NBT_SHOW_BOTTOM, true);
	}

}
