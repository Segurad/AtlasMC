package de.atlascore.inventory.component.effect;

import java.io.IOException;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.effect.ClearAllEffects;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

@Singleton
public class CoreClearAllEffects implements ClearAllEffects {

	public static final CoreClearAllEffects INSTANCE = new CoreClearAllEffects();
	
	@Override
	public void apply(Entity target, ItemStack item) {
		if (target instanceof LivingEntity entity)
			entity.removePotionEffects();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_TYPE, getType().getNamespacedKeyRaw());
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.skipToEnd();
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		// not required
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		// not required
	}
	
	@Override
	public CoreClearAllEffects clone() {
		return this;
	}

}
