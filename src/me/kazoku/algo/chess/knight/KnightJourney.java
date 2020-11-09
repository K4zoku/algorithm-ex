package me.kazoku.algo.chess.knight;

import me.kazoku.algo.chess.Position;
import me.kazoku.algo.chess.TableSize;
import me.kazoku.utils.Node;
import me.kazoku.utils.Pair;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

public final class KnightJourney {

    private final Position start;
    private final Position destination;
    private Position min = new Position(Long.MIN_VALUE/2);
    private Position max = new Position(Long.MAX_VALUE/2);

    public KnightJourney(Position start, Position destination) {
        this.start = start;
        this.destination = destination;
    }

    public KnightJourney(TableSize size, Position start, Position destination) {
        min = new Position(0, 0);
        max = new Position(size.getWidth(), size.getHeight());
        if (
            start.getX() < 0 || start.getY() < 0 ||
            start.getX() > max.getX() || start.getY() > max.getY() ||
            destination.getX() < 0 || destination.getY() < 0 ||
            destination.getX() > max.getX() || destination.getY() > max.getY()
        ) throw new IndexOutOfBoundsException();
        this.start = start;
        this.destination = destination;
    }

    public Long calculateSteps(Duration timeLimit) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future<Long> handler = executorService.submit((Callable<Long>) this::calculateSteps);
        long result;
        try {
            result = handler.get(timeLimit.toMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // -504 mean timeout
            result = -504L;
        } catch (InterruptedException e) {
            // -500 mean an error occurred
            Thread.currentThread().interrupt();
            result = -500L;
        } catch (ExecutionException e) {
            // -500 mean an error occurred
            e.printStackTrace();
            result = -500L;
        }
        executorService.shutdownNow();
        return result;
    }

    public Long calculateSteps() {
        Queue<Pair<Position, Long>> queue = new LinkedList<>();
        queue.add(new Pair<>(start, 1L));
        Set<Position> landed = new HashSet<>();
        landed.add(start);

        while (!queue.isEmpty()) {
            Pair<Position, Long> pair = queue.poll();
            Position current = pair.getKey();

            if (current.equals(destination)) return pair.getValue();

            for (KnightMove km : KnightMove.values()) {
                Position next = km.move(current);
                if (next.getX() < min.getX() || next.getY() < min.getY() || next.getX() > max.getX() || next.getY() > max.getY() || landed.contains(next)) {
                    continue;
                }
                queue.add(new Pair<>(next, pair.getValue() + 1));
                landed.add(next);
            }
        }

        // -1 means impossible to reach destination, but impossible to impossible
        // Thanos: Impossible
        return -1L;
    }

    public List<Position> getJourney() {
        Queue<Node<Position>> queue = new LinkedList<>();
        queue.add(new Node<>(start));
        Set<Position> landed = new HashSet<>();
        landed.add(start);

        while (!queue.isEmpty()) {
            Node<Position> currentNode = queue.poll();

            if (currentNode.get().equals(destination)) {
                LinkedList<Position> result = new LinkedList<>();
                for (Node<Position> node = currentNode; node != null; node = node.getPrevious())
                    // It is evolving, just backwards
                    result.addFirst(node.get());
                return result;
            }

            for (KnightMove km : KnightMove.values()) {
                Position next = km.move(currentNode.get());
                currentNode.setNext(new Node<>(currentNode, next));
                if (next.getX() < min.getX() || next.getY() < min.getY() || next.getX() > max.getX() || next.getY() > max.getY() || landed.contains(next)) {
                    // Outstanding move!
                    continue;
                }
                queue.add(currentNode.getNext());
                landed.add(next);
            }
        }
        return Collections.emptyList();
    }
}
