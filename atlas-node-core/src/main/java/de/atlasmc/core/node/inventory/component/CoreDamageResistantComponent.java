package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.writeIdentifier;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.entity.DamageType;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.DamageResistantComponent;
import de.atlasmc.tag.TagKey;
import io.netty.buffer.ByteBuf;

public class CoreDamageResistantComponent extends AbstractItemComponent implements DamageResistantComponent {
	
	private TagKey<DamageType> types;
	
	public CoreDamageResistantComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDamageResistantComponent clone() {
		return (CoreDamageResistantComponent) super.clone();
	}

	@Override
	public TagKey<DamageType> getDamageTypes() {
		return types;
	}

	@Override
	public void setDamageTypes(TagKey<DamageType> types) {
		this.types = types;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NamespacedKey key = readIdentifier(buf);
		if (key == null)
			return;
		types = new TagKey<>(key);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (types == null)
			writeIdentifier(null, buf);
		else
			writeIdentifier(types.getNamespacedKey(), buf);
	}

}
