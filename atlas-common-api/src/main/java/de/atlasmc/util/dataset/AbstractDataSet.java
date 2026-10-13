package de.atlasmc.util.dataset;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.function.Predicate;

import de.atlasmc.NamespacedKey.Namespaced;

public abstract class AbstractDataSet<T extends Namespaced> extends AbstractCollection<T> implements DataSet<T> {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DataSet<?> dataSet))
			return false;
		if (size() != dataSet.size())
			return false;
		return containsAll(dataSet);
	}
	
	@Override
	public abstract int hashCode();
	
    @Override 
    public boolean addAll(Collection<? extends T> c) { 
    	throw new UnsupportedOperationException(); 
    }
    
    @Override 
    public void clear() { 
    	throw new UnsupportedOperationException(); 
    }
    
    @Override 
    public boolean remove(Object o) { 
    	throw new UnsupportedOperationException(); 
    }
    
    @Override 
    public boolean removeAll(Collection<?> c) { 
    	throw new UnsupportedOperationException(); 
    }
    
    @Override 
    public boolean removeIf(Predicate<? super T> filter) { 
    	throw new UnsupportedOperationException(); 
    }
    
    @Override 
    public boolean retainAll(Collection<?> c) { 
    	throw new UnsupportedOperationException(); 
    }

}
