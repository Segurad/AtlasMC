package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.SculkCatalyst;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSculkCatalyst extends CoreBlockData implements SculkCatalyst {

	protected static NBTFieldContainer<CoreSculkCatalyst> NBT_FIELDS;
	
	private static final CharKey NBT_BLOOM = CharKey.literal("bloom");
	
	static {
		NBT_FIELDS = CoreBlockData.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_BLOOM, (holder, reader) -> {
			holder.setBloom(reader.readByteTag() == 1);
		});
	}
	
	private boolean bloom;
	
	public CoreSculkCatalyst(Material material) {
		super(material);
	}

	@Override
	public boolean isBloom() {
		return bloom;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (bloom?0:1);
	}

	@Override
	public void setBloom(boolean bloom) {
		this.bloom = bloom;
	}
	
	@Override
	public CoreSculkCatalyst clone() {
		return (CoreSculkCatalyst) super.clone();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (bloom)
			writer.writeByteTag(NBT_BLOOM, true);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBlockData> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
