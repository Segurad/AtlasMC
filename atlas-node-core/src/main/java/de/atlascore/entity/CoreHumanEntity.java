package de.atlascore.entity;

import java.io.IOException;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.HumanEntity;
import de.atlasmc.entity.Player;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.inventory.MainHand;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.util.CooldownHandler;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.io.NBTObjectWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import de.atlasmc.util.nbt.tag.CompoundTag;

public class CoreHumanEntity extends CoreLivingEntity implements HumanEntity {
	
	protected static final MetaDataField<Float>
	META_ADDITIONAL_HEARTS = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+1, 0.0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_SCORE = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Byte>
	META_SKIN_SETTINGS = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+3, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_MAIN_HAND = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+4, (byte) 1, MetaDataType.BYTE);
	protected static final MetaDataField<CompoundTag>
	META_SHOULDER_LEFT = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+5, null, MetaDataType.NBT_DATA);
	protected static final MetaDataField<CompoundTag>
	META_SHOULDER_RIGHT = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+6, null, MetaDataType.NBT_DATA);
	
	protected static final int LAST_META_INDEX = CoreLivingEntity.LAST_META_INDEX+6;
	
	private PlayerInventory inv;
	private CraftingInventory craftingInv;
	private double additionalHealth;
	private Entity shoulderRight;
	private Entity shoulderLeft;
	private byte shoulderChanged; // 0x01 Right 0x02 Left
	private final CooldownHandler<ItemType> cooldowns;
	private int foodLevel = 20;
	private float foodExhaustionLevel;
	private float foodSaturationLevel;
	protected boolean flying;
	protected boolean instantbuild;
	protected boolean invulnerable;
	protected boolean canBuild;
	protected boolean canFly;
	protected float flySpeed;
	protected float walkSpeed;
	
	public CoreHumanEntity(EntityType type) {
		super(type);
		this.cooldowns = createItemCooldownHander();
	}
	
	protected CooldownHandler<ItemType> createItemCooldownHander() { 
		return new CooldownHandler<>();
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ADDITIONAL_HEARTS);
		metaContainer.set(META_SCORE);
		metaContainer.set(META_SKIN_SETTINGS);
		metaContainer.set(META_MAIN_HAND);
		metaContainer.set(META_SHOULDER_LEFT);
		metaContainer.set(META_SHOULDER_RIGHT);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public PlayerInventory getInventory() {
		if (inv == null)
			inv = ContainerFactory.PLAYER_INV_FACTORY.create(InventoryType.PLAYER, this);
		return inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

	@Override
	public double getAdditionHearts() {
		return additionalHealth;
	}

	@Override
	public void setAdditionHearts(double value) {
		this.additionalHealth = value;
	}

	@Override
	public int getDisplayedSkinParts() {
		return metaContainer.getData(META_SKIN_SETTINGS) & 0xFF;
	}

	@Override
	public void setDisplayedSkinParts(int parts) {
		metaContainer.get(META_SKIN_SETTINGS).setData((byte) parts);
	}

	@Override
	public MainHand getMainHand() {
		return MainHand.getByID(metaContainer.getData(META_MAIN_HAND));
	}

	@Override
	public void setMainHand(MainHand hand) {
		if (hand == null)
			throw new IllegalArgumentException("Hand can not be null!");
		metaContainer.get(META_MAIN_HAND).setData((byte) hand.getID());
	}

	@Override
	public Entity getRightShoulder() {
		return shoulderRight;
	}

	@Override
	public void setRightShoulder(Entity entity) {
		if (shoulderRight == entity)
			return;
		if (entity instanceof Player)
			throw new IllegalArgumentException("Player can not be a shoulder pet!");
		shoulderRight = entity;
		if (entity != null)
			entity.remove();
		shoulderChanged |= 0x01;
	}

	@Override
	public Entity getLeftShoulder() {
		return shoulderLeft;
	}

	@Override
	public void setLeftShoulder(Entity entity) {
		if (shoulderLeft == entity)
			return;
		if (entity instanceof Player)
			throw new IllegalArgumentException("Player can not be a shoulder pet!");
		shoulderLeft = entity;
		if (entity != null)
			entity.remove();
		shoulderChanged |= 0x02;
	}
	
	@Override
	protected void prepUpdate() {
		super.prepUpdate();
		if (shoulderChanged != 0) {
			if ((shoulderChanged & 0x01) == 0x01) {
				prepShoulderUpdate(META_SHOULDER_RIGHT, shoulderRight);
			}
			if ((shoulderChanged & 0x02) == 0x02) {
				prepShoulderUpdate(META_SHOULDER_LEFT, shoulderLeft);
			}
			shoulderChanged = 0;
		}
	}
	
	@Override
	protected void doTick() {
		super.doTick();
		cooldowns.tick();
	}
	
	private void prepShoulderUpdate(MetaDataField<CompoundTag> shoulder, Entity entity) {
		if (entity == null)
			metaContainer.get(shoulder).setData(null);
		else {
			try {
				@SuppressWarnings("unchecked")
				NBTSerializationHandler<Entity> handler = (NBTSerializationHandler<Entity>) entity.getNBTHandler();
				NBTObjectWriter writer = new NBTObjectWriter();
				writer.writeCompoundTag();
				handler.serialize(entity, writer, NBTSerializationContext.DEFAULT_CLIENT);
				writer.writeEndTag();
				CompoundTag tag = (CompoundTag) writer.toNBT();
				metaContainer.get(shoulder).setData(tag);
			} catch (IOException e) {
				throw new NBTException("Error while createing entity nbt!", e);
			}
		}
	}

	@Override
	public CraftingInventory getCraftingInventory() {
		if (craftingInv == null)
			craftingInv = ContainerFactory.CRAFTING_INV_FACTORY.create(InventoryType.CRAFTING, this);
		return craftingInv;
	}

	@Override
	public boolean isSneaking() {
		return (metaContainer.get(META_ENTITY_FLAGS).getData() & 0x2) == 0x2;
	}

	@Override
	public void setSneaking(boolean sneaking) {
		MetaData<Byte> meta = metaContainer.get(META_ENTITY_FLAGS);
		meta.setData((byte) (sneaking ? meta.getData() | 0x02 : meta.getData() & 0xFD));
	}

	@Override
	public void setCooldown(ItemType type, int ticks) {
		cooldowns.setCooldown(type, ticks);
	}

	@Override
	public int getCooldown(ItemType type) {
		return cooldowns.getCooldown(type);
	}

	@Override
	public int getCooldownPast(ItemType type) {
		return cooldowns.getCooldownPast(type);
	}

	@Override
	public boolean hasCooldown(ItemType type) {
		return cooldowns.hasCooldown(type);
	}

	@Override
	public int removeCooldown(ItemType type) {
		return cooldowns.removeCooldown(type);
	}

	@Override
	public int getFoodLevel() {
		return foodLevel;
	}

	@Override
	public void setFoodLevel(int level) {
		if (level > 20 || level < 0)
			throw new IllegalArgumentException("Level must be between 0 and 20: " + level);
		this.foodLevel = level;
	}

	@Override
	public float getFoodExhaustionLevel() {
		return foodExhaustionLevel;
	}

	@Override
	public void setFoodExhaustionLevel(float level) {
		this.foodExhaustionLevel = level;
	}

	@Override
	public float getFoodSaturationLevel() {
		return this.foodSaturationLevel;
	}

	@Override
	public void setFoodSaturationLevel(float level) {
		this.foodSaturationLevel = level;
	}

	@Override
	public float getWalkSpeed() {
		return walkSpeed;
	}

	@Override
	public void setWalkSpeed(float speed) {
		this.walkSpeed = speed;
	}

	@Override
	public boolean canFly() {
		return canFly;
	}

	@Override
	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	@Override
	public boolean isFlying() {
		return flying;
	}

	@Override
	public void setFlying(boolean flying) {
		this.flying = flying;
	}

	@Override
	public float getFlySpeed() {
		return flySpeed;
	}

	@Override
	public void setFlySpeed(float flySpeed) {
		this.flySpeed = flySpeed;
	}

	@Override
	public boolean canInstaBuild() {
		return instantbuild;
	}

	@Override
	public void setInstaBuild(boolean instabuild) {
		this.instantbuild = instabuild;
	}

	@Override
	public boolean canBuild() {
		return canBuild;
	}

	@Override
	public void setCanBuild(boolean canBuild) {
		this.canBuild = canBuild;
	}

}
