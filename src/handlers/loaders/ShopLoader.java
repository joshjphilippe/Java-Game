package handlers.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import handlers.FileHandler;
import handlers.InventoryHandler;
import handlers.ShopHandler;
import handlers.Utils;
import jdk.jshell.execution.Util;
import main.Main;
import player.Player;

public class ShopLoader {

    public static ArrayList<ShopHandler> currentShop = new ArrayList<ShopHandler>();

    private static final String dir = "./data/shops/";
    private static String[] shopOptions = {"Buy", "Sell", "Leave"};
    private static String[] loopOptions = {"Yes", "No"};

    public static void loadShop(Player p, String shopName) {
        if(!currentShop.isEmpty()) {
            currentShop.clear();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(dir+shopName+".csv")));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] i = line.split(",");
                //ID, Stock
                ShopHandler item = new ShopHandler(Integer.parseInt(i[0]), Integer.parseInt(i[1]));
                currentShop.add(item);
            }
            displayShop();
            System.out.println("Shop ["+shopName+"] Loaded: "+currentShop.size());
        } catch (IOException ioe) {
            System.out.println("Shop ["+shopName+"] File not found!");
        }
    }

    public static void displayShop() {
        for(int i = 0; i < currentShop.size(); i++) {
            Main.addNoSMessage("Index: ["+i+"] "+ItemLoader.items.get(currentShop.get(i).getItemId()).toString()+"\nQuantity: ["+currentShop.get(i).getStock()+"]\n\n");
        }
    }

    public static void enterShop(Player p, String shopTitle) {
        boolean optionSelected = false;
       loop: do {
           int shopChoice = JOptionPane.showOptionDialog(null, "What would you like to do?", "Welcome to "+shopTitle+"!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, shopOptions, null);
           switch(shopChoice) {
               case 0:
                    optionSelected = true;
                    buyItems(p, shopTitle);
                    break;
                case 1:
                    optionSelected = true;
                    sellItems(p, shopTitle);
                    break;
                case 2:
                    optionSelected = true;
                    Utils.messagePrompt("Thank you, come again!");
                    break loop;
            }
        } while(!optionSelected == true);
    }

    private static void sellItems(Player p, String shopTitle) {
        boolean isValid = false;
        itemSelected: while(isValid == false) {
            String input = JOptionPane.showInputDialog(null, "Enter the name of the item you would like to sell (case sensitive):");
            if(input.isBlank() || input.isEmpty()) {
                Utils.messagePrompt("Cannot contain empty space or be blank!");
            } else if(input.matches("[0-9]+")) {
                Utils.messagePrompt("There aren't any numerical items!");
            } else if(!InventoryHandler.inventory.containsKey(input)) {
                Utils.messagePrompt("Cannot locate this item within Inventory!");
            } else if(InventoryHandler.inventory.get(input) == 0) {
                Utils.messagePrompt("You no longer have this item!");
                InventoryHandler.inventory.remove(input);
                InventoryHandler.refreshInventory(p);
            } else if(InventoryHandler.inventory.containsKey(input)) {
                isValid = true;
                System.out.println("Yes, hello: "+input);
                sellProcess(p, input, shopTitle);
            } else if(input.equalsIgnoreCase("exit")) {
                Utils.messagePrompt("Thank you, come again!");
                break itemSelected;
            }
        }
    }

    private static void sellProcess(Player p, String item, String shopTitle) {
        checklist: for(int i = 0; i < ItemLoader.items.size(); i++) {
            String itemName = ItemLoader.items.get(i).getItemName();
            /**
             * Loop through all of our game items
             * Once we find the item, get the price
             */
            if(itemName.equals(item)) {
                int itemPrice = ItemLoader.items.get(i).getPrice();
                boolean isValid = false;
                goodAmount: while(isValid == false) {
                    String qInput = JOptionPane.showInputDialog(null, "How many would you like to sell?");
                    if(qInput.isBlank() || qInput.isEmpty()) {
                        Utils.messagePrompt("Cannot contain empty space or be blank!");
                    } else if(qInput.matches("^[a-zA-Z]*$")) {
                        Utils.messagePrompt("Cannot contain alpha or special characters!");
                    } else if(qInput.matches("[0-9]+")) {
                        isValid = true;
                        int quantity = Integer.parseInt(qInput);
                        if(InventoryHandler.inventory.containsKey(item)) {
                            int itemAmount = InventoryHandler.inventory.get(item);
                            if(quantity > itemAmount) {
                                Utils.messagePrompt("You do not have that many of this item!");
                            } else if(quantity == 0 || quantity <= -1) {
                                Utils.messagePrompt("You cannot sell 0 or negative of something!");
                            } else if(quantity <= itemAmount) {
                                int revenue = itemPrice * quantity;
                                int addGold = p.getGold() + revenue;
                                InventoryHandler.removeItem(item, quantity);
                                InventoryHandler.refreshInventory(p);
                                p.setGold(addGold);
                                FileHandler.savePlayer(p);
                                Main.updateGold(p);
                                Utils.messagePrompt("You've sold: ["+quantity+"] ["+item+"(s)] for [+"+revenue+"] gold!");
                            }
                        }
                    }
                    boolean goodOption = false;
                    shopAgain: do {
                        int again = JOptionPane.showOptionDialog(null, "Would you like to shop again?", "Welcome to "+shopTitle+"!", JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, null, loopOptions, null);
                        switch(again) {
                            case 0:
                                goodOption = true;
                                sellItems(p, shopTitle);
                                break;
                            case 1:
                                /** Might loop this to bring back to the original shop menu, probably not */
                                goodOption = true;
                                Utils.messagePrompt("Thank you, come again!");
                                break shopAgain;
                        }
                    } while(!goodOption == true);
                }
                break checklist;
            }
        }
    }

    private static void buyItems(Player p, String shopTitle) {
        boolean isValid = false;
        selectItem: while(isValid == false) {
            String choice = JOptionPane.showInputDialog(null, "Enter Item Index");
            if(choice.isBlank() || choice.isEmpty()) {
                Utils.messagePrompt("Cannot contain empty space or be blank!");
            } else if(choice.matches("^[a-zA-Z]*$")) {
                Utils.messagePrompt("Cannot contain alpha or special characters!");
            } else if(choice.length() > 3) {
                Utils.messagePrompt("No item index is greater than 3 places!");
            } else if(choice.matches("[0-9]+")) {
                int index = Integer.parseInt(choice);
                if(index > -1 && index <= currentShop.size() - 1) {
                    isValid = true;
                    buyProcess(p, index, shopTitle);
                } else {
                    Utils.messagePrompt("This isn't a valid item within this shop!");
                }
            }
        }
    }
    
    /**
     * TODO:
     * Create a temporary file for whatever shop was loaded
     * After the player shops.
     * Then when this specific shop is loaded again, check for filepath of this shop
     * And load that specific shop.
     * 
     * ^^^ So this is another check that needs to bedone within the LoadShop method
     * The issue with this is that this game isn't constantly running.
     * 
     * Maybe a way to refresh the shop would be to check the files creation date and time?
     * within the loadshop and after x amount of time we delete this temp shop file?
     * Idk, because right now the player can just close and reopen the game and shop
     * Which will refresh the stock.
     * @param p
     * @param index
     * @param shopTitle
     */
    private static void buyProcess(Player p, int index, String shopTitle) {
        boolean isValid = false;
        System.out.println("Yes, hello: "+index);
        shopping: while(isValid == false) {
            String input = JOptionPane.showInputDialog(null, "How many would you like to buy?");
            if(input.isBlank() || input.isEmpty()) {
                Utils.messagePrompt("Cannot contain empty space or be blank!");
            } else if(input.matches("^[a-zA-Z]*$")) {
                Utils.messagePrompt("Cannot contain alpha or special characters!");
            } else if(input.matches("[0-9]+")) {
                int amount = Integer.parseInt(input);
                int itemStock = currentShop.get(index).getStock();
                if(amount == 0 || amount <= -1) {
                    Utils.messagePrompt("You cannot purchase a negative or 0 amount!");
                } else if(amount > itemStock) {
                    Utils.messagePrompt("You cannot purchase more than what the shop has!");
                } else if(amount >= 1 && amount <= itemStock) {
                    isValid = true;
                    String itemName = ItemLoader.items.get(currentShop.get(index).getItemId()).getItemName();
                    int itemPrice = ItemLoader.items.get(currentShop.get(index).getItemId()).getPrice();
                    System.out.println("Item price: "+itemPrice);
                    System.out.println("Item stock: "+itemStock);
                    int playerGold = p.getGold();
                    int cost = itemPrice * amount;
                    int missAmt = cost - playerGold;
                    if(playerGold < cost) {
                        Utils.messagePrompt("You do not have enough gold! You need: [+"+missAmt+"] more gold for ["+amount+"] ["+itemName+"(s)]!");
                    } else if(playerGold >= cost) {
                        int fee = playerGold - cost;
                        int reduceStock = itemStock - amount;
                        p.setGold(fee);
                        currentShop.get(index).setStock(reduceStock);
                        displayShop();
                        InventoryHandler.addItem(itemName, amount);
                        InventoryHandler.refreshInventory(p);
                        FileHandler.savePlayer(p);
                        Main.updateGold(p);
                        Utils.messagePrompt("You have successfully purchased: ["+amount+"] ["+itemName+"(s)]!");
                    }
                    boolean goodOption = false;
                    shopAgain: do {
                        int again = JOptionPane.showOptionDialog(null, "Would you like to shop again?", "Welcome to "+shopTitle+"!", JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, null, loopOptions, null);
                        switch(again) {
                            case 0:
                                goodOption = true;
                                sellItems(p, shopTitle);
                                break;
                            case 1:
                                /** Might loop this to bring back to the original shop menu, probably not */
                                goodOption = true;
                                Utils.messagePrompt("Thank you, come again!");
                                break shopAgain;
                        }
                    } while(!goodOption == true);
                }
            }
        }
    }


    
}
