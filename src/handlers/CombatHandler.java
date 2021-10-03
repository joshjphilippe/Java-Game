package handlers;

import java.util.Random;

import javax.swing.JOptionPane;

import handlers.loaders.NPCLoader;
import main.Main;
import player.Player;

public class CombatHandler {
	
	private static boolean isNPCTurn = false;
    private static Random rand = new Random();
    private static String[] combatOptions = {"Attack", "Use Item", "Examine", "Run"};
    
    /**
     * BUG
     * All npcs of the same selected NPC type get updated during combat
     * @param p
     */
    public static void startCombat(Player p) {
        do {
            int npc = JOptionPane.showOptionDialog(null, null, "Select an NPC", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, NPCLoader.spawned.toArray(), null);
            if(NPCLoader.spawned.get(npc).getHp() > 0) {
                int npcHP = NPCLoader.spawned.get(npc).getHp();
                String npcName = NPCLoader.spawned.get(npc).getName();
                String examine = NPCLoader.spawned.get(npc).getDesc();

                while(npcHP > 0) {
                    int y = 0;
                    while (y < 1) {
                        int xChoice = JOptionPane.showOptionDialog(null, "You are facing off against a: "+npcName+"! What will you do?", "Combat Against: "+npcName, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, combatOptions, null);
                        switch(xChoice) {
                            case 0:
                                attackNPC(p, npc);
                                break;
                            case 1:
                                Main.addMessage("You clicked use item!");
                                break;
                            case 2:
                                Main.addMessage("You examine the ["+npcName+"], ["+examine+"]!");
                                break;
                            case 3:
                                NPCLoader.spawned.clear();
                                Main.addMessage("You run from the fight!");
                                y = 1;
                                break;
                        }
                    }
                    break;
                }
                break;
            }
        } while(!NPCLoader.spawned.isEmpty());
    }
    
    /**
     * Combat theme starts
     * Future plan: check agility -- faster agility goes first
     *     Prompt options: attack, examine, use items
     * After every attack check npc current health
     * If health <= 0 stop combat
     * else continue combat
     * 
     * need to create loot system
     * 
     * @param p
     * @param npc
     */
    private static void attackNPC(Player p, int npc) {
        int startHp = NPCLoader.spawned.get(npc).getHp();
        int id = NPCLoader.spawned.get(npc).getId();
        int atk = NPCLoader.spawned.get(npc).getAtk();
        int def = NPCLoader.spawned.get(npc).getDef();
        String desc = NPCLoader.spawned.get(npc).getDesc();
        String npcName = NPCLoader.spawned.get(npc).getName();


        int playerAtk = 9;
        Main.addMessage("\nYou attack the: "+npcName+"!");
        Main.addMessage("\nYou hit a: "+playerAtk+"!");
        Utils.delay(1);
        int newNpcHp = startHp - playerAtk;

        NPCLoader.spawned.get(npc).setHp(newNpcHp);

        int currentHp = NPCLoader.spawned.get(npc).getHp();

        Utils.delay(1);
        Main.addMessage("\nThe "+npcName+" now has: "+currentHp+" Health left!");
        NPCLoader.whoSpawned();
        Utils.delay(3);
        deathCheck(p, npc);
    }
    
    private static void attackPlayer(int npc, Player p) {
        String npcName = NPCLoader.spawned.get(npc).getName();
        int npcHP = NPCLoader.spawned.get(npc).getHp();
        int npcAttack = NPCLoader.spawned.get(npc).getAtk();

        if(p.getHp() > 0) {
            int attack = rand.nextInt(npcAttack);

            Main.addMessage("The "+npcName+" attacks!");
            Main.addMessage("The "+npcName+" hits a: "+attack+"!");
            Utils.delay(1);
    
            int currentPlayerHp = p.getHp();
            int newPlayerHp = currentPlayerHp - attack;
            p.setHp(newPlayerHp);
            Main.updateHp(p);
            
            if(p.getHp() <= 0) {
                JOptionPane.showMessageDialog(null, "You have been defeated.. Rest your soul traveller..");
            	if(true) {
                    p.setHp(1);
                    FileHandler.savePlayer(p);
                    System.exit(0);
            	}
            } else {
            	 Main.addMessage("\nYou now have: "+p.getHp()+" Health left!\n");
            }
        }
    }
    
    public static void deathCheck(Player p, int npc) {
        String npcName = NPCLoader.spawned.get(npc).getName();
        int npcHP = NPCLoader.spawned.get(npc).getHp();
        if(npcHP > 0) {
            isNPCTurn = true;
            if(isNPCTurn == true) {
                attackPlayer(npc, p);
            }
        } else {
            isNPCTurn = false;
            Main.addMessage("\nYou have defeated the: "+npcName+"!");
            NPCLoader.spawned.remove(npc);
            startCombat(p);
            ItemUsage.minorPoisonUsed = 0;
        }
    }
    /**
     * Prompt them for item name and pass on to ItemUsage
     * TODO: Add a itemUse counter for different items..
     * Probably split items into general categories
     * Healing/buff Items can be used infinitely ("until hp is full/buff cap reached")
     * NPC Damaging Items can only be used 1-2 times per player turn
     * @param p
     * @param npc
     */
    public static void useItem(Player p, NPCHandler npc) {
        String item = JOptionPane.showInputDialog(null, "Type Item Name", "Use Item", JOptionPane.INFORMATION_MESSAGE);
        if(InventoryHandler.inventory.containsKey(item) && InventoryHandler.inventory.get(item) >= 1) {
            InventoryHandler.removeItem(item, 1);
            InventoryHandler.saveInventory(p);
            ItemUsage.useItem(p, npc, item);
            Main.refreshInventory(p);
        } else {
            JOptionPane.showMessageDialog(null, "This item does not exist!", "Use Item", JOptionPane.ERROR_MESSAGE);
        }
    }
}
