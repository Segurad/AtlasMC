package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Farmland;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFarmland extends CoreBlockData implements Farmland {

	private final int maxmoisture;
	private int moisture;
	
	protected static final CharKey MOISTURE = CharKey.literal("moisture");
	
	static {
		NBT_FIELDS.setField(MOISTURE, (holder, reader) -> {
			if (Farmland.class.isInstance(holder)) {
				((Farmland) holder).setMoisture(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	public CoreFarmland(Material material) {
		super(material);
		maxmoisture = 7;
	}

	@Override
	public int getMoisture() {
		return moisture;
	}

	@Override
	public int getMaxMoisture() {
		return maxmoisture;
	}

	@Override
	public void setMoisture(int moisture) {
		if (moisture > maxmoisture || moisture < 0) throw new IllegalArgumentException("Level is not between 0 and " + maxmoisture + ": " + moisture);
		this.moisture = moisture;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(MOISTURE, moisture);
	}

}
