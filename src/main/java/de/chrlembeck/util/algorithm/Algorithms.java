package de.chrlembeck.util.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Algorithms {

    private Algorithms() {
    }

    public static final <S> List<S> breadthFirstSearch(final S state, final Function<StateWrapper<S>, Iterable<S>> stateProducer, final Consumer<StateWrapper<S>> consumer, final Predicate<StateWrapper<S>> lastElement) {
        final Queue<StateWrapper<S>> queue = new LinkedList<>();
        StateWrapper<S> currentState = new StateWrapper<>(state, null);
        queue.add(currentState);
        consumer.accept(currentState);
        while (!queue.isEmpty()) {
            currentState = queue.poll();
            final Iterable<S> possibleStates = stateProducer.apply(currentState);
            for (final S possibleState: possibleStates) {
                final StateWrapper<S> newState = new StateWrapper<>(possibleState, currentState);
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

        public StateWrapper(final S state, final StateWrapper<S> predecessor) {
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
            final List<S> stateList;
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
