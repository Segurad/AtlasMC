package de.atlasmc.attribute;

import java.util.UUID;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.util.Validate;

public class AttributeModifier {

	private double amount;
	private Operation operation;
	private final UUID uuid;
	private final String name;
	private EquipmentSlot slot;
	
	public AttributeModifier(UUID uuid, String name, double amount, Operation operation, EquipmentSlot slot) {
		this.amount = amount;
		this.uuid = uuid;
		this.name = name;
		this.operation = operation;
		this.slot = slot;
	}

	public EquipmentSlot getSlot() {
		return slot;
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
		return uuid;
	}

	public String getName() {
		return name;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public void setSlot(EquipmentSlot slot) {
		this.slot = slot;
	}

}
