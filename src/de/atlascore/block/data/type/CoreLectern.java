package de.atlascore.block.data.type;

import de.atlasmc.Material;
import de.atlasmc.block.data.type.Lectern;
import de.atlasmc.util.nbt.io.NBTWriter;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CorePowerable;

public class CoreLectern extends CoreDirectional4Faces implements Lectern {

	protected static final String
	BOOK = "book";
	
	static {
		NBT_FIELDS.setField(BOOK, (holder, reader) -> {
			if (holder instanceof Lectern)
			((Lectern) holder).setBook(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean book, powered;
	
	public CoreLectern(Material material) {
		super(material);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public boolean hasBook() {
		return book;
	}

	@Override
	public void setBook(boolean book) {
		this.book = book;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				(book?0:2)+
				getFaceValue()*4;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPowered()) writer.writeByteTag(CorePowerable.POWERED, true);
		if (hasBook()) writer.writeByteTag(BOOK, true);
	}

}
