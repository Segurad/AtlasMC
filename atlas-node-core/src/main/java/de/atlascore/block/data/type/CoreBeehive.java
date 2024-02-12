package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Beehive;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBeehive extends CoreDirectional4Faces implements Beehive {

	private int honeyLevel;
	
	protected static final CharKey HONEY_LEVEL = CharKey.literal("honey_level");
	
	static {
		NBT_FIELDS.setField(HONEY_LEVEL, (holder, reader) -> {
			if (holder instanceof Beehive) {
				((Beehive) holder).setHoneyLevel(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	public CoreBeehive(Material material) {
		super(material);
	}

	@Override
	public int getHoneyLevel() {
		return honeyLevel;
	}

	@Override
	public int getMaxHoneyLevel() {
		return 5;
	}

	@Override
	public void setHoneyLevel(int honeyLevel) {
		if (honeyLevel >  5 || honeyLevel < 0) throw new IllegalArgumentException("Level is not between 0 and 5: " + honeyLevel);
		this.honeyLevel = honeyLevel;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				honeyLevel+
				getFaceValue()*6;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(HONEY_LEVEL, honeyLevel);
	}

}
