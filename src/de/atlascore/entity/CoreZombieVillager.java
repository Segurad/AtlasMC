package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Villager.VillagerData;
import de.atlasmc.entity.Villager.VillagerProfession;
import de.atlasmc.entity.Villager.VillagerType;
import de.atlasmc.entity.ZombieVillager;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreZombieVillager extends CoreZombie implements ZombieVillager {

	protected static final MetaDataField<Boolean>
	META_IS_CONVERTING = new MetaDataField<>(CoreZombie.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<VillagerData>
	META_VILLAGER_DATA = new MetaDataField<>(CoreZombie.LAST_META_INDEX + 2, null, MetaDataType.VILLAGER_DATA);
	
	protected static final int LAST_META_INDEX = CoreZombie.LAST_META_INDEX+2;
	
	protected static final CharKey
		NBT_OFFERS = CharKey.of("Offers"),
		NBT_RECIPES = CharKey.of("Recipes"),
		NBT_TYPE = CharKey.of("type"),
		NBT_PROFESSION = CharKey.of("profession"),
		NBT_LEVEL = CharKey.of("level"),
		NBT_VILLAGER_DATA = CharKey.of("VillagerData"),
		NBT_XP = CharKey.of("Xp"),
		NBT_CONVERSION_PLAYER = CharKey.of("ConversionPlayer"),
		NBT_CONVERSION_TIME = CharKey.of("ConversionTime");
	
	static {
		NBT_FIELDS.setField(NBT_CONVERSION_PLAYER, (holder, reader) -> {
			if (holder instanceof ZombieVillager) {
				((ZombieVillager) holder).setConversionPlayer(reader.readUUID());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_CONVERSION_TIME, (holder, reader) -> {
			if (holder instanceof ZombieVillager) {
				((ZombieVillager) holder).setConversionTime(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_XP, (holder, reader) -> {
			if (holder instanceof ZombieVillager) {
				((ZombieVillager) holder).setXp(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_VILLAGER_DATA, (holder, reader) -> {
			reader.readNextEntry();
			ZombieVillager villager = (ZombieVillager) holder;
			while (reader.getType() != TagType.TAG_END) {
				final CharSequence value = reader.getFieldName();
				if (NBT_PROFESSION.equals(value)) {
					VillagerProfession prof = VillagerProfession.getByNameID(reader.readStringTag());
					if (prof != null)
						break;
					villager.setVillagerProfession(prof);
				} else if (NBT_TYPE.equals(value)) {
					VillagerType type = VillagerType.getByNameID(reader.readStringTag());
					if (type != null)
						break;
					villager.setVillagerType(type);
				} else if (NBT_LEVEL.equals(value))
					villager.setLevel(reader.readIntTag());
				else
					reader.skipTag();
			}
			reader.readNextEntry();
		});
	}
	
	private List<MerchantRecipe> recipes;
	private int xp;
	private UUID conversionPlayer;
	private int conversionTime = -1;
	
	public CoreZombieVillager(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_CONVERTING);
		metaContainer.set(META_VILLAGER_DATA);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isConverting() {
		return metaContainer.getData(META_IS_CONVERTING);
	}

	@Override
	public VillagerType getVillagerType() {
		return metaContainer.getData(META_VILLAGER_DATA).getType();
	}

	@Override
	public void setVillagerType(VillagerType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		MetaData<VillagerData> data = metaContainer.get(META_VILLAGER_DATA);
		data.getData().setType(type);
		data.setChanged(true);
	}

	@Override
	public VillagerProfession getVillagerProfession() {
		return metaContainer.getData(META_VILLAGER_DATA).getProfession();
	}

	@Override
	public void setVillagerProfession(VillagerProfession profession) {
		if (profession == null)
			throw new IllegalArgumentException("Profession can not be null!");
		MetaData<VillagerData> data = metaContainer.get(META_VILLAGER_DATA);
		data.getData().setProfession(profession);
		data.setChanged(true);
	}

	@Override
	public int getLevel() {
		return metaContainer.getData(META_VILLAGER_DATA).getLevel();
	}

	@Override
	public void setLevel(int level) {
		if (level < 1)
			throw new IllegalArgumentException("Level can not be lower than 1: " + level);
		MetaData<VillagerData> data = metaContainer.get(META_VILLAGER_DATA);
		data.getData().setLevel(level);
		data.setChanged(true);
	}

	@Override
	public void setConverting(boolean converting) {
		metaContainer.get(META_IS_CONVERTING).setData(converting);
	}

	@Override
	public MerchantRecipe getRecipe(int index) {
		if (index >= getRecipeCount() || index < 0)
			return null;
		return recipes.get(index);
	}

	@Override
	public int getRecipeCount() {
		return recipes == null ? 0 : recipes.size();
	}

	@Override
	public List<MerchantRecipe> getRecipes() {
		if (recipes == null)
			recipes = new ArrayList<>();
		return recipes;
	}

	@Override
	public void addRecipe(MerchantRecipe recipe) {
		getRecipes().add(recipe);
	}

	@Override
	public boolean hasRecipes() {
		return recipes != null && !recipes.isEmpty();
	}

	@Override
	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public int getXp() {
		return xp;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getConversionTime() > -1)
			writer.writeIntTag(NBT_CONVERSION_TIME, getConversionTime());
		if (getConversionPlayer() != null)
			writer.writeUUID(NBT_CONVERSION_PLAYER, getConversionPlayer());
		writer.writeCompoundTag(NBT_VILLAGER_DATA);
		writer.writeIntTag(NBT_LEVEL, getLevel());
		writer.writeStringTag(NBT_PROFESSION, getVillagerProfession().getNameID());
		writer.writeStringTag(NBT_TYPE, getVillagerType().getNameID());
		writer.writeEndTag();
		writer.writeIntTag(NBT_XP, getXp());
		if (hasRecipes()) {
			writer.writeCompoundTag(NBT_OFFERS);
			List<MerchantRecipe> recipes = getRecipes();
			writer.writeListTag(NBT_RECIPES, TagType.COMPOUND, recipes.size());
			for (MerchantRecipe recipe : recipes) {
				recipe.toNBT(writer, systemData);
				writer.writeEndTag();
			}
			writer.writeEndTag();
		}
	}

	@Override
	public void setConversionPlayer(UUID uuid) {
		this.conversionPlayer = uuid;
	}

	@Override
	public UUID getConversionPlayer() {
		return conversionPlayer;
	}

	@Override
	public void setConversionTime(int ticks) {
		this.conversionTime = ticks;
	}

	@Override
	public int getConversionTime() {
		return conversionTime;
	}

}
