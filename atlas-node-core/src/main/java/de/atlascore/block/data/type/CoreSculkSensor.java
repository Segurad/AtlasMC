package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAnaloguePowerable;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.SculkSensor;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSculkSensor extends CoreAnaloguePowerable implements SculkSensor {
	
	public static final CharKey NBT_SCULK_SENSOR_PHASE = CharKey.literal("sculk_sensor_phase");
	
	static {
		NBT_FIELDS.setField(NBT_SCULK_SENSOR_PHASE, (holder, reader) -> {
			if (holder instanceof SculkSensor data) {
				data.setPhase(Phase.getByName(reader.readStringTag()));
			} else reader.skipTag();
		});
	}
	
	private boolean waterlogged;
	private Phase phase;
	
	public CoreSculkSensor(Material material) {
		super(material);
		phase = Phase.INACTIVE;
	}
	
	@Override
	public CoreSculkSensor clone() {
		return (CoreSculkSensor) super.clone();
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}

	@Override
	public void setPhase(Phase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Phase can not be null!");
		this.phase = phase;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (waterlogged?0:1) + phase.getID()*2 + getPower()*6;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (waterlogged)
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
		if (phase != Phase.INACTIVE)
			writer.writeStringTag(NBT_SCULK_SENSOR_PHASE, phase.getName());
	}
	
	@Override
	public Phase getPhase() {
		return phase;
	}

}
