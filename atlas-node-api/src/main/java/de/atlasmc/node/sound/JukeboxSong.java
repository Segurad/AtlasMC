package de.atlasmc.node.sound;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class JukeboxSong extends ProtocolRegistryValueBase implements NBTSerializable {
	
	public static final NBTSerializationHandler<JukeboxSong>
	NBT_HANDLER = NBTSerializationHandler
					.builder(JukeboxSong.class)
					.addField(Sound.getNBTSoundField("sound_event", JukeboxSong::getSound, JukeboxSong::setSound, null))
					.chat("description", JukeboxSong::getDescription, JukeboxSong::setDescription)
					.floatField("length_in_seconds", JukeboxSong::getLength, JukeboxSong::setLength)
					.intField("comparator_output", JukeboxSong::getComparatorOutput, JukeboxSong::setComparatorOutput, 0)
					.build();
	
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
	
	public Sound getSound() {
		return sound;
	}
	
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public Chat getDescription() {
		return description;
	}
	
	public void setDescription(Chat description) {
		this.description = description;
	}
	
	public float getLength() {
		return length;
	}
	
	public void setLength(float length) {
		this.length = length;
	}
	
	public int getComparatorOutput() {
		return comparatorOutput;
	}
	
	public void setComparatorOutput(int comparatorOutput) {
		this.comparatorOutput = comparatorOutput;
	}

	@Override
	public boolean hasNBT() {
		return true;
	}

	@Override
	public NBTSerializationHandler<? extends JukeboxSong> getNBTHandler() {
		return NBT_HANDLER;
	}

}
