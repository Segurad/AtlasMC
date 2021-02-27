package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Powerable;

public interface Lectern extends Directional, Powerable {

	public boolean hasBook();
	public void setBook(boolean book);
}
