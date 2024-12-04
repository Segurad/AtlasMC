package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Lectern;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LecternInventory;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLectern extends CoreTileEntity implements Lectern {

	protected static final NBTFieldContainer<CoreLectern> NBT_FIELDS;
	
	protected static final CharKey
	BOOK = CharKey.literal("Book"),
	PAGE = CharKey.literal("Page");
	
	private LecternInventory inv;
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(BOOK, (holder, reader) -> {
			Material mat = null;
			if (!NBT_ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(NBT_ID);
				mat = Material.getByName(reader.readStringTag());
				reader.reset();
			} else mat = Material.getByName(reader.readStringTag());
			ItemStack item = new ItemStack(mat);
			item.fromNBT(reader);
			holder.setBook(item);
		});
		NBT_FIELDS.setField(PAGE, (holder, reader) -> {
			holder.setPage(reader.readIntTag());
		});
	}
	
	public CoreLectern(Material type) {
		super(type);
	}

	@Override
	public ItemStack getBook() {
		if (inv == null) return null;
		return getInventory().getBook();
	}

	@Override
	public void setBook(ItemStack book) {
		getInventory().setBook(book);
	}

	@Override
	public int getPage() {
		if (inv == null) 
			return 0;
		return getInventory().getPage();
	}

	@Override
	public void setPage(int page) {
		getInventory().setPage(page);
	}

	@Override
	public LecternInventory getInventory() {
		if (inv == null) 
			inv = ContainerFactory.LECTERN_INV_FACTORY.create(InventoryType.LECTERN, this);
		return inv;
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreLectern> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) 
			return;
		if (getBook() == null) 
			return;
		writer.writeCompoundTag(BOOK);
		getBook().toNBT(writer, systemData);
		writer.writeEndTag();
		writer.writeIntTag(PAGE, getPage());
	}

}
