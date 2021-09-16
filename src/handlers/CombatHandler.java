package handlers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import main.Main;
import player.Player;

public class CombatHandler {
	
	private static boolean isNPCTurn = false;
    private static boolean isPlayerDead = false;
    private static boolean isNpcDead = false;
    private static boolean isCombatWon = false;
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
     * while playerIsDead and npcIsdead = false
     *     Prompt options: attack, examine, run
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
        int atk = rand.nextInt(p.getAtk());
        Main.addMessage("\nYou attack the: "+npc.getName()+"!");
        Main.addMessage("\nYou hit a: "+atk+"!\n");
        delay(1);
        int oldHp = npc.getHp();
        int newHp = oldHp - atk;
        npc.setHp(newHp);
        delay(1);
        Main.addMessage("\nThe "+npc.getName()+" now has: "+npc.getHp()+" Health left!");
        delay(3);
        deathCheck(p, npc);
    }
    
    private static void attackPlayer(NPCHandler npc, Player p) {
        if(p.getHp() > 0) {
            int atk = rand.nextInt(npc.getAtk());

            Main.addMessage("\nThe "+npc.getName()+" attacks!");
            Main.addMessage("\nThe "+npc.getName()+" hits a: "+atk+"!");
            delay(1);
    
            int oldHp = p.getHp();
            int newHp = oldHp - atk;
            p.setHp(newHp);
            Main.updateHp(p);
            
            if(p.getHp() <= 0) {
                JOptionPane.showMessageDialog(null, "You have been defeated.. Rest your soul traveller..");
            	isPlayerDead = true;
            	if(isPlayerDead = true) {
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
    
    public static void delay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void deathCheck(Player p, NPCHandler npc) {
        if(npc.getHp() > 0) {
            isCombatWon = false;
            isNpcDead = false;
            isNPCTurn = true;
            if(isNPCTurn == true) {
                attackPlayer(npc, p);
            }
        } else {
            isNpcDead = true;
            isCombatWon = true;
            isNPCTurn = false;
            if(isCombatWon = true) {
            	 Main.addMessage("\nYou have defeated the: "+npc.getName()+"!");
            }
        }
    }
}
