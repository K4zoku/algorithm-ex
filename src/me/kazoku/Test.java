package me.kazoku;

import me.kazoku.algo.chess.Position;
import me.kazoku.algo.chess.TableSize;
import me.kazoku.algo.chess.knight.KnightJourney;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Scanner;

public final class Test {
    public static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("[Thread/Main] Input start position: ");
        Position start = new Position(INPUT.nextLong(), INPUT.nextLong());
        System.out.print("[Thread/Main] Input end position: ");
        Position end = new Position(INPUT.nextLong(), INPUT.nextLong());
        System.out.println("[Thread/Main] Start Position: " + start);
        System.out.println("[Thread/Main] End Position: " + end);
        KnightJourney knightJourney = new KnightJourney(TableSize.DEFAULT, start, end);

        Thread benchmark = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            System.out.println("[Thread/Benchmark] Total steps: " + knightJourney.calculateSteps());
            long endTime = System.currentTimeMillis();
            System.out.println("[Thread/Benchmark] Time elapsed: " + (endTime - startTime) + "ms");
            Thread.currentThread().interrupt();
        });
        benchmark.start();

        Thread printTour = new Thread(() -> {
            StringBuilder tour = new StringBuilder();
            LinkedList<Position> journey = (LinkedList<Position>) knightJourney.getJourney();
            journey.forEach(position -> {
                tour.append(position.toString());
                if (!position.equals(journey.getLast())) tour.append(" ↦ ");
            });
            System.out.println("[Thread/Tour] Knight Tour: " + tour);

        });
        printTour.start();

        long steps = knightJourney.calculateSteps(Duration.ofSeconds(1));
        if (steps > 0) System.out.println("[Thread/Main] Total steps: " + steps);
        else {
            switch ((int) steps) {
                case -504:
                    System.out.println("[Thread/Main] Maximum time exceeded!");
                    break;
                case -1:
                    System.out.println("[Thread/Main] Impossible!");
                    break;
                case -500:
                    System.out.println("[Thread/Main] Ouch! An error occurred!");
                    break;
                default:
                    System.out.println("[Thread/Main] This is the default case and never appear on the console!");
                    break;
            }
        }
    }
}
