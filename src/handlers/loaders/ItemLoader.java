package handlers.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import handlers.ItemHandler;

/**
 * @author Joshua Jean-Philippe
 * Handles item loading
 * Items will be stored in CSV file and loaded from CSV
 */
public class ItemLoader {

	private static final String dir = "./data/items.csv";
	public static ArrayList<ItemHandler> items = new ArrayList<ItemHandler>();

	public static void loadItems() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(dir)));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] i = line.split(",");
                //id name price desc
				ItemHandler item = new ItemHandler(Integer.parseInt(i[0]), i[1], Integer.parseInt(i[2]), i[3]);
				items.add(item);
			}
			System.out.println("Items Loaded: "+items.size());
		} catch (IOException ioe) {
			System.out.println("Item File not found!");
		}
	}
 
}
