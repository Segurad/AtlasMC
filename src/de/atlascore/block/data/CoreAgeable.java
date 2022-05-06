package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAgeable extends CoreBlockData implements Ageable {
	
	public static final CharKey
	AGE = CharKey.of("age");
	
	static {
		NBT_FIELDS.setField(AGE, (holder, reader) -> {
			if (holder instanceof Ageable) {
				((Ageable) holder).setAge(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	private int age;
	private int maxage;
	
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
		if (age > maxage || age < 0) throw new IllegalArgumentException("Age is not between 0 and " + maxage + ": " + age);
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
