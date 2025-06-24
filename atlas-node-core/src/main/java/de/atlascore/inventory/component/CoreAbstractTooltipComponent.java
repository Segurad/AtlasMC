package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.AbstractTooltipComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public abstract class CoreAbstractTooltipComponent extends AbstractItemComponent implements AbstractTooltipComponent {
	
	protected static final NBTSerializationHandler<CoreAbstractTooltipComponent> NBT_HANDLER;
	
	protected static final CharKey
	NBT_SHOW_IN_TOOLTIP = CharKey.literal("show_in_tooltip");
	
	
	static {
		NBT_HANDLER = NBTSerializationHandler
				.builder(CoreAbstractTooltipComponent.class)
				.bool("show_in_tooltip", CoreAbstractTooltipComponent::isShowTooltip, CoreAbstractTooltipComponent::setShowTooltip, false)
				.build();
	}
	
	protected boolean showInTooltip;
	
	public CoreAbstractTooltipComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public boolean isShowTooltip() {
		return showInTooltip;
	}

	@Override
	public void setShowTooltip(boolean show) {
		this.showInTooltip = show;
	}
	
	@Override
	public CoreAbstractTooltipComponent clone() {
		return (CoreAbstractTooltipComponent) super.clone();
	}

}
