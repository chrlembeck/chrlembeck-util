package de.chrlembeck.util.collections;

import java.util.Collection;

/**
 * Some utility methods around any kinds of collections.
 * 
 * @author Christoph Lembeck
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class CollectionsUtil {

    /**
     * Checks, if the collection is null or empty.
     * 
     * @param collection
     *            Collection to be checked.
     * @return {@code true} if the collection is {@code null} or empty, {@code false} if it is not null and contains any
     *         data.
     * @param <E>
     *            Type of the elements in the collection.
     * @see Collection#isEmpty()
     */
    public static <E> boolean isNullOrEmpty(final Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }
}