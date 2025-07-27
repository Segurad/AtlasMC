package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.FireworkExplosion;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.FireworkExplosionComponent;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreFireworkExplosionComponent extends AbstractItemComponent implements FireworkExplosionComponent {

	private FireworkExplosion explosion;
	
	public CoreFireworkExplosionComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreFireworkExplosionComponent clone() {
		CoreFireworkExplosionComponent clone = (CoreFireworkExplosionComponent) super.clone();
		if (explosion != null)
			clone.explosion = explosion.clone();
		return clone;
	}

	@Override
	public FireworkExplosion getExplosion() {
		return explosion;
	}

	@Override
	public void setExplosion(FireworkExplosion explosion) {
		this.explosion = explosion;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.FIREWORK_EXPLOSION;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		explosion = readFireworkExplosion(buf);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeFireworkExplosion(explosion, buf);
	}

}
