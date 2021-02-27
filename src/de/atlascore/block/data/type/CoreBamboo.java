package de.atlascore.block.data.type;

import de.atlasmc.Material;
import de.atlasmc.block.data.type.Bamboo;
import de.atlasmc.util.Validate;

public class CoreBamboo extends CoreSapling implements Bamboo {

	private int age;
	private Leaves leaves;
	
	public CoreBamboo(Material material) {
		super(material, 1);
		leaves = Leaves.NONE;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public int getMaxAge() {
		return 1;
	}

	@Override
	public void setAge(int age) {
		Validate.isTrue(age <= 1 && age >= 0, "Age is not between 0 and 1: " + age);
		this.age = age;
	}

	@Override
	public Leaves getLeaves() {
		return leaves;
	}

	@Override
	public void setLeaves(Leaves leaves) {
		Validate.notNull(leaves, "Leaves can not be null!");
		this.leaves = leaves;
	}

}
