package handlers;



/**
 * @author Joshua Jean-Philippe
 * NPC Constructor
 */
public class NPCHandler {
	
    private int id;
    private String name;
    private int hp;
    private int atk;
    private int def;
    private String desc;
    
    
    public NPCHandler(int id, String name, int hp, int atk, int def, String desc) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.desc = desc;
    }
 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


	@Override
	public String toString() {
		return "ID: ["+this.getId()+"], Name: [" +this.getName()+ "], Health: ["+this.getHp()+"], Attack: ["+this.getAtk()+"], Defence: ["+this.getDef()+"], Description: ["+this.getDesc()+"].";
	}  

}