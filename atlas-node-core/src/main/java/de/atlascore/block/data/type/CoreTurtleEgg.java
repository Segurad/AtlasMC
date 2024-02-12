package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreHatchable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TurtleEgg;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTurtleEgg extends CoreHatchable implements TurtleEgg {

	protected static final CharKey NBT_EGGS = CharKey.literal("eggs");
	
	static {
		NBT_FIELDS.setField(NBT_EGGS, (holder, reader) -> {
			if (holder instanceof TurtleEgg)
			((TurtleEgg) holder).setEggs(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private int eggs;
	
	public CoreTurtleEgg(Material material) {
		super(material);
		eggs = 1;
	}

	@Override
	public int getEggs() {
		return eggs;
	}

	@Override
	public int getMaxEggs() {
		return 4;
	}

	@Override
	public int getMinEggs() {
		return 1;
	}

	@Override
	public void setEggs(int eggs) {
		if (eggs < 1 || eggs > 4) 
			throw new IllegalArgumentException("Eggs is not between 1 and 4: " + eggs);
		this.eggs = eggs;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(eggs-1)*3;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getEggs() > 1) 
			writer.writeIntTag(NBT_EGGS, getEggs());
	}

}
