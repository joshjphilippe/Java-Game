package handlers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import main.Main;
import player.Player;
import player.Skills;
import player.Tools;

/**
 * 
 * @author Joshua Jean-Philippe
 * Serialize the player file for writing and reading
 * Serializing this information allows us to save it and retrieve it later
 */
public class FileHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final static String savedir = "./data/saves/";
	private static Player p = new Player("null", 10, 5, 10);

	private static String[] badValues = {"  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          ", "           ", "            ", "             ", "              ", "               ", "                ", "!", "@", "#", "$", "%", "^", "&", "*", "(" ,")", "-", "_", "=", "+", "\"", "\'", "<", ",", ".", ">", "/", "?", ":", ";", "[", "]", "{", "}", "|", "\\", "`", "~"};

	public static boolean stringContainsBadValues(String string, String[] check) {
		for(int i = 0; i < badValues.length; i++) {
			if(string.contains(badValues[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create dummy player object
	 * Overwrite the name with desired character name
	 * Save/Write the new player file
	 * 
	 * Current Player params: name hp atk gold
	 * 
	 * Catching IOException since we're handling input/output of a file
	 * We need to "catch" any errors that may occur during this process
	 * 
	 */
	public static void createPlayer() {
		String name = JOptionPane.showInputDialog("Enter Character Name:");
		if(stringContainsBadValues(name, badValues)) {
			Utils.messagePrompt("You entered invalid characters!");
		} else if(name.isEmpty()) {
				Utils.messagePrompt("Null entry\t\tNo File Created");
			} else if(name.length() > 16) {
				Utils.messagePrompt("Name too long!");
			} else if(name.startsWith(" ") || (name.endsWith(" "))) {
				Utils.messagePrompt("Can't being or end with a space!");
			} else {
				p.setName(name);
				InventoryHandler.createInventory(p);
				Skills.createSkills(p);
				Tools.createToolBelt(p);
				try {
					FileOutputStream fileOut = new FileOutputStream(savedir + p.getName()+".ser");//We save using .ser because we are serializing the player
					ObjectOutputStream objOut = new ObjectOutputStream(fileOut);//This lets us write the player object to their save file
					objOut.writeObject(p);
					objOut.close();//Closing the object stream since we don't need it anymore
					System.out.println("Player: "+ p.getName() + ", has been created.");
				} catch(IOException ioe) {
					System.out.println("There was an error in creating player file. \n"
							+ "Check file path for possible issue!");
				}			
			}
		}

	/**
	 * We'll make a dummy player object
	 * Populate the dummy with our player's stats
	 * 
	 * Create an array for the files in a savedir
	 * Populate the array with the info
	 * We'll then loop through the array and print out each file
	 * 
	 * Current Player params: name hp atk gold
	 * 
	 * Catching IOException again because we're handling input/output of files
	 * 
	 */
	public static void loadPlayer() {		
		/**
		 * Prompt character name and being loading process
		 * FileInputStream to import player file from savedir
		 * ObjectInputStream to import the data from the file into our Dummy player object
		 * And manipulate it with our Player Constructor
		 */
		String name = JOptionPane.showInputDialog("Enter Character Name:");
		try {
			FileInputStream fis = new FileInputStream(savedir + name + ".ser");
			ObjectInputStream ois = new ObjectInputStream(fis);//This will import the players file from the FileInputStream
			p = (Player) ois.readObject();//This is going to take our dummy player object and read the data from our ObjectInputStream and create the player from the player constructor
			ois.close();//Close ois since we're done loading player
			Main.mainScreen(p);
			Main.addMessage("Player: ["+ p.getName() +"] has been loaded!\nNow Displaying Player Information..\n");
			Main.addMessage(p.getStats()+"\n\n");
			InventoryHandler.loadInventory(p);
			Skills.loadSkills(p);
			Tools.loadToolBelt(p);
		} catch(IOException | ClassNotFoundException ioe) {
			System.out.println("Error loading player, improper name? Save directory missing?");
		}
	}
	
	/**
	 * Get current player information
	 * Save/Output the file :)
	 * 
	 * Current Player params: name hp atk gold
	 * 
	 * We are using IOException because we are handling the transference of files!!!
	 * 
	 * @param p = current Player object/file being used by game
	 * 
	 * 
	 */
	public static void savePlayer(Player p) {
		System.out.println("\nRetrieving Player's Information..");
		Utils.delay(1);
		
		InventoryHandler.saveInventory(p);
		Skills.saveSkills(p);
		Tools.saveToolBelt(p);

		System.out.println("\nSaving Player's Information..");
		Utils.delay(1);
		
		try {
			FileOutputStream fos = new FileOutputStream(savedir + p.getName() + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(p);
			oos.close();
			System.out.println(p.getName()+", has been saved!\n");
		} catch(IOException ioe) {
			System.out.println("Couldn't save the data! File path missing?");
		}
	}

	public static void deleteFile() {
		String name = JOptionPane.showInputDialog("Enter Character Name:");
		try {
			Path main = Paths.get(savedir+name+".ser");
			Path skills = Paths.get(savedir+name+"_skills.csv");
			Path toolBelt = Paths.get(savedir+name+"_toolbelt.csv");
			Path inventory = Paths.get("./data/inventory/"+name+".txt");

			Files.delete(main);
			Files.delete(skills);
			Files.delete(toolBelt);
			Files.delete(inventory);

			System.out.println("Player deleted!");
		} catch (IOException ioe) {
			System.out.println("This file doesn't exist! Check spelling!");
		}
	}
	



}
