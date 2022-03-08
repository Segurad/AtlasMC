package de.atlasmc.inventory.meta;

public interface SkullMeta extends TileEntityMeta {

	public SkullMeta clone();
	
	@Override
	public default Class<? extends SkullMeta> getInterfaceClass() {
		return SkullMeta.class;
	}
	
}
