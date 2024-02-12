package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Powerable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePowerable extends CoreBlockData implements Powerable {

	private boolean powered;
	
	public static final CharKey
	NBT_POWERED = CharKey.literal("powered");
	
	static {
		NBT_FIELDS.setField(NBT_POWERED, (holder, reader) -> {
			if (holder instanceof Powerable) {
				((Powerable) holder).setPowered(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CorePowerable(Material material) {
		super(material);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(powered?0:1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(NBT_POWERED, powered);
	}

}
