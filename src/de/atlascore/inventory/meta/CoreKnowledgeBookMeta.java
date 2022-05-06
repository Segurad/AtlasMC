package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.KnowledgeBookMeta;
import de.atlasmc.recipe.Recipe;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreKnowledgeBookMeta extends CoreItemMeta implements KnowledgeBookMeta {
	
	protected static final CharKey NBT_RECIPES = CharKey.of("Recipes");
	
	static {
		NBT_FIELDS.setField(NBT_RECIPES, (holder, reader) -> {
			if (holder  instanceof KnowledgeBookMeta) {
				List<Recipe> recipes = ((KnowledgeBookMeta) holder).getRecipes();
				while(reader.getRestPayload() > 0) {
					recipes.add(Recipe.getByName(reader.readStringTag()));
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private List<Recipe> recipes;
	
	public CoreKnowledgeBookMeta(Material material) {
		super(material);
	}

	@Override
	public void addRecipe(Recipe... recipes) {
		if (this.recipes == null) this.recipes = new ArrayList<Recipe>();
		for (Recipe r : recipes) {
			this.recipes.add(r);
		}
	}

	@Override
	public CoreKnowledgeBookMeta clone() {
		CoreKnowledgeBookMeta clone = (CoreKnowledgeBookMeta) super.clone();
		if (clone == null)
			return null;
		if (hasRecipes()) 
			clone.setRecipes(new ArrayList<Recipe>(recipes));
		return clone;
	}

	@Override
	public List<Recipe> getRecipes() {
		if (recipes == null) this.recipes = new ArrayList<Recipe>();
		return recipes;
	}

	@Override
	public boolean hasRecipes() {
		return recipes != null && !recipes.isEmpty();
	}

	@Override
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasRecipes()) {
			writer.writeListTag(NBT_RECIPES, TagType.STRING, recipes.size());
			for (Recipe r : recipes) {
				writer.writeStringTag(null, r.getNamespacedName());
			}
		}
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		KnowledgeBookMeta bookMeta = (KnowledgeBookMeta) meta;
		if (hasRecipes() != bookMeta.hasRecipes())
			return false;
		if (hasRecipes() && !getRecipes().equals(bookMeta.getRecipes()))
			return true;
		return true;
	}

}
