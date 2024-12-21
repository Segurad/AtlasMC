package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreAgeable extends CoreBlockData implements Ageable {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.AGE);
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
		if (age > maxage || age < 0) 
			throw new IllegalArgumentException("Age is not between 0 and " + maxage + ": " + age);
		this.age = age;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+age;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
