package hr.markocaleta.prvih11.fantasy;

import hr.markocaleta.prvih11.competition.Season;
import hr.markocaleta.prvih11.user.AppUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FantasyTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Season season;

    @Column(nullable = false, length = 100)
    private String name;

    @Version
    private long version;

    @ElementCollection
    @CollectionTable(name = "fantasy_team_player",
            joinColumns = @JoinColumn(name = "fantasy_team_id"))
    private Set<SquadSlot> squad = new HashSet<>();

    public FantasyTeam(AppUser user, Season season, String name, Set<SquadSlot> squad) {
        this.user = user;
        this.season = season;
        this.name = name;
        long distinctPlayers = squad.stream().map(SquadSlot::playerId).distinct().count();
        if (distinctPlayers != squad.size()) {
            throw new IllegalArgumentException("A player can appear only once in the squad");
        }
        this.squad.addAll(squad);
    }

    public Set<SquadSlot> getSquad() {
        return Collections.unmodifiableSet(squad);
    }
}
