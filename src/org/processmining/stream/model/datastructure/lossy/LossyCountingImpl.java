package org.processmining.stream.model.datastructure.lossy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.processmining.stream.model.datastructure.AbstractDataStructure;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterFactory;

import gnu.trove.set.hash.THashSet;

/**
 * Lossy bucket algorithm, based on: Approximate Frequency Counts over Data
 * Streams; Manku et al.; 2002;
 * 
 * @author svzelst
 *
 * @param <T>
 */
public class LossyCountingImpl<T> extends AbstractDataStructure<T, LossyCountingParameterDefinition>
		implements LossyCounting<T> {

	protected final Map<T, Integer> buckets = new ConcurrentHashMap<>();
	protected int bucketSize;
	protected int currentBucket = 1;
	protected double epsilon;

	protected final Map<T, Integer> frequencies = new ConcurrentHashMap<>();

	protected int items = 0;

	protected Object mutex = new Object();

	public LossyCountingImpl(Map<LossyCountingParameterDefinition, DSParameter<?>> params) {
		this(params, (Integer) params.get(LossyCountingParameterDefinition.SIZE).getValue());
	}

	private LossyCountingImpl(Map<LossyCountingParameterDefinition, DSParameter<?>> params, int windowSize) {
		super(params);
		setBucketSize(windowSize);
	}

	public Collection<T> add(T e) {
		if (frequencies.containsKey(e)) {
			frequencies.put(e, frequencies.get(e) + 1);
		} else {
			frequencies.put(e, 1);
		}
		if (!buckets.containsKey(e)) {
			buckets.put(e, currentBucket - 1);
		}
		items++;
		if (items % bucketSize == 0) {
			return clean();
		}
		return new HashSet<>();
	}

	protected Collection<T> clean() {
		return clean(frequencies.keySet());
	}

	protected Collection<T> clean(Set<T> candidates) {
		final Set<T> toRemove = new THashSet<>();
		for (T t : candidates) {
			if (frequencies.get(t) + buckets.get(t) <= currentBucket) {
				toRemove.add(t);
			}
		}
		for (T t : toRemove) {
			remove(t);
		}
		currentBucket++;
		return toRemove;
	}

	public void clear() {
		frequencies.clear();
		buckets.clear();
	}

	public boolean contains(Object o) {
		return frequencies.keySet().contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return frequencies.keySet().contains(c);
	}

	@Override
	public int getBucketOf(T e) {
		int result = 0;
		if (buckets.containsKey(e)) {
			result = buckets.get(e);
		}
		return result;
	}

	@Override
	public int getBucketSize() {
		return bucketSize;
	}

	@Override
	public int getCurrentBucket() {
		return currentBucket;
	}

	@Override
	public long getFrequencyOf(T e) {
		int result = 0;
		if (frequencies.containsKey(e) && buckets.containsKey(e)) {
			result = frequencies.get(e) + buckets.get(e);
		}
		return result;
	}

	@Override
	public double getMaximumApproximationError() {
		return epsilon;
	}

	public boolean isEmpty() {
		return frequencies.isEmpty();
	}

	public Iterator<T> iterator() {
		return frequencies.keySet().iterator();
	}

	protected void remove(T t) {
		frequencies.remove(t);
		buckets.remove(t);
	}

	protected double setBucketSize(int bucketSize) {
		assert (bucketSize > 0);
		this.bucketSize = bucketSize;
		epsilon = 1d / bucketSize;
		getParameters().put(LossyCountingParameterDefinition.SIZE, DSParameterFactory.createParameter(bucketSize));
		return epsilon;
	}

	protected int setMaximumApproximationError(double epsilon) {
		assert (epsilon > 0 && epsilon <= 1);
		this.epsilon = epsilon;
		bucketSize = (int) (1 / epsilon);
		getParameters().put(LossyCountingParameterDefinition.SIZE, DSParameterFactory.createParameter(bucketSize));
		return bucketSize;
	}

	@Override
	public String toString() {
		String str = "{";
		for (T elem : frequencies.keySet()) {
			str += "(" + elem.toString() + ", " + frequencies.get(elem) + ", " + buckets.get(elem) + "), ";
		}
		if (str.length() > 2) {
			str = str.substring(0, str.length() - 2);
		}
		str += "}";
		return str;
	}

	public Type getType() {
		return Type.LOSSY_COUNTING;
	}

	public long getSize() {
		return frequencies.size();
	}

	public int getCapacity() {
		return bucketSize;
	}
}
