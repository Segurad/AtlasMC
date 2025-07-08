package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.AbstractTooltipComponent;

public abstract class CoreAbstractTooltipComponent extends AbstractItemComponent implements AbstractTooltipComponent {
	
	protected boolean showInTooltip = true;
	
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
