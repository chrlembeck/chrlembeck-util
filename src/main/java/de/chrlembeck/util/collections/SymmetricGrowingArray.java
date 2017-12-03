package de.chrlembeck.util.collections;

import java.util.function.IntFunction;

public class SymmetricGrowingArray<T> {

    private final IntFunction<T[]> arrayProducer;

    private T[] array;

    public SymmetricGrowingArray(final IntFunction<T[]> arrayProducer) {
        this.arrayProducer = arrayProducer;
        this.array = arrayProducer.apply(1);
    }

    public T get(final int index) {
        final int internalIndex = mapIndex(index);
        return internalIndex >= array.length? null:array[internalIndex];
    }

    public T put(final int index, final T newValue) {
        final int internalIndex = mapIndex(index);
        checkSize(internalIndex+1);
        final T result = array[internalIndex];
        array[internalIndex] = newValue;
        return result;
    }

    private void checkSize(final int neededSize) {
        if (array.length < neededSize) {
            final T[] newArray = arrayProducer.apply(neededSize);
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    private static int mapIndex(final int index) {
        return (index >= 0) ? index << 1: ((-index)<<1)-1;
    }

    public int[] getSize() {
        return new int[] {-array.length/2, (array.length-1)/2};
    }
}