package hr.markocaleta.prvih11.competition;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal budget;

    @Column(nullable = false)
    private int maxPerClub;

    public Competition(String name, BigDecimal budget, int maxPerClub) {
        this.name = name;
        this.budget = budget;
        this.maxPerClub = maxPerClub;
    }
}
