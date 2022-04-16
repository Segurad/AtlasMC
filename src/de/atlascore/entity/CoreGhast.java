package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Ghast;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreGhast extends CoreMob implements Ghast {

	protected static final MetaDataField<Boolean>
	META_IS_ATTACKING = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final String
	NBT_EXPLOSION_POWER = "ExplosionPower";
	
	static {
		NBT_FIELDS.setField(NBT_EXPLOSION_POWER, (holder, reader) -> {
			if (holder instanceof Ghast) {
				((Ghast) holder).setExplosionPower(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	private int explosionPower;
	
	public CoreGhast(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_ATTACKING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isAttacking() {
		return metaContainer.getData(META_IS_ATTACKING);
	}

	@Override
	public void setAttacking(boolean attacking) {
		metaContainer.get(META_IS_ATTACKING).setData(attacking);		
	}

	@Override
	public void setExplosionPower(int power) {
		this.explosionPower = power;
	}

	@Override
	public int getExplosionPower() {
		return explosionPower;
	}

}
