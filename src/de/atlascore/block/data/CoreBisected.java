package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Bisected;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBisected extends CoreBlockData implements Bisected {
	
	public static final CharKey
	HALF = CharKey.of("half");
	
	static {
		NBT_FIELDS.setField(HALF, (holder, reader) -> {
			if (holder instanceof Bisected) {
				((Bisected) holder).setHalf(Half.getByName(reader.readStringTag()));
			} else reader.skipTag();
		});
	}
	
	private Half half;
	
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
		if (half == null) 
			throw new IllegalArgumentException("Half can not be null!");
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
