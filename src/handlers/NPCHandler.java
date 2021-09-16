package handlers;

/**
 * @author Joshua Jean-Philippe
 * NPC Constructor
 */
public class NPCHandler {
	
    public String name;
    public int hp;
    public int atk;
    public String desc;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


	@Override
	public String toString() {
		return "NPC: [" +this.getName()+ "], Health: ["+this.getHp()+"], Attack: ["+this.getAtk()+"], Description: ["+this.getDesc()+"].";
	}

}
