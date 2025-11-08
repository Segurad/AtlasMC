package de.atlasmc.nbt;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTReader;

@FunctionalInterface
public interface NBTField<H> {
	
	void setField(H holder, NBTReader reader) throws IOException;

	@SuppressWarnings("unchecked")
	public static <H> NBTField<H> skip() {
		return (NBTField<H>) AbstractNBTBase.SKIP;
	}
	
}
 