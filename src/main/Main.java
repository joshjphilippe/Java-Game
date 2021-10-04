package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import handlers.CombatHandler;
import handlers.FileHandler;
import handlers.InventoryHandler;
import handlers.loaders.ArmorLoader;
import handlers.loaders.ItemLoader;
import handlers.loaders.NPCLoader;
import player.Player;
import player.Skills;
import player.Tools;
import player.skills.woodcutting.Trees;
import player.skills.woodcutting.Woodcutting;

/**
 * 
 * @author Joshua Jean-Philippe
 * This is currently CLI, converting to GUI tomorrow
 *
 */
public class Main {
	/*Core*/
	private static JFrame frame;
	private static Container container;
	
	/*Title Screen*/
	private static JPanel titlePanel, titleBPanel;
	private JLabel titleLabel;
	private JButton createButton, loadButton, deleteButton, exitButton;
	private Font titleFont = new Font("Sans-Serif", Font.BOLD, 50);

	/*Main Screen*/
	private static JPanel mainScreenPanel, playerDetailPanel, 
	playerInventoryPanel, mainScreenActionPanel, subPlayerDetailPanel;
	
	private static JLabel playerNameLabel, playerHpLabel, 
	playerAtkLabel, playerGoldLabel;
	
	private static JScrollPane verticalPane, inventoryPane;
	private static JButton combatTestButton, shoppingButton, woodCuttingTestButton, displaySkillsButton, displayToolBeltButton, safeExitButton;
	private static JTextArea console;
	public static JTextArea inventoryArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
					NPCLoader.loadNpcs();
					NPCLoader.loadNPCWeapons();
					ItemLoader.loadItems();
					Skills.loadXpTable();
					Trees.loadTrees();
					Tools.loadAllTools();
					ArmorLoader.loadAllEquipment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the GUI
	 * For now the main screen will just have a title
	 * and 3 buttons Create Load Exit
	 */
	public Main() {
		frame = new JFrame("Text Based Game - Now With GUI!");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(550, 550);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		container = frame.getContentPane();

		/*Title Screen*/
		titlePanel = new JPanel();
		titlePanel.setBounds(40, 25, 450, 100);
		
		titleLabel = new JLabel("Big Update!");
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(titleFont);
		titlePanel.add(titleLabel);
		
		titleBPanel = new JPanel();
		titleBPanel.setBounds(40, 450, 450, 50);//X Y position followed by Width Height
		titleBPanel.setLayout(new GridLayout(1, 4));//Layout the buttons in 1 row with 3 columns
		
		createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileHandler.createPlayer();
			}
		});
		createButton.setBackground(Color.WHITE);
		createButton.setForeground(Color.BLACK);
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileHandler.loadPlayer();
			}
		});
		loadButton.setBackground(Color.WHITE);
		loadButton.setForeground(Color.BLACK);

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileHandler.deleteFile();
			}
		});
		deleteButton.setBackground(Color.WHITE);
		deleteButton.setForeground(Color.BLACK);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		exitButton.setBackground(Color.WHITE);
		exitButton.setForeground(Color.BLACK);
		
		
		titleBPanel.add(createButton);
		titleBPanel.add(loadButton);
		titleBPanel.add(deleteButton);
		titleBPanel.add(exitButton);
		
		
		container.add(titlePanel);
		container.add(titleBPanel);
	}
	
	public static void mainScreen(Player p) {
		titlePanel.setVisible(false);
		titleBPanel.setVisible(false);
		
		/*Main Window*/
		mainScreenPanel = new JPanel();
		mainScreenPanel.setBounds(0, 0, 400, 400);
		mainScreenPanel.setLayout(new GridLayout(1, 1));
		
		/*Console area this is where game will happen for the most part*/
		console = new JTextArea();
		console.setLineWrap(true);
		console.setEditable(false);
		console.setCaretPosition(console.getDocument().getLength());
		console.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		

		verticalPane = new JScrollPane(console);
		verticalPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		mainScreenPanel.add(verticalPane);
		
		/*Player Information*/
		playerDetailPanel = new JPanel();
		playerDetailPanel.setBounds(410, 10, 115, 120);
		playerDetailPanel.setLayout(new GridLayout(4, 1));
		
		playerNameLabel = new JLabel("Name: "+p.getName());
		playerNameLabel.setForeground(Color.BLACK);
		playerHpLabel = new JLabel("HP: "+p.getHp());
		playerHpLabel.setForeground(Color.BLACK);
		playerAtkLabel = new JLabel("Atk: "+p.getAtk());
		playerAtkLabel.setForeground(Color.BLACK);
		playerGoldLabel = new JLabel("Gold: "+p.getGold());
		playerGoldLabel.setForeground(Color.BLACK);
		
		playerDetailPanel.add(playerNameLabel);
		playerDetailPanel.add(playerHpLabel);
		playerDetailPanel.add(playerAtkLabel);
		playerDetailPanel.add(playerGoldLabel);
		
		playerInventoryPanel = new JPanel();
		playerInventoryPanel.setBounds(410, 150, 115, 250);
		playerInventoryPanel.setLayout(new GridLayout(1, 1));

		subPlayerDetailPanel = new JPanel();
		subPlayerDetailPanel.setBounds(410, 403, 115, 100);
		subPlayerDetailPanel.setLayout(new GridLayout(4, 1));
		
		inventoryArea = new JTextArea();
		inventoryArea.setLineWrap(true);
		inventoryArea.setEditable(false);
		inventoryPane = new JScrollPane(inventoryArea);
		inventoryPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		playerInventoryPanel.add(inventoryPane);
		
		
		/*Main Screen Action Buttons*/
		mainScreenActionPanel = new JPanel();
		mainScreenActionPanel.setBounds(0, 401, 400, 50);
		mainScreenActionPanel.setLayout(new GridLayout(2, 4));
		
		combatTestButton = new JButton("Combat Test");
		combatTestButton.setBackground(Color.WHITE);
		combatTestButton.setForeground(Color.BLACK);
		combatTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*NPCHandler man = NPCLoader.spawnNPC(0);
				CombatHandler.startCombat(p, man);
				FileHandler.savePlayer(p);*/
				NPCLoader.spawnNPC(0, 0);
				NPCLoader.spawnNPC(1, 1);

				Main.addMessage("\n----------------");
				NPCLoader.whoSpawned();

				CombatHandler.startCombat(p);
				FileHandler.savePlayer(p);
			}
		});
		
		shoppingButton = new JButton("Refresh inventory");
		shoppingButton.setBackground(Color.WHITE);
		shoppingButton.setForeground(Color.BLACK);
		shoppingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshInventory(p);
				p.setHp(10);
				updateHp(p);
				FileHandler.savePlayer(p);
			}
		});

		woodCuttingTestButton = new JButton("Test Woodcutting!");
		woodCuttingTestButton.setBackground(Color.WHITE);
		woodCuttingTestButton.setForeground(Color.BLACK);
		woodCuttingTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Trees.spawned.clear();
				for(int i = 0; i < 6; i++) {
					Trees.spawnTrees(i, 0);
				}
				Woodcutting.chopTree(p);
			}
		});

		displaySkillsButton = new JButton("Skills");
		displaySkillsButton.setBackground(Color.WHITE);
		displaySkillsButton.setForeground(Color.BLACK);
		displaySkillsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.addMessage("\n\n=======================");
				Main.addMessage("Skill - Level - Exp");
				Skills.displaySkills();
				Main.addMessage("=======================\n\n");
			}
		});

		displayToolBeltButton = new JButton("Toolbelt");
		displayToolBeltButton.setBackground(Color.WHITE);
		displayToolBeltButton.setForeground(Color.BLACK);
		displayToolBeltButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.addMessage("\n\n=======================");
				Main.addMessage("Tool - Attribute Value - Durability");
				Tools.displayToolbelt();
				Main.addMessage("=======================\n\n");
			}
		});

		safeExitButton = new JButton("Safe Exit");
		safeExitButton.setBackground(Color.WHITE);
		safeExitButton.setForeground(Color.BLACK);
		safeExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileHandler.savePlayer(p);
				frame.dispose();
				System.exit(0);
			}
		});
		
		
		mainScreenActionPanel.add(combatTestButton);
		mainScreenActionPanel.add(shoppingButton);
		mainScreenActionPanel.add(woodCuttingTestButton);

		subPlayerDetailPanel.add(displaySkillsButton);
		subPlayerDetailPanel.add(displayToolBeltButton);
		subPlayerDetailPanel.add(safeExitButton);
		
		container.add(mainScreenPanel);
		container.add(playerDetailPanel);
		container.add(mainScreenActionPanel);
		container.add(playerInventoryPanel);
		container.add(subPlayerDetailPanel);

		SwingUtilities.updateComponentTreeUI(frame);
	}
	
	/*
	 * We need a custom method to access throughout the program
	 * For adding text to the console
	 */
	public static void addMessage(String message) {
		console.append(message+"\n");
	}
	
	public static void updateHp(Player p) {
		playerHpLabel.setText("HP: "+p.getHp());
	}
	
	public static void refreshInventory(Player p) {
		inventoryArea.setText("");
		InventoryHandler.loadInventory(p);
	}

	public static void addMessageObject(Object object) {
		console.append(object.toString());
	}

}
