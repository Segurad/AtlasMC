package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Sapling;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSapling extends CoreBlockData implements Sapling {

	protected static final CharKey
	STAGE = CharKey.of("stage");
	
	static {
		NBT_FIELDS.setField(STAGE, (holder, reader) -> {
			if (holder instanceof Sapling)
			((Sapling) holder).setStage(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private int stage;
	private int maxstage;
	
	public CoreSapling(Material material) {
		this(material, 1);
	}
	
	public CoreSapling(Material material, int maxstage) {
		super(material);
		this.maxstage = maxstage;
	}

	@Override
	public int getMaxStage() {
		return maxstage;
	}

	@Override
	public int getStage() {
		return stage;
	}

	@Override
	public void setStage(int stage) {
		if (maxstage < stage || stage < 0) throw new IllegalArgumentException("Stage is not between 0 and " + maxstage + ": " + stage);
		this.stage = stage;
	}

	@Override
	public int getStateID() {
		return super.getStateID() + stage;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getStage() > 0) writer.writeIntTag(STAGE, getStage());
	}
	
}
