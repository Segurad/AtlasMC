package de.atlasmc.attribute;

import java.util.UUID;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.util.Validate;

public class AttributeModifier {

	private double amount;
	private Operation operation;
	
	public AttributeModifier(UUID uuid, String name, double amount, Operation operation, EquipmentSlot slot) {
		// TODO Auto-generated constructor stub
	}

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
		MULTIPLY_SCALAR_1;

		public static Operation byID(int id) {
			Validate.isTrue(id >= 0 && id < 3, "ID is not between 0 and 2: " + id);
			Operation[] ops = Operation.values();
			return ops[id];
		}
	}

	public UUID getUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
