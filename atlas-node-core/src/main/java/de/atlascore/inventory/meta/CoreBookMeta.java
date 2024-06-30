package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.BookMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBookMeta extends CoreItemMeta implements BookMeta {
	
	protected static final CharKey
	NBT_AUTHOR = CharKey.literal("author"),
	NBT_GENERATION = CharKey.literal("generation"),
	NBT_PAGES = CharKey.literal("pages"),
	NBT_RESOLVED = CharKey.literal("resolved"),
	NBT_TITLE = CharKey.literal("title");
	
	static {
		NBT_FIELDS.setField(NBT_AUTHOR, (holder, reader) -> {
			if (holder instanceof BookMeta) {
				((BookMeta) holder).setAuthor(reader.readStringTag());
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_GENERATION, (holder, reader) -> {
			if (holder instanceof BookMeta) {
				((BookMeta) holder).setGeneration(Generation.getByID(reader.readIntTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_PAGES, (holder, reader) -> {
			if (holder instanceof BookMeta) {
				BookMeta meta = ((BookMeta) holder);
				while(reader.getRestPayload() > 0) {
					meta.addPage(reader.readStringTag());
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_RESOLVED, (holder, reader) -> {
			if (holder instanceof BookMeta) {
				((BookMeta) holder).setResolved(reader.readByteTag() == 1);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_TITLE, (holder, reader) -> {
			if (holder instanceof BookMeta) {
				((BookMeta) holder).setTitle(reader.readStringTag());
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private String author;
	private String title;
	private Generation generation;
	private List<String> pages;
	private boolean resolved;
	
	public CoreBookMeta(Material material) {
		super(material);
		if (material == Material.WRITTEN_BOOK) {
			title = "";
			author = "";
			generation = Generation.ORGINAL;
		}
	}

	@Override
	public Generation getGeneration() {
		return generation;
	}

	@Override
	public boolean hasGeneration() {
		return generation != null;
	}

	@Override
	public void setGeneration(Generation generation) {
		this.generation = generation;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public boolean hasAuthor() {
		return author != null;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public boolean hasTitle() {
		return title != null;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public List<String> getPages() {
		if (pages == null) pages = new ArrayList<>();
		return pages;
	}

	@Override
	public void addPage(String... pages) {
		for (String page : pages) {
			getPages().add(page);
		}
	}

	@Override
	public void setPage(int page, String data) {
		if (getPageCount() > page && page >= 0)
			getPages().set(page, data);
	}

	@Override
	public void setPage(List<String> pages) {
		getPages().clear();
		getPages().addAll(pages);
	}

	@Override
	public boolean hasPages() {
		return pages != null && !pages.isEmpty();
	}

	@Override
	public String getPage(int page) {
		if (getPageCount() > page && page >= 0)
			return getPages().get(page);
		return null;
	}

	@Override
	public int getPageCount() {
		return hasPages() ? pages.size() : 0;
	}

	@Override
	public boolean isResolved() {
		return resolved;
	}

	@Override
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasAuthor()) writer.writeStringTag(NBT_AUTHOR, author);
		if (hasGeneration()) writer.writeIntTag(NBT_GENERATION, generation.ordinal());
		if (hasPages()) {
			writer.writeListTag(NBT_PAGES, TagType.STRING, pages.size());
			for (String s : pages) {
				writer.writeStringTag(null, s);
			}
		}
		if (isResolved()) writer.writeByteTag(NBT_RESOLVED, 1);
		if (hasTitle()) writer.writeStringTag(NBT_TITLE, title);
	}
	
	@Override
	public CoreBookMeta clone() {
		CoreBookMeta clone = (CoreBookMeta) super.clone();
		if (clone == null)
			return null;
		if (hasPages())
			clone.setPage(new ArrayList<>(pages));
		return clone;
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		BookMeta bookMeta = (BookMeta) meta;
		if (hasAuthor() != bookMeta.hasAuthor())
			return false;
		if (hasAuthor() && !getAuthor().equals(bookMeta.getAuthor()))
			return false;
		if (hasTitle() != bookMeta.hasTitle())
			return false;
		if (hasTitle() && !getTitle().equals(bookMeta.getTitle()))
			return false;
		if (getGeneration() != bookMeta.getGeneration())
			return false;
		if (isResolved() != bookMeta.isResolved())
			return false;
		if (hasPages() != hasPages())
			return false;
		if (hasPages() && !getPages().equals(bookMeta.getPages()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((generation == null) ? 0 : generation.hashCode());
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
		result = prime * result + (resolved ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

}
