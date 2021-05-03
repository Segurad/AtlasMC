package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.CommandBlock;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCommandBlock extends CoreDirectional6Faces implements CommandBlock {

	private boolean conditional;
	
	protected static final String
	CONDITIONAL = "conditional";
	
	static {
		NBT_FIELDS.setField(CONDITIONAL, (holder, reader) -> {
			if (CommandBlock.class.isInstance(holder)) {
				((CommandBlock) holder).setConditional(reader.readByteTag() == 1);
			} else reader.skipNBT();
		});
	}
	
	public CoreCommandBlock(Material material) {
		super(material);
	}

	@Override
	public boolean isConditional() {
		return conditional;
	}

	@Override
	public void setConditional(boolean conditional) {
		this.conditional = conditional;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(conditional?0:6);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(CONDITIONAL, conditional);
	}

}
