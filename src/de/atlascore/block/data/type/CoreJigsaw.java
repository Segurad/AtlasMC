package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Jigsaw;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreJigsaw extends CoreBlockData implements Jigsaw {

	protected static final CharKey
	ORIENTATION = CharKey.of("orientation");
	
	static {
		NBT_FIELDS.setField(ORIENTATION, (holder, reader) -> {
			if (holder instanceof Jigsaw)
			((Jigsaw) holder).setOrientation(Orientation.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private Orientation orientation;
	
	public CoreJigsaw(Material material) {
		super(material);
		orientation = Orientation.NORTH_UP;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(Orientation orientation) {
		if (orientation == null) 
			throw new IllegalArgumentException("Orientation can not be null!");
		this.orientation = orientation;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+orientation.ordinal();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getOrientation() != Orientation.NORTH_UP) 
			writer.writeStringTag(ORIENTATION, getOrientation().getNameID());
	}

}
