package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Ravager;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRavager extends CoreRaider implements Ravager {

	protected static final NBTFieldContainer<CoreRavager> NBT_FIELDS;
	
	protected static final CharKey
	NBT_ATTACK_TICK = CharKey.literal("AttackTick"),
	NBT_ROAR_TICK = CharKey.literal("RoarTick"),
	NBT_STUN_TICK = CharKey.literal("StunTick");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreRaider.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_ATTACK_TICK, (holder, reader) -> {
			holder.setAttackCooldown(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_ROAR_TICK, (holder, reader) -> {
			holder.setRoarTime(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_STUN_TICK, (holder, reader) -> {
			holder.setStunTime(reader.readIntTag());
		});
	}
	
	private int cooldownAttack;
	private int timeRoar;
	private int timeStunned;

	public CoreRavager(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreRavager> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public void setAttackCooldown(int ticks) {
		this.cooldownAttack = ticks;
	}

	@Override
	public int getAttackCooldown() {
		return cooldownAttack;
	}

	@Override
	public void setRoarTime(int ticks) {
		this.timeRoar = ticks;
	}

	@Override
	public int getRoarTime() {
		return timeRoar;
	}

	@Override
	public void setStunTime(int ticks) {
		this.timeStunned = ticks;
	}

	@Override
	public int getStunTime() {
		return timeStunned;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_ATTACK_TICK, cooldownAttack);
		writer.writeIntTag(NBT_ROAR_TICK, timeRoar);
		writer.writeIntTag(NBT_STUN_TICK, timeStunned);
	}
	
}
