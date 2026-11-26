package hr.markocaleta.prvih11.competition;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gameweek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Season season;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private Instant deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private GameweekStatus status;

    public Gameweek(Season season, int number, Instant deadline) {
        this.season = season;
        this.number = number;
        this.deadline = deadline;
        this.status = GameweekStatus.OPEN;
    }
}
