package de.atlasmc.inventory.animation;

import java.util.Timer;
import java.util.TimerTask;

import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.entity.HumanEntity;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.DummyTask;

public final class PlopIn extends AbstractAnimation {

	public PlopIn(int[] slots, ItemStack[] items, int delay, Sound sound) {
		super(slots, items, delay, sound);
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
				inv.setItem(slots[index], items[index]);
				if (sound != null) {
					for (HumanEntity e : inv.getViewers()) {
						if (!(e instanceof Player))
							continue;
						Player p = (Player) e;
						p.playSound(p, sound, SoundCategory.MASTER, 1, 1, 0); // TODO sound seeding
					}
				}
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
}
