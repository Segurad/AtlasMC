package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.GoatHornMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreGoatHornMeta extends CoreItemMeta implements GoatHornMeta {

	private static final CharKey
	NBT_INSTRUMENT = CharKey.literal("instrument");
	
	static {
		NBT_FIELDS.setField(NBT_INSTRUMENT, (holder, reader) -> {
			if (!(holder instanceof GoatHornMeta)) {
				((GoatHornMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
				return;
			}
			GoatHornMeta meta = (GoatHornMeta) holder;
			meta.setInstrument(Instrument.getByNameID(reader.readStringTag()));
		});
	}
	
	private Instrument instrument;
	
	public CoreGoatHornMeta(Material material) {
		super(material);
		instrument = Instrument.PONDER;
	}

	@Override
	public Instrument getInstrument() {
		return instrument;
	}

	@Override
	public void setInstrument(Instrument instrument) {
		if (instrument == null)
			throw new IllegalArgumentException("Instrument can not be null!");
		this.instrument = instrument;
	}
	
	@Override
	public CoreGoatHornMeta clone() {
		return (CoreGoatHornMeta) super.clone();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (instrument != Instrument.PONDER) {
			writer.writeStringTag(NBT_INSTRUMENT, instrument.getName());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
		return result;
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage)) {
			return false;
		}
		CoreGoatHornMeta other = (CoreGoatHornMeta) meta;
		if (instrument != other.instrument)
			return false;
		return true;
	}
}
