package de.atlasmc.inventory.component;

public interface AbstractTooltipComponent extends ItemComponent {
	
	boolean isShowTooltip();
	
	void setShowTooltip(boolean show);
	
	AbstractTooltipComponent clone();

}
