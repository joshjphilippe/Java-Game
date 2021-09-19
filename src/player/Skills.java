package player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Joshua Jean-Philippe
 * Will hold all information pretaining to player skills
 * Instead of cluttering main player file
 * Skills will be managed here
 */

public class Skills {

    private static final String dir = "./data/XpPerLevel.txt";
    private static final String skillsDir = "./data/saves/";
    public static Map<Integer, Integer> xpPerLevel = new LinkedHashMap<>();
    public static ArrayList<Skills> playerSkills = new ArrayList<Skills>();

    private String skillName;
    private int currentLevel;
    private int currentExp;

    public Skills(String skillName, int currentLevel, int currentExp) {
        this.skillName = skillName;
        this.currentLevel = currentLevel;
        this.currentExp = currentExp;
    }
    
    public String getSkillName() {
        return skillName;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }


    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }


    public int getCurrentExp() {
        return currentExp;
    }


    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    @Override
    public String toString() {
        return getSkillName()+","+getCurrentLevel()+","+getCurrentExp();
    }


    public static void main(String args[]) {
        Player p = new Player("Dummy", 10, 5, 1);
        //createSkills(p);
        loadSkills(p);
        //saveSkills(p);
        System.out.println(playerSkills.get(0).getSkillName());
        System.out.println(playerSkills.get(0).getCurrentLevel());
        System.out.println(playerSkills.get(0).getCurrentExp());

        System.out.println(playerSkills.get(2).getSkillName());
        System.out.println(playerSkills.get(2).getCurrentLevel());
        System.out.println(playerSkills.get(2).getCurrentExp());

        playerSkills.get(2).setCurrentLevel(29);
        saveSkills(p);

    }
    

    public static void loadSkills(Player p) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(skillsDir+p.getName()+"_skills.csv")));
            String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //skillName currentLevel currentExp
                Skills skill = new Skills(i[0], Integer.parseInt(i[1]), Integer.parseInt(i[2]));
                playerSkills.add(skill);
            }
            System.out.println("Skills for Player ["+p.getName()+"] have been loaded. ["+playerSkills.size()+"]");
        } catch (IOException ioe) {
            System.out.println("Cannot locate specified Player's skills file!");
        }
    }

    public static void createSkills(Player p) {
        File f = new File(skillsDir+p.getName()+"_skills.csv");
        try {
            if(f.createNewFile()) {
                System.out.println("Skills File created for: "+p.getName());
                FileWriter fw = new FileWriter(f);
                fw.write("Woodcutting,1,0\n");
                fw.write("Firemaking,1,0\n");
                fw.write("Cooking,1,0");
                fw.close();
                //saveSkills(p);
            } else {
                System.out.println("This Skills File already exists!");
            }
        } catch (IOException ioe) {
            System.out.println("Directory missing for Skills!");
        }
    }

    public static void saveSkills(Player p) {
        try {
            File f = new File(skillsDir+p.getName()+"_skills.csv");
            FileWriter fw = new FileWriter(f);
            for(int i = 0; i < playerSkills.size(); i++) {
                String skill = playerSkills.get(i).toString();
                fw.write(skill);
                if(i < playerSkills.size() - 1)
                    fw.write("\n");
            }
            fw.close();
        } catch (IOException ioe) {
            System.out.println("Cannot find Player's Skills file!");
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

    
/*    public static String xpTil() {
        int nextLevel = playerLevel + 1;
        int expNeeded = xpPerLevel.get(nextLevel);
        int difference = expNeeded - playerCurrentXp;
        return "\nExp until level: "+nextLevel+" is ["+difference+"]";
    }

    public static void displayTable() {
        for (int i : xpPerLevel.keySet()) {
            System.out.println("Level: "+i+" Exp: "+xpPerLevel.get(i));
        }
    }

    public static void levelUpCheck() {
        if(playerCurrentXp > xpPerLevel.get(playerLevel + 1)) {
            int newLevel = playerLevel + 1;
            playerLevel = newLevel;
            System.out.println("\nCongratulations! You have levelled up!");
            System.out.println("Woodcutting level is now: "+playerLevel);
        } else {
            System.out.println(xpTil());
        }
    }*/


}
