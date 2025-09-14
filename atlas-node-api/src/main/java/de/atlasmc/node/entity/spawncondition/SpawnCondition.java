package de.atlasmc.node.entity.spawncondition;

import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class SpawnCondition implements NBTSerializable {
	
	public static final NBTSerializationHandler<SpawnCondition>
	NBT_HANDLER = NBTSerializationHandler
					.builder(SpawnCondition.class)
					.defaultConstructor(SpawnCondition::new)
					.intField("priority", SpawnCondition::getPriority, SpawnCondition::setPriority)
					.typeCompoundField("condition", SpawnCondition::getCondition, SpawnCondition::setCondition, Condition.NBT_HANDLER)
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
	public NBTSerializationHandler<? extends SpawnCondition> getNBTHandler() {
		return NBT_HANDLER;
	}

}
