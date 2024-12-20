package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.StructureBlock;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreStructureBlock extends CoreBlockData implements StructureBlock {

	protected static final NBTFieldContainer<CoreStructureBlock> NBT_FIELDS;
	
	protected static final CharKey
	MODE = CharKey.literal("mode");

	static {
		NBT_FIELDS = CoreBlockData.NBT_FIELDS.fork();
		NBT_FIELDS.setField(MODE, (holder, reader) -> {
			holder.setMode(Mode.getByName(reader.readStringTag()));
		});
	}
	
	private Mode mode;
	
	public CoreStructureBlock(Material material) {
		super(material);
		mode = Mode.LOAD;
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		if (mode == null) throw new IllegalArgumentException("Mode can not be null!");
		this.mode = mode;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+mode.ordinal();
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreStructureBlock> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getMode() != Mode.SAVE) writer.writeStringTag(MODE, getMode().name().toLowerCase());
	}
	
}
