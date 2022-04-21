package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Scaffolding;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreScaffolding extends CoreWaterlogged implements Scaffolding {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	BOTTOM = "bottom",
	DISTANCE = "distance";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreWaterlogged.NBT_FIELDS);
		NBT_FIELDS.setField(BOTTOM, (holder, reader) -> {
			if (holder instanceof Scaffolding)
			((Scaffolding) holder).setBottom(reader.readByteTag() == 1);
			else reader.skipTag();
		});
		NBT_FIELDS.setField(DISTANCE, (holder, reader) -> {
			if (holder instanceof Scaffolding)
			((Scaffolding) holder).setDistance(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private boolean bottom;
	private int distance;
	
	public CoreScaffolding(Material material) {
		super(material);
		distance = 7;
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public int getMaxDistance() {
		return 7;
	}

	@Override
	public boolean isBottom() {
		return bottom;
	}

	@Override
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	@Override
	public void setDistance(int distance) {
		if (distance < 0 && distance > 7) throw new IllegalArgumentException("Distance is not between 0 and 7: " + distance);
		this.distance = distance;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				distance*2+
				(bottom?0:16);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isBottom()) writer.writeByteTag(BOTTOM, true);
		if (getDistance() < 7) writer.writeByteTag(DISTANCE, getDistance());
	}

}
