package de.atlasmc.node.entity.spawncondition;

import java.util.function.Predicate;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.WorldLocation;

public interface Condition extends Predicate<WorldLocation>, NBTSerializable {
	
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
