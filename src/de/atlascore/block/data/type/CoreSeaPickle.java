package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.SeaPickle;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSeaPickle extends CoreWaterlogged implements SeaPickle {

	protected static final String
	PICKLES = "pickles";
	
	static {
		NBT_FIELDS.setField(PICKLES, (holder, reader) -> {
			if (holder instanceof SeaPickle)
			((SeaPickle) holder).setPickles(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private int pickles;
	
	public CoreSeaPickle(Material material) {
		super(material);
	}

	@Override
	public int getMaxPickles() {
		return 4;
	}

	@Override
	public int getMinPickles() {
		return 1;
	}

	@Override
	public int getPickles() {
		return pickles;
	}

	@Override
	public void setPickles(int pickles) {
		if (pickles < 1 && pickles > 4) throw new IllegalArgumentException("Pickles is not between 1 and 4: " + pickles);
		this.pickles = pickles;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(pickles-1)*2;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getPickles() > 1) writer.writeIntTag(PICKLES, getPickles());
	}
	
}
