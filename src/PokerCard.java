public class PokerCard extends Card{
    private int value,num;
    private String type;

    public PokerCard(int value, int num, String type){
        setValue(value);
        setNum(num);
        setType(type);
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
