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

import main.Main;
import player.skills.woodcutting.Trees;
import player.skills.woodcutting.Woodcutting;

/**
 * @author Joshua Jean-Philippe
 * Will hold all information pretaining to player skills
 * Instead of cluttering main player file
 * Skills will be managed here
 */

public class Skills {

    private static final String dir = "./data/xpTable.txt";
    private static final String skillsDir = "./data/saves/";
    public static Map<Integer, Integer> xpTable = new LinkedHashMap<>();
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
                fw.close();
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
                xpTable.put(lvl, exp);
            }
            br.close();
        } catch (IOException ioe) {
            System.out.println("Exp table not found!");
        }
    }
 
    public static String xpTil(Player p, int skillId) {
        int playerLevel = playerSkills.get(skillId).getCurrentLevel();
        int playerCurrentXp = playerSkills.get(skillId).getCurrentExp();

        int nextLevel = playerLevel + 1;
        int expNeeded = xpTable.get(nextLevel);
        int difference = expNeeded - playerCurrentXp;
        saveSkills(p);

        return "\nExp until level: "+nextLevel+" is ["+difference+"]";
    }

    public static void levelUpCheck(Player p, int skillId) {
        String currentSkill = playerSkills.get(skillId).getSkillName();
        int playerLevel = playerSkills.get(skillId).getCurrentLevel();
        int playerCurrentXp = playerSkills.get(skillId).getCurrentExp(); 

        if(playerCurrentXp > xpTable.get(playerLevel + 1)) {
            playerSkills.get(skillId).setCurrentLevel(playerLevel + 1);
            saveSkills(p);
            playerSkills.clear();
            loadSkills(p);
            int playerLevelUpdated = playerSkills.get(skillId).getCurrentLevel();
            Main.addMessage("\nCongratulations! You have levelled up!");
            Main.addMessage(currentSkill+" level is now: "+playerLevelUpdated+"\n");
        } else {
            Main.addMessage(xpTil(p, skillId));
        }
    }

    public static void increaseXp(Player p, int skillId, int amount) {
        int playerCurrentXp = playerSkills.get(skillId).getCurrentExp();
        playerSkills.get(skillId).setCurrentExp(playerCurrentXp + amount);
        refreshSkills(p);
    }

    public static void refreshSkills(Player p) {
        saveSkills(p);
        playerSkills.clear();
        loadSkills(p);
    }

    public static void displayXpTable() {
        for (int i : xpTable.keySet()) {
            System.out.println("Level: "+i+" Exp: "+xpTable.get(i));
        }
    }

}
