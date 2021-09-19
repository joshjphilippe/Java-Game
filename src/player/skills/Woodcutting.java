package player.skills;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import handlers.Utils;


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
 */
public class Woodcutting {

    private static final String dir = "./data/XpPerLevel.txt";
    private static final String dirtrees = "./data/trees.txt";
    public static Map<Integer, Integer> xpPerLevel = new LinkedHashMap<>();
    public static Map<Integer, String> treeTypes = new LinkedHashMap<>();

    static int lvlReq[] = {1, 5, 10};
    static int expEarn[] = {10, 20, 30};
    static int treeRange[] = {10, 50, 60};
    static String trees[] = {"Normal", "Hard-Wood", "Oak"};
    static int playerLevel = 4;
    static int playerCurrentXp = 377;
    static Random rand = new Random();


    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        loadXpTable();
        loadTrees();

        displayTable();
        System.out.println("");
        displayTrees();

                
        System.out.println("\nYour current Woodcutting Level is: "+getCurrentLevel());
        System.out.println("Your current Woodcutting Exp is: "+getCurrentXp());

        Utils.delay(1);
        System.out.println("You enter a forest and see multiple trees");
        System.out.println(Arrays.toString(trees));

        System.out.print("\nWhich tree would you like to cut? 0 1 2: ");

        int choice = scanner.nextInt();
        if(treeTypes.containsKey(choice) && playerLevel >= lvlReq[choice]) {
            System.out.println("You can cut this: "+treeTypes.get(choice)+" tree!");
            Utils.delay(2);
            if(rand.nextInt(101) <= treeRange[choice]) {
                int earnedExp = rand.nextInt(expEarn[choice]);
                System.out.println("\nYou've successfully chopped down the tree!");
                System.out.println("You've earned: "+earnedExp+" Woodcutting Experience Points!");
                increaseXp(earnedExp);
                levelCheck();
                
            } else {
                int earnedExp = rand.nextInt(expEarn[choice] - 2);
                System.out.println("\nYou've chopped down the tree, albeit clumsily, reduced experience earned!");
                System.out.println("You've earned: "+earnedExp+" Woodcutting Experience Points!");
                increaseXp(earnedExp);
                levelCheck();
            }
        } else {
            System.out.println("You can't cut a(n): "+treeTypes.get(choice)+" tree!");
        }

    }

    public static void loadXpTable() {
        try {
            File f = new File(dir);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] kv = line.split(":");
                int lvl = Integer.parseInt(kv[0]);
                int exp = Integer.parseInt(kv[1]);
                xpPerLevel.put(lvl, exp);
            }
            br.close();
        } catch (IOException ioe) {
            System.out.println("Exp table not found!");
        }
    }

    public static void loadTrees() {
        try {
            File f = new File(dirtrees);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] kv = line.split(":");
                int id = Integer.parseInt(kv[0].trim());
                String tree = kv[1].trim();
                treeTypes.put(id, tree);
            }
            br.close();
        } catch (IOException ioe) {
            System.out.println("Tree table not found!");
        }
    }

    public static void displayTable() {
        for (int i : xpPerLevel.keySet()) {
            System.out.println("Level: "+i+" Exp: "+xpPerLevel.get(i));
        }
    }

    public static void displayTrees() {
    	for(int i : treeTypes.keySet()) {
    		System.out.println("ID: "+i+" Tree: "+treeTypes.get(i));
    	}
    }

    public static String xpTil() {
        int nextLevel = playerLevel + 1;
        int expNeeded = xpPerLevel.get(nextLevel);
        int difference = expNeeded - playerCurrentXp;
        return "\nExp until level: "+nextLevel+" is ["+difference+"]";
    }

    public static void levelCheck() {
        if(playerCurrentXp >= xpPerLevel.get(playerLevel + 1)) {
            int newLevel = playerLevel + 1;
            playerLevel = newLevel;
            System.out.println("\nCongratulations! You have levelled up!");
            System.out.println("Woodcutting level is now: "+playerLevel);
        } else {
            System.out.println(xpTil());
        }
    }

    public static int getCurrentLevel() {
        return playerLevel;
    }

    public static void increaseLevel() {
        playerLevel += 1;
    }

    public static int getCurrentXp() {
        return playerCurrentXp;
    }
    
    public static void increaseXp(int amount) {
        playerCurrentXp += amount;
    }



}
