package de.atlasmc.node.entity.component;

import org.joml.Vector3i;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.UnsafeAPI;

public class EndCrystalMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<EndCrystalMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(EndCrystalMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("beam_target", EndCrystalMetaComponent::getBeamTarget, EndCrystalMetaComponent::setBeamTarget, NBTCodecs.VECTOR_3I)
					.boolField("ShowBottom", EndCrystalMetaComponent::getShowBottom, EndCrystalMetaComponent::setShowBottom, false)
					.build();

	public static final MetaDataField<Vector3i>
	META_BEAM_TARGET = new MetaDataField<>(8, null, EntityMetaTypes.OPT_POSITION);
	public static final MetaDataField<Boolean>
	META_SHOW_BOTTOM = new MetaDataField<>(9, true, EntityMetaTypes.BOOLEAN);

	public EndCrystalMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_BEAM_TARGET);
		container.set(META_SHOW_BOTTOM);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}

	public Vector3i getBeamTarget() {
		return hasTarget() ? getBeamTarget(new Vector3i()) : null;
	}

	@Nullable
	public Vector3i getBeamTarget(Vector3i loc) {
		var data = getHolder().getMetaContainer().getData(META_BEAM_TARGET);
		if (data == null)
			return null;
		loc.set(data);
		return loc;
	}

	@UnsafeAPI
	public Vector3i getBeamTargetUnsafe() {
		return getHolder().getMetaContainer().getData(META_BEAM_TARGET);
	}
	
	public void setBeamTarget(Vector3i loc) {
		if (loc == null) {
			resetTarget();
			return;
		}
		getHolder().getMetaContainer().setData(META_BEAM_TARGET, loc);
	}

	public void setBeamTarget(int x, int y, int z) {
		var container = getHolder().getMetaContainer();
		var vec = container.getData(META_BEAM_TARGET);
		if (vec != null) {
			vec.set(x, y, z);
			container.setChanged(META_BEAM_TARGET);
		} else {
			container.setData(META_BEAM_TARGET, new Vector3i(x, y, z));
		}
	}

	public boolean getShowBottom() {
		return getHolder().getMetaContainer().getData(META_SHOW_BOTTOM);
	}

	public void setShowBottom(boolean show) {
		getHolder().getMetaContainer().setData(META_SHOW_BOTTOM, show);
	}

	public boolean hasTarget() {
		return getHolder().getMetaContainer().getData(META_BEAM_TARGET) != null;
	}

	public void resetTarget() {
		getHolder().getMetaContainer().setData(META_BEAM_TARGET, null);
	}
	
	@Override
	public NBTCodec<? extends EndCrystalMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
