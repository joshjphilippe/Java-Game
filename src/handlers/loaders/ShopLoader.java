package handlers.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import handlers.FileHandler;
import handlers.InventoryHandler;
import handlers.ItemHandler;
import handlers.ShopHandler;
import handlers.Utils;
import main.Main;
import player.Player;

public class ShopLoader {

    public static ArrayList<ShopHandler> currentShop = new ArrayList<ShopHandler>();

    private static final String dir = "./data/shops/";
    private static String[] shopOptions = {"Buy", "Sell", "Leave"};

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
                    Utils.messagePrompt("This doesn't work yet!");
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
                        Main.reloadInvView(p);
                        Main.updateGold(p);
                        Utils.messagePrompt("You have successfully purchased: ["+amount+"] ["+itemName+"(s)]!");
                    }
                    boolean goodOption = false;
                    shopAgain: do {
                        String again = JOptionPane.showInputDialog(null, "Would you like to buy another item? 'yes' or 'no'");
                        if(again.equalsIgnoreCase("yes") && goodOption == false) {
                            goodOption = true;
                            buyItems(p, shopTitle);
                        } else if(again.equalsIgnoreCase("no") && goodOption == false) {
                            /** Might loop this to bring back to the original shop menu, probably not */
                            goodOption = true;
                            Utils.messagePrompt("Thank you, come again!");
                            break shopAgain;
                        } else if(!again.equalsIgnoreCase("yes") || !again.equalsIgnoreCase("no")) {
                            Utils.messagePrompt("What?");
                        }
                    } while(!goodOption == true);
                }
            }
        }
    }


    
}
