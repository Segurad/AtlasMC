package de.atlascore.block.tile;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Skull;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSkull extends CoreTileEntity implements Skull {

	protected static final NBTFieldSet<CoreSkull> NBT_FIELDS;
	
	protected static final CharKey
	EXTRA_TYPE = CharKey.literal("ExtraType"),
	OWNER = CharKey.literal("Owner"),
	UUID = CharKey.literal("Id"),
	NAME = CharKey.literal("Name"),
	PROPERTIES = CharKey.literal("Properties"),
	TEXTURES = CharKey.literal("textures"),
	SIGNATURE = CharKey.literal("Signature"),
	VALUE = CharKey.literal("Value");

	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBTField<CoreSkull> name = (holder, reader) -> {
			holder.setPlayerName(reader.readStringTag());
		};
		NBT_FIELDS.setField(EXTRA_TYPE, name);
		NBT_FIELDS.setSet(OWNER)
		.setField(UUID, (holder, reader) -> {
			holder.setPlayerUUID(reader.readUUID());
		}).setField(NAME, name).setField(PROPERTIES, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				final CharSequence textureKey= reader.getFieldName();
				if (TEXTURES.equals(textureKey)) {
					reader.readNextEntry();
					while (reader.getRestPayload() > 0) {
						while (reader.getType() != TagType.TAG_END) {
							final CharSequence value = reader.getFieldName();
							if (SIGNATURE.equals(value))
								holder.setTextureSignature(reader.readStringTag());
							else if (VALUE.equals(value))
								holder.setTextureValue(reader.readStringTag());
							else
								reader.skipTag();
						}
						reader.skipTag();
					}
				} else
					reader.skipTag();
			}
			reader.readNextEntry();
		});
		
	}
	
	private String name;
	private String signature;
	private String texture;
	private UUID uuid;
	
	public CoreSkull(BlockType type) {
		super(type);
	}

	@Override
	public String getTextureSignature() {
		return signature;
	}

	@Override
	public void setTextureSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String getTextureValue() {
		return texture;
	}

	@Override
	public void setTextureValue(String value) {
		this.texture = value;
	}

	@Override
	public UUID getPlayerUUID() {
		return uuid;
	}

	@Override
	public void setPlayerUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getPlayerName() {
		return name;
	}

	@Override
	public void setPlayerName(String name) {
		this.name = name;
	}
	
	@Override
	protected NBTFieldSet<? extends CoreSkull> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeCompoundTag(OWNER);
		if (name == null || uuid == null || texture == null) {
			writer.writeEndTag();
			return;
		}
			writer.writeUUID(UUID, uuid);
			writer.writeStringTag(NAME, name);
			writer.writeCompoundTag(PROPERTIES);
			writer.writeListTag(TEXTURES, TagType.COMPOUND, 1);
			writer.writeStringTag(NAME, signature);
			writer.writeStringTag(VALUE, texture);
			writer.writeEndTag();
		writer.writeEndTag();
	}

}
