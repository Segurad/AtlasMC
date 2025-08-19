package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readTextComponent;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeTextComponent;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.io.protocol.ProtocolUtil.readSound;
import static de.atlasmc.io.protocol.ProtocolUtil.writeSound;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.Instrument;
import de.atlasmc.inventory.component.InstrumentComponent;
import de.atlasmc.sound.Sound;
import io.netty.buffer.ByteBuf;

public class CoreInstrumentComponent extends AbstractItemComponent implements InstrumentComponent {
	
	private Instrument instrument;
	
	public CoreInstrumentComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreInstrumentComponent clone() {
		return (CoreInstrumentComponent) super.clone();
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
