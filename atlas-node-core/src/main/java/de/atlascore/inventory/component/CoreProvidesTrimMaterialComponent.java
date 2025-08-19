package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ProvidesTrimMaterialComponent;
import de.atlasmc.inventory.component.TrimComponent.TrimMaterial;

public class CoreProvidesTrimMaterialComponent extends AbstractItemComponent implements ProvidesTrimMaterialComponent {

	private TrimMaterial material;
	
	public CoreProvidesTrimMaterialComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreProvidesTrimMaterialComponent clone() {
		return (CoreProvidesTrimMaterialComponent) super.clone();
	}

	@Override
	public TrimMaterial getMaterial() {
		return material;
	}

	@Override
	public void setMaterial(TrimMaterial material) {
		this.material = material;
	}

}
