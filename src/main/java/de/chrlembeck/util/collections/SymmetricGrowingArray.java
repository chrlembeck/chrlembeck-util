package de.chrlembeck.util.collections;

import java.util.function.IntFunction;

public class SymmetricGrowingArray<T> {

    private IntFunction<T[]> arrayProducer;

    private T[] array;

    public SymmetricGrowingArray(IntFunction<T[]> arrayProducer) {
        this.arrayProducer = arrayProducer;
        this.array = arrayProducer.apply(1);
    }

    public T get(int index) {
        int internalIndex = mapIndex(index);
        return internalIndex >= array.length? null:array[internalIndex];
    }

    public T put(int index, T newValue) {
        int internalIndex = mapIndex(index);
        checkSize(internalIndex+1);
        T result = array[internalIndex];
        array[internalIndex] = newValue;
        return result;
    }

    private void checkSize(int neededSize) {
        if (array.length < neededSize) {
            T[] newArray = arrayProducer.apply(neededSize);
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    private static int mapIndex(int index) {
        return (index >= 0) ? index << 1: ((-index)<<1)-1;
    }

    public int[] getSize() {
        return new int[] {-array.length/2, (array.length-1)/2};
    }
}