package de.chrlembeck.util.collections;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * Implementierung des Enumeration Interfaces zum Iterieren über die Inhalte eines Arrays.
 *
 * @author Christoph Lembeck
 * @param <T>
 *            Typ der in dem Array enthaltenen Daten.
 */
@SuppressWarnings("PMD.ReplaceEnumerationWithIterator")
public class ArrayEnumeration<T> implements Enumeration<T> {

    /**
     * Referenz auf das Array, über das iteriert werden soll.
     */
    private final T[] array;

    /**
     * Aktuelle Position innerhalb des Arrays.
     */
    private int currentIndex;

    /**
     * Erstellt eine neue Enumeration über das übergebene Array.
     * 
     * @param array
     *            Array, über das iteriert werden soll.
     */
    @SafeVarargs
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public ArrayEnumeration(final T... array) {
        this.array = array;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMoreElements() {
        return array.length > currentIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T nextElement() {
        if (array.length > currentIndex) {
            return array[currentIndex++];
        }
        throw new NoSuchElementException("The enumeration contains only " + array.length + " elements.");
    }
}