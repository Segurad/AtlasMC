package de.atlasmc.entity;

import de.atlasmc.Material;
import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.MainHand;
import de.atlasmc.inventory.PlayerInventory;

public interface HumanEntity extends LivingEntity, InventoryHolder {
	
	public PlayerInventory getInventory();
	
	public double getAdditionHearts();
	
	public void setAdditionHearts(double value);
	
	public int getDisplayedSkinParts();
	
	public void setDisplayedSkinParts(int parts);
	
	public MainHand getMainHand();
	
	public void setMainHand(MainHand mainhand);
	
	public Entity getRightShoulder();
	
	public void setRightShoulder(Entity entity);
	
	public Entity getLeftShoulder();
	
	public void setLeftShoulder(Entity entity);

	public CraftingInventory getCraftingInventory();
	
	boolean isSneaking();
	
	void setSneaking(boolean sneaking);
	
	void setCooldown(Material material, int ticks);
	
	int getCooldown(Material material);
	
	int getCooldownPast(Material material);
	
	boolean hasCooldown(Material material);
	
	int removeCooldown(Material material);

}
