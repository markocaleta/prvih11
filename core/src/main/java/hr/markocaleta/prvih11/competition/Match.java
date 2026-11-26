package hr.markocaleta.prvih11.competition;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Gameweek gameweek;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Club homeClub;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Club awayClub;

    @Column(nullable = false)
    private Instant kickoff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MatchStatus status;

    private Integer homeGoals;

    private Integer awayGoals;

    public Match(Gameweek gameweek, Club homeClub, Club awayClub, Instant kickoff) {
        if (homeClub == awayClub
                || (homeClub.getId() != null && homeClub.getId().equals(awayClub.getId()))) {
            throw new IllegalArgumentException("A club cannot play against itself");
        }
        this.gameweek = gameweek;
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.kickoff = kickoff;
        this.status = MatchStatus.SCHEDULED;
    }

}
