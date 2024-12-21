package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Bee;
import de.atlasmc.entity.Entity;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BeesComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBeesComponent extends AbstractItemComponent implements BeesComponent {
	
	protected static final CharKey
	NBT_ENTITY_DATA = CharKey.literal("entity_data"),
	NBT_MIN_TICKS_IN_HIVE = CharKey.literal("min_ticks_in_hive"),
	NBT_TICKS_IN_HIVE = CharKey.literal("ticks_in_hive");
	
	private static final int DEFAULT_BEES_SIZE = 5;
	
	private List<Bee> bees;
	
	public CoreBeesComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreBeesComponent clone() {
		CoreBeesComponent clone = (CoreBeesComponent) super.clone();
		if (clone == null)
			return null;
		return clone;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		List<Bee> bees = this.bees;
		if (bees == null || bees.isEmpty())
			return;
		final int size = bees.size();
		writer.writeListTag(getNamespacedKeyRaw(), TagType.COMPOUND, size);
		for (int i = 0; i < size; i++) {
			writer.writeCompoundTag(NBT_ENTITY_DATA);
			Bee bee = bees.get(i);
			bee.toNBT(writer, systemData);
			writer.writeEndTag();
			writer.writeIntTag(NBT_MIN_TICKS_IN_HIVE, bee.getHiveMinOccupationTicks());
			writer.writeIntTag(NBT_TICKS_IN_HIVE, bee.getTicksInHive());
			writer.writeEndTag();
		}
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			Bee bee = null;
			int minTicksInHive = -1;
			int ticksInHive = -1;
			while (reader.getType() != TagType.TAG_END) {
				CharSequence key = reader.getFieldName();
				if (NBT_ENTITY_DATA.equals(key)) {
					reader.readNextEntry();
					Entity ent = Entity.getFromNBT(reader); 
					if (ent instanceof Bee b)
						bee = b;
				} else if (NBT_MIN_TICKS_IN_HIVE.equals(key)) {
					minTicksInHive = reader.readIntTag();
				} else if (NBT_TICKS_IN_HIVE.equals(key)) {
					ticksInHive = reader.readIntTag();
				} else {
					reader.skipTag();
				}
			}
			if (bee != null) {
				if (minTicksInHive != -1)
					bee.setHiveMinOccupationTicks(minTicksInHive);
				if (ticksInHive != -1)
					bee.setTicksInHive(ticksInHive);
				addBee(bee);
			}
			reader.readNextEntry();
		}
	}

	@Override
	public List<Bee> getBees() {
		if (bees == null)
			bees = new ArrayList<>(DEFAULT_BEES_SIZE);
		return bees;
	}

	@Override
	public void removeBee(Bee bee) {
		if (bees == null)
			return;
		bees.remove(bee);
	}

	@Override
	public void addBee(Bee bee) {
		getBees().add(bee);
	}

	@Override
	public int getBeeCount() {
		return bees == null ? 0 : bees.size();
	}

}
