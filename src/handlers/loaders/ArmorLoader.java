package handlers.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import handlers.ArmorHandler;
import main.Main;
import player.Player;

/**
 * @author Joshua Jean-Philippe
 * Loads the different equipment files
 * Helmets, Chest, Arms, Legs, Boots
 */
public class ArmorLoader {

    /**
     * Name
     * Defence Value
     */
    private static final String saveDir = "./data/saves/";
    private static final String dir = "./data/equipment/";
    private static final String helmDir = dir+"helmets.csv";
    private static final String chestDir = dir+"chests.csv";
    private static final String armsDir = dir+"arms.csv";
    private static final String legsDir = dir+"legs.csv";
    private static final String bootsDir = dir+"boots.csv";

    /**
     * Armor positions
     * Helm 0
     * Chest 1
     * Arms 2
     * Legs 3
     * Boots 4
     */
    public static ArrayList<ArmorHandler> currentEquipment = new ArrayList<ArmorHandler>();
    public static ArrayList<ArmorHandler> helmList = new ArrayList<ArmorHandler>();
    public static ArrayList<ArmorHandler> chestList = new ArrayList<ArmorHandler>();
    public static ArrayList<ArmorHandler> armsList = new ArrayList<ArmorHandler>();
    public static ArrayList<ArmorHandler> legsList = new ArrayList<ArmorHandler>();
    public static ArrayList<ArmorHandler> bootsList = new ArrayList<ArmorHandler>();

    public static void loadAllEquipment() {
        loadHelms();
        loadChests();
        loadArms();
        loadLegs();
        loadBoots();
    }

    public static int totalArmor() {
        int helm = currentEquipment.get(0).getDefValue();
        int chest = currentEquipment.get(1).getDefValue();
        int arms = currentEquipment.get(2).getDefValue();
        int legs = currentEquipment.get(3).getDefValue();
        int boots = currentEquipment.get(4).getDefValue();

        return helm + chest + arms + legs + boots;

    }
 
    public static void replaceHelm(int helmId) {
        currentEquipment.remove(0);
        currentEquipment.add(0, helmList.get(helmId));
    }

    public static void replaceChest(int chestId) {
        currentEquipment.remove(1);
        currentEquipment.add(1, helmList.get(chestId));
    }

    public static void replaceArms(int armsId) {
        currentEquipment.remove(2);
        currentEquipment.add(2, helmList.get(armsId));
    }

    public static void replaceLegs(int legsId) {
        currentEquipment.remove(3);
        currentEquipment.add(3, helmList.get(legsId));
    }

    public static void replaceBoots(int bootsId) {
        currentEquipment.remove(4);
        currentEquipment.add(4, helmList.get(bootsId));
    }

    public static void createEquipment(Player p) {
        File f = new File(saveDir+p.getName()+"_equipment.csv");
        try {
            if(f.createNewFile()) {
                System.out.println("Equipment File created for: "+p.getName());
                FileWriter fw = new FileWriter(f);
                fw.write("Nothing,0\n");
                fw.write("Nothing,0\n");
                fw.write("Nothing,0\n");
                fw.write("Nothing,0\n");
                fw.write("Nothing,0\n");
                fw.write("Nothing,0\n");
                fw.close();
            } else {
                System.out.println("This Toolbelt File already exists!");
            }
        } catch (IOException ioe) {
            System.out.println("Directory missing for Toolbelt!");
        }
    }

    public static void loadEquipment(Player p) {
        try {
			BufferedReader br = new BufferedReader(new FileReader(new File(saveDir+p.getName()+"_equipment.csv")));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //Equipment name, Defence value
				ArmorHandler equipment = new ArmorHandler(i[0], Integer.parseInt(i[1]));
				currentEquipment.add(equipment);
			}
			System.out.println("Player Equipment Loaded: "+currentEquipment.size());
		} catch (IOException ioe) {
			System.out.println("Player Equipment File not found!");
		}
    }

    public static void saveEquipment(Player p) {
        try {
            File f = new File(saveDir+p.getName()+"_equipment.csv");
            FileWriter fw = new FileWriter(f);
            for(int i = 0; i < currentEquipment.size(); i++) {
                String armor = currentEquipment.get(i).toString();
                fw.write(armor);
                if(i < currentEquipment.size() - 1)
                    fw.write("\n");
            }
            fw.close();
        } catch (IOException ioe) {
            System.out.println("Cannot find Player's Equipment file!");
        }
    }

    public static void refreshEquipment(Player p) {
        saveEquipment(p);
        currentEquipment.clear();
        loadEquipment(p);
    }

    public static void displayEquipment() {
        for(int i = 0; i < currentEquipment.size(); i++) {
            Main.addMessage(currentEquipment.get(i).toString());
        }
    }

    private static void loadHelms() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(helmDir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //Equipment name, Defence value
				ArmorHandler helmet = new ArmorHandler(i[0], Integer.parseInt(i[1]));
				helmList.add(helmet);
			}
			System.out.println("Helmet armor pieces Loaded: "+helmList.size());
		} catch (IOException ioe) {
			System.out.println("Helmet armor pieces File not found!");
		}
    }

    private static void loadChests() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(chestDir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //ToolName AttributeValue Dura
				ArmorHandler chest = new ArmorHandler(i[0], Integer.parseInt(i[1]));
				chestList.add(chest);
			}
			System.out.println("Chest armor pieces Loaded: "+chestList.size());
		} catch (IOException ioe) {
			System.out.println("Chest armor pieces File not found!");
		}
    }

    private static void loadArms() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(armsDir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //ToolName AttributeValue Dura
				ArmorHandler arms = new ArmorHandler(i[0], Integer.parseInt(i[1]));
				armsList.add(arms);
			}
			System.out.println("Arms armor pieces Loaded: "+armsList.size());
		} catch (IOException ioe) {
			System.out.println("Arms armor pieces File not found!");
		}
    }

    private static void loadLegs() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(legsDir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //ToolName AttributeValue Dura
				ArmorHandler legs = new ArmorHandler(i[0], Integer.parseInt(i[1]));
				legsList.add(legs);
			}
			System.out.println("Leg armor pieces Loaded: "+legsList.size());
		} catch (IOException ioe) {
			System.out.println("Leg armor pieces File not found!");
		}
    }

    private static void loadBoots() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(bootsDir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //ToolName AttributeValue Dura
				ArmorHandler boot = new ArmorHandler(i[0], Integer.parseInt(i[1]));
				bootsList.add(boot);
			}
			System.out.println("Boot armor pieces Loaded: "+bootsList.size());
		} catch (IOException ioe) {
			System.out.println("Boot armor pieces File not found!");
		}
    }
}
