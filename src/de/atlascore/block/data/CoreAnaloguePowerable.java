package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAnaloguePowerable extends CoreBlockData implements AnaloguePowerable {
	
	protected static final String 
	POWER = "power";
	
	static {
		NBT_FIELDS.setField(POWER, (holder, reader) -> {
			if (holder instanceof AnaloguePowerable) {
				((AnaloguePowerable) holder).setPower(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	private int power;
	
	public CoreAnaloguePowerable(Material material) {
		super(material);
	}

	@Override
	public int getMaxPower() {
		return 15;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public void setPower(int level) {
		if (level > 15 || level < 0) throw new IllegalArgumentException("Power is not between 0 and 15: " + level);
		this.power = level;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + power;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(POWER, power);
	}

}
