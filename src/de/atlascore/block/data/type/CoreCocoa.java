package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAgeable;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Cocoa;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCocoa extends CoreDirectional4Faces implements Cocoa {

	private int age;
	
	public CoreCocoa(Material material) {
		super(material);
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public int getMaxAge() {
		return 2;
	}

	@Override
	public void setAge(int age) {
		if (age > 2 || age < 0) throw new IllegalArgumentException("Age is not between 0 and 2: " + age);
		this.age = age;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+age*4;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getAge() > 0) writer.writeIntTag(CoreAgeable.AGE, getAge());
	}

}
