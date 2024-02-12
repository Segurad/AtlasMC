package de.atlasmc.tick;

import java.util.ArrayList;

public class TickPipeline<T> {
	
	private final ArrayList<Ticker<? extends T>> tickers;
	
	public TickPipeline() {
		tickers = new ArrayList<>();
	}
	
	public TickPipeline(int capacity) {
		tickers = new ArrayList<>(capacity);
	}
	
	public void addFirst(Ticker<? extends T> ticker) {
		tickers.add(0, ticker);
	}
	
	public void addBefor(Class<? extends Ticker<T>> befor, Ticker<T> ticker) {
		for (int i = 0; i < tickers.size(); i++) {
			if (!befor.isInstance(tickers.get(i))) continue;
			tickers.add(i, ticker);
			return;
		}
		tickers.add(ticker);
	}
	
	public void addAfter(Class<? extends Ticker<?>> after, Ticker<T> ticker) {
		for (int i = 0; i < tickers.size(); i++) {
			if (!after.isInstance(tickers.get(i))) continue;
			tickers.add(i+1, ticker);
			return;
		}
		tickers.add(ticker);
	}
	
	public void addLast(Ticker<T> ticker) {
		tickers.add(ticker);
	}
	
	public void remove(Ticker<T> ticker) {
		tickers.remove(ticker);
	}
	
	public void remove(Class<? extends Ticker<T>> clazz) {
		for (Ticker<?> t : tickers) {
			if (!clazz.isInstance(t)) continue;
			tickers.remove(t);
			break;
		}
	}
 
}
