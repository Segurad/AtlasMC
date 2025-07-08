package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.writeIdentifier;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.DamageType;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DamageResistantComponent;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import io.netty.buffer.ByteBuf;

public class CoreDamageResistantComponent extends AbstractItemComponent implements DamageResistantComponent {
	
	private Tag<DamageType> types;
	
	public CoreDamageResistantComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreDamageResistantComponent clone() {
		return (CoreDamageResistantComponent) super.clone();
	}

	@Override
	public Tag<DamageType> getDamageTypes() {
		return types;
	}

	@Override
	public void setDamageTypes(Tag<DamageType> types) {
		this.types = types;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.DAMAGE_RESISTANT;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NamespacedKey key = readIdentifier(buf);
		if (key == null)
			return;
		types = Tags.getTag(key);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (types == null)
			writeIdentifier(null, buf);
		else
			writeIdentifier(types.getNamespacedKey(), buf);
	}

}
