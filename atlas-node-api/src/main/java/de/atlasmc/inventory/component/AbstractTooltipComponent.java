package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractTooltipComponent extends ItemComponent {
	
	static final NBTSerializationHandler<AbstractTooltipComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractTooltipComponent.class)
					.boolField("show_in_tooltip", AbstractTooltipComponent::isShowTooltip, AbstractTooltipComponent::setShowTooltip, true)
					.build();
	
	boolean isShowTooltip();
	
	void setShowTooltip(boolean show);
	
	AbstractTooltipComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends AbstractTooltipComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
