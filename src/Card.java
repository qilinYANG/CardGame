// The abstraction of a physical card.
public abstract class Card {
    private boolean isVisible = true;

    final void setVisible() {
        isVisible = true;
    }

    final void setInvisible() {
        isVisible = false;
    }

    final boolean isVisible() {
        return isVisible;
    }
}
