package handlers;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import handlers.loaders.ArmorLoader;
import handlers.loaders.NPCLoader;
import main.Main;
import player.Player;

public class CombatHandler {
	
	private static boolean isNPCTurn = false;
    private static Random rand = new Random();
    private static String[] combatOptions = {"Attack", "Use Item", "Examine", "Run"};
    private static ArrayList<NPCWeaponHandler> savedWeapon = new ArrayList<NPCWeaponHandler>();

    /**
     * BUG
     * All npcs of the same selected NPC type get updated during combat
     * @param p
     */
    public static void startCombat(Player p) {
        int index = JOptionPane.showOptionDialog(null, null, "Combat!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, NPCLoader.spawned.toArray(), null);
        
        NPCHandler spawned = NPCLoader.spawned.get(index);
        int hp = spawned.getHp();
        int id = spawned.getId();
        int atk = spawned.getAtk();
        int def = spawned.getDef();
        String desc = spawned.getDesc();
        String name =spawned.getName();

        NPCHandler npc = NPCLoader.createNPC(id, name, hp, atk, def, desc);

        int npcWeapon = NPCLoader.randWeapon(0, 3);
        savedWeapon.add(NPCLoader.npcWeapon.get(npcWeapon));
        do {
            combat: while(npc.getHp() > 0) {
                int y = 0;
                while (y < 1) {
                    int xChoice = JOptionPane.showOptionDialog(null, "You are facing off against a: "+name+"! What will you do?", "Combat Against: "+name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, combatOptions, null);
                    switch(xChoice) {
                        case 0:
                            attackNPC(p, npc, index);
                            y = 1;
                            break;
                        case 1:
                            Main.addMessage("You clicked use item!");
                            break;
                        case 2:
                            Main.addMessage("You examine the ["+name+"], ["+desc+"]!");
                            break;
                        case 3:
                            NPCLoader.spawned.clear();
                            savedWeapon.clear();
                            Main.addMessage("You run from the fight!");
                            y = 1;
                            break combat;
                    }
                }
            }
        } while(!NPCLoader.spawned.isEmpty());
    }
    
    /**
     * Combat theme starts
     * Future plan: check agility -- faster agility goes first
     *     Prompt options: attack, examine, use items
     * After every attack, check npc current health
     * If health <= 0 stop combat
     * else continue combat
     * 
     * need to create loot system
     * 
     * @param p
     * @param npc
     */
    private static void attackNPC(Player p, NPCHandler npc, int index) {
        int playerAtk = rand.nextInt(p.getAtk());
        Main.addMessage("\nYou attack the: "+npc.getName()+"!");
        Main.addMessage("\nYou hit a: "+playerAtk+"!");
        Utils.delay(1);
        int newNpcHp = npc.getHp() - playerAtk;

        npc.setHp(newNpcHp);
        int currentHp = npc.getHp();

        Utils.delay(1);
        Main.addMessage("\nThe "+npc.getName()+" now has: "+currentHp+" Health left!");
        
        Utils.delay(3);
        deathCheck(p, npc, index);
    }
    
    private static void attackPlayer(NPCHandler npc, Player p) {
        String npcName = npc.getName();
        String weaponName = savedWeapon.get(0).getWeaponName();
        int weaponDamage = savedWeapon.get(0).getAttackValue();
        int npcAttack = npc.getAtk() + weaponDamage;
        int playerDefence = ArmorLoader.totalArmor();

        if(p.getHp() > 0) {
            if(playerDefence == 0) {
                int attack = rand.nextInt( ((int) Math.ceil((float) npcAttack / 1) - 1 + 1) + 1);
                Main.addMessage("The "+npcName+" attacks with its ["+weaponName+"]!");
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
            } else {
                int attack = rand.nextInt( ((int) Math.ceil((float) npcAttack / playerDefence) - 1 + 1) + 1);
                Main.addMessage("The "+npcName+" attacks with its ["+weaponName+"]!");
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
    }
    
    private static void deathCheck(Player p, NPCHandler npc, int index) {
        if(npc.getHp() > 0) {
            isNPCTurn = true;
            if(isNPCTurn == true) {
                attackPlayer(npc, p);
            }
        } else if(npc.getHp() <= 0) {
            isNPCTurn = false;
            Main.addMessage("\nYou have defeated the: "+npc.getName()+"!");
            NPCLoader.spawned.remove(index);
            savedWeapon.clear();
            ItemUsage.minorPoisonUsed = 0;
            if(NPCLoader.spawned.size() > 0)
            startCombat(p);
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
