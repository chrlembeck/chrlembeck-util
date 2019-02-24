package de.chrlembeck.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.net.URLClassLoader;

/**
 * ObjectInputStream zum Lesen von Objekten, deren Klassen für den Leser ggf. unbekannt sein können. Zum Auflösen dieser
 * Klassen wird dem InputStream ein entsprechend passender Classloader mitgegeben.
 * 
 * @author Christoph Lembeck
 * @see URLClassLoader
 */
public class ClassLoaderObjectInputStream extends ObjectInputStream {

    /**
     * Zum Laden der benötigen Klassen verwendbarer Classloader.
     */
    private final ClassLoader classLoader;

    /**
     * Erzeugt eine Instanz des InputStreams.
     * 
     * @param inputStream
     *            Stream, aus dem die eigentlichen Daten gelesen werden sollen.
     * @param classLoader
     *            Classloader zum Erzeugen der Klassen für die gelesenen Objekte.
     * @throws IOException
     *             Falls beim Lesen aus dem Stream ein Problem auftritt.
     */
    public ClassLoaderObjectInputStream(final InputStream inputStream, final ClassLoader classLoader)
            throws IOException {
        super(inputStream);
        this.classLoader = classLoader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> resolveClass(final ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        final String className = desc.getName();
        try {
            final Class<?> classRef = classLoader.loadClass(className);
            return classRef;
        } catch (final ClassNotFoundException cnfe) {
            return super.resolveClass(desc);
        }
    }
}