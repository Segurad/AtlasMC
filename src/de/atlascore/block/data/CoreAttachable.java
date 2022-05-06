package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Attachable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAttachable extends CoreBlockData implements Attachable {
	
	public static final CharKey
	ATTACHED = CharKey.of("attached");
	
	static {
		NBT_FIELDS.setField(ATTACHED, (holder, reader) -> {
			if (holder instanceof Attachable)
			((Attachable) holder).setAttached(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean attached;
	
	public CoreAttachable(Material material) {
		super(material);
	}
	
	@Override
	public boolean isAttached() {
		return attached;
	}
	
	@Override
	public void setAttached(boolean attached) {
		this.attached = attached;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (attached ? 0 : 1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isAttached()) writer.writeByteTag(ATTACHED, true);
	}

}
