package de.atlasmc.entity;

import java.io.IOException;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface Merchant {

	public MerchantRecipe getRecipe(int index);
	
	public int getRecipeCount();
	
	public List<MerchantRecipe> getRecipes();
	
	public void addRecipe(MerchantRecipe recipe);
	
	public boolean hasRecipes();
	
	public static class MerchantRecipe implements Cloneable, NBTHolder {
		
		protected static final CharKey
		NBT_ID = CharKey.literal("id"), // required for get material
		NBT_BUY = CharKey.literal("buy"),
		NBT_BUY_B = CharKey.literal("buyB"),
		NBT_DEMAND = CharKey.literal("demand"),
		NBT_MAX_USES = CharKey.literal("maxUses"),
		NBT_PRICE_MULTIPLIER = CharKey.literal("priceMultiplier"),
		NBT_REWARD_EXP = CharKey.literal("rewardExp"),
		NBT_SELL = CharKey.literal("sell"),
		NBT_SPECIAL_PRICE = CharKey.literal("specialPrice"),
		NBT_USES = CharKey.literal("uses"),
		NBT_XP = CharKey.literal("xp");
		
		private ItemStack inputItem1;
		private ItemStack inputItem2;
		private ItemStack outputItem;
		private boolean disabled;
		private int trades;
		private int maxTrades;
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
		public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
			if (hasInputItem1()) {
				writer.writeCompoundTag(NBT_BUY);
				getInputItem1().toNBT(writer, systemData);
				writer.writeEndTag();
			}
			if (hasInputItem1()) {
				writer.writeCompoundTag(NBT_BUY_B);
				getInputItem2().toNBT(writer, systemData);
				writer.writeEndTag();
			}
			if (getOutputItem() != null) {
				writer.writeCompoundTag(NBT_SELL);
				getOutputItem().toNBT(writer, systemData);
				writer.writeEndTag();
			}
			writer.writeIntTag(NBT_DEMAND, demand);
			writer.writeIntTag(NBT_MAX_USES, maxTrades);
			writer.writeFloatTag(NBT_PRICE_MULTIPLIER, priceMultiplier);
			writer.writeIntTag(NBT_USES, trades);
			writer.writeIntTag(NBT_XP, xp);
		}

		@Override
		public void fromNBT(NBTReader reader) throws IOException {
			while (reader.getType() != TagType.TAG_END) {
				int itemID = 0;
				final CharSequence value = reader.getFieldName();
				if (NBT_BUY.equals(value))
					itemID++;
				else if (NBT_BUY_B.equals(value))
					itemID++;
				else if (NBT_SELL.equals(value)) {
					reader.readNextEntry();
					Material mat = null;
					if (!NBT_ID.equals(reader.getFieldName())) {
						reader.mark();
						reader.search(NBT_ID);
						mat = Material.getByName(reader.readStringTag());
						reader.reset();
					} else mat = Material.getByName(reader.readStringTag());
					ItemStack item = new ItemStack(mat);
					item.fromNBT(reader);
					if (itemID == 2)
						setInputItem1(item);
					else if (itemID == 1)
						setInputItem2(item);
					else if (itemID == 0)
						setOutputItem(item);
				} else if (NBT_DEMAND.equals(value))
					setDemand(reader.readIntTag());
				else if (NBT_MAX_USES.equals(value))
					setMaxTrades(reader.readIntTag());
				else if (NBT_PRICE_MULTIPLIER.equals(value))
					setPriceMultiplier(reader.readFloatTag());
				else if (NBT_REWARD_EXP.equals(value))
					reader.skipTag(); // TODO skipped reward xp flag
				else if (NBT_SPECIAL_PRICE.equals(value))
					setSpecialPrice(reader.readIntTag());
				else if (NBT_USES.equals(value))
					setTrades(reader.readIntTag());
				else if (NBT_XP.equals(value))
					setXP(reader.readIntTag());
			}
			reader.readNextEntry();
		}
	}

}
