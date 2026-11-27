package hr.markocaleta.prvih11.competition;

import java.util.ArrayList;
import java.util.List;

public final class RoundRobinScheduler {

    private RoundRobinScheduler() {}

    public static <T> List<Round<T>> schedule(List<T> teams, int cycles) {

        int n = teams.size();
        if (n < 2 || n % 2 != 0 ) {
            throw new IllegalArgumentException("Number of teams must be even and at least 2, got " + n);
        }
        if (cycles < 1) {
            throw new IllegalArgumentException("Cycles must be at least 1, got " + cycles);
        }

        List<Round<T>> first = firstCycle(teams);

        List<Round<T>> scheduleList = new ArrayList<>();

        for(int i = 0; i < cycles; i++) {
            for (Round<T> round : first) {
                scheduleList.add(i % 2 == 0 ? round : reverseRound(round));
            }
        }

        return scheduleList;
    }

    private static <T> List<Round<T>> firstCycle (List<T> teams) {

        int n = teams.size();
        int m = n - 1;

        T fixed = teams.getFirst();
        List<T> rest = new ArrayList<>(teams.subList(1, n));

        List<Round<T>> roundsList = new ArrayList<>();

        for (int r = 0; r < m; r++) {
            List<T> row = new ArrayList<>();
            row.add(fixed);
            row.addAll(rest);

            List<Pairing<T>> matches = new ArrayList<>();
            for (int i = 0; i < n / 2; i++) {
                T a = row.get(i);
                T b = row.get(n - 1 - i);
                if (i == 0 && r % 2 == 1) {
                    matches.add(new Pairing<>(b, a));
                } else {
                    matches.add(new Pairing<>(a, b));
                }
            }
            roundsList.add(new Round<>(matches));

            rest.addLast(rest.removeFirst());
        }

        return roundsList;
    }

    private static <T> Round<T> reverseRound(Round<T> round) {

        List<Pairing<T>> reversed = new ArrayList<>();
        for(Pairing<T> pairing : round.matches()) {
            reversed.add(pairing.reversed());
        }

        return new Round<>(reversed);
    }
}
