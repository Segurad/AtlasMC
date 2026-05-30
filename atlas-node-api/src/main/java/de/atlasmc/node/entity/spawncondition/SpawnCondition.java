package de.atlasmc.node.entity.spawncondition;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.util.annotation.NotNull;

public class SpawnCondition implements NBTSerializable {
	
	@NotNull
	public static final NBTCodec<SpawnCondition>
	NBT_CODEC = NBTCodec
					.builder(SpawnCondition.class)
					.defaultConstructor(SpawnCondition::new)
					.intField("priority", SpawnCondition::getPriority, SpawnCondition::setPriority)
					.codec("condition", SpawnCondition::getCondition, SpawnCondition::setCondition, Condition.NBT_HANDLER)
					.build();

	private int priority;
	private Condition condition;
	
	private SpawnCondition() {
		// serialization
	}
	
	public SpawnCondition(int priority, Condition condition) {
		if (condition == null)
			throw new IllegalStateException("Condition can not be null!");
		this.priority = priority;
		this.condition = condition;
	}
	
	public int getPriority() {
		return priority;
	}
	
	private void setPriority(int priority) {
		this.priority = priority;
	}
	
	public Condition getCondition() {
		return condition;
	}
	
	private void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	@Override
	public NBTCodec<? extends SpawnCondition> getNBTCodec() {
		return NBT_CODEC;
	}

}
