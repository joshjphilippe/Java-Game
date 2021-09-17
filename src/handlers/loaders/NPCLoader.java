package handlers.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import handlers.NPCHandler;

/**
 * @author Joshua Jean-Philippe
 * Reads NPC information from csv file and creates 
 * multiple NPC Objects using NPCHandler
 */
public class NPCLoader {

    private static final String dir = "./data/npcs.csv";
    public static ArrayList<NPCHandler> npcs = new ArrayList<NPCHandler>();
    private static NPCHandler npch;


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
		} catch (IOException ioe) {
			System.out.println("NPC File not found!");
		}
    }
    
    /**
	 * Use this to spawn npc in game
	 * Using NPCHandler to read our npcs list and create an NPC based
	 * off of it's indexed position.
	 * @param index location of NPC in ArrayList
	 * @return NPCHandler Object
	 */
	public static NPCHandler spawnNPC(int index) {
		NPCHandler npc = npcs.get(index);
		/**Setting the values for the NPC we requested for in index**/
        int id = npc.getId();
		String name = npc.getName();
		int hp = npc.getHp();
		int atk = npc.getAtk();
		int def = npc.getDef();
		String desc = npc.getDesc();

		//This is what is actually creating the NPC object based off of the info sent to it
		setNpch(new NPCHandler(id, name, hp, atk, def, desc));
		return npch;
	}

    private static void setNpch(NPCHandler npch) {
		NPCLoader.npch = npch;
	}


    
}
