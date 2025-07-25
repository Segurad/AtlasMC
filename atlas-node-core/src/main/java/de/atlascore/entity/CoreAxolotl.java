package de.atlascore.entity;

import de.atlasmc.entity.Axolotl;
import de.atlasmc.entity.EntityType;

public class CoreAxolotl extends CoreAgeableMob implements Axolotl {
	
	private Variant variant;
	private boolean fromBucket;
	
	public CoreAxolotl(EntityType type) {
		super(type);
		variant = Variant.LUCY;
	}

	@Override
	public Variant getVariant() {
		return variant;
	}

	@Override
	public void setVariant(Variant variant) {
		if (variant == null)
			throw new IllegalArgumentException("Variant can not be null!");
		this.variant = variant;
	}

	@Override
	public boolean isFromBucket() {
		return fromBucket;
	}

	@Override
	public void setFromBucket(boolean bucket) {
		this.fromBucket = bucket;
	}
	
}
