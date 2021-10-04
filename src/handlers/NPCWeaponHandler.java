package handlers;

public class NPCWeaponHandler {

    private String weaponName;
    private int attackValue;

    public NPCWeaponHandler(String weaponName, int attackValue) {
        this.weaponName = weaponName;
        this.attackValue = attackValue;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public int getAttackValue() {
        return attackValue;
    }
    
    
}
