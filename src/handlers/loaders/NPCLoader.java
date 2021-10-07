package handlers.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import handlers.NPCHandler;
import handlers.NPCWeaponHandler;
import main.Main;

/**
 * @author Joshua Jean-Philippe
 * Reads NPC information from csv file and creates 
 * multiple NPC Objects based off of blueprint of NPCHandler
 */
public class NPCLoader {

    private static final String dir = "./data/npcs.csv";
	private static final String weapDir = "./data/npcweapons/villagers.csv";
    public static ArrayList<NPCHandler> npcs = new ArrayList<NPCHandler>();
	public static ArrayList<NPCHandler> spawned = new ArrayList<NPCHandler>();
	public static ArrayList<NPCWeaponHandler> npcWeapon = new ArrayList<NPCWeaponHandler>();
    private static NPCHandler npch;
	private static Random rand = new Random();


    public static void loadNpcs() {
        try {
			BufferedReader br = new BufferedReader(new FileReader(new File(dir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] n = line.split(",");
                //id name hp atk def desc
				NPCHandler npc = new NPCHandler(Integer.parseInt(n[0]), n[1], Integer.parseInt(n[2]), Integer.parseInt(n[3]), Integer.parseInt(n[4]), n[5]);
				npcs.add(npc);
			}
			System.out.println("NPCs Loaded: "+npcs.size());
			loadNPCWeapons();
		} catch (IOException ioe) {
			System.out.println("NPC File not found!");
		}
    }

	/**
	 * For now this will just load the villagers.csv
	 * Letter I will have 1 massive weapons list and depending on the npcs
	 * I'll let them loop through the last within a certain range
	 * Example: villagers will loop through range 0-2 and pick a random weapon
	 * Goblins will loop through 3-6 and pick a random weapon there
	 */
	public static void loadNPCWeapons() {
        try {
			BufferedReader br = new BufferedReader(new FileReader(new File(weapDir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] n = line.split(",");
                //Name atkvalue
				NPCWeaponHandler weapon = new NPCWeaponHandler(n[0], Integer.parseInt(n[1]));
				npcWeapon.add(weapon);
			}
			System.out.println("NPC Weapons Loaded: "+npcWeapon.size());
		} catch (IOException ioe) {
			System.out.println("NPC Weapons File not found!");
		}
    }

	public static int randWeapon(int min, int max) {
		int weapon = rand.nextInt(max - min) + min;
		return weapon;
	}


	public static void spawnNPC(int pos, int id) {
		spawned.add(pos, npcs.get(id));
	}

	public static void whoSpawned() {
        for(int i = 0; i < spawned.size(); i++) {
            Main.addMessage("Index: ["+i+"] "+spawned.get(i).toString());
        }
	}
    
    /**
	 * Use this to spawn npc in game
	 * Using NPCHandler to read our npcs list and create an NPC based
	 * off of it's indexed position.
	 * @param index location of NPC in ArrayList
	 * @return NPCHandler Object
	 */
	public static NPCHandler createNPC(int id, String name, int hp, int atk, int def, String desc) {
		setNpch(new NPCHandler(id, name, hp, atk, def, desc));
		return npch;
	}

    private static void setNpch(NPCHandler npch) {
		NPCLoader.npch = npch;
	}


    
}
