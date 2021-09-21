package de.atlascore.block.tile;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Skull;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreSkull extends CoreTileEntity implements Skull {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	EXTRA_TYPE = "ExtraType",
	OWNER = "Owner",
	UUID = "Id",
	NAME = "Name",
	PROPERTIES = "Properties",
	TEXTURES = "textures",
	SIGNATURE = "Signature",
	VALUE = "Value";

	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBTField name = (holder, reader) -> {
			if (holder instanceof Skull)
			((Skull) holder).setPlayerName(reader.readStringTag());
			else reader.skipTag();
		};
		NBT_FIELDS.setField(EXTRA_TYPE, name);
		NBT_FIELDS.setContainer(OWNER)
		.setField(UUID, (holder, reader) -> {
			if (holder instanceof Skull)
			((Skull) holder).setPlayerUUID(reader.readUUID());
		}).setField(NAME, name).setField(PROPERTIES, (holder, reader) -> {
			if (!(holder instanceof Skull)) {
				reader.skipTag();
				return;
			}
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				switch (reader.getFieldName()) {
				case TEXTURES:
					Skull skull = (Skull) holder;
					reader.readNextEntry();
					while (reader.getRestPayload() > 0) {
						switch (reader.getFieldName()) {
						case SIGNATURE:
							skull.setTextureSignature(reader.readStringTag());
							break;
						case VALUE:
							skull.setTextureValue(reader.readStringTag());
							break;
						default:
							reader.skipTag();
							break;
						}
					}
					break;
				default:
					reader.skipTag();
					break;
				}
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
