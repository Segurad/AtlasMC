package de.atlasmc.entity;

import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.MainHand;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.inventory.ItemType;

public interface HumanEntity extends LivingEntity, InventoryHolder {
	
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
