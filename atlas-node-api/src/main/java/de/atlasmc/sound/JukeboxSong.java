package de.atlasmc.sound;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class JukeboxSong extends ProtocolRegistryValueBase {
	
	protected static final NBTFieldSet<JukeboxSong> NBT_FIELDS;
	
	protected static final CharKey
	NBT_SOUND_EVENT = CharKey.literal("sound_event"),
	NBT_DESCRIPTION = CharKey.literal("description"),
	NBT_LENGTH_IN_SECONDS = CharKey.literal("length_in_seconds"),
	NBT_COMPARATOR_OUTPUT = CharKey.literal("comparator_output");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_SOUND_EVENT, (holder, reader) -> {
			holder.sound = Sound.fromNBT(reader);
		});
		NBT_FIELDS.setField(NBT_DESCRIPTION, (holder, reader) -> {
			holder.description = ChatUtil.fromNBT(reader);
		});
		NBT_FIELDS.setField(NBT_COMPARATOR_OUTPUT, (holder, reader) -> {
			holder.comparatorOutput = reader.readIntTag();
		});
		NBT_FIELDS.setField(NBT_LENGTH_IN_SECONDS, (holder, reader) -> {
			holder.length = reader.readFloatTag();
		});
	}
	
	private Sound sound;
	private Chat description;
	private float length;
	private int comparatorOutput;

	public JukeboxSong(Sound sound, Chat description, float length, int comparatorOutput) {
		this(NamespacedKey.INLINE_DEFINITION, -1, sound, description, length, comparatorOutput);
	}
	
	public JukeboxSong(NamespacedKey key, int id, Sound sound, Chat description, float length, int comparatorOutput) {
		super(key, id);
		this.sound = sound;
		this.description = description;
		this.length = length;
		this.comparatorOutput = comparatorOutput;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		Sound.toNBT(NBT_SOUND_EVENT, sound, writer, systemData);
		ChatUtil.toNBT(NBT_DESCRIPTION, description, writer);
		writer.writeFloatTag(NBT_LENGTH_IN_SECONDS, length);
		writer.writeIntTag(NBT_COMPARATOR_OUTPUT, comparatorOutput);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public boolean hasNBT() {
		return true;
	}

}
