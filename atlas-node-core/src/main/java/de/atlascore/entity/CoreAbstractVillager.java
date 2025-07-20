package de.atlascore.entity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import de.atlasmc.entity.AbstractVillager;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.MerchantInventory;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAbstractVillager extends CoreAgeableMob implements AbstractVillager {

	protected static final MetaDataField<Integer>
	META_HEAD_SHAKE_TIME = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldSet<CoreAbstractVillager> NBT_FIELDS;
	
	protected static final CharKey
		NBT_OFFERS = CharKey.literal("Offers"),
		NBT_RECIPES = CharKey.literal("Recipes"),
		NBT_LAST_RESTOCK = CharKey.literal("LastRestock"),
		NBT_RESTOCKS_TODAY = CharKey.literal("RestocksToday");
	
	static {
		NBT_FIELDS = CoreAgeableMob.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_OFFERS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				if (!reader.getFieldName().equals(NBT_RECIPES)) {
					reader.skipTag();
					continue;
				}
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					MerchantRecipe recipe = new MerchantRecipe();
					recipe.fromNBT(reader);
					holder.addRecipe(recipe);
				}
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_LAST_RESTOCK, (holder, reader) -> {
			holder.setLastRestock(reader.readLongTag());
		});
		NBT_FIELDS.setField(NBT_RESTOCKS_TODAY, (holder, reader) -> {
			holder.setRestocksToday(reader.readIntTag());
		});
	}
		
	private MerchantInventory inv;
	private long lastRestock;
	private int restocksToday;
	
	public CoreAbstractVillager(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreAbstractVillager> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_HEAD_SHAKE_TIME);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public MerchantRecipe getRecipe(int index) {
		return getInventory().getRecipe(index);
	}

	@Override
	public int getRecipeCount() {
		return getInventory().getRecipeCount();
	}

	@Override
	public List<MerchantRecipe> getRecipes() {
		return getInventory().getRecipes();
	}

	@Override
	public int getHeadShakeTimer() {
		return metaContainer.getData(META_HEAD_SHAKE_TIME);
	}

	@Override
	public void setHeadShakeTimer(int time) {
		metaContainer.get(META_HEAD_SHAKE_TIME).setData(time);		
	}

	@Override
	public MerchantInventory getInventory() {
		if (inv == null)
			inv = ContainerFactory.MERCHANT_INV_FACTORY.create(InventoryType.MERCHANT, this);
		return inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

	@Override
	public void addRecipe(MerchantRecipe recipe) {
		if (recipe == null)
			throw new IllegalArgumentException("Recipe can not be null!");
		getInventory().addRecipe(recipe);
	}

	@Override
	public void setLastRestock(long tick) {
		this.lastRestock = tick;
	}

	@Override
	public long getLastRestock() {
		return lastRestock;
	}

	@Override
	public int getRestocksToday() {
		return restocksToday;
	}

	@Override
	public void setRestocksToday(int restocks) {
		this.restocksToday = restocks;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasRecipes()) {
			writer.writeCompoundTag();
			List<MerchantRecipe> recipes = getInventory().getRecipes();
			writer.writeListTag(NBT_RECIPES, TagType.COMPOUND, recipes.size());
			for (MerchantRecipe recipe : recipes) {
				recipe.toNBT(writer, systemData);
				writer.writeEndTag();
			}
			writer.writeEndTag();
		}
		writer.writeLongTag(NBT_LAST_RESTOCK, lastRestock);
		writer.writeIntTag(NBT_RESTOCKS_TODAY, restocksToday);
	}

	@Override
	public boolean hasRecipes() {
		return inv != null && inv.getRecipeCount() > 0;
	}

}
