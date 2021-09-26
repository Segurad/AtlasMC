package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Lectern;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LecternInventory;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreLectern extends CoreTileEntity implements Lectern {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	BOOK = "Book",
	PAGE = "Page";
	
	private LecternInventory inv;
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(BOOK, (holder, reader) -> {
			if (holder instanceof Lectern) {
				reader.mark();
				reader.search(ID);
				Material type = Material.getByName(reader.readStringTag());
				reader.reset();
				ItemStack item = new ItemStack(type);
				item.fromNBT(reader);
				((Lectern) holder).setBook(item);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(PAGE, (holder, reader) -> {
			if (holder instanceof Lectern) 
			((Lectern) holder).setPage(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	public CoreLectern(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
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
		if (inv == null) return 0;
		return getInventory().getPage();
	}

	@Override
	public void setPage(int page) {
		getInventory().setPage(page);
	}

	@Override
	public LecternInventory getInventory() {
		if (inv == null) inv = ContainerFactory.LECTERN_INV_FACTORY.create(InventoryType.LECTERN, this);
		return inv;
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) return;
		if (getBook() == null) return;
		writer.writeCompoundTag(BOOK);
		getBook().toNBT(writer, systemData);
		writer.writeEndTag();
		writer.writeIntTag(PAGE, getPage());
	}

}
