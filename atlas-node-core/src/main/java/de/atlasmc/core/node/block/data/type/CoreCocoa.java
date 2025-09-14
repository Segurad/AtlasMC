package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.Cocoa;

public class CoreCocoa extends CoreDirectional4Faces implements Cocoa {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, BlockDataProperty.AGE);
	}
	
	private int age;
	
	public CoreCocoa(BlockType type) {
		super(type);
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
		if (age > 2 || age < 0) 
			throw new IllegalArgumentException("Age is not between 0 and 2: " + age);
		this.age = age;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+age*4;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
