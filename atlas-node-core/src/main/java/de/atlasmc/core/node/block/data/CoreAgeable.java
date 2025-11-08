package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Ageable;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreAgeable extends CoreBlockData implements Ageable {
	
	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.AGE);
	}
	
	protected int age;
	private int maxage;
	
	public CoreAgeable(BlockType type) {
		this(type, 15);
	}
	
	public CoreAgeable(BlockType type, int maxage) {
		super(type);
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
		if (age > maxage || age < 0) 
			throw new IllegalArgumentException("Age is not between 0 and " + maxage + ": " + age);
		this.age = age;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+age;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
