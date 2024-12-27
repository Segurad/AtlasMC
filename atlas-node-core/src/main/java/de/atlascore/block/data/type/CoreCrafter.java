package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreOrientable;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Crafter;

public class CoreCrafter extends CoreOrientable implements Crafter {

protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreOrientable.PROPERTIES, 
				BlockDataProperty.TRIGGERED,
				BlockDataProperty.CRAFTING);
	}
	
	protected boolean triggered;
	protected boolean crafting;
	
	public CoreCrafter(Material material) {
		super(material);
	}
	
	@Override
	public CoreCrafter clone() {
		return (CoreCrafter) super.clone();
	}

	@Override
	public boolean isTriggered() {
		return triggered;
	}

	@Override
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	@Override
	public boolean isCrafting() {
		return crafting;
	}

	@Override
	public void setCrafting(boolean crafting) {
		this.crafting = crafting;
	}
	
	@Override
	public int getStateID() {
		return material.getBlockStateID()+(triggered?0:1)+orientation.ordinal()*2+(crafting?0:24);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
