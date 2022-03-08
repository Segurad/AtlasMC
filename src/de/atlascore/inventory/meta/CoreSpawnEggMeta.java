package de.atlascore.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.SpawnEggMeta;

public class CoreSpawnEggMeta extends CoreItemMeta implements SpawnEggMeta {

	private EntityType type;
	
	public CoreSpawnEggMeta(Material material) {
		super(material);
	}

	@Override
	public CoreSpawnEggMeta clone() {
		return (CoreSpawnEggMeta) super.clone();
	}

	@Override
	public EntityType getSpawnedType() {
		return type;
	}

	@Override
	public void setSpawnedType(EntityType type) {
		this.type = type;
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage, boolean checkClass) {
		if (!super.isSimilar(meta, ignoreDamage, checkClass))
			return false;
		SpawnEggMeta eggMeta = (SpawnEggMeta) meta;
		if (getSpawnedType() != null && !getSpawnedType().equals(eggMeta.getSpawnedType()))
			return false;
		else if (eggMeta.getSpawnedType() != null)
			return false;
		return true;
	}

}
