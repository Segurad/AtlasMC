package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.Instrument;
import de.atlasmc.inventory.component.InstrumentComponent;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreInstrumentComponent extends AbstractItemComponent implements InstrumentComponent {
	
	protected static final CharKey
	NBT_SOUND_EVENT = CharKey.literal("sound_event"),
	NBT_USE_DURATION = CharKey.literal("use_duration"),
	NBT_RANGE = CharKey.literal("range"),
	NBT_DESCRIPTION = CharKey.literal("description");
	
	private Instrument instrument;
	
	public CoreInstrumentComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreInstrumentComponent clone() {
		return (CoreInstrumentComponent) super.clone();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (instrument == null)
			return;
		if (instrument.getID() != -1) {
			writer.writeNamespacedKey(key.toString(), instrument.getNamespacedKey());
		} else {
			writer.writeCompoundTag(key.toString());
			Sound.toNBT(NBT_SOUND_EVENT, instrument.getSound(), writer, systemData);
			writer.writeFloatTag(NBT_USE_DURATION, instrument.getUseDuration());
			writer.writeFloatTag(NBT_RANGE, instrument.getRange());
			ChatUtil.toNBT(NBT_DESCRIPTION, instrument.getDescription(), writer);
			writer.writeEndTag();
		}
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		TagType type = reader.getType();
		switch (type) {
		case STRING: {
			NamespacedKey key = reader.readNamespacedKey();
			instrument = Instrument.get(key);
		}
		case COMPOUND: { // in line instrument definition
			Sound sound = null;
			float useDuration = 0;
			float range = 0;
			Chat description = null;
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				CharSequence key = reader.getFieldName();
				if (NBT_SOUND_EVENT.equals(key)) {
					sound = Sound.fromNBT(reader);
				} else if (NBT_USE_DURATION.equals(key)) {
					useDuration = reader.readFloatTag();
				} else if (NBT_RANGE.equals(key)) {
					range = reader.readFloatTag();
				} else if (NBT_DESCRIPTION.equals(key)) {
					description = ChatUtil.fromNBT(reader);
				} else {
					reader.skipTag();
				}
			}
			reader.readNextEntry();
			instrument = new Instrument(sound, useDuration, range, description);
		}
		default:
			throw new IllegalArgumentException("Unexpected tag type: " + type);
		}
	}

	@Override
	public Instrument getInstrument() {
		return instrument;
	}

	@Override
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.INSTRUMENT;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		final int id = readVarInt(buf);
		if (id == 0) {
			Sound sound = readSound(buf);
			float duration = buf.readFloat();
			float range = buf.readFloat();
			Chat description = readTextComponent(buf);
			instrument = new Instrument(sound, duration, range, description);
		} else {
			instrument = Instrument.getByID(id-1);
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		int id = instrument.getID();
		if (id != -1) {
			writeVarInt(id+1, buf);
		} else {
			writeSound(instrument.getSound(), buf);
			buf.writeFloat(instrument.getUseDuration());
			buf.writeFloat(instrument.getRange());
			writeTextComponent(instrument.getDescription(), buf);
		}
	}

}
