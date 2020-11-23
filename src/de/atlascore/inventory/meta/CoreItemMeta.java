package de.atlascore.inventory.meta;

import java.io.DataOutput;
import java.util.List;
import java.util.Map;

import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.ItemFlag;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.NBT;

public class CoreItemMeta implements ItemMeta {
	
	public CoreItemMeta(Material material) {

	}
	
	public CoreItemMeta clone() {
		try {
			return (CoreItemMeta) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NBT toNBT() {
		return null;
	}

	@Override
	public DataOutput toNBT(DataOutput out) {
		return out;
	}

	@Override
	public void setDisplayName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLore(List<String> lore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEnchant(Enchantment enchantment, int level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addItemFlags(ItemFlag... flags) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasDisplayName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEnchants() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<Enchantment, Integer> getEnchants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEnchantLevel(Enchantment enchantment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasLore() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getLore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomModelData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getCustomModelData() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ItemFlag> getItemFlags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasItemFlag(ItemFlag flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAttributeModifiers() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<Attribute, List<AttributeModifier>> getAttributeModifiers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AttributeModifier> getAttributeModifiers(Attribute attribute) {
		// TODO Auto-generated method stub
		return null;
	}
}
