package de.atlascore.block.data.type;

import java.util.HashSet;
import java.util.Set;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.BrewingStand;

public class CoreBrewingStand extends CoreBlockData implements BrewingStand {

	private boolean[] bottles;
	
	public CoreBrewingStand(Material material) {
		super(material);
		bottles = new boolean[3];
	}

	@Override
	public Set<Integer> getBottles() {
		Set<Integer> s = new HashSet<>(getMaxBottles());
		for (int i = 0; i < bottles.length; i++) {
			if (bottles[i]) s.add(i);
		}
		return s;
	}

	@Override
	public int getMaxBottles() {
		return 3;
	}

	@Override
	public boolean hasBottle(int bottle) {
		return bottles[bottle];
	}

	@Override
	public void setBottle(int bottle, boolean has) {
		bottles[bottle] = has;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(bottles[0]?0:1)+
				(bottles[1]?0:2)+
				(bottles[2]?0:4);
	}

}
