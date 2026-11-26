package hr.markocaleta.prvih11.competition;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Competition competition;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 10)
    private String shortName;

    @Column(unique = true, length = 50)
    private String externalId;

    public Club(Competition competition, String name, String shortName) {
        this.competition = competition;
        this.name = name;
        this.shortName = shortName;
    }
}
