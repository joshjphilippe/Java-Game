package player;

import java.io.Serializable;

/**
 * 
 * @author Joshua Jean-Philippe
 * 
 * This will hold all the information about a player, stats etc
 * We serialize this class so that we can send the object(player) to a data stream
 */
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int hp;
	private int atk;
	private int gold;
	
	/**
	 * Player constructor.
	 * This allows us to construct a player
	 * @param name = player name
	 * @param hp = player current hp
	 * @param atk = player current atk
	 * @param gold = player current gold
	 */
	public Player(String name, int hp, int atk, int gold) {
		this.setName(name);
		this.setHp(hp);
		this.setAtk(atk);
		this.setGold(gold);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @return the atk
	 */
	public int getAtk() {
		return atk;
	}

	/**
	 * @param atk the atk to set
	 */
	public void setAtk(int atk) {
		this.atk = atk;
	}

	/**
	 * @return the gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @param gold the gold to set
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}
	
    public String getStats() {
    	String s = String.format("\n"
    			+ "Name: %s, "
    			+ "Health: %d, "
    			+ "Attack: %d, "
    			+ "Gold: %d", this.getName(), this.getHp(), this.getAtk(), this.getGold());
    	System.out.println(s);
		return s;
    }

}
