package hr.markocaleta.prvih11.competition;

public record Pairing<T>(T home, T away) {

    public Pairing<T> reversed() {
        return new Pairing<>(away, home);
    }
}
