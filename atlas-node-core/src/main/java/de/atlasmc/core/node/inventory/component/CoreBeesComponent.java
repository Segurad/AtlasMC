package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.block.tile.Beehive.Occupant;
import de.atlasmc.node.entity.Bee;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BeesComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreBeesComponent extends AbstractItemComponent implements BeesComponent {
	
	private static final int DEFAULT_BEES_SIZE = 5;
	
	private List<Occupant> bees;
	
	public CoreBeesComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreBeesComponent clone() {
		CoreBeesComponent clone = (CoreBeesComponent) super.clone();
		if (clone == null)
			return null;
		return clone;
	}
	
	@Override
	public boolean hasBees() {
		return bees != null && !bees.isEmpty();
	}

	@Override
	public List<Occupant> getBees() {
		if (bees == null)
			bees = new ArrayList<>(DEFAULT_BEES_SIZE);
		return bees;
	}

	@Override
	public void removeBee(Bee bee) {
		if (!hasBees())
			return;
		final List<Occupant> list = bees;
		final int size = bees.size();
		for (int i = 0; i < size; i++) {
			Occupant o = list.get(i);
			if (o.getBee().equals(bee)) {
				list.remove(i);
				return;
			}
		}
	}

	@Override
	public void addBee(Bee bee) {
		getBees().add(new Occupant(bee));
	}

	@Override
	public int getBeeCount() {
		return bees == null ? 0 : bees.size();
	}

}
