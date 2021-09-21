package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Bisected;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBisected extends CoreBlockData implements Bisected {

	private Half half;
	
	protected static final String HALF = "half";
	
	static {
		NBT_FIELDS.setField(HALF, (holder, reader) -> {
			if (Bisected.class.isInstance(holder)) {
				((Bisected) holder).setHalf(Half.getByName(reader.readStringTag()));
			} else reader.skipTag();
		});
	}
	
	public CoreBisected(Material material) {
		super(material);
		this.half = Half.BOTTOM;
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		Validate.notNull(half, "Half can not be null!");
		this.half = half;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+half.ordinal();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(HALF, half.name().toLowerCase());
	}

}
