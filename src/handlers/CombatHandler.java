package handlers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import main.Main;
import player.Player;

public class CombatHandler {
	
	private static boolean isNPCTurn = false;
    private static Random rand = new Random();
    static String[] combatOptions = {"Attack", "Examine"};
    
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
        Main.addMessage("\nYou hit a: "+playerAtk+"!\n");
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

            Main.addMessage("\nThe "+npc.getName()+" attacks!");
            Main.addMessage("\nThe "+npc.getName()+" hits a: "+npcAttack+"!");
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
    	 Main.addMessage("\nYou examine: ["+npc.getName()+"], ["+npc.getDesc()+"]!\n");
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
            }
        }
    }
}
