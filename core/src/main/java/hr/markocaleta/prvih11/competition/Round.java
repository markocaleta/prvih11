package hr.markocaleta.prvih11.competition;

import java.util.List;

public record Round<T>(List<Pairing<T>> matches) {}
