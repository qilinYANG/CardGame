// An abstraction of a general player.
public abstract class Player {
    protected String name;
    protected double score;

    final void setName(String name) {
        this.name = name;
    }

    final void setScore(double score) {
        this.score = score;
    }

    final String getName() {
        return name;
    }

    final double getScore() {
        return score;
    }
}
