package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAnaloguePowerable extends CoreBlockData implements AnaloguePowerable {

	private int power;
	
	protected static final String POWER = "power";
	
	static {
		NBT_FIELDS.setField(POWER, (holder, reader) -> {
			if (AnaloguePowerable.class.isInstance(holder)) {
				((AnaloguePowerable) holder).setPower(reader.readIntTag());
			} else reader.skipNBT();
		});
	}
	
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
		Validate.isTrue(level <= 15 && level >= 0, "power is not between 0 and " + 15 + ": " + level);
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
