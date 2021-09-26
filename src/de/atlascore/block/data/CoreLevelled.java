package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Levelled;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLevelled extends CoreBlockData implements Levelled {

	protected static final String
	LEVEL = "level";
	
	static {
		NBT_FIELDS.setField(LEVEL, (holder, reader) -> {
			if (holder instanceof Levelled)
			((Levelled) holder).setLevel(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private int level;
	private final int maxlevel;
	
	public CoreLevelled(Material material) {
		super(material);
		maxlevel = 15;
	}

	@Override
	public int getMaxLevel() {
		return maxlevel;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		if (level > maxlevel || level < 0) throw new IllegalArgumentException("Level is not between 0 and " + maxlevel + ": " + level);
		this.level = level;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + level;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(LEVEL, getLevel());
	}

}
