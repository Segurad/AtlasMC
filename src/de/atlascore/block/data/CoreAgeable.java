package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.util.Validate;

public class CoreAgeable extends CoreBlockData implements Ageable {

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
		Validate.isTrue(age <= maxage && age >= 0, "Age is not between 0 and " + maxage + ": " + age);
		this.age = age;
	}

}
