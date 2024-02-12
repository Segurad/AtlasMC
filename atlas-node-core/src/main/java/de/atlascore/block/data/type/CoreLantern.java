package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Lantern;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLantern extends CoreWaterlogged implements Lantern {

	protected static final CharKey
	HANGING = CharKey.literal("hanging");
	
	static {
		NBT_FIELDS.setField(HANGING, (holder, reader) -> {
			if (holder instanceof Lantern)
			((Lantern) holder).setHanging(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean hanging;
	
	public CoreLantern(Material material) {
		super(material);
	}

	@Override
	public boolean isHanging() {
		return hanging;
	}

	@Override
	public void setHanging(boolean hanging) {
		this.hanging = hanging;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(hanging?0:2);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isHanging()) writer.writeByteTag(HANGING, true);
	}

}
