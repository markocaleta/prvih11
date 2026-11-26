package hr.markocaleta.prvih11.player;

import hr.markocaleta.prvih11.competition.Club;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Club club;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private Position position;

    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal price;

    @Column(nullable = false)
    private int rating;

    @Column(unique = true, length = 50)
    private String externalId;

    public Player(Club club, String firstName, String lastName, Position position, BigDecimal price, int rating) {
        this.club = club;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.price = price;
        this.rating = rating;
    }
}
