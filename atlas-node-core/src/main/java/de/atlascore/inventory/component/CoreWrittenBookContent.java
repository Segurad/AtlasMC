package de.atlascore.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.WrittenBookContentComponent;

public class CoreWrittenBookContent extends AbstractItemComponent implements WrittenBookContentComponent {

	private String title;
	private String author;
	private List<Chat> pages;
	private Generation generation = Generation.ORGINAL;
	private boolean resolved;
	
	public CoreWrittenBookContent(ComponentType type) {
		super(type);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public List<Chat> getPages() {
		if (pages == null)
			pages = new ArrayList<>();
		return pages;
	}

	@Override
	public boolean hasPages() {
		return pages != null && !pages.isEmpty();
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public Generation getGeneration() {
		return generation;
	}

	@Override
	public void setGeneration(Generation generation) {
		if (generation == null)
			throw new IllegalArgumentException("Generation can not be null!");
		this.generation = generation;
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
	public CoreWrittenBookContent clone() {
		CoreWrittenBookContent clone = (CoreWrittenBookContent) super.clone();
		if (hasPages()) {
			clone.pages = new ArrayList<>(pages.size());
			for (Chat page : pages) {
				clone.pages.add(page.clone());
			}
		}
		return clone;
	}

}
