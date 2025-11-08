package de.atlasmc.node.entity;

import java.util.List;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.MerchantInventory;
import de.atlasmc.util.annotation.NotNull;

public interface Merchant extends InventoryHolder {

	public static final NBTCodec<Merchant>
	NBT_HANDLER = NBTCodec
					.builder(Merchant.class)
					.beginComponent("Offers")
					.codecList("Recipes", Merchant::hasRecipes, Merchant::getRecipes, MerchantRecipe.NBT_HANDLER)
					.endComponent()
					.intField("HeadShakeTimer", Merchant::getHeadShakeTimer, Merchant::setHeadShakeTimer)
					.build();
	
	int getHeadShakeTimer();
	
	void setHeadShakeTimer(int time);
	
	MerchantInventory getInventory();
	
	default MerchantRecipe getRecipe(int index) {
		return hasInventory() ? getInventory().getRecipe(index) : null;
	}

	default int getRecipeCount() {
		return hasInventory() ? getInventory().getRecipeCount() : 0;
	}

	default List<MerchantRecipe> getRecipes() {
		return getInventory().getRecipes();
	}

	default void addRecipe(MerchantRecipe recipe) {
		if (recipe == null)
			throw new IllegalArgumentException("Recipe can not be null!");
		getInventory().addRecipe(recipe);
	}

	default boolean hasRecipes() {
		return hasInventory() && getInventory().getRecipeCount() > 0;
	}
	
	public static class MerchantRecipe implements Cloneable, NBTSerializable {
		
		public static final NBTCodec<MerchantRecipe>
		NBT_HANDLER = NBTCodec
						.builder(MerchantRecipe.class)
						.defaultConstructor(MerchantRecipe::new)
						.codec("buy", MerchantRecipe::getInputItem1, MerchantRecipe::setInputItem1, ItemStack.NBT_HANDLER)
						.codec("buyB", MerchantRecipe::getInputItem2, MerchantRecipe::setInputItem2, ItemStack.NBT_HANDLER)
						.intField("demand", MerchantRecipe::getDemand, MerchantRecipe::setDemand, 0)
						.intField("maxUses", MerchantRecipe::getMaxUses, MerchantRecipe::setMaxUses, 0)
						.floatField("priceMultiplier", MerchantRecipe::getPriceMultiplier, MerchantRecipe::setPriceMultiplier, 0)
						.boolField("rewarded", MerchantRecipe::isRewardExp, MerchantRecipe::setRewardExp, false)
						.codec("sell", MerchantRecipe::getOutputItem, MerchantRecipe::setOutputItem, ItemStack.NBT_HANDLER)
						.intField("specialPRice", MerchantRecipe::getSpecialPrice, MerchantRecipe::setSpecialPrice, 0)
						.intField("uses", MerchantRecipe::getUses, MerchantRecipe::setUses, 0)
						.intField("xp", MerchantRecipe::getXP, MerchantRecipe::setXP, 0)
						.build();
		
		private ItemStack inputItem1;
		private ItemStack inputItem2;
		private ItemStack outputItem;
		private boolean rewardExp;
		private int uses;
		private int maxUses;
		private int xp;
		private int specialPrice;
		private int demand;
		private float priceMultiplier;
		
		public MerchantRecipe() {}
		
		public MerchantRecipe(ItemStack inputItem1, ItemStack outputItem) {
			this.inputItem1 = inputItem1;
			this.outputItem = outputItem;
		}

		public ItemStack getInputItem1() {
			return inputItem1;
		}

		public ItemStack getInputItem2() {
			return inputItem2;
		}

		public ItemStack getOutputItem() {
			return outputItem;
		}

		public boolean isRewardExp() {
			return rewardExp;
		}

		public int getUses() {
			return uses;
		}

		public int getMaxUses() {
			return maxUses;
		}

		public int getXP() {
			return xp;
		}

		public int getSpecialPrice() {
			return specialPrice;
		}

		public int getDemand() {
			return demand;
		}

		public float getPriceMultiplier() {
			return priceMultiplier;
		}

		public void setInputItem1(@NotNull ItemStack inputItem1) {
			this.inputItem1 = inputItem1;
		}

		public void setInputItem2(ItemStack inputItem2) {
			this.inputItem2 = inputItem2;
		}

		public void setOutputItem(@NotNull ItemStack outputItem) {
			this.outputItem = outputItem;
		}

		public void setRewardExp(boolean reward) {
			this.rewardExp = reward;
		}

		public void setUses(int uses) {
			this.uses = uses;
		}

		public void setMaxUses(int maxUses) {
			this.maxUses = maxUses;
		}

		public void setXP(int xp) {
			this.xp = xp;
		}

		public void setSpecialPrice(int specialPrice) {
			this.specialPrice = specialPrice;
		}

		public void setDemand(int demand) {
			this.demand = demand;
		}

		public void setPriceMultiplier(float priceMultiplier) {
			this.priceMultiplier = priceMultiplier;
		}
		
		public boolean hasInputItem1() {
			return inputItem1 != null;
		}
		
		public boolean hasInputItem2() {
			return inputItem2 != null;
		}
		
		public MerchantRecipe clone() {
			MerchantRecipe clone = null;
			try {
				clone = (MerchantRecipe) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			if (clone == null) return null;
			clone.setInputItem1(inputItem1.clone());
			clone.setOutputItem(outputItem.clone());
			if (hasInputItem2()) 
				clone.setInputItem2(inputItem2.clone());
			return clone;
		}
		
		@Override
		public NBTCodec<? extends MerchantRecipe> getNBTCodec() {
			return NBT_HANDLER;
		}
		
	}

}
