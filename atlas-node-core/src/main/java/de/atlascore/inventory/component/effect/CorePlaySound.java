package de.atlascore.inventory.component.effect;

import static de.atlasmc.io.protocol.ProtocolUtil.readSound;
import static de.atlasmc.io.protocol.ProtocolUtil.writeSound;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.entity.Entity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.effect.PlaySound;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CorePlaySound implements PlaySound {

	protected static final NBTFieldSet<CorePlaySound> NBT_FIELDS;
	
	protected static final CharKey NBT_SOUND = CharKey.literal("sound");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_SOUND, (holder, reader) -> {
			holder.sound = Sound.fromNBT(reader);
		});
		NBT_FIELDS.setField(NBT_TYPE, NBTField.skip());
	}
	
	private Sound sound;
	
	public CorePlaySound() {
		setSound(null);
	}
	
	@Override
	public void apply(Entity target, ItemStack item) {
		target.causeSound(sound);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_TYPE, getType().getNamespacedKeyRaw());
		Sound.toNBT(NBT_SOUND, sound, writer, systemData);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		sound = readSound(buf);
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		writeSound(sound, buf);
	}

	@Override
	public Sound getSound() {
		return sound;
	}

	@Override
	public void setSound(Sound sound) {
		if (sound == null)
			this.sound = EnumSound.BLOCK_NOTE_BLOCK_PLING;
		else
			this.sound = sound;
	}
	
	@Override
	public CorePlaySound clone() {
		try {
			return (CorePlaySound) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sound);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorePlaySound other = (CorePlaySound) obj;
		return Objects.equals(sound, other.sound);
	}

}
