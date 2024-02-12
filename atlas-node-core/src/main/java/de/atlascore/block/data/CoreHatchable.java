package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Hatchable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreHatchable extends CoreBlockData implements Hatchable {

	public static final CharKey NBT_HATCH = CharKey.literal("hatch");
	
	static {
		NBT_FIELDS.setField(NBT_HATCH, (holder, reader) -> {
			if (holder instanceof Hatchable data)
				data.setHatch(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private int hatch;
	private final int maxHatch;
	
	public CoreHatchable(Material material) {
		this(material, 2);
	}
	
	public CoreHatchable(Material mater, int maxHatch) {
		super(mater);
		this.maxHatch = maxHatch;
	}
	
	@Override
	public void setHatch(int hatch) {
		if (hatch < 0 || hatch > 2) 
			throw new IllegalArgumentException("Hatch is not between 0 and " + maxHatch + ": " + hatch);
		this.hatch = hatch;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + hatch;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hatch > 0)
			writer.writeIntTag(NBT_HATCH, hatch);
	}

	@Override
	public int getHatch() {
		return hatch;
	}

	@Override
	public int getMaxHatch() {
		return maxHatch;
	}

}
