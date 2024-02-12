package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Jukebox;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreJukebox extends CoreBlockData implements Jukebox {

	protected static final CharKey
	HAS_RECORD = CharKey.literal("has_record");
	
	static {
		NBT_FIELDS.setField(HAS_RECORD, (holder, reader) -> {
			if (holder instanceof Jukebox)
			((Jukebox) holder).setRecord(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean record;
	
	public CoreJukebox(Material material) {
		super(material);
	}

	@Override
	public boolean hasRecord() {
		return record;
	}

	@Override
	public void setRecord(boolean has) {
		this.record = has;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(record?0:1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasRecord()) writer.writeByteTag(HAS_RECORD, true);
	}

}
