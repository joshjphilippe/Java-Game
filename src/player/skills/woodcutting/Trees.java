package player.skills.woodcutting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Joshua Jean-Philippe
 * We load ALL THE TREES
 * Then we spawn the trees we want
 * 
 * This class handles all of that ^
 */
public class Trees {

	private static final String dir = "./data/skills/trees.csv";
	public static ArrayList<Trees> trees = new ArrayList<Trees>();
    public static ArrayList<Trees> spawned = new ArrayList<Trees>();

    private String treeType;
    private int levelReq;
    private int expEarned;
    private int successNum;

    public Trees(String treeType, int levelReq, int expEarned, int successNum) {
        this.treeType = treeType;
        this.levelReq = levelReq;
        this.expEarned = expEarned;
        this.successNum = successNum;
    }

    public String getTreeType() {
        return treeType;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getExpEarned() {
        return expEarned;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public static void loadTrees() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(dir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //type level exp succ
				Trees tree = new Trees(i[0], Integer.parseInt(i[1]), Integer.parseInt(i[2]), Integer.parseInt(i[3]));
				trees.add(tree);
			}
			System.out.println("Trees Loaded: "+trees.size());
		} catch (IOException ioe) {
			System.out.println("Trees File not found!");
		}
	}

    /**
     * Testing Tree Spawning
     */
    public static void spawnTrees(int pos, int treeType) {
        spawned.add(pos, trees.get(treeType));
    }

    private static void displayTrees() {
        for(int i = 0; i < trees.size(); i++) {
            System.out.println(trees.get(i).toString());
        }
    }

    public static void displaySpawnedTrees() {
        for(int i = 0; i < spawned.size(); i++) {
            System.out.println("Index: ["+i+"] "+spawned.get(i).toString());
        }
    }

    @Override
    public String toString() {
        return "\nTree Type: ["+this.getTreeType()+"], Level Required: ["+this.getLevelReq()+"], Exp Earned: ["+this.getExpEarned()+"], Success Number: ["+this.getSuccessNum()+"]";
   }

}
