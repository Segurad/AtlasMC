package de.atlasmc.inventory.component;

import java.util.List;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ToolComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:tool");
	
	public static final NBTSerializationHandler<ToolComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ToolComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(COMPONENT_KEY)
					.floatField("default_mining_speed", ToolComponent::getDefaultMinigSpeed, ToolComponent::setDefaultMinigSpeed, 1)
					.intField("damage_per_block", ToolComponent::getDamagePerBlock, ToolComponent::setDamagePerBlock, 1)
					.boolField("can_destroy_blocks_in_creative", ToolComponent::canDestroyBlocksInCreative, ToolComponent::setDestroyBlockInCreative, true)
					.typeList("rules", ToolComponent::hasRules, ToolComponent::getRules, Rule.NBT_HANDLER)
					.endComponent()
					.build();
	
	float getDefaultMinigSpeed();
	
	void setDefaultMinigSpeed(float speed);
	
	int getDamagePerBlock();
	
	void setDamagePerBlock(int damage);
	
	boolean canDestroyBlocksInCreative();
	
	void setDestroyBlockInCreative(boolean can);
	
	List<Rule> getRules();
	
	boolean hasRules();
	
	Rule createRule();
	
	void addRule(Rule rule);
	
	void removeRule(Rule rule);
	
	@Override
	default NBTSerializationHandler<? extends ToolComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static class Rule implements NBTSerializable {
		
		public static final NBTSerializationHandler<Rule>
		NBT_HANDLER = NBTSerializationHandler
						.builder(Rule.class)
						.defaultConstructor(Rule::new)
						.dataSetField("blocks", Rule::getBlocks, Rule::setBlocks, ItemType.getRegistry())
						.floatField("speed", Rule::getSpeed, Rule::setSpeed)
						.boolField("correct_for_drops", Rule::isCorrectForDrops, Rule::setCorrectForDrops, false)
						.build();
		
		private DataSet<ItemType> blocks;
		private float speed;
		private boolean correctForDrops;
		
		public DataSet<ItemType> getBlocks() {
			return blocks;
		}
		
		public void setBlocks(DataSet<ItemType> blocks) {
			this.blocks = blocks;
		}
		
		public float getSpeed() {
			return speed;
		}
		
		public void setSpeed(float speed) {
			this.speed = speed;
		}
		
		public boolean isCorrectForDrops() {
			return correctForDrops;
		}
		
		public void setCorrectForDrops(boolean correctForDrops) {
			this.correctForDrops = correctForDrops;
		}
		
		@Override
		public NBTSerializationHandler<? extends NBTSerializable> getNBTHandler() {
			return NBT_HANDLER;
		}
		
	}

}
