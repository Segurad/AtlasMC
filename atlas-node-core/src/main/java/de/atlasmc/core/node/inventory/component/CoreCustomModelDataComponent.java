package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.CustomModelDataComponent;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class CoreCustomModelDataComponent extends AbstractItemComponent implements CustomModelDataComponent {
	
	private FloatList floats;
	private BooleanList flags;
	private List<String> strings;
	private IntList colors;
	
	public CoreCustomModelDataComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCustomModelDataComponent clone() {
		CoreCustomModelDataComponent clone = (CoreCustomModelDataComponent) super.clone();
		if (floats != null)
			clone.floats = new FloatArrayList(floats);
		if (flags != null)
			clone.flags = new BooleanArrayList(flags);
		if (strings != null)
			clone.strings = new ArrayList<>(strings);
		if (colors != null)
			clone.colors = new IntArrayList(colors);
		return clone;
	}

	@Override
	public FloatList getFloats() {
		if (floats == null)
			floats = new FloatArrayList();
		return floats;
	}

	@Override
	public void setFloats(FloatList floats) {
		FloatList current = getFloats();
		current.clear();
		current.addAll(floats);
	}

	@Override
	public BooleanList getFlags() {
		if (flags == null)
			flags = new BooleanArrayList();
		return flags;
	}

	@Override
	public void setFlags(BooleanList flags) {
		BooleanList current = getFlags();
		current.clear();
		current.addAll(flags);
	}

	@Override
	public List<String> getStrings() {
		if (strings == null)
			strings = new ArrayList<>();
		return strings;
	}

	@Override
	public void setStrings(List<String> strings) {
		List<String> current = getStrings();
		current.clear();
		current.addAll(strings);
	}

	@Override
	public IntList getColors() {
		if (colors == null)
			colors = new IntArrayList();
		return colors;
	}

	@Override
	public void setColors(IntList colors) {
		IntList current = getColors();
		current.clear();
		current.addAll(colors);
	}

	@Override
	public boolean hasFloats() {
		return floats != null && !floats.isEmpty();
	}

	@Override
	public boolean hasFlags() {
		return flags != null && !flags.isEmpty();
	}

	@Override
	public boolean hasStrings() {
		return strings != null && !strings.isEmpty();
	}

	@Override
	public boolean hasColors() {
		return colors != null && !colors.isEmpty();
	}

}
