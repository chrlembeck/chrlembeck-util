package de.chrlembeck.util.collections;

import java.util.Collection;

public class CollectionsUtil {

    /**
     * Prüft, ob die übergebene Collection null oder leer ist.
     * 
     * @param collection
     *            Zu prüfende Collection.
     * @return {@code true} falls die Collection null oder leer ist, sonst {@code false}.
     */
    public static <E> boolean isNullOrEmpty(final Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }
}