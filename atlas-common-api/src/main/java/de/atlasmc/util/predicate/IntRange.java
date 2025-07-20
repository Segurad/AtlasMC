package de.atlasmc.util.predicate;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import it.unimi.dsi.fastutil.ints.IntPredicate;

public class IntRange implements IntPredicate {
	
	public static final NBTSerializationHandler<IntRange>
	NBT_HANDLER = NBTSerializationHandler
					.builder(IntRange.class)
					.defaultConstructor(IntRange::new)
					.intField("min", IntRange::getMin, IntRange::setMin, Integer.MIN_VALUE)
					.intField("max", IntRange::getMax, IntRange::setMax, Integer.MAX_VALUE)
					.build();
	
	private int min = Integer.MIN_VALUE;
	private int max = Integer.MAX_VALUE;
	
	public int getMin() {
		return min;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	@Override
	public boolean test(int value) {
		return value >= min && value <= max;
	}

}
