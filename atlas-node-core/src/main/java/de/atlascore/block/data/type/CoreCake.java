package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Cake;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCake extends CoreBlockData implements Cake {

	protected static final CharKey
	BITES = CharKey.literal("bites");
	
	static {
		NBT_FIELDS.setField(BITES, (holder, reader) -> {
			if (holder instanceof Cake)
			((Cake) holder).setBites(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private int bites;
	
	public CoreCake(Material material) {
		super(material);
	}

	@Override
	public int getBites() {
		return bites;
	}

	@Override
	public int getMaxBites() {
		return 6;
	}

	@Override
	public void setBites(int bites) {
		if (bites < 0 || bites > 7) throw new IllegalArgumentException("Bites is not between 0 and 6: " + bites);
		this.bites = bites;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+bites;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(BITES, getBites());
	}

}
