package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.Instrument;
import de.atlasmc.node.inventory.component.InstrumentComponent;

public class CoreInstrumentComponent extends AbstractItemComponent implements InstrumentComponent {
	
	private Instrument instrument;
	
	public CoreInstrumentComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreInstrumentComponent clone() {
		return (CoreInstrumentComponent) super.clone();
	}

	@Override
	public Instrument getInstrument() {
		return instrument;
	}

	@Override
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

}
