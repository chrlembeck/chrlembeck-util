package de.chrlembeck.util.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Algorithms {

    public static final <S> List<S> breadthFirstSearch(S state, Function<StateWrapper<S>, Iterable<S>> stateProducer, Consumer<StateWrapper<S>> consumer, Predicate<StateWrapper<S>> lastElement) {
        Queue<StateWrapper<S>> queue = new LinkedList<>();
        StateWrapper<S> currentState = new StateWrapper<>(state, null);
        queue.add(currentState);
        consumer.accept(currentState);
        while (!queue.isEmpty()) {
            currentState = queue.poll();
            Iterable<S> possibleStates = stateProducer.apply(currentState);
            for (S possibleState: possibleStates) {
                StateWrapper<S> newState = new StateWrapper<>(possibleState, currentState);
                consumer.accept(newState);
                if (lastElement.test(newState)) {
                    return newState.getStates();
                }
                queue.add(newState);
            }
        }
        return null;
    }

    public static class StateWrapper<S> {

        final S state;

        final StateWrapper<S> predecessor;

        public StateWrapper(S state, StateWrapper<S> predecessor) {
            this.state = state;
            this.predecessor = predecessor;
        }

        public S getState() {
            return state;
        }

        public StateWrapper<S> getPredecessor() {
            return predecessor;
        }

        public List<S> getStates() {
            final List<S> l;
            if (predecessor == null) {
                l = new ArrayList<>();
            } else {
                l = predecessor.getStates();
            }
            l.add(state);
            return l;
        }
    }
}
