package de.atlasmc.node.entity;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.entity.spawncondition.SpawnCondition;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class EntityVariant extends ProtocolRegistryValueBase implements NBTSerializable {
	
	public static final NBTSerializationHandler<EntityVariant>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EntityVariant.class)
					.defaultConstructor(EntityVariant::new)
					.typeList("spawn_conditions", EntityVariant::hasSpawnConditions, EntityVariant::getSpawnCondition, SpawnCondition.NBT_HANDLER)
					.build();
	
	private List<SpawnCondition> conditions;
	
	public EntityVariant(NamespacedKey key, NamespacedKey clientKey, int id) {
		super(key, clientKey, id);
	}
	
	public EntityVariant(String key, String clientKey, int id) {
		super(key, clientKey, id);
	}
	
	public EntityVariant() {
		super();
	}
	
	public boolean hasSpawnConditions() {
		return conditions != null && !conditions.isEmpty();
	}
	
	public List<SpawnCondition> getSpawnCondition() {
		if (conditions != null)
			conditions = new ArrayList<>();
		return conditions;
	}

	@Override
	public NBTSerializationHandler<? extends NBTSerializable> getNBTHandler() {
		return NBT_HANDLER;
	}

}
