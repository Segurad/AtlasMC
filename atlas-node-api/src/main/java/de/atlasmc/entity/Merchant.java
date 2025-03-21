package de.atlasmc.entity;

import java.io.IOException;
import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface Merchant {

	public MerchantRecipe getRecipe(int index);
	
	public int getRecipeCount();
	
	public List<MerchantRecipe> getRecipes();
	
	public void addRecipe(MerchantRecipe recipe);
	
	public boolean hasRecipes();
	
	public static class MerchantRecipe implements Cloneable, NBTHolder {
		
		protected static final NBTFieldSet<MerchantRecipe> NBT_FIELDS;
		
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
		
		static {
			NBT_FIELDS = NBTFieldSet.newSet();
			NBT_FIELDS.setField(NBT_BUY, (holder, reader) -> {
				reader.readNextEntry();
				holder.inputItem1 = ItemStack.getFromNBT(reader);
			});
			NBT_FIELDS.setField(NBT_BUY_B, (holder, reader) -> {
				reader.readNextEntry();
				holder.inputItem2 = ItemStack.getFromNBT(reader);
			});
			NBT_FIELDS.setField(NBT_DEMAND, (holder, reader) -> {
				holder.demand = reader.readIntTag();
			});
			NBT_FIELDS.setField(NBT_MAX_USES, (holder, reader) -> {
				holder.maxUses = reader.readIntTag();
			});
			NBT_FIELDS.setField(NBT_PRICE_MULTIPLIER, (holder, reader) -> {
				holder.priceMultiplier = reader.readIntTag();
			});
			NBT_FIELDS.setField(NBT_REWARD_EXP, (holder, reader) -> {
				holder.rewardExp = reader.readBoolean();
			});
			NBT_FIELDS.setField(NBT_SELL, (holder, reader) -> {
				reader.readNextEntry();
				holder.outputItem = ItemStack.getFromNBT(reader);
			});
			NBT_FIELDS.setField(NBT_SPECIAL_PRICE, (holder, reader) -> {
				holder.specialPrice = reader.readIntTag();
			});
			NBT_FIELDS.setField(NBT_USES, (holder, reader) -> {
				holder.uses = reader.readIntTag();
			});
			NBT_FIELDS.setField(NBT_XP, (holder, reader) -> {
				holder.xp = reader.readIntTag();
			});
		}
		
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
		public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
			if (inputItem1 != null) {
				writer.writeCompoundTag(NBT_BUY);
				inputItem1.toNBT(writer, systemData);
				writer.writeEndTag();
			}
			if (inputItem2 != null) {
				writer.writeCompoundTag(NBT_BUY_B);
				inputItem2.toNBT(writer, systemData);
				writer.writeEndTag();
			}
			writer.writeIntTag(NBT_DEMAND, demand);
			writer.writeIntTag(NBT_MAX_USES, maxUses);
			writer.writeFloatTag(NBT_PRICE_MULTIPLIER, priceMultiplier);
			writer.writeByteTag(NBT_REWARD_EXP, rewardExp);
			if (outputItem != null) {
				writer.writeCompoundTag(NBT_SELL);
				outputItem.toNBT(writer, systemData);
				writer.writeEndTag();
			}
			writer.writeIntTag(NBT_SPECIAL_PRICE, demand);
			writer.writeIntTag(NBT_USES, uses);
			writer.writeIntTag(NBT_XP, xp);
		}

		@Override
		public void fromNBT(NBTReader reader) throws IOException {
			NBTUtil.readNBT(NBT_FIELDS, this, reader);
		}
	}

}
