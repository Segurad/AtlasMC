package de.atlascore.block.data.type;

import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Bamboo;

public class CoreBamboo extends CoreSapling implements Bamboo {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreSapling.PROPERTIES, 
				BlockDataProperty.AGE,
				BlockDataProperty.LEAVES);
	}
	
	private int age;
	private Leaves leaves;
	
	public CoreBamboo(BlockType type) {
		super(type, 1);
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
		if (age > 1 || age < 0) throw new IllegalArgumentException("Age is not between 0 and 1: " + age);
		this.age = age;
	}

	@Override
	public Leaves getLeaves() {
		return leaves;
	}

	@Override
	public void setLeaves(Leaves leaves) {
		if (leaves == null) throw new IllegalArgumentException("Leaves can not be null!");
		this.leaves = leaves;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
