public class PokerCard extends Card{
    private int value;
    private String type;
    private boolean facestatus;

    public PokerCard(int value, String type,boolean facestatus){
        setValue(value);
        setFacestatus(facestatus);
        setType(type);
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFacestatus() {
        return facestatus;
    }

    public void setFacestatus(boolean facestatus) {
        this.facestatus = facestatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
