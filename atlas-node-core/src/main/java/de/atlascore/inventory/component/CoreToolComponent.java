package de.atlascore.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ToolComponent;

public class CoreToolComponent extends AbstractItemComponent implements ToolComponent {

	private float defaultMinigSpeed;
	private int damagePerBlock;
	private boolean canDestroyBlockInCreative;
	private List<Rule> rules;
	
	public CoreToolComponent(ComponentType type) {
		super(type);
	}

	@Override
	public float getDefaultMinigSpeed() {
		return defaultMinigSpeed;
	}

	@Override
	public void setDefaultMinigSpeed(float speed) {
		this.defaultMinigSpeed = speed;
	}

	@Override
	public int getDamagePerBlock() {
		return damagePerBlock;
	}

	@Override
	public void setDamagePerBlock(int damage) {
		this.damagePerBlock = damage;
	}

	@Override
	public boolean canDestroyBlocksInCreative() {
		return canDestroyBlockInCreative;
	}

	@Override
	public void setDestroyBlockInCreative(boolean can) {
		this.canDestroyBlockInCreative = can;
	}

	@Override
	public List<Rule> getRules() {
		if (rules == null)
			rules = new ArrayList<>();
		return rules;
	}

	@Override
	public boolean hasRules() {
		return rules != null && !rules.isEmpty();
	}
	
	@Override
	public CoreToolComponent clone() {
		CoreToolComponent clone = (CoreToolComponent) super.clone();
		if (hasRules()) {
			clone.rules = new ArrayList<>(rules.size());
			for (Rule rule : rules) {
				clone.rules.add(rule.clone());
			}
		}
		return clone;
	}

}
