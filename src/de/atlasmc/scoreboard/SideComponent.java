package de.atlasmc.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class SideComponent implements Component {

	protected final ArrayList<String> oldtext;
	private final ArrayList<TextComponent> texts;
	protected Objective obj;
	private final Scoreboard board;
	private final PlayerScoreboard source;
	private final String name;
	private String display;
	private boolean unregistered;
	
	public SideComponent(PlayerScoreboard playerScoreboard, String name, String display) {
		this.board = playerScoreboard.getScoreboard();
		this.source = playerScoreboard;
		this.name = name; this.display = display;
		texts = new ArrayList<>();
		oldtext = new ArrayList<>();
	}

	public void update() {
		if (unregistered) throw new Error("Component is not registered");
		if (obj == null) return;
		oldtext.forEach(l -> board.resetScores(l));
		oldtext.clear();
		final int size = texts.size();
		for (int i = 0; i < size; i++) {
			final String text = texts.get(i).getText();
			obj.getScore(text).setScore(i);
			oldtext.add(0, text);
		}
	}
	
	public void updateLine(int line) {
		if (unregistered) throw new Error("Component is not registered");
		if (obj == null) return;
		if (line < 0 || line >= texts.size()) return;
		board.resetScores(oldtext.get(line));
		final String text = texts.get(line).getText();
		obj.getScore(text).setScore(line);
		oldtext.set(line, text);
	}
	
	public void addLine(TextComponent value) {
		if (unregistered) throw new Error("Component is not registered");
		texts.add(0, value);
		update();
	}
	
	public void addLine(TextComponent value, int index) {
		if (unregistered) throw new Error("Component is not registered");
		texts.add(index, value);
		update();
	}
	
	public void removeLine(int index) {
		if (unregistered) throw new Error("Component is not registered");
		texts.remove(index);
		update();
	}
	
	public List<TextComponent> getLines() {
		if (unregistered) throw new Error("Component is not registered");
		return texts;
	}
	
	public String getDisplayName() {
		if (unregistered) throw new Error("Component is not registered");
		return display;
	}
	
	public void setDisplayName(String value) {
		if (unregistered) throw new Error("Component is not registered");
		display = value;
		if (obj != null) obj.setDisplayName(value);
	}
	
	public String getName() {
		if (unregistered) throw new Error("Component is not registered");
		return name;
	}

	public void unregister() {
		if (unregistered) return;
		unregistered = true;
		oldtext.forEach(l -> board.resetScores(l));
		obj.unregister();
		source.unregisterSide(this);
	}

	@Override
	public boolean isActive() {
		if (unregistered) throw new Error("Component is not registered");
		return source.getActiveSideComponent() == this;
	}

	@Override
	public void setActiv(boolean value) {
		if (unregistered) throw new Error("Component is not registered");
		source.setActiveSideComponent(value ? this : null);
		if (value) {
			obj = board.registerNewObjective(name, "dummy", display);
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			update();
		} else {
			oldtext.forEach(l -> board.resetScores(l));
			obj.unregister();
			obj = null;
			oldtext.clear();
		}
	}

	@Override
	public final boolean isRegistered() {
		return !unregistered;
	}

	@Override
	public final PlayerScoreboard getScoreboard() {
		return source;
	}
	
	
}
