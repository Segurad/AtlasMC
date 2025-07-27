package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.FireworkExplosion;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.FireworksComponent;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreFireworksComponent extends AbstractItemComponent implements FireworksComponent {
	
	private List<FireworkExplosion> explosions; 
	private int flightDuration;
	
	public CoreFireworksComponent(NamespacedKey key) {
		super(key);
		flightDuration = 1;
	}
	
	@Override
	public CoreFireworksComponent clone() {
		CoreFireworksComponent clone = (CoreFireworksComponent) super.clone();
		if (explosions != null) {
			clone.explosions = new ArrayList<>();
			final int size = explosions.size();
			for (int i = 0; i < size; i++) {
				FireworkExplosion explosion = explosions.get(i);
				clone.explosions.add(explosion.clone());
			}
		}
		return clone;
	}

	@Override
	public List<FireworkExplosion> getExplosions() {
		if (explosions == null)
			explosions = new ArrayList<>();
		return explosions;
	}

	@Override
	public boolean hasExplosions() {
		return explosions != null && !explosions.isEmpty();
	}

	@Override
	public void addExplosion(FireworkExplosion explosion) {
		getExplosions().add(explosion);
	}

	@Override
	public void removeExplosions(FireworkExplosion explosion) {
		if (explosions != null)
			explosions.remove(explosion);
	}

	@Override
	public int getFlightDuration() {
		return flightDuration;
	}

	@Override
	public void setFlightDuration(int duration) {
		this.flightDuration = duration;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.FIREWORKS;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		flightDuration = readVarInt(buf);
		final int count = readVarInt(buf);
		if (explosions != null)
			explosions.clear();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				addExplosion(readFireworkExplosion(buf));
			}
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(flightDuration, buf);
		if (hasExplosions()) {
			final int size = explosions.size();
			writeVarInt(size, buf);
			for (int i = 0; i < size; i++) {
				FireworkExplosion explosion = explosions.get(i);
				writeFireworkExplosion(explosion, buf);
			}
		} else {
			writeVarInt(0, buf);
		}
	}

}
