package de.atlascore.block.tile;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Skull;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreSkull extends CoreTileEntity implements Skull {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	EXTRA_TYPE = CharKey.of("ExtraType"),
	OWNER = CharKey.of("Owner"),
	UUID = CharKey.of("Id"),
	NAME = CharKey.of("Name"),
	PROPERTIES = CharKey.of("Properties"),
	TEXTURES = CharKey.of("textures"),
	SIGNATURE = CharKey.of("Signature"),
	VALUE = CharKey.of("Value");

	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBTField name = (holder, reader) -> {
			((Skull) holder).setPlayerName(reader.readStringTag());
		};
		NBT_FIELDS.setField(EXTRA_TYPE, name);
		NBT_FIELDS.setContainer(OWNER)
		.setField(UUID, (holder, reader) -> {
			((Skull) holder).setPlayerUUID(reader.readUUID());
		}).setField(NAME, name).setField(PROPERTIES, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				final CharSequence textureKey= reader.getFieldName();
				if (TEXTURES.equals(textureKey)) {
					Skull skull = (Skull) holder;
					reader.readNextEntry();
					while (reader.getRestPayload() > 0) {
						while (reader.getType() != TagType.TAG_END) {
							final CharSequence value = reader.getFieldName();
							if (SIGNATURE.equals(value))
								skull.setTextureSignature(reader.readStringTag());
							else if (VALUE.equals(value))
								skull.setTextureValue(reader.readStringTag());
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
	
	private String name, signature, texture;
	private UUID uuid;
	
	public CoreSkull(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
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
	protected NBTFieldContainer getFieldContainerRoot() {
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
