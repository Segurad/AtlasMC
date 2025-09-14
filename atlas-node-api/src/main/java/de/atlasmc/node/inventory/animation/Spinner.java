package de.atlasmc.node.inventory.animation;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.atlasmc.node.entity.HumanEntity;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.DummyTask;

public class Spinner extends AbstractAnimation {

	private final boolean nextrandom;
	int index = 0;

	public Spinner(int[] slots, ItemStack[] items, int delay, Sound sound, boolean nextrandom, int randomspinns,
			int stopstpisns) {
		super(slots, items, delay, sound);
		this.nextrandom = nextrandom;
	}

	@Override
	public void play(Inventory inv) {
		TimerTask task = new TimerTask() {
			Timer timer = new Timer();
			boolean started = false;
			int index = 0;

			@Override
			public void run() {
				if (!started) {
					start();
					return;
				}
				update(inv);
				index++;
				if (index < slots.length)
					timer.schedule(new DummyTask(this), delay * 50);
			}

			private void start() {
				started = true;
				timer.schedule(new DummyTask(this), delay * 50);
			}
		};
		new DummyTask(task).run();
	}

	@Override
	public Animation clone() {
		return super.clone();
	}

	protected void update(Inventory inv) {
		for (int i = 0; i < slots.length - 1; i++) {
			inv.setItem(slots[i], inv.getItem(slots[i + 1]));
		}
		if (nextrandom) {
			inv.setItem(slots[slots.length - 1], items[new Random().nextInt(items.length)]);
		} else {
			inv.setItem(slots[slots.length - 1], items[index]);
			index++;
			if (items.length <= index)
				index = 0;
		}
		if (sound != null) {
			for (HumanEntity e : inv.getViewers()) {
				if (!(e instanceof Player))
					continue;
				Player p = (Player) e;
				p.playSound(p, sound, 1, 1);
			}
		}
	}

}
