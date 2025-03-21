package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.HideTooltipComponent;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class CoreHideTooltipComponent extends AbstractItemComponent implements HideTooltipComponent {
	
	public static final CoreHideTooltipComponent INSTANCE = new CoreHideTooltipComponent(ComponentType.HIDE_TOOLTIP.getKey());
	
	public CoreHideTooltipComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreHideTooltipComponent clone() {
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
		return ComponentType.HIDE_TOOLTIP;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
