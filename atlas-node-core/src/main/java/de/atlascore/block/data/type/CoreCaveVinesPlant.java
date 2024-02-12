package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.CaveVinesPlant;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCaveVinesPlant extends CoreBlockData implements CaveVinesPlant {

	public static final CharKey NBT_BERRIES = CharKey.literal("berries");
	
	static {
		NBT_FIELDS.setField(NBT_BERRIES, (holder, reader) -> {
			if (holder instanceof CaveVinesPlant data) {
				data.setBerries(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private boolean berries;
	
	public CoreCaveVinesPlant(Material material) {
		super(material);
	}

	@Override
	public boolean hasBerries() {
		return berries;
	}

	@Override
	public void setBerries(boolean berries) {
		this.berries = berries;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (berries?0:1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (berries)
			writer.writeByteTag(NBT_BERRIES, true);
	}

}
