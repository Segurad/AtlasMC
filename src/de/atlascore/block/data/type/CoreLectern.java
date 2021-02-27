package de.atlascore.block.data.type;

import de.atlasmc.Material;
import de.atlasmc.block.data.type.Lectern;

import de.atlascore.block.data.CoreDirectional4Faces;

public class CoreLectern extends CoreDirectional4Faces implements Lectern {

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

}
