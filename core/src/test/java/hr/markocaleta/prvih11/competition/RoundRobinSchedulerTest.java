package hr.markocaleta.prvih11.competition;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoundRobinSchedulerTest {

    private static final List<Integer> TEN_TEAMS = IntStream.rangeClosed(1, 10).boxed().toList();

    @Test
    void tenTeamsAndFourCyclesGiveThirtySixRoundsOfFiveMatches() {
        List<Round<Integer>> schedule = RoundRobinScheduler.schedule(TEN_TEAMS, 4);

        assertThat(schedule).hasSize(36);
        assertThat(schedule).allSatisfy(round -> assertThat(round.matches()).hasSize(5));
    }

    @Test
    void everyTeamPlaysExactlyOncePerRound() {
        for (Round<Integer> round : RoundRobinScheduler.schedule(TEN_TEAMS, 4)) {
            List<Integer> playing = new ArrayList<>();
            for (Pairing<Integer> match : round.matches()) {
                playing.add(match.home());
                playing.add(match.away());
            }
            assertThat(playing).containsExactlyInAnyOrderElementsOf(TEN_TEAMS);
        }
    }

    @Test
    void noClubPlaysAgainstItself() {
        for(Round<Integer> round : RoundRobinScheduler.schedule(TEN_TEAMS, 4)) {
            for(Pairing<Integer> match : round.matches()) {
                assertThat(match.home()).isNotEqualTo(match.away());
            }
        }
    }

    @Test
    void everyPairMeetsFourTimesTwiceAtEachHome() {
        Map<Set<Integer>, Integer> meetings = new HashMap<>();
        Map<Pairing<Integer>, Integer> homeGames = new HashMap<>();

        for (Round<Integer> round : RoundRobinScheduler.schedule(TEN_TEAMS, 4)) {
            for (Pairing<Integer> match : round.matches()) {
                meetings.merge(Set.of(match.home(), match.away()), 1, Integer::sum);
                homeGames.merge(match, 1, Integer::sum);
            }
        }

        assertThat(meetings).hasSize(45);
        assertThat(meetings.values()).allMatch(count -> count == 4);

        assertThat(homeGames).hasSize(90);
        assertThat(homeGames.values()).allMatch(count -> count == 2);
    }

    @Test
    void oddNumberOfTeamsIsRejected() {
        List<Integer> threeTeams = List.of(1, 2, 3);

        assertThatThrownBy(() -> RoundRobinScheduler.schedule(threeTeams, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
