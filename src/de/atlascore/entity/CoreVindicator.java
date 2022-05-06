package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Vindicator;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreVindicator extends CoreRaider implements Vindicator {

	protected static final CharKey
	NBT_JOHNNY = CharKey.of("Johnny");
	
	static {
		NBT_FIELDS.setField(NBT_JOHNNY, (holder, reader) -> {
			if (holder instanceof Vindicator) {
				((Vindicator) holder).setJohnny(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private boolean johnny;
	
	public CoreVindicator(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public void setJohnny(boolean johnny) {
		this.johnny = johnny;
	}

	@Override
	public boolean isJohnny() {
		return johnny;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isJohnny())
			writer.writeByteTag(NBT_JOHNNY, johnny);
	}

}
