package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.IdentifierComponent;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreIdentifierComponent extends AbstractItemComponent implements IdentifierComponent {

	private NamespacedKey identifier;
	
	public CoreIdentifierComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeNamespacedKey(key.toString(), identifier);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		identifier = reader.readNamespacedKey();
	}

	@Override
	public void setIdentifier(NamespacedKey id) {
		identifier = id;
	}

	@Override
	public NamespacedKey getIdentifier() {
		return identifier;
	}

}
