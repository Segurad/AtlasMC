package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.BookMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBookMeta extends CoreItemMeta implements BookMeta {

	private String author, title;
	private Generation generation;
	private List<String> pages;
	private boolean resolved;
	
	protected static final String
		AUTHOR = "author",
		GENERATION = "generation",
		PAGES = "pages",
		RESOLVED = "resolved",
		TITLE = "title";
	
	static {
		NBT_FIELDS.setField(AUTHOR, (holder, reader) -> {
			if (BookMeta.class.isInstance(holder)) {
				((BookMeta) holder).setAuthor(reader.readStringTag());
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(GENERATION, (holder, reader) -> {
			if (BookMeta.class.isInstance(holder)) {
				((BookMeta) holder).setGeneration(Generation.getByID(reader.readIntTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(PAGES, (holder, reader) -> {
			if (BookMeta.class.isInstance(holder)) {
				BookMeta meta = ((BookMeta) holder);
				while(reader.getRestPayload() > 0) {
					meta.addPage(reader.readStringTag());
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(RESOLVED, (holder, reader) -> {
			if (BookMeta.class.isInstance(holder)) {
				((BookMeta) holder).setResolved(reader.readByteTag() == 1);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(TITLE, (holder, reader) -> {
			if (BookMeta.class.isInstance(holder)) {
				((BookMeta) holder).setTitle(reader.readStringTag());
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
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
	public void toNBT(NBTWriter writer, String local, boolean systemData) throws IOException {
		super.toNBT(writer, local, systemData);
		if (hasAuthor()) writer.writeStringTag(AUTHOR, author);
		if (hasGeneration()) writer.writeIntTag(GENERATION, generation.ordinal());
		if (hasPages()) {
			writer.writeListTag(PAGES, TagType.STRING, pages.size());
			for (String s : pages) {
				writer.writeStringTag(null, s);
			}
		}
		if (isResolved()) writer.writeByteTag(RESOLVED, 1);
		if (hasTitle()) writer.writeStringTag(TITLE, title);
	}

}
