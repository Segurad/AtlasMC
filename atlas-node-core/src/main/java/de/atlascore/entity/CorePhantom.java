package de.atlascore.entity;

import org.joml.Vector3i;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Phantom;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CorePhantom extends CoreMob implements Phantom {

	protected static final MetaDataField<Integer>
	META_PHANTOM_SIZE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	private Vector3i anchor = new Vector3i();
	
	public CorePhantom(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PHANTOM_SIZE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getSize() {
		return metaContainer.getData(META_PHANTOM_SIZE);
	}

	@Override
	public void setSize(int size) {
		metaContainer.get(META_PHANTOM_SIZE).setData(size);	
		// TODO alter hitbox (horizontal=0.9 + 0.2*size and vertical=0.5 + 0.1 * i)
	}

	@Override
	public Vector3i getAnchorUnsafe() {
		return anchor;
	}

	@Override
	public Vector3i getAnchor(Vector3i pos) {
		return pos.set(anchor);
	}

	@Override
	public void setAnchor(Vector3i pos) {
		this.anchor.set(pos);
	}

}
