package main.zones;

import java.util.Random;
import java.util.Scanner;

import handlers.FileHandler;
import handlers.InventoryHandler;
import handlers.Utils;
import handlers.loaders.ItemLoader;
import handlers.loaders.NPCLoader;
import main.Main;
import player.Player;

/**
 * @author Joshua Jean-Philippe
 * This the the StarterPlains zone
 * This Zone will be used for everything I want to test
 * Then when it's game time I can see what methods I need
 * and just create a ZoneTemplate class and implement it
 * for whatever zones I'm making
 */
public class StarterPlains {
    

    public static String zoneStart = "Welcome to the Starter Plains!";
    public static String zoneDescription = "All new players begin here.\nDuring prealpha at least!";
    public static int[] npcs = {NPCLoader.npcs.get(2).getId(), NPCLoader.npcs.get(3).getId()};
    public static String[] zoneOptions = {"Travel", "Train", "Rest", "Return"};
    public static String[] travelOptions = {"Town", "Forest", "Cave", "Return"};
    public static String[] trainOptions = {"Woodcutting", "Return"};
    public static String[] movementOptions = {"Up", "Down", "Left", "Right"};
    public static int[] smallChest = {ItemLoader.items.get(12).getItemId(), ItemLoader.items.get(13).getItemId()};
    private static Random rand = new Random();

    public static void enterZone(Player p) {
        Main.addMessage(zoneStart);
        Main.addMessage(zoneDescription);

        cave(p);

    }

    /**
     * How do cave??????????????
     * Yes No
     * idk
     * @param p
     */

     /**
      * this is working so far. more developing and bug testing.
      * @param p
      */
    public static void cave(Player p) {
        for(int i = 0; i < 7; i++) {
            if(Math.random() < 0.8) {
                NPCLoader.spawnNPC(i, npcs[0]);
            } else {
                NPCLoader.spawnNPC(i, npcs[1]);
            }
        }
        NPCLoader.whoSpawned();
        int pX = 0; 
        int pY = 0;
        int caveXEnd = 5; 
        int caveYEnd = 5;
        char direction;
        System.out.println("Player X: "+pX+", Player Y: "+pY);
        Scanner scanner = new Scanner(System.in);
        boolean Running = true;
        while(Running) {
            direction = scanner.next().charAt(0);
            switch(direction) {
                case 'w':
                case 'W':
                    if(pX >= 0 && pY == 0) {
                        System.out.println("There is a wall here!");
                    } else {
                        pX++;
                        System.out.println("Player X: "+pX+", Player Y: "+pY);
                    }
                    break;
                case 's':
                case 'S':
                    if(pX == caveXEnd && pY == caveYEnd) {
                        System.out.println("There is a wall!");
                    } else {
                        pY--;
                        System.out.println("Player X: "+pX+", Player Y: "+pY);
                    }
                    break;
            }
        }
        /*int x, y;
        int pX = 0;
        int pY = 0;
        char player;
        boolean Running = true;
        Scanner input = new Scanner(System.in);
        System.out.print("X: ");
        x = input.nextInt();
        System.out.print("Y: ");
        y  = input.nextInt();
        int cave[][] =  new int[x][y];
        cave[pX][pY] = 1;
        print(cave);
        while(Running) {
            player = input.next().charAt(0);
            switch(player) {
                case 'w':
                case 'W':
                    cave[pX][pY] = 0;
                    cave[--pX][pY] = 1;
                    print(cave);
                    break;
                case 's':
                case 'S':
                    cave[pX][pY] = 0;
                    cave[++pX][pY] = 1;
                    print(cave);
                    break;
                case 'd':
                case 'D':
                    cave[pX][pY] = 0;
                    cave[pX][++pY] = 1;
                    print(cave);
                    break;
                case 'a':
                case 'A':
                    cave[pX][pY] = 0;
                    cave[pX][--pY] = 1;
                    print(cave);
                    break;
            }
        }*/
    }

    private static void print(int[][] cave) {
        for(int y = 0; y < cave.length; y++) {
            for(int x = 0; x < cave[0].length; x++) {
                System.out.print(cave[y][x] + "\t");
            }
            System.out.println();
        }
    }

    public static void rollChestFound(Player p) {
        if(Math.random() < 0.5) {
            Utils.messagePrompt("You've found a small chest!");
            Main.addMessage("\n===================");
            for(int i = 0; i < smallChest.length; i++) {
                int randGold = rand.nextInt(50);
                int addedGold = p.getGold() + randGold;
                String itemName = ItemLoader.items.get(smallChest[i]).getItemName();
                int itemPrice = ItemLoader.items.get(smallChest[i]).getPrice();
                String itemDesc = ItemLoader.items.get(smallChest[i]).getDesc();
                int amount = rand.nextInt((5 - 1) + 1);
                Main.addMessage("Item: ["+itemName+"] -- Price: ["+itemPrice+"] -- Desc: ["+itemDesc+"]");
                InventoryHandler.addItem(itemName, amount);
                p.setGold(addedGold);
            }
            InventoryHandler.refreshInventory(p);
            FileHandler.savePlayer(p);
            Main.addMessage("===================");
        }
    }




}
