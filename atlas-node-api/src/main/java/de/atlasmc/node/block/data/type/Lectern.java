package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Powerable;

public interface Lectern extends Directional, Powerable {

	public boolean hasBook();
	public void setBook(boolean book);
}
