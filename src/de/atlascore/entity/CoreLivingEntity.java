package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreLivingEntity extends CoreEntity implements LivingEntity {

	/**
	 * 0x01 Is hand active<br>
	 * 0x02 active hand 0 = main hand, 1 = off hand<br>
	 * 0x04 riptide spin attack<br>
	 */
	protected static final MetaDataField<Byte>
	META_HAND_STATES = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Float>
	META_HEALTH = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 1.0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_POTION_EFFECT_COLOR = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, 0, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_REDUCE_POTION_AMBIENT = new MetaDataField<>(CoreEntity.LAST_META_INDEX+4, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_DISPLAY_ARROWS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+5, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_DISPLAY_BEE_STINGERS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+6, 0, MetaDataType.INT);
	protected static final MetaDataField<Long>
	META_OCCUPIED_BED_POSITION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+7, null, MetaDataType.OPT_POSITION);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+7;
	
	private double health;
	
	public CoreLivingEntity(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<>(META_HAND_STATES));
		metaContainer.set(new MetaData<>(META_HEALTH));
		metaContainer.set(new MetaData<>(META_POTION_EFFECT_COLOR));
		metaContainer.set(new MetaData<>(META_REDUCE_POTION_AMBIENT));
		metaContainer.set(new MetaData<>(META_DISPLAY_ARROWS));
		metaContainer.set(new MetaData<>(META_DISPLAY_BEE_STINGERS));
		metaContainer.set(new MetaData<>(META_OCCUPIED_BED_POSITION));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public float getHeadPitch() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	protected void prepUpdate() {
		super.prepUpdate();
		metaContainer.get(META_HEALTH).setData((float) health);
	}

	@Override
	public double getHealth() {
		return health;
	}

	@Override
	public void setHealth(double health) {
		this.health = health;
	}
	
	@Override
	public boolean isDead() {
		return health < 0.0;
	}

	@Override
	public int getDisplayedArrows() {
		return metaContainer.getData(META_DISPLAY_ARROWS);
	}

	@Override
	public int getDisplayedBeeStingers() {
		return metaContainer.getData(META_DISPLAY_BEE_STINGERS);
	}

	@Override
	public void setDisplayedArrows(int arrows) {
		metaContainer.get(META_DISPLAY_ARROWS).setData(arrows);		
	}

	@Override
	public void setDisplayedBeeStringers(int stingers) {
		metaContainer.get(META_DISPLAY_BEE_STINGERS).setData(stingers);		
	}

	@Override
	public Color getPotionAmbientColor() {
		return Color.fromRGB(getPotionAmbientColorRGB());
	}

	@Override
	public void setPotionAmbientColor(Color color) {
		setPortalCooldown(color == null ? 0 : color.asRGB());
	}

	@Override
	public int getPotionAmbientColorRGB() {
		return metaContainer.getData(META_POTION_EFFECT_COLOR);
	}

	@Override
	public void setPotionAmbientColor(int rgb) {
		if (rgb < 0 || rgb > 0xFFFFFF)
			throw new IllegalArgumentException("Color out of range: " + rgb);
		metaContainer.get(META_POTION_EFFECT_COLOR).setData(rgb);
	}

	@Override
	public boolean isPotionAmbientReduced() {
		return metaContainer.getData(META_REDUCE_POTION_AMBIENT);
	}

	@Override
	public void setPotionAmbientReduced(boolean reduced) {
		metaContainer.get(META_REDUCE_POTION_AMBIENT).setData(reduced);	
	}

}
