package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.node.enchantments.Enchantment;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface AbstractEnchantmentComponent extends ItemComponent {
	
	public static final StreamCodec<AbstractEnchantmentComponent>
	STREAM_CODEC = StreamCodec
					.builder(AbstractEnchantmentComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.mapRegistryValueToVarInt(AbstractEnchantmentComponent::hasEnchants, AbstractEnchantmentComponent::getStoredEnchants, Enchantment.REGISTRY_KEY)
					.build();
	
	void addEnchant(Enchantment ench, int level);
	
	int getEnchantLevel(Enchantment ench);
	
	Object2IntMap<Enchantment> getStoredEnchants();
	
	boolean hasConflictingEnchant(Enchantment ench);
	
	boolean hasEnchants();
	
	boolean hasEnchant(Enchantment ench);
	
	void removeEnchant(Enchantment ench);
	
	AbstractEnchantmentComponent clone();
	
	@Override
	default StreamCodec<? extends AbstractEnchantmentComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
