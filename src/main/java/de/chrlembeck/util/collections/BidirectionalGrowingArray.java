package de.chrlembeck.util.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.IntFunction;

/**
 * Hilfsklasse zum Speichern von Elementen in einem dynamisch wachsenden Array mit beliebigen Start- und End-Indizes.
 * Beim Speichern von Elementen an Stellen, die noch nicht Teil des Arrays sind, wird das Array automatisch auf die
 * benötigte Größe erweitert. Zugriffe auf Elemente neben dem Array werden mit der Rückgabe von null beantwortet.
 * 
 * @author Christoph Lembeck
 *
 * @param <T>
 *            Typ der Elemente in dem Array.
 */
public class BidirectionalGrowingArray<T> implements Iterable<T> {

    /**
     * Funktion zur Erzeugung eines neuen Arrays des passenden Typs.
     */
    private final IntFunction<T[]> arrayProducer;

    /**
     * Internes Array zur Speicherung der enthaltenen Daten.
     */
    T[] array;

    /**
     * Kleinster Index oder linker Rand des Arrays.
     */
    private int offset;

    /**
     * Erzeugt ein neues dynamisches Array beginnend beim Index 0.
     * 
     * @param arrayProducer
     *            Funktion zur Erzeugung von Arrays des Typs T.
     */
    public BidirectionalGrowingArray(final IntFunction<T[]> arrayProducer) {
        this(arrayProducer, 0);
    }

    /**
     * Erzeugt ein neues dynamisches Array beginnend beim gewünschten Startindex.
     * 
     * @param arrayProducer
     *            Funktion zur Erzeugung von Arrays des Typs T.
     * @param startIndex
     *            Initialer linker Rand oder kleinster Index des Arrays.
     */
    public BidirectionalGrowingArray(final IntFunction<T[]> arrayProducer, final int startIndex) {
        this.arrayProducer = arrayProducer;
        this.array = arrayProducer.apply(0);
        this.offset = startIndex;
    }

    /**
     * Gibt das Element an der gewünschten Position innerhalb des Arrays wieder. Liegt die Position neben den
     * Arraygrenzen, wird null zurückgegeben.
     * 
     * @param index
     *            Index des gewünschten Elements aus dem Array.
     * @return Element an der Arrayposition oder null, falls dort kein Wert hinterlegt ist oder der Index außerhalb der
     *         Arraygrenzen liegt.
     */
    public T get(final int index) {
        final int internalIndex = mapIndex(index);
        return (internalIndex < 0 || internalIndex >= array.length) ? null : array[internalIndex];
    }

    /**
     * Legt den übergebenen Wert an der gewünschten Position im Array ab. Liegt die Position außerhalb der Arraygrenzen,
     * wird das Array automatisch erweitert.
     * 
     * @param index
     *            Position, an der der Wert abgelegt werden soll.
     * @param newValue
     *            Wert, der in dem Array gespeichert werden soll.
     * @return Bisheriger Wert an der Arrayposition oder null, falls dort noch kein Wert hinterlegt war.
     */
    public T put(final int index, final T newValue) {
        int internalIndex = mapIndex(index);
        internalIndex = checkSize(internalIndex);
        final T result = array[internalIndex];
        array[internalIndex] = newValue;
        return result;
    }

    /**
     * Prüft, ob der gewünschte Index innerhalb der Array-Grenzen liegt und erweitert das Array ggf.
     * 
     * @param neededInternalIndex
     *            Interner Indexwert, dessen Vorhandensein in dem Array sichergestellt werden soll.
     * @return Neuer interner Index, falls durch eine benötigte Vergrößerung des Arrays eine Änderung der Arraygrenzen
     *         stattgefunden hat.
     */
    private int checkSize(final int neededInternalIndex) {
        final int delta = neededInternalIndex < 0 ? -neededInternalIndex : neededInternalIndex - array.length + 1;
        if (delta > 0) {
            final T[] newArray = arrayProducer.apply(array.length + delta);
            if (neededInternalIndex < 0) {
                System.arraycopy(array, 0, newArray, delta, array.length);
            } else {
                System.arraycopy(array, 0, newArray, 0, array.length);
            }
            array = newArray;
            if (neededInternalIndex < 0) {
                offset -= delta;
                return 0;
            }
        }
        return neededInternalIndex;
    }

    /**
     * Rechnet den gewünschten Array-Index in die Position für die interne Speicherung um.
     * 
     * @param index
     *            Gewünschte Array-Position.
     * @return Umgerechneter Index für den Zugriff auf das interne Array.
     */
    private int mapIndex(final int index) {
        return index - offset;
    }

    /**
     * Gibt den kleinsten und größten genutzen ArrayIndex des Arrays zurück. Beide Indizes befinden sich babei noch
     * innerhalb des Arrays.
     * 
     * @return Zweielementige Array mit dem kleinsten und größten noch im Array befindlichen Index.
     * @throws ArrayIndexOutOfBoundsException
     *             Falls das Array leer ist.
     */
    public int[] getRange() throws ArrayIndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new int[] { offset, offset + array.length - 1 };
    }

    /**
     * Prüft, ob das Array leer ist.
     * 
     * @return true, wenn das Array leer ist, sonst false.
     */
    private boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Gibt die aktuelle Länge des Arrays zurück.
     * 
     * @return Aktuelle Länge des Arrays.
     */
    public int size() {
        return array.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int pos;

            @Override
            public boolean hasNext() {
                return pos < array.length;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    return array[pos++];
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}