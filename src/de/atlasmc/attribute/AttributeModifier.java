package de.atlasmc.attribute;

import java.util.UUID;

import de.atlasmc.inventory.EquipmentSlot;

public class AttributeModifier {

	private double amount;
	private Operation operation;
	
	public EquipmentSlot getSlot() {
		return null;
	}
	
	public double getAmount() {
		return amount;
	}

	public Operation getOperation() {
		return operation;
	}
	
	public static enum Operation {
		ADD_NUMBER,
		ADD_SCALAR,
		MULTIPLY_SCALAR_1
	}

	public UUID getUUID() {
		// TODO Auto-generated method stub
		return null;
	}

}
