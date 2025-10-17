package de.atlasmc.node.attribute;

import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public class AttributeModifier implements Cloneable, NBTSerializable {

	public static final NBTCodec<AttributeModifier>
	NBT_HANDLER = NBTCodec
					.builder(AttributeModifier.class)
					.defaultConstructor(AttributeModifier::new)
					.doubleField("amount", AttributeModifier::getAmount, AttributeModifier::setAmount, 0)
					.namespacedKey("id", AttributeModifier::getID, AttributeModifier::setID)
					.enumStringField("operation", AttributeModifier::getOperation, AttributeModifier::setOperation, Operation.class, Operation.ADD_VALUE)
					.enumStringField("slot", AttributeModifier::getSlot, AttributeModifier::setSlot, EquipmentSlot.class, EquipmentSlot.ANY)
					// TODO display
					.build();
	
	private double amount;
	private Operation operation = Operation.ADD_VALUE;
	private NamespacedKey id;
	private EquipmentSlot slot = EquipmentSlot.ANY;
	
	private AttributeModifier() {
		// for internal use
	}
	
	public AttributeModifier(NamespacedKey id, double amount, Operation operation) {
		this(id, amount, operation, EquipmentSlot.ANY);
	}
	
	public AttributeModifier(NamespacedKey id, double amount, Operation operation, EquipmentSlot slot) {
		this.amount = amount;
		this.id = id;
		if (operation != null)
			this.operation = operation;
		if (slot != null)
			this.slot = slot;
	}
	
	public void setID(NamespacedKey id) {
		this.id = id;
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
	
	public static enum Operation implements IDHolder, EnumName {
		/**
		 * Adds the value to the base value of the attribute
		 */
		ADD_VALUE("add_value"),
		/**
		 * Multiplies the base value
		 */
		ADD_MULTIPLIED_BASE("add_multiplied_base"),
		/**
		 * Multiples the attribute with the value
		 */
		ADD_MULTIPLIED_TOTAL("add_multiplied_total");

		private String name;
		
		private Operation(String name) {
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

	public NamespacedKey getID() {
		return id;
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
	
	@Override
	public AttributeModifier clone() {
		AttributeModifier clone = null;
		try {
			clone = (AttributeModifier) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((slot == null) ? 0 : slot.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeModifier other = (AttributeModifier) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (operation != other.operation)
			return false;
		return slot == other.slot;
	}

	@Override
	public NBTCodec<? extends AttributeModifier> getNBTCodec() {
		return NBT_HANDLER;
	}

}
