package handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import main.Main;
import player.Player;

/**
 * @author: Joshua Jean-Philippe
 * Using hashmap to store player inventory in Key-Value pairs
 * Need to add item descriptions too..
 * Checks for valid items in items.txt
 */
public class InventoryHandler {

    private final static String dir = "./data/inventory/";
    public static Map<String, Integer> inventory = new HashMap<>();
    
    /**
     * Create a text file with player name
     * If the name/inventory exists, shutdown program
     * @param p
     */
    public static void createInventory(Player p) {
        String name = p.getName();
        File f = new File(dir+name+".txt");
        try {
            if(f.createNewFile()) {
                System.out.println(name+" Inventory Created");
                saveInventory(p);
            } else {
                System.out.println("Inventory exists! Exiting Program!");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the player's inventory file
     * Write the new contents of inventory to HashMap
     */
    public static void saveInventory(Player p) {
        try {
            File f = new File(dir+p.getName()+".txt");
            BufferedWriter bw = new BufferedWriter((new FileWriter(f)));
            for(Map.Entry<String, Integer> entry : inventory.entrySet()) {
                bw.write(entry.getKey()+":"+entry.getValue());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException ioe) {
            System.out.println("Error with saving inventory! Directory not found?");
            ioe.printStackTrace();
        }

    }

    /**
     * Load player file
     * Read each line and split the Key:Value pairs at the :
     * store the Key Values into hashmap
     * @param p
     */
    public static void loadInventory(Player p) {
    	if(!inventory.isEmpty())
    		inventory.clear();
        try {
            File f = new File(dir+p.getName()+".txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] kv = line.split(":");
                String item = kv[0].trim();
                Integer amount = Integer.parseInt(kv[1].trim());
                if(!item.equals("") && !amount.equals("")) {
                    inventory.put(item, amount);
                }
            }
            br.close();
            displayInventory();
        } catch (IOException ioe) {
            System.out.println("Error with loading inventory! File not found?");
            ioe.printStackTrace();
        }
    }

    /**
     * Loop through keyvalue pairs of hashmap
     */
    public static void displayInventory() {
    	for(String i : inventory.keySet()) {
    		Main.inventoryArea.append(i+":"+inventory.get(i)+"\n");
    	}
    }

    /**
     * Check if inventory already contains item
     * if it does, then add the new amount to it
     * if it doesn't, then add the new item
     * @param name
     * @param amount
     */
    public static void addItem(String name, int amount) {
        if(inventory.containsKey(name)) {
            inventory.put(name, inventory.get(name) + amount);
        } else {
            inventory.put(name, amount);
        }
    }

    /**
     * Does the same as addItem but we're subtracting now
     * However if the item is <= 0 we delete it from hashmap
     * @param name
     * @param amount
     */
    public static void removeItem(String name, int amount) {
        if(inventory.containsKey(name)) {
            inventory.put(name, inventory.get(name) - amount);
        } else if(inventory.containsKey(name) && inventory.get(name) == 1) {
                inventory.remove(name);
        }
    }

}

