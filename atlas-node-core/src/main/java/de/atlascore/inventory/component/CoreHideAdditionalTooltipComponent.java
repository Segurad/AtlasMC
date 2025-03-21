package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.HideAdditionalTooltipComponent;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class CoreHideAdditionalTooltipComponent extends AbstractItemComponent implements HideAdditionalTooltipComponent {
	
	public static final CoreHideAdditionalTooltipComponent INSTANCE = new CoreHideAdditionalTooltipComponent(ComponentType.HIDE_ADDITIONAL_TOOLTIP.getKey());
	
	public CoreHideAdditionalTooltipComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreHideAdditionalTooltipComponent clone() {
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
		return ComponentType.HIDE_ADDITIONAL_TOOLTIP;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
