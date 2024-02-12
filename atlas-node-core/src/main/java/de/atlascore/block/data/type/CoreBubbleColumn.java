package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.BubbleColumn;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBubbleColumn extends CoreBlockData implements BubbleColumn {
	
	protected static final CharKey DRAG = CharKey.literal("drag");
	
	static {
		NBT_FIELDS.setField(DRAG, (holder, reader) -> {
			if (holder instanceof BubbleColumn) {
				((BubbleColumn) holder).setDrag(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private boolean drag;
	
	public CoreBubbleColumn(Material material) {
		super(material);
		drag = true;
	}

	@Override
	public boolean isDrag() {
		return drag;
	}

	@Override
	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	@Override
	public int getStateID() {
		return super.getStateID()+(drag?0:1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isDrag()) writer.writeByteTag(DRAG, true);
	}
}
