package de.atlasmc.util.nbt;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;

@FunctionalInterface
public interface NBTField<H> {
	
	void setField(H holder, NBTReader reader) throws IOException;

	@SuppressWarnings("unchecked")
	public static <H> NBTField<H> skip() {
		return (NBTField<H>) AbstractNBTBase.SKIP;
	}
	
}
 