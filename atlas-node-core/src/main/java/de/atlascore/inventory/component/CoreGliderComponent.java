package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.GliderComponent;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class CoreGliderComponent extends AbstractItemComponent implements GliderComponent {
	
	public static final CoreGliderComponent INSTANCE = new CoreGliderComponent(ComponentType.GLIDER.getKey());
	
	public CoreGliderComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreGliderComponent clone() {
		return this;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.skipTag();
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.GLIDER;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
