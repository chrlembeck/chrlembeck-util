package de.chrlembeck.util.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utility-Klasse Zur einfachen Verwendung ausgewählter Standard-Algorithmen.
 *
 * @author Christoph Lembeck
 */
public final class Algorithms {

    /**
     * private constructor
     */
    private Algorithms() {
    }

    /**
     * Führt eine Tiefensuche durch gibt bei Fund eines den Suchkriterien entsprechenden Zusatdns die Liste der
     * Zustände, die zu dem Treffer geführt haben zurück.
     * 
     * @param state
     *            Startzustand für den Beginn der Tiefensuche.
     * @param stateProducer
     *            Producer zur Erzeugung einer Liste von Nachfolgezuständen zu dem ihm übergebenen Zustand.
     * @param consumer
     *            Consumer zur Verarbeitung aller während der Suche generierten Zustände. Falls die Zwischenzustände
     *            nicht verarbieter werden sollen kann hier null übergeben werden.
     * @param acceptanceCriterion
     *            Prüfkriterium für die Erkennung des gesuchten Zustands. Ist das Prüfkriterium erfüllt, wird die Suche
     *            beendet und die bis zur Erreichung des gefundenen Zustands durchlaufenen Zustände werden als Ergebnis
     *            zurückgegeben.
     * 
     * @param <State>
     *            Typ der Objekte für die Speicherung der Zwischenzustände.
     * @return Liste der bis zum Erreichen des gesuchten Zustands durchlaufenen Zwischenzustände inklusive Start- und
     *         Zielzustand. Null, falls kein erzeugter Zustand dem Suchkriterium entsprochen hat.
     */
    public static final <State> List<State> breadthFirstSearch(final State state,
            final Function<StateWrapper<State>, Iterable<State>> stateProducer,
            final Consumer<StateWrapper<State>> consumer, final Predicate<StateWrapper<State>> acceptanceCriterion) {
        // Queue zur Aufbewahrung der noch zu verarbeitenden Zwischenzustände.
        final Queue<StateWrapper<State>> queue = new LinkedList<>();
        // Referenz auf den als nächstes zu Verarbeitenden Zustand.
        StateWrapper<State> currentState = new StateWrapper<>(state, null);
        // Queue mit dem Startzustand befüllen, damit mit diesem begonnen werden kann.
        queue.add(currentState);
        // Falls gewünscht, aktuellen Zustand an einen Consumer übermitteln.
        if (consumer != null) {
            consumer.accept(currentState);
        }
        // Suchen, bis keine Zwischenzustände mehr vorhanden sind.
        while (!queue.isEmpty()) {
            // nächsten aktuellen Zustand aus der Wareschlange nehmen.
            currentState = queue.poll();
            // neue Zwischenzustände auf Grundlage des aktuellen Zustand ermitteln lassen.
            final Iterable<State> possibleStates = stateProducer.apply(currentState);
            // die neuen Zwischenzustände verarbeiten
            for (final State possibleState : possibleStates) {
                // Die erzeugten Zustände in den Wrapper packen, damit ihre Verbindung zu ihren Vorgängern abfragbar
                // bleiben.
                final StateWrapper<State> newState = new StateWrapper<>(possibleState, currentState);
                // Falls gewünscht, aktuellen Zustand an einen Consumer übermitteln.
                if (consumer != null) {
                    consumer.accept(newState);
                }
                // prüfen, ob der neue Zustand bereits dem Suchkriterium entspricht.
                if (acceptanceCriterion.test(newState)) {
                    // bei einem Treffer Liste der Zwischenzustände zusammen mit dem Treffer ausgeben.
                    return newState.getStates();
                }
                // Lag kein Treffer vor, wird der Zusand in die Warteschlange gepackt und es geht mit dem nächsten
                // Zustand weiter...
                queue.add(newState);
            }
        }
        // Gibt es keine Zwischenzustände mehr, die bearbeitet werden müssen und lag bis dahin noch kein Treffer vor,
        // ist das Ergebnis null.
        return null;
    }

    /**
     * Wrapper-Klasse für die Speicherung eines Zwischenzustands für die Breitensuche. Neben dem Zustand selbst wird
     * hier auch die Referenz auf den Vorgänger, aus dem dieser Zustand entstanden ist gespeichert.
     * 
     * @author Christoph Lembeck
     *
     * @param <State>
     *            Typ des zu speichernden Zustand-Objekts.
     */
    public static class StateWrapper<State> {

        /**
         * Referenz auf den hinterlegten Zustand.
         */
        private final State state;

        /**
         * Referenz auf einen ggf. vorhandenen Vorgänger dieses Zustands.
         */
        private final StateWrapper<State> predecessor;

        /**
         * Erzeugt einen neuen Wrapper um den übergebenen Zustand mit Referenz auf den übergebenen Vorgänger.
         * 
         * @param state
         *            Zustand, der in dem Objekt festgehalten werden soll
         * @param predecessor
         *            Referenz auf einen ggf. vorhandenen Vorgängerzustand.
         */
        StateWrapper(final State state, final StateWrapper<State> predecessor) {
            this.state = state;
            this.predecessor = predecessor;
        }

        /**
         * Gibt den enthaltenen Zustand zurück.
         * 
         * @return Im Wrapper enthaltenes Zustands-Objekt.
         */
        public State getState() {
            return state;
        }

        /**
         * Gibt den Vorgänger dieses Zustands zurück oder null, falls es keinen Vorgänger für den Zustand gibt.
         * 
         * @return Referenz auf den Vorgängerzustand oder null, falls kein solcher existiert.
         */
        public StateWrapper<State> getPredecessor() {
            return predecessor;
        }

        /**
         * Erzeugt eine Liste von Zuständen, in der Reihenfolge, in der der Algorithmus diese durchwandert hat. Der
         * erste Zustand entspricht dabei dem Ausgangszustand und der letzte dem Zielzustand.
         * 
         * @return Liste der Zustände vom Ausgang bis zum Ziel.
         */
        public List<State> getStates() {
            final List<State> stateList;
            if (predecessor == null) {
                stateList = new ArrayList<>();
            } else {
                stateList = predecessor.getStates();
            }
            stateList.add(state);
            return stateList;
        }
    }
}
