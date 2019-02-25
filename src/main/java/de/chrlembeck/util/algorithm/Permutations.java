package de.chrlembeck.util.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Hilfsklasse zur Erzeugung von Permutationen einer geordneten Liste von Elementen.
 *
 * @param <T> Typ der Listenelemente.
 */
public class Permutations<T> implements Iterator<T[]>, Iterable<T[]> {

    /**
     * Index der zuletzt ausgegebenen Permutation.
     */
    private long counter;

    /**
     * Kopie der Originalliste in ihrer ursprünglichen Reihenfolge.
     */
    @SuppressWarnings("PMD.LooseCoupling")
    private final ArrayList<T> originalList;

    private final int[] positions;

    /**
     * Erzeugermethode für neue Arrays des hier zu verarbeitenden Listentyps.
     */
    private final Function<Integer, T[]> arrayProducer;

    /**
     * Erzeugt einen neuen Iterator für die Generierung von Permutation der Elemente in dem übergebenen Array.
     *
     * @param originalSet   Elemente, die permutiert werden sollen.
     * @param arrayProducer Erzeugermethode für neue Arrays des hier zu verarbeitenden Listentyps.
     */
    public Permutations(final T[] originalSet, final Function<Integer, T[]> arrayProducer) {
        this.originalList = new ArrayList<T>(originalSet.length);
        this.arrayProducer = arrayProducer;
        for (final T element : originalSet) {
            this.originalList.add(element);
        }
        this.counter = 0;
        this.positions = new int[originalSet.length];
    }

    /**
     * Erzeugt einen neuen Iterator für die Generierung von Permutation der Elemente der übergebenen Liste.
     *
     * @param originalSet   Elemente, die permutiert werden sollen.
     * @param arrayProducer Erzeugermethode für neue Arrays des hier zu verarbeitenden Listentyps.
     */
    public Permutations(final Collection<T> originalSet, final Function<Integer, T[]> arrayProducer) {
        this.originalList = new ArrayList<T>(originalSet);
        this.arrayProducer = arrayProducer;
        this.counter = 0;
        this.positions = new int[originalSet.size()];
    }

    @Override
    public boolean hasNext() {
        return remaining() > 0;
    }

    public long remaining() {
        return factorial(originalList.size()) - counter;
    }

    private static long factorial(final int argument) {
        if (argument < 0) {
            throw new IllegalArgumentException("argument may not be less than zero: " + argument);
        }
        long result = argument;
        for (int factor = argument - 1; factor >= 2; factor--) {
            result *= factor;
        }
        return result;
    }

    @Override
    public T[] next() {
        counter++;
        int p;
        @SuppressWarnings("unchecked") final ArrayList<T> tmp = (ArrayList<T>) originalList.clone();
        final T[] result = arrayProducer.apply(originalList.size());
        for (int i = 0; i < positions.length; i++) {
            result[i] = tmp.remove(positions[i]);
        }
        p = positions.length - 1;
        positions[p]++;
        while (p > 0 && positions[p] == positions.length - p) {
            positions[p] = 0;
            p--;
            positions[p]++;
        }
        return result;
    }

    private static int[] getProgram(int n, final int c) {
        final int[] result = new int[c];
        for (int i = c - 1; i >= 0; i--) {
            result[i] = n % (i + 1);
            n /= (i + 1);
        }
        return result;
    }

    /**
     * Erzeugt aus der übergebenen Liste die Permutation der Liste mit dem angegebenen Index.
     * Kann verwendet werden, um gezielt eine bestimmte Permutation der Liste zu erzeugen.
     *
     * @param original      Originalreihenfolge der Elemente.
     * @param index         Index der gewünschten Permutation.
     * @param arrayProducer Erzeugerfunktion für Arrays des Listentyps.
     * @param <T>           Typ der zu permutierenden Daten.
     * @return Durch den index bestimmte Permutation der Elemente.
     */
    @SuppressWarnings("PMD.UseArraysAsList")
    public static <T> T[] getPerm(final T[] original, final int index, final Function<Integer, T[]> arrayProducer) {
        final int[] program = getProgram(index, original.length);
        final ArrayList<T> list = new ArrayList<T>();
        for (int i = 0; i < original.length; i++) {
            list.add(program[i], original[i]);
        }
        final T[] result = arrayProducer.apply(original.length);
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(original.length - i - 1);
        }
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T[]> iterator() {
        return this;
    }
}