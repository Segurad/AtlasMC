package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Cocoa;
import de.atlasmc.util.Validate;

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
		Validate.isTrue(age <= 2 && age >= 0, "Age is not between 0 and 2: " + age);
		this.age = age;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+age*4;
	}

}
