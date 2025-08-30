package de.atlasmc.entity.spawncondition;

import java.util.function.Predicate;

import de.atlasmc.Location;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Condition extends Predicate<Location>, NBTSerializable {
	
	public static final NBTSerializationHandler<Condition>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Condition.class)
					.searchKeyConstructor("type", ConditionType.REGISTRY_KEY, ConditionType::createCondition, Condition::getType)
					.build();

	ConditionType getType();
	
	@Override
	default NBTSerializationHandler<? extends Condition> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
