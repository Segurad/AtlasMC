package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAgeable extends CoreBlockData implements Ageable {

	private int age;
	private int maxage;
	
	protected static final String AGE = "age";
	
	static {
		NBT_FIELDS.setField(AGE, (holder, reader) -> {
			if (Ageable.class.isInstance(holder)) {
				((Ageable) holder).setAge(reader.readIntTag());
			} else reader.skipNBT();
		});
	}
	
	public CoreAgeable(Material material) {
		this(material, 15);
	}
	
	public CoreAgeable(Material material, int maxage) {
		super(material);
		this.maxage = maxage;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public int getMaxAge() {
		return maxage;
	}

	@Override
	public void setAge(int age) {
		Validate.isTrue(age <= maxage && age >= 0, "Age is not between 0 and " + maxage + ": " + age);
		this.age = age;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+age;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(AGE, age);
	}

}
