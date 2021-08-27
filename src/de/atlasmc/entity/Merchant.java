package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public interface Merchant {

	public MerchantRecipe getRecipe(int index);
	
	public int getRecipeCount();
	
	public List<MerchantRecipe> getRecipes();
	
	
	
	public static class MerchantRecipe implements Cloneable {
		
		private ItemStack inputItem1, inputItem2, outputItem;
		private boolean disabled;
		private int trades, maxTrades, xp, specialPrice, demand;
		private float priceMultiplier;
		
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

		public boolean isDisabled() {
			return disabled;
		}

		public int getTrades() {
			return trades;
		}

		public int getMaxTrades() {
			return maxTrades;
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

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

		public void setTrades(int trades) {
			this.trades = trades;
		}

		public void setMaxTrades(int maxTrades) {
			this.maxTrades = maxTrades;
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
			if (hasInputItem2()) clone.setInputItem2(inputItem2.clone());
			return clone;
		}
	}

}
