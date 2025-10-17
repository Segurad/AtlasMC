package de.atlasmc.node.entity.spawncondition;

import java.util.function.Predicate;

import de.atlasmc.node.Location;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Condition extends Predicate<Location>, NBTSerializable {
	
	public static final NBTCodec<Condition>
	NBT_HANDLER = NBTCodec
					.builder(Condition.class)
					.searchKeyConstructor("type", ConditionType.REGISTRY_KEY, ConditionType::createCondition, Condition::getType)
					.build();

	ConditionType getType();
	
	@Override
	default NBTCodec<? extends Condition> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
