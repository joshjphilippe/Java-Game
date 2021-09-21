package handlers;

import java.util.Random;

import javax.swing.JOptionPane;

import main.Main;
import player.Player;

public class CombatHandler {
	
	private static boolean isNPCTurn = false;
    private static Random rand = new Random();
    private static String[] combatOptions = {"Attack", "Use Item", "Examine"};
    
    public static void startCombat(Player p, NPCHandler npc) {
        while(npc.getHp() > 0) {
            int y = 0;
            while(y < 10) {
            	int choice = JOptionPane.showOptionDialog(
            			null, 
            			"You are facing off against a: "+npc.getName()+"! What will you do?", 
            			"Combat Against: "+npc.getName(), 
            			JOptionPane.DEFAULT_OPTION, 
            			JOptionPane.PLAIN_MESSAGE, null, 
            			combatOptions, combatOptions[0]);
            	switch(choice) {
            	case 0:
            		attackNPC(p, npc);
            		y = 11;
            		break;
            	case 1:
                    useItem(p, npc);
                    break;
                case 2:
            		examineNPC(p, npc);
            		break;
            	}
            }
        }
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
    private static void attackNPC(Player p, NPCHandler npc) {
        int playerAtk = rand.nextInt(p.getAtk());
        Main.addMessage("\nYou attack the: "+npc.getName()+"!");
        Main.addMessage("\nYou hit a: "+playerAtk+"!");
        Utils.delay(1);
        int currentNpcHp = npc.getHp();
        int newNpcHp = currentNpcHp - playerAtk;
        npc.setHp(newNpcHp);
        Utils.delay(1);
        Main.addMessage("\nThe "+npc.getName()+" now has: "+npc.getHp()+" Health left!");
        Utils.delay(3);
        deathCheck(p, npc);
    }
    
    private static void attackPlayer(NPCHandler npc, Player p) {
        if(p.getHp() > 0) {
            int npcAttack = rand.nextInt(npc.getAtk());

            Main.addMessage("The "+npc.getName()+" attacks!");
            Main.addMessage("The "+npc.getName()+" hits a: "+npcAttack+"!");
            Utils.delay(1);
    
            int currentPlayerHp = p.getHp();
            int newPlayerHp = currentPlayerHp - npcAttack;
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
    
    private static void examineNPC(Player p, NPCHandler npc) {
    	 Main.addMessage("\nYou examine: ["+npc.getName()+"], ["+npc.getDesc()+"]!");
    }
    
    public static void deathCheck(Player p, NPCHandler npc) {
        if(npc.getHp() > 0) {
            isNPCTurn = true;
            if(isNPCTurn == true) {
                attackPlayer(npc, p);
            }
        } else {
            isNPCTurn = false;
            if(true) {
            	Main.addMessage("\nYou have defeated the: "+npc.getName()+"!");
                ItemUsage.minorPoisonUsed = 0;
            }
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
