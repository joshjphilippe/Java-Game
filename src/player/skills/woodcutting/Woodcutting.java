package player.skills.woodcutting;

import java.util.Random;
import java.util.Scanner;

import handlers.Utils;
import player.Player;
import player.Skills;


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

    static String skillName = Skills.playerSkills.get(0).getSkillName();
    static int currentLevel = Skills.playerSkills.get(0).getCurrentLevel();
    static int currentXp = Skills.playerSkills.get(0).getCurrentExp();

    static Axes playerAxe = Axes.MITHRIL;
    static Random rand = new Random();

    public enum Axes {
        WOOD(1),
        COPPER(2),
        IRON(3),
        MITHRIL(4);

        int sharpness;

        Axes(int sharpness) {
            this.sharpness = sharpness;
        }

        public int getSharpness() {
            return this.sharpness;
        }
    }

    public static void chopTree(Player p) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nYour current "+skillName+" Level is: "+currentLevel);
        System.out.println("Your current "+skillName+" Exp is: "+currentXp);

        Utils.delay(1);
        System.out.println("You enter a forest and see multiple trees");
        System.out.println(Trees.spawned.toString());

        System.out.print("\nWhich tree would you like to cut?: ");

        int choice = scanner.nextInt();

        if(Trees.spawned.contains(Trees.spawned.get(choice)) && currentLevel >= Trees.spawned.get(choice).getLevelReq()) {
            System.out.println("You begin cutting the: "+Trees.spawned.get(choice).getTreeType()+" tree!");
            Utils.delay(2);


            if(rand.nextInt(Trees.spawned.get(choice).getSuccessNum() + 5 - playerAxe.getSharpness()) <= Trees.spawned.get(choice).getSuccessNum()) {


                int earnedExp = Trees.spawned.get(choice).getExpEarned();
                System.out.println("\nYou've successfully chopped down the tree!");
                System.out.println("You've earned: "+earnedExp+" "+skillName+" Experience Points!");
                Skills.increaseXp(p, 0, earnedExp);
                Skills.levelUpCheck(p, 0);
            } else {
                int earnedExp = rand.nextInt(rand.nextInt(Trees.spawned.get(choice).getExpEarned() - 1 + 1) + 1);
                if(earnedExp <= 0) {
                    earnedExp = 1;
                }
                System.out.println("\nYou've chopped down the tree, albeit clumsily, reduced experience earned!");
                System.out.println("You've earned: "+earnedExp+" "+skillName+" Experience Points!");
                Skills.increaseXp(p, 0, earnedExp);
                Skills.levelUpCheck(p, 0);
            }
        } else {
            System.out.println("You can't cut a(n): "+Trees.spawned.get(choice).getTreeType()+" tree!");
        }
        scanner.close();
    }



}
