package handlers;

import main.Main;
import player.Player;

/**
 * @author Joshua Jean-Philippe
 * Rough idea
 * Item usage..
 * Switch cases based off itemId..
 */
public class ItemUsage {

    public static int minorPoisonUsed = 0; //this is just an idea test

    public static void useItem(Player p, NPCHandler npc, String item) {
        switch(item) {
            case "Minor Potion":
                int currentHp = p.getHp();
                int healedHp = currentHp + 5;
                p.setHp(healedHp);
                Main.addMessage("Used: "+item+" and healed 5 HP!");
                Main.updateHp(p);
                break;
            case "Minor Poison":
                if(minorPoisonUsed != 2) {
                    int npcHp = npc.getHp();
                    int lostHp = npcHp - 2; // make this rng :)
                    npc.setHp(lostHp);
                    Main.addMessage("\nUsed: "+item+" against: "+npc.getName()+" and they lost 2 HP!");
                    Main.updateHp(p);
                    minorPoisonUsed += 1;
                } else {
                    Main.addMessage("\nYou cannot use this anymore!\n");
                }
                break;
            case "Biscuit":
                System.out.println("Bisucit");
            break;
        }
    }

}
