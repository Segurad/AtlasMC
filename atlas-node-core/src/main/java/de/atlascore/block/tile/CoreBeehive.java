package de.atlascore.block.tile;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3i;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Beehive;
import de.atlasmc.entity.Bee;

public class CoreBeehive extends CoreTileEntity implements Beehive {
	
	private Vector3i flower;
	private List<Occupant> bees;
	
	public CoreBeehive(BlockType type) {
		super(type);
		flower = new Vector3i();
	}
	
	@Override
	public List<Occupant> getBees() {
		if (bees == null) 
			bees = new ArrayList<>();
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
		if (bees == null) 
			bees = new ArrayList<>();
		bees.add(new Occupant(bee));
	}
	
	@Override
	public int getBeeCount() {
		if (bees == null) 
			return 0;
		return bees.size();
	}
	
	@Override
	public Beehive clone() {
		CoreBeehive hive = (CoreBeehive) super.clone();
		hive.flower = new Vector3i(flower);
		if (bees != null) {
			hive.bees = new ArrayList<>(bees.size());
			// TODO clone bees
		}
		return hive;
	}

	@Override
	public Vector3i getFlowerPosUnsafe() {
		return flower;
	}

	@Override
	public Vector3i getFlowerPos(Vector3i loc) {
		return loc.set(flower);
	}

	@Override
	public void setFlowerPos(Vector3i loc) {
		flower.set(loc);
	}

	@Override
	public boolean hasBees() {
		return bees != null && !bees.isEmpty();
	}

}
