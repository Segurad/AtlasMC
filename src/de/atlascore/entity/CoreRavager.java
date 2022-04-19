package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Ravager;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreRavager extends CoreRaider implements Ravager {

	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_ATTACK_TICK = "AttackTick",
	NBT_ROAR_TICK = "RoarTick",
	NBT_STUN_TICK = "StunTick";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreRaider.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_ATTACK_TICK, (holder, reader) -> {
			if (holder instanceof Ravager) {
				((Ravager) holder).setAttackCooldown(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ROAR_TICK, (holder, reader) -> {
			if (holder instanceof Ravager) {
				((Ravager) holder).setRoarTime(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_STUN_TICK, (holder, reader) -> {
			if (holder instanceof Ravager) {
				((Ravager) holder).setStunTime(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	private int cooldownAttack;
	private int timeRoar;
	private int timeStunned;

	public CoreRavager(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
