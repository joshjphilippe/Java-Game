package handlers;

public class ArmorHandler {

    private String armorName;
    private int defValue;

    public ArmorHandler(String armorName, int defValue) {
        this.armorName = armorName;
        this.defValue = defValue;
    }

    public String getArmorName() {
        return armorName;
    }

    public int getDefValue() {
        return defValue;
    }

    @Override
    public String toString() {
        return getArmorName()+","+getDefValue();
    }
    

}
