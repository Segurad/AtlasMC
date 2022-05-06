package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.WanderingTrader;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreWanderingTrader extends CoreAbstractVillager implements WanderingTrader {

	protected static final CharKey
	NBT_DESPAWN_DELAY = CharKey.of("DespawnDelay");
	//NBT_WANDER_TARGET = "WanderTarget";
	
	static {
		NBT_FIELDS.setField(NBT_DESPAWN_DELAY, (holder, reader) -> {
			if (holder instanceof WanderingTrader) {
				((WanderingTrader) holder).setDespawnDelay(reader.readIntTag());
			} else reader.skipTag();
		});
		//NBT_FIELDS.setField(NBT_WANDER_TARGET, NBTField.SKIP); TODO wandering target 
	}
	
	private int despawnDelay;
	
	public CoreWanderingTrader(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public void setDespawnDelay(int delay) {
		this.despawnDelay = delay;
	}

	@Override
	public int getDespawnDelay() {
		return despawnDelay;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_DESPAWN_DELAY, getDespawnDelay());
	}
	
}
