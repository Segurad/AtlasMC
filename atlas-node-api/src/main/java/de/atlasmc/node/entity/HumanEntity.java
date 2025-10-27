package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.CraftingInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.node.inventory.MainHand;
import de.atlasmc.node.inventory.PlayerInventory;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface HumanEntity extends LivingEntity, InventoryHolder {
	
	public static final NBTCodec<HumanEntity>
	NBT_HANDLER = NBTCodec
					.builder(HumanEntity.class)
					.beginComponent("abilities")
					.boolField("flying", HumanEntity::isFlying, HumanEntity::setFlying, false)
					.floatField("flySPeed", HumanEntity::getFlySpeed, HumanEntity::setFlySpeed, 0.05f)
					.boolField("instabuild", HumanEntity::canInstaBuild, HumanEntity::setInstaBuild, false)
					.boolField("invulnerable", HumanEntity::isInvulnerable, HumanEntity::setInvulnerable, false)
					.boolField("mayBuild", HumanEntity::canBuild, HumanEntity::setCanBuild, false)
					.boolField("mayfly", HumanEntity::canFly, HumanEntity::setCanFly, false)
					.floatField("walkSpeed", HumanEntity::getWalkSpeed, HumanEntity::setWalkSpeed, 0.1f)
					.endComponent()
					// current_explosion_impact_pos
					// DataVersion
					// Dimension
					// EnderChest
					// entered_nether_pos
					.floatField("foodExhaustionLevel", HumanEntity::getFoodExhaustionLevel, HumanEntity::setFoodExhaustionLevel, 0)
					.intField("foodLevel", HumanEntity::getFoodLevel, HumanEntity::setFoodLevel, 0)
					.floatField("foodSaturationLevel", HumanEntity::getFoodSaturationLevel, HumanEntity::setFoodSaturationLevel, 0)
					//.intField("foodTickTimer", null, null)
					// ignore_fall_damage_from_current_explosion
					// Inventory
					// LastDeathLocation
					.typeCompoundField("ShoulderEntityLeft", HumanEntity::getLeftShoulder, HumanEntity::setLeftShoulder, Entity.NBT_CODEC)
					.typeCompoundField("ShoulderEntityRight", HumanEntity::getRightShoulder, HumanEntity::setRightShoulder, Entity.NBT_CODEC)
					.build();
	
	float getWalkSpeed();
	
	void setWalkSpeed(float speed);
	
	boolean canFly();
	
	void setCanFly(boolean canFly);
	
	boolean isFlying();
	
	void setFlying(boolean flying);
	
	float getFlySpeed();
	
	void setFlySpeed(float flySpeed);
	
	boolean canInstaBuild();
	
	void setInstaBuild(boolean instabuild);
	
	boolean canBuild();
	
	void setCanBuild(boolean canBuild);
	
	PlayerInventory getInventory();
	
	double getAdditionHearts();
	
	void setAdditionHearts(double value);
	
	int getDisplayedSkinParts();
	
	void setDisplayedSkinParts(int parts);
	
	MainHand getMainHand();
	
	void setMainHand(MainHand mainhand);
	
	Entity getRightShoulder();
	
	void setRightShoulder(Entity entity);
	
	Entity getLeftShoulder();
	
	void setLeftShoulder(Entity entity);

	CraftingInventory getCraftingInventory();
	
	boolean isSneaking();
	
	void setSneaking(boolean sneaking);
	
	void setCooldown(ItemType type, int ticks);
	
	int getCooldown(ItemType type);
	
	int getCooldownPast(ItemType type);
	
	boolean hasCooldown(ItemType type);
	
	int removeCooldown(ItemType type);
	
	int getFoodLevel();
	
	void setFoodLevel(int level);
	
	float getFoodExhaustionLevel();
	
	void setFoodExhaustionLevel(float level);
	
	float getFoodSaturationLevel();
	
	void setFoodSaturationLevel(float level);
	
}
