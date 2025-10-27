package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.EnchantmentGlintOverrideComponent;

public class CoreEnchantmentGlintOverrideComponent extends AbstractItemComponent implements EnchantmentGlintOverrideComponent {

	private boolean glint;
	
	public CoreEnchantmentGlintOverrideComponent(ComponentType type) {
		super(type);
		this.glint = true;
	}
	
	@Override
	public CoreEnchantmentGlintOverrideComponent clone() {
		return (CoreEnchantmentGlintOverrideComponent) super.clone();
	}

	@Override
	public boolean hasGlint() {
		return glint;
	}

	@Override
	public void setGlint(boolean glint) {
		this.glint = glint;
	}
	
}
