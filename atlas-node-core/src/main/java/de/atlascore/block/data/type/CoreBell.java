package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Bell;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBell extends CoreDirectional4Faces implements Bell {

	protected static final CharKey
	ATTACHEMENT = CharKey.literal("attachment");
	
	static {
		NBT_FIELDS.setField(ATTACHEMENT, (holder, reader) -> {
			if (holder instanceof Bell)
			((Bell) holder).setAttachment(Attachment.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private boolean powered;
	private Attachment attachment;
	
	public CoreBell(Material material) {
		super(material);
		attachment = Attachment.FLOOR;
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
	public Attachment getAttachment() {
		return attachment;
	}

	@Override
	public void setAttachment(Attachment attachment) {
		if (attachment == null) throw new IllegalArgumentException("Attachment can not be null!");
		this.attachment = attachment;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(powered?0:1)+
				getFaceValue()*2+
				attachment.ordinal()*8;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPowered()) writer.writeByteTag(CorePowerable.NBT_POWERED, true);
		writer.writeStringTag(ATTACHEMENT, getAttachment().name().toLowerCase());
	}
	
}
