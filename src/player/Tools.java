package player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Joshua Jean-Philippe
 * Handle everything involving the Players Toolbelt and tools
 * The toolbelt will hold
 * Axe, Pickaxe, Fishing Rod.. more later
 */
public class Tools {

    /**
     * Tool Positions
     * 0 Axe
     * 1 Pickaxe
     * 2 Fishing Rod
     */

    private static final String dir = "./data/saves/";
    private static final String axesDir = "./data/tools/axes.csv";
    private static final String picksDir = "./data/tools/picks.csv";

    public static ArrayList<Tools> playerToolBelt = new ArrayList<Tools>();
    public static ArrayList<Tools> axesList = new ArrayList<Tools>();
    public static ArrayList<Tools> picksList = new ArrayList<Tools>();
   

    private String toolName;
    private int attributeValue;
    private int toolDurability;

    public Tools(String toolName, int attributeValue, int toolDurability) {
        this.toolName = toolName;
        this.attributeValue = attributeValue;
        this.toolDurability = toolDurability;
    }

    public String getToolName() {
        return toolName;
    }

    public int getAttributeValue() {
        return attributeValue;
    }

    public int getToolDurability() {
        return toolDurability;
    }

    public void setToolDurability(int toolDurability) {
        this.toolDurability = toolDurability;
    }

    @Override
    public String toString() {
        return getToolName()+","+getAttributeValue()+","+getToolDurability();
    }

    public static void replaceAxe(int toolPosition, int newToolLoc) {
        playerToolBelt.remove(toolPosition);
        playerToolBelt.add(toolPosition, Tools.axesList.get(newToolLoc));
    }

    public static void replacePick(int toolPosition, int newToolLoc) {
        playerToolBelt.remove(toolPosition);
        playerToolBelt.add(toolPosition, Tools.picksList.get(newToolLoc));
    }

    public static void loadToolBelt(Player p) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(dir+p.getName()+"_toolbelt.csv")));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //Toolname Sharpness durability
				Tools tool = new Tools(i[0], Integer.parseInt(i[1]), Integer.parseInt(i[2]));
				playerToolBelt.add(tool);
			}
			System.out.println("Toolbelt Loaded: "+playerToolBelt.size());
		} catch (IOException ioe) {
			System.out.println("Trees File not found!");
		}
    }

    public static void createToolBelt(Player p) {
        File f = new File(dir+p.getName()+"_toolbelt.csv");
        try {
            if(f.createNewFile()) {
                System.out.println("Toolbelt File created for: "+p.getName());
                FileWriter fw = new FileWriter(f);
                fw.write("Wood Axe,1,20\n");
                fw.write("Wood Pickaxe,1,20\n");
                fw.close();
            } else {
                System.out.println("This Toolbelt File already exists!");
            }
        } catch (IOException ioe) {
            System.out.println("Directory missing for Toolbelt!");
        }
    }

    public static void loadAllTools() {
        loadAxes();
        loadPicks();
    }

    private static void loadAxes() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(axesDir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //ToolName AttributeValue Dura
				Tools axe = new Tools(i[0], Integer.parseInt(i[1]), Integer.parseInt(i[2]));
				axesList.add(axe);
			}
			System.out.println("Axes Loaded: "+axesList.size());
		} catch (IOException ioe) {
			System.out.println("Axes File not found!");
		}
    }

    private static void loadPicks() {
        try {
			BufferedReader br = new BufferedReader(new FileReader(new File(picksDir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //ToolName AttributeValue Dura
				Tools pickAxe = new Tools(i[0], Integer.parseInt(i[1]), Integer.parseInt(i[2]));
				picksList.add(pickAxe);
			}
			System.out.println("Pickaxes Loaded: "+picksList.size());
		} catch (IOException ioe) {
			System.out.println("Pickaxes File not found!");
		}
    }

    public static void saveToolBelt(Player p) {
        try {
            File f = new File(dir+p.getName()+"_toolbelt.csv");
            FileWriter fw = new FileWriter(f);
            for(int i = 0; i < playerToolBelt.size(); i++) {
                String tool = playerToolBelt.get(i).toString();
                fw.write(tool);
                if(i < playerToolBelt.size() - 1)
                    fw.write("\n");
            }
            fw.close();
        } catch (IOException ioe) {
            System.out.println("Cannot find Player's Toolbelt file!");
        }
    }

}
