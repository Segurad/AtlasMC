package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.entity.Arrow;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreArrow extends CoreAbstractArrow implements Arrow {

	protected static final MetaDataField<Integer>
	META_COLOR = new MetaDataField<>(CoreAbstractArrow.LAST_META_INDEX+1, -1, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreAbstractArrow.LAST_META_INDEX+1;
	
	public CoreArrow(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_COLOR);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public Color getColor() {
		int color = getColorRGB();
		return color == -1 ? null : new Color(color);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.ARROW;
	}

	@Override
	public void setColor(Color color) {
		setColor(color.asRGB());
	}

	@Override
	public int getColorRGB() {
		return metaContainer.getData(META_COLOR);
	}

	@Override
	public void setColor(int rgb) {
		if (rgb < 0)
			metaContainer.get(META_COLOR).setData(-1);
		else
			metaContainer.get(META_COLOR).setData(rgb & 0xFFFFFF);
	}

}
