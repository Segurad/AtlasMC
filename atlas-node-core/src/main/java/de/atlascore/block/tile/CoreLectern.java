package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Lectern;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LecternInventory;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLectern extends CoreTileEntity implements Lectern {

	protected static final NBTFieldSet<CoreLectern> NBT_FIELDS;
	
	protected static final CharKey
	BOOK = CharKey.literal("Book"),
	PAGE = CharKey.literal("Page");
	
	private LecternInventory inv;
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(BOOK, (holder, reader) -> {
			reader.readNextEntry();
			ItemStack item = ItemStack.getFromNBT(reader);
			holder.setBook(item);
		});
		NBT_FIELDS.setField(PAGE, (holder, reader) -> {
			holder.setPage(reader.readIntTag());
		});
	}
	
	public CoreLectern(BlockType type) {
		super(type);
	}

	@Override
	public ItemStack getBook() {
		if (inv == null) 
			return null;
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
	protected NBTFieldSet<? extends CoreLectern> getFieldSetRoot() {
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
