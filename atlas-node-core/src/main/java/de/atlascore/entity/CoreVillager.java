package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.Material;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Villager;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreVillager extends CoreAbstractVillager implements Villager {
	
	protected static final MetaDataField<VillagerData>
	META_VILLAGER_DATA = new MetaDataField<>(CoreAbstractVillager.LAST_META_INDEX+1, null, MetaDataType.VILLAGER_DATA);
	
	protected static final int LAST_META_INDEX = CoreAbstractVillager.LAST_META_INDEX+1;
	
	protected static final NBTFieldSet<CoreVillager> NBT_FIELDS;
	
	protected static final CharKey
		NBT_TYPE = CharKey.literal("type"),
		NBT_PROFESSION = CharKey.literal("profession"),
		NBT_LEVEL = CharKey.literal("level"),
		NBT_INVENTORY = CharKey.literal("Inventory"),
		NBT_GOSSIPS = CharKey.literal("Gossips"),
		NBT_LAST_GOSSIP_DECAY = CharKey.literal("LastGossipDecay"),
		NBT_WILLING = CharKey.literal("Willing"),
		NBT_VILLAGER_DATA = CharKey.literal("VillagerData"),
		NBT_XP = CharKey.literal("Xp");
	
	static {
		NBT_FIELDS = CoreAbstractVillager.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_INVENTORY, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				Material mat = null;
				if (!NBT_ID.equals(reader.getFieldName())) {
					reader.mark();
					reader.search(NBT_ID);
					mat = Material.getByName(reader.readStringTag());
					reader.reset();
				} else mat = Material.getByName(reader.readStringTag());
				ItemStack item = new ItemStack(mat);
				item.fromNBT(reader);
				holder.addPocketItem(item);
			}
		});
		NBT_FIELDS.setField(NBT_GOSSIPS, NBTField.skip()); // TODO skipped because i found a use case
		NBT_FIELDS.setField(NBT_LAST_GOSSIP_DECAY, NBTField.skip()); // TODO see gossip
		NBT_FIELDS.setField(NBT_XP, (holder, reader) -> {
			holder.setXp(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_VILLAGER_DATA, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				final CharSequence value = reader.getFieldName();
				if (NBT_PROFESSION.equals(value)) {
					VillagerProfession prof = VillagerProfession.getByName(reader.readStringTag());
					if (prof != null)
						break;
					holder.setVillagerProfession(prof);
				} else if (NBT_TYPE.equals(value)) {
					VillagerType type = VillagerType.getByName(reader.readStringTag());
					if (type != null)
						break;
					holder.setVillagerType(type);
				} else if (NBT_LEVEL.equals(value))
					holder.setLevel(reader.readIntTag());
				else
					reader.skipTag();
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_WILLING, (holder, reader) -> {
			((Villager) holder).setWilling(reader.readByteTag() == 1);
		});
	}
	
	private List<ItemStack> pocket;
	private int xp;
	private boolean willing;
	
	public CoreVillager(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreVillager> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
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
	public List<ItemStack> getPocketContents() {
		if (pocket == null)
			pocket = new ArrayList<>();
		return pocket;
	}

	@Override
	public void addPocketItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getPocketContents().add(item);
	}

	@Override
	public void setPocketItems(List<ItemStack> pocket) {
		List<ItemStack> p = getPocketContents();
		p.clear();
		p.addAll(pocket);
	}

	@Override
	public boolean hasPocketItems() {
		return pocket != null && !pocket.isEmpty();
	}

	@Override
	public int getXp() {
		return xp;
	}

	@Override
	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public void addXp(int xp) {
		this.xp += xp;
	}

	@Override
	public boolean isWilling() {
		return willing;
	}

	@Override
	public void setWilling(boolean willing) {
		this.willing = willing;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasPocketItems()) {
			List<ItemStack> items = getPocketContents();
			writer.writeListTag(NBT_INVENTORY, TagType.COMPOUND, items.size());
			int index = 0;
			for (ItemStack item : items) {
				item.toSlot(writer, systemData, index++);
				writer.writeEndTag();
			}
		}
		writer.writeCompoundTag(NBT_VILLAGER_DATA);
		writer.writeStringTag(NBT_TYPE, getVillagerType().getName());
		writer.writeStringTag(NBT_PROFESSION, getVillagerProfession().getName());
		writer.writeIntTag(NBT_LEVEL, getLevel());
		writer.writeEndTag();
		writer.writeIntTag(NBT_XP, getXp());
		writer.writeByteTag(NBT_WILLING, isWilling());
	}
	
}
