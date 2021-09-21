package player.skills.woodcutting;

import java.util.Random;

import javax.swing.JOptionPane;

import handlers.InventoryHandler;
import handlers.Utils;
import main.Main;
import player.Player;
import player.Skills;
import player.Tools;


/**
 * @author Joshua Jean-Philippe
 * This is will handle everything woodcutting related
 * At the moment this is its own stand alone program
 * So it can be ran by itself
 * Play with the playerCurrentXp number, but make sure the exp you use
 * Is for the right playerLevel number. There will never be a point where these
 * Numbers never correlate properly, so break it if you want to but dont be surprised!
 * 
 * TODO:
 * Add more tree types
 * Test more EXP tables and earnings 
 * Find a way to make woodcutting more interactive maybe, it's a TBAG so..
 * Trees will need to be constructed and spawned into the game like NPCs, CSV file
 * Spawned Trees need to be added to a list or hard coded into areas later
 * 
 * Tree Constructor = Type Level Exp Success
 * 
 * 
 * 
 * Completed:
 * Exp is now exponential (CurrentLevel + NextLevel)^2, max level is 30
 * Added Axes, Axes provide sharpness and the sharper the Axe the easier it is
 * to get exp
 * Added more trees
 */
public class Woodcutting {

    static Random rand = new Random();

    Tools t;

    static String playerAxe = Tools.playerToolBelt.get(0).getToolName();
    static int axeAttri = Tools.playerToolBelt.get(0).getAttributeValue();
    static int axeDura = Tools.playerToolBelt.get(0).getToolDurability();

    static int newDura = axeDura - rand.nextInt(3 - 1 + 1) + 1;
    
    public static void chopTree(Player p) {
        String skillName = Skills.playerSkills.get(0).getSkillName();
        int currentLevel = Skills.playerSkills.get(0).getCurrentLevel();
        int currentXp = Skills.playerSkills.get(0).getCurrentExp();

        Main.addMessage("\nYour current "+skillName+" Level is: "+currentLevel);
        Main.addMessage("Your current "+skillName+" Exp is: "+currentXp);

        Utils.delay(1);
        Main.addMessage("\nYou enter a forest and see multiple trees");
        //Main.addMessageObject(Trees.displaySpawnedTrees());

        Main.addMessage("\nWhich tree would you like to cut?");

        do {
            int choice = JOptionPane.showOptionDialog(
                null, 
                null, 
                null, 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, null, 
                Trees.spawned.toArray(), null);

            if(Trees.spawned.contains(Trees.spawned.get(choice)) && currentLevel >= Trees.spawned.get(choice).getLevelReq()) {
                Main.addMessage("You begin cutting the: ["+Trees.spawned.get(choice).getTreeType()+"] tree with your ["+playerAxe+"]");
                Utils.delay(2);
        
        
                if(rand.nextInt(Trees.spawned.get(choice).getSuccessNum() + 5 - axeAttri) <= Trees.spawned.get(choice).getSuccessNum()) {

                    int earnedExp = Trees.spawned.get(choice).getExpEarned();
                    
                    Main.addMessage("\nYou've successfully chopped down the tree!");
                    Main.addMessage("You've earned: "+earnedExp+" "+skillName+" Experience Points!");
                    
                    
                    
                    InventoryHandler.addItem(Trees.spawned.get(choice).getTreeType()+" Log", rand.nextInt(5 - 2) + 2);
                    InventoryHandler.saveInventory(p);
                    Main.refreshInventory(p);
                    Skills.increaseXp(p, 0, earnedExp);
                    Skills.levelUpCheck(p, 0);
                    Trees.spawned.remove(choice);
                    
                } else {
                    int earnedExp = rand.nextInt(rand.nextInt(Trees.spawned.get(choice).getExpEarned() - 1 + 1) + 1);
                    if(earnedExp <= 0) {
                        earnedExp = 1;
                    }
                    Main.addMessage("\nYou've chopped down the tree, albeit clumsily, reduced experience earned!");
                    Main.addMessage("You've earned: "+earnedExp+" "+skillName+" Experience Points!");
                    InventoryHandler.addItem(Trees.spawned.get(choice).getTreeType()+" Log", rand.nextInt(1));
                    InventoryHandler.saveInventory(p);
                    Main.refreshInventory(p);
                    Skills.increaseXp(p, 0, earnedExp);
                    Skills.levelUpCheck(p, 0);
                    Trees.spawned.remove(choice);
                    }
            } else {
                Main.addMessage("You can't cut a(n): "+Trees.spawned.get(choice).getTreeType()+" tree!");
            }
        } while(!Trees.spawned.isEmpty());
    }



}
