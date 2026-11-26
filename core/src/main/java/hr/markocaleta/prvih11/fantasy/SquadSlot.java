package hr.markocaleta.prvih11.fantasy;

import jakarta.persistence.Embeddable;

@Embeddable
public record SquadSlot(Long playerId, boolean starter) {}