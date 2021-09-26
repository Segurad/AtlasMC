package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TurtleEgg;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTurtleEgg extends CoreBlockData implements TurtleEgg {

	protected static final String
	HATCH = "hatch",
	EGGS = "eggs";
	
	static {
		NBT_FIELDS.setField(EGGS, (holder, reader) -> {
			if (holder instanceof TurtleEgg)
			((TurtleEgg) holder).setEggs(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(HATCH, (holder, reader) -> {
			if (holder instanceof TurtleEgg)
			((TurtleEgg) holder).setHatch(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private int eggs, hatch;
	
	public CoreTurtleEgg(Material material) {
		super(material);
		eggs = 1;
	}

	@Override
	public int getEggs() {
		return eggs;
	}

	@Override
	public int getHatch() {
		return hatch;
	}

	@Override
	public int getMaxEggs() {
		return 4;
	}

	@Override
	public int getMaxHatch() {
		return 2;
	}

	@Override
	public int getMinEggs() {
		return 1;
	}

	@Override
	public void setHatch(int hatch) {
		if (hatch < 0 || hatch > 2) throw new IllegalArgumentException("Hatch is not between 0 and 2: " + hatch);
		this.hatch = hatch;
	}

	@Override
	public void setEggs(int eggs) {
		if (eggs < 1 || eggs > 4) throw new IllegalArgumentException("Eggs is not between 1 and 4: " + eggs);
		this.eggs = eggs;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				hatch+(eggs-1)*3;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getEggs() > 1) writer.writeIntTag(EGGS, getEggs());
		if (getHatch() > 0) writer.writeIntTag(HATCH, getHatch());
	}

}
