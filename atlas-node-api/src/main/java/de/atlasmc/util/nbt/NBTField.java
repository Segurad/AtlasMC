package de.atlasmc.util.nbt;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;

@FunctionalInterface
public interface NBTField<H extends NBTHolder> {
	
	public void setField(H holder, NBTReader reader) throws IOException;

	@SuppressWarnings("unchecked")
	public static <H extends NBTHolder> NBTField<H> skip() {
		return (NBTField<H>) AbstractNBTBase.SKIP;
	}
	
}
 