package de.atlasmc.attribute;

import java.util.List;
import java.util.UUID;

import de.atlasmc.inventory.EquipmentSlot;

public class AttributeModifier {

	private double amount;
	private Operation operation;
	private final UUID uuid;
	private final String name;
	private EquipmentSlot slot;
	
	public AttributeModifier(String name, double amount, Operation operation) {
		this(UUID.randomUUID(), name, amount, operation, null);
	}
	
	public AttributeModifier(UUID uuid, String name, double amount, Operation operation) {
		this(uuid, name, amount, operation, null);
	}
	
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

		private static List<Operation> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Operation getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Operation> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
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
