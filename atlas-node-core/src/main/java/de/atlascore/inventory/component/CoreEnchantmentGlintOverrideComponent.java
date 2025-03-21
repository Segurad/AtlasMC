package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.EnchantmentGlintOverrideComponent;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreEnchantmentGlintOverrideComponent extends AbstractItemComponent implements EnchantmentGlintOverrideComponent {

	private boolean glint;
	
	public CoreEnchantmentGlintOverrideComponent(NamespacedKey key) {
		super(key);
		this.glint = true;
	}
	
	@Override
	public CoreEnchantmentGlintOverrideComponent clone() {
		return (CoreEnchantmentGlintOverrideComponent) super.clone();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (!glint)
			writer.writeByteTag(key.toString(), false);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		glint = reader.readBoolean();
	}

	@Override
	public boolean hasGlint() {
		return glint;
	}

	@Override
	public void setGlint(boolean glint) {
		this.glint = glint;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.ENCHANTMENT_GLINT_OVERRIDE;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		glint = buf.readBoolean();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		buf.writeBoolean(glint);
	}
	
}
