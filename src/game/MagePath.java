 

/**
 * extra quest for "Medieval Adventure"
 *
 * @author Tobin Brenner (Started on Apr 19 2022)
 * @version 1.0
 */
package game;
 
 
import java.util.Scanner;
import java.util.Random;
import java.io.IOException;

public class MagePath { 
	
	static final String INPUT_INDICATOR = "...";
	static final String NUMBER_TOO_LARGE = "Woah, that number is pretty big.";
	static final String INVALID_OPTION = "Enter a valid option.";
	
	static boolean debug = false;

	static Scanner scan = new Scanner(System.in);
	static Random rand = new Random();

	static String lineInput = null; //used for whatever strings the player is inputting into the console
	static int intInput = 0; //used for whatever ints the player is inputting into the console

	static String enemyName = null;
	static String enemyDescription = null;
	static String[] enemyAttackList = new String[3];
	static int[] enemyAttackDamage = new int[3];
	static int enemyAttack = 0;
	static int enemyMaxHealth = 0;
	static int enemyHealth = 0;
	static int enemyGoldDropAmount  = 0;
	static int enemyDefence = 0; //enemy defence multiplier (percentage value)
	static double[] enemyHitRate = new double[3]; //enemy hitrate multiplier (percentage value)

	static String playerName = "";
	static String[] playerAttackList = new String[3];
	static int[] playerAttackDamage = new int[3];
	static int playerMaxHealth = 20;
	static int playerHealth = playerMaxHealth;
	static int playerDefence = 0; //enemy defence multiplier (percentage value)
	static double[] playerHitRate = new double[3]; //enemy hitrate multiplier (percentage value)

	static int playerMaxMagic = 10;
	static int playerMagic = playerMaxMagic;

	static int experience = 0;
	static int level = 1;
	
	static int enemyLevel = 1;

	static boolean playerTurn = false; //to determine if it's the players turn or the enemies turn
	boolean attackMissed = false;

	static String battleIntro = "Get ready to fight!";
	static String hudMessage1 = null;
	static String hudMessage2 = null;
	static String hudMessage3 = null;
	static String hudMessage4 = null;
	static String hudMessage5 = null;
	static int damage = 0;
	
	static boolean loop = true;
	static boolean alternateTurn = true;

	static boolean dead = false; //if true, then ur ded :p
	
	static boolean gameWon = false; //if true, then on exit of the game you will be shown the end screen
	
	static boolean grammarAn = false; //for proper grammar

	static int playTime; //the number of miliseconds that you have played the game for

	static int intDump;
	/*
	4 weapon upgrades:
	you start with: wooden staff (1x)
	buy from the shop: bronze staff (2x)
	find in the woods: gold plated staff (3x)
	find in the tower: enchanted staff (4x)

	3 armor upgrads:
	you start with: nothing
	buy from the shop: hunting armour
	find in the cave: chainmail armour
	 */

	public static void nextLine() {
		System.out.print("\n" + INPUT_INDICATOR);
		lineInput = scan.nextLine();
		if (lineInput.isEmpty()) {
			lineInput = " ";
		}
	}

	public static void lineConfirm() {
		boolean gotPrompt = false;
		System.out.print("\n" + INPUT_INDICATOR);
		while (loop) {
			lineInput = scan.nextLine();
			if (lineInput.isEmpty()) {
				lineInput = " ";
			}
			if (lineInput.charAt(0) != WorldMap.keyMoveUp 
			|| lineInput.charAt(0) != WorldMap.keyMoveLeft 
			|| lineInput.charAt(0) != WorldMap.keyMoveDown 
			|| lineInput.charAt(0) != WorldMap.keyMoveRight) {
				loop = false;
			} else if (!gotPrompt) {
				WorldMap.printText("Please enter a value other than " 
				+ WorldMap.keyMoveUp + ", " 
				+ WorldMap.keyMoveLeft + ", " 
				+ WorldMap.keyMoveDown + ", or " 
				+ WorldMap.keyMoveRight + ".");
				gotPrompt = true;
			}
		}
		loop = true;
	}

	public static int enterNumber(int lowerBound, int upperBound) {
		int tempInput = 0;
		boolean tempLoop = true;
		boolean inputLoop = true;
		
		if (upperBound == -1) {
			upperBound = 999999999; //999 million
		}
		
		while (inputLoop) {
			String temp = scan.nextLine();
		
			tempLoop = false;
			if (temp.length() > 6) {
				WorldMap.printText(NUMBER_TOO_LARGE + "\n");
			} else if (temp.isEmpty()) {
				WorldMap.printText(INVALID_OPTION + "\n");
			} else {
				tempLoop = true;
			}
			
			while (tempLoop) {
				tempInput = Integer.parseInt(temp);
				
				if ((tempInput < lowerBound) || (tempInput > upperBound)) {
					WorldMap.printText(INVALID_OPTION + "\n");
				} else {
					tempLoop = false;
					inputLoop = false;
				}
			}
		}
		return tempInput;
	}
	
	public static void getEnemyStats(int id) {
		/*
		VARIABLE NAMES AND USES
		enemyName: display name of the enemy
		enemyDescription: description of the enemy when you use the "inspect enemy" move
		enemyMaxHealth: max health of enemy
		enemyDefence: damage multiplier, your attacks are multiplied by this value to lower the damage
		enemyAttackList[x]: name of the attack, indexes 0-2
		enemyAttackDamage[x]: damage of the attack, indexes 0-2
		enemyHitRate[x]: % chance of the enemies attack to hit, if it doesn't hit then well, it will miss
		enemyGoldDropAmount: base value of gold coins for the enemy to drop
		 */
		enemyLevel = level + (rand.nextInt(7) - 5);
		
		if (enemyLevel <= 0) {
			enemyLevel = 1;
		}
		
		if (id == 1) {
			enemyName = "Training Dummy";
			enemyDescription = "Focus on the fight!";
			enemyMaxHealth = 30;
			enemyDefence = 1;
			enemyAttackList[0] = "Vicious Stare";
			enemyAttackDamage[0] = 0;
			enemyAttackList[1] = "Head Butt";
			enemyAttackDamage[1] = 3;
			enemyAttackList[2] = "Aggressive Kick";
			enemyAttackDamage[2] = 6;
			enemyHitRate[0] = 100;
			enemyHitRate[1] = 100;
			enemyHitRate[2] = 100;
			enemyGoldDropAmount = 5;
		} else if (id == 2) {
			enemyName = "Trader Steve";
			enemyDescription = "A humble traveller. Do not go easy on him, for he will not go easy on you, as long as he is teaching.";
			enemyMaxHealth = 20;
			enemyDefence = 1;
			enemyAttackList[0] = "Punch";
			enemyAttackDamage[0] = 3;
			enemyAttackList[1] = "Kick";
			enemyAttackDamage[1] = 6;
			enemyAttackList[2] = "Sword Swipe";
			enemyAttackDamage[2] = 10;
			enemyHitRate[0] = 100;
			enemyHitRate[1] = 100;
			enemyHitRate[2] = 100;
		} else if (id == 3) {
			enemyName = "Barbarian";
			enemyDescription = "These people wish ill on everyone, it seems. Defeat him for the good of all, and not just you!";
			enemyMaxHealth = 30;
			enemyDefence = 1;
			enemyAttackList[0] = "Boomerang";
			enemyAttackDamage[0] = 3;
			enemyAttackList[1] = "NONAME";
			enemyAttackDamage[1] = 6;
			enemyAttackList[2] = "Scicle Slash";
			enemyAttackDamage[2] = 10;
			enemyHitRate[0] = 100;
			enemyHitRate[1] = 100;
			enemyHitRate[2] = 100;
		} else if (id == 4) {
			enemyName = "Sorcerer's Footsoldier";
			enemyDescription = "A henchman of the evil sorcerer.";
			enemyMaxHealth = 20;
			enemyDefence = 1;
			enemyAttackList[0] = "Punch";
			enemyAttackDamage[0] = 3;
			enemyAttackList[1] = "Kick";
			enemyAttackDamage[1] = 6;
			enemyAttackList[2] = "Sword Swipe";
			enemyAttackDamage[2] = 10;
			enemyHitRate[0] = 100;
			enemyHitRate[1] = 100;
			enemyHitRate[2] = 100;
		} else if (id == 5) {
			enemyName = "Troll";
			enemyDescription = "A troll of the woods. They have moves that you may not see coming, be carful.";
			enemyMaxHealth = 20;
			enemyDefence = 1;
			enemyAttackList[0] = "placeholder 1";
			enemyAttackDamage[0] = 3;
			enemyAttackList[1] = "placeholder 2";
			enemyAttackDamage[1] = 6;
			enemyAttackList[2] = "placeholder 3";
			enemyAttackDamage[2] = 10;
			enemyHitRate[0] = 100;
			enemyHitRate[1] = 100;
			enemyHitRate[2] = 100;
		} else if (id == 6) {
			enemyName = "Bad Bat";
			enemyDescription = "These bats are more agressive than normal bats...";
			enemyMaxHealth = 15;
			enemyDefence = 1;
			enemyAttackList[0] = "placeholder 1";
			enemyAttackDamage[0] = 7;
			enemyAttackList[1] = "placeholder 2";
			enemyAttackDamage[1] = 7;
			enemyAttackList[2] = "placeholder 3";
			enemyAttackDamage[2] = 7;
			enemyHitRate[0] = 100;
			enemyHitRate[1] = 100;
			enemyHitRate[2] = 100;
		} else {
			enemyName = "MissingNO.";
			enemyDescription = "Invalid enemy ID was used!";
			enemyMaxHealth = 30;
			enemyDefence = 1;
			enemyAttackList[0] = "Attack 1";
			enemyAttackDamage[0] = 5;
			enemyAttackList[1] = "Attack 2";
			enemyAttackDamage[1] = 5;
			enemyAttackList[2] = "Attack 3";
			enemyAttackDamage[2] = 5;
			enemyHitRate[0] = 100;
			enemyHitRate[1] = 100;
			enemyHitRate[2] = 100;
			enemyGoldDropAmount = 0;
		}
	}

	public static String playerHudStats() {
		return playerName + " HP:" + playerHealth + "/" + playerMaxHealth + " MP:" + playerMagic + "/" + playerMaxMagic + " LVL " + level;
	}

	public static String enemyHudStats() {
		return enemyName + " HP:" + enemyHealth + "/" + enemyMaxHealth + " LVL " + enemyLevel;
	}

	private static void printBattleHud() {
		
		if (enemyHealth < 0) {
			enemyHealth = 0;
		}
		if (playerHealth < 0) {
			playerHealth = 0;
		}
		if (damage < 0) {
			damage = 0;
		}

		clearConsole();

		System.out.print(enemyHudStats() + "\n\n\n" 
			+ playerHudStats() + "\n"
		);
		if (!hudMessage1.isEmpty()) {
			WorldMap.printText(hudMessage1 + "\n");
		}
		if (!hudMessage2.isEmpty()) {
			WorldMap.printText(hudMessage2 + "\n");
		}
		if (!hudMessage3.isEmpty()) {
			WorldMap.printText(hudMessage3 + "\n");
		}
		if (!hudMessage4.isEmpty()) {
			WorldMap.printText(hudMessage4 + "\n");
		}
		if (!hudMessage5.isEmpty()) {
			WorldMap.printText(hudMessage5 + "\n");
		}
	}
	
	private static void playAttackSound() {
		int soundIndex = rand.nextInt(2);
		
		switch (soundIndex) {
			case 0:
				WorldMap.sfx = new AudioPlayer(AudioPlayer.DAMAGE1_PATH, false, WorldMap.sfxVolume);
				break;
			case 1:
				WorldMap.sfx = new AudioPlayer(AudioPlayer.DAMAGE2_PATH, false, WorldMap.sfxVolume);
				break;
			case 2:
				WorldMap.sfx = new AudioPlayer(AudioPlayer.DAMAGE3_PATH, false, WorldMap.sfxVolume);
				break;
		}
		WorldMap.sfx.play();
	}
	
	public static void startFight(int enemyID) {
		clearConsole();
		int enemyAttackIndex = 0;
		int playerAttackIndex = 0;
		
		
		getEnemyStats(enemyID);
		enemyHealth = enemyMaxHealth;

		WorldMap.music = new AudioPlayer(AudioPlayer.BATTLE_PATH, true, WorldMap.volume);
		WorldMap.music.play();

		playerTurn = rand.nextBoolean();
		if (enemyName.equals("Trader Steve")) {
			if (playerTurn) {
				WorldMap.printDialogue("Trader Steve,\nI will let you go first.");
			} else {
				WorldMap.printDialogue("Trader Steve,\nI will attack you now!");
			}
		} else {
			if (grammarAn) {
				WorldMap.printText("An " + enemyName + " appears!");
			} else {
				WorldMap.printText("A " + enemyName + " appears!");
			}
			if (playerTurn) { //if it's true, then it's the players turn
				WorldMap.printText(" They don't notice you. It's your time to strike!");
			} else {
				WorldMap.printText(" They are about to attack!");
			}
		}
		nextLine();

		/*
		The hud needs to contain:
		enemy name, enemy health
		(one line of padding)
		player name, player health, player magic meter, player level

		some kind of text message for the player to read, but if there's nothing this will serve as a padding line
		(one line of padding)
		CAN CONTAIN
		ascii art of the fighters (not nessacary (prob won't do it) but would be SICK [prob not doing this])
		 */
		loop = true;

		hudMessage1 = "";
		hudMessage2 = "";
		hudMessage3 = "";
		hudMessage4 = "";
		hudMessage5 = "";
		damage = 0;

		enemyGoldDropAmount += rand.nextInt(9) - 5; //variating the coin drop amount, can vary between -4 and +4 of the base value

		if (enemyGoldDropAmount < 0) {
			enemyGoldDropAmount = 0;
		}
		
		while (loop) { //start of battle loop
			if (!dead) { //start of alive if
				// playerHealth = playerMaxHealth; //cheat code? idk if you want to call it that or not lol
				if (playerTurn) {
					hudMessage1 = "It's your turn! What move would you like to do?";
					hudMessage2 = "1 - " + playerAttackList[0];
					hudMessage3 = "2 - " + playerAttackList[1];
					hudMessage4 = "3 - " + playerAttackList[2];
					hudMessage5 = "4 - Inspect Enemy";

					printBattleHud();

					intInput = enterNumber(1, 4);

					if (intInput == 4) {
						hudMessage1 = enemyDescription;
						hudMessage2 = "";
						hudMessage3 = "";
						hudMessage4 = "";
						hudMessage5 = "";
					} else {
						playerAttackIndex = intInput - 1;
						damage = playerAttackDamage[playerAttackIndex] + (rand.nextInt(5) - 2);

						if (damage <= 0) { //sets any sub zero damage value to zero, to make sure you can't do negative damage
							damage = 0;
						}
	
						enemyHealth -= damage;
	
						hudMessage1 = "You used " + playerAttackList[playerAttackIndex] + "!";
						hudMessage2 = "You did " + damage + " damage.";
						hudMessage3 = "";
						hudMessage4 = "";
						hudMessage5 = "";
						playAttackSound();
					}					
					printBattleHud();
					playerTurn = false;
				} else {
					enemyAttackIndex = rand.nextInt(2);
					damage = enemyAttackDamage[enemyAttackIndex] + (rand.nextInt(5) - 2);

					if (damage <= 0) { //sets any sub zero damage value to zero, to make sure you can't do negative damage
						damage = 0;
					}

					playerHealth -= damage;

					hudMessage1 = "The enemy " + enemyName + " uses " + enemyAttackList[enemyAttackIndex] + "!";
					hudMessage2 = enemyName + " did " + damage + " damage.";
					hudMessage3 = "";
					hudMessage4 = "";
					hudMessage5 = "";
					playAttackSound();
					printBattleHud();
					playerTurn = true;
				}
				nextLine();

				if (playerHealth <= 0) {
					playerHealth = 0;
				}
				if (enemyHealth <= 0) {
					enemyHealth = 0;
				}

			} //end of alive if
			//checking if the battle should be over
			if (enemyName.equals("Trader Steve")) {
				if (enemyHealth <= 0) {
					enemyHealth = 1;
					loop = false;
					dead = false;

					hudMessage1 = "Trader Steve,";
					hudMessage2 = "Good job. You are more powerful than I thought.";
					hudMessage3 = "Have these " + enemyGoldDropAmount + " Gold Coins.";
					hudMessage4 = "";
					hudMessage5 = "";
					printBattleHud();

				} else if (playerHealth <= 0) {
					loop = false;
					dead = false;
					playerHealth = 1;

					hudMessage1 = "Trader Steve,";
					hudMessage2 = "You should work on your fighting. If this were a real fight, you would have perished.";
					hudMessage3 = "I have put a health potion in your inventory, you should drink it.";
					hudMessage4 = "";
					hudMessage5 = "";
					printBattleHud();
				}
			} else {
				if (enemyHealth <= 0) {
					loop = false;
					dead = false;

					hudMessage1 = "You defeated the " + enemyName + "!";
					hudMessage2 = "You got " + enemyGoldDropAmount + " Gold Coins.";
					hudMessage3 = "";
					hudMessage4 = "";
					hudMessage5 = "";
					printBattleHud();

				} else if (playerHealth <= 0) {
					loop = false;
					dead = true;

					hudMessage1 = "";
					hudMessage2 = "";
					hudMessage3 = "";
					hudMessage4 = "";
					hudMessage5 = "";
					printBattleHud();
				}
			}
		} //end of battle loop
		WorldMap.music.stop();
	}

	public static String returnPlayTime(long seconds) {
		long tempSeconds = (seconds / 1000) % 60;
		long tempMinutes = ( (seconds / 1000) / 60) % 60;
		long tempHours = ( ( (seconds / 1000) / 60) / 60);

		if (tempSeconds <= 9 && tempMinutes <= 9) {
			return ("Current playtime: " + tempHours + ":0" + tempMinutes + ":0" + tempSeconds);
		} else if (tempSeconds <= 9) {
			return ("Current playtime: " + tempHours + ":" + tempMinutes + ":0" + tempSeconds);
		} else if (tempMinutes <= 9) {
			return ("Current playtime: " + tempHours + ":0" + tempMinutes + ":" + tempSeconds);
		} else {
			return ("Current playtime: " + tempHours + ":" + tempMinutes + ":" + tempSeconds);
		}
	}
	
	public static void clearConsole() {
		try {
		    /**
    		 * runs a new process, with cmd.exe
			 * 
			 * run /c to run the commands in a different thread, so that there isn't any garbage printed to the current console
			 * /c will also close the new thread when it is done executing
			 * 
			 * run cls to clear the console
			 * 
			 * also, did i steal this from a stackoverflow post? maybe......
			 */
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (IOException | InterruptedException ex) {
		    ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		boolean skip = false;
		
		WorldMap.startTime = System.currentTimeMillis();
		Save.createFiles();

		// WorldMap.printText("Reading game settings data...\n");
		Save.readSettingsFile();

		// WorldMap.printText("Reading game save data...\n");
		Save.readSaveFile();
		
		if (WorldMap.blacksmithChest) {
			WorldMap.blacksmithMultiplier = 5;
		}
		// WorldMap.printText("Reading account data...\n");
		Save.readAccountFile();

		WorldMap.printText("All good!");
		
		clearConsole();
		
		skip = (!playerName.isEmpty()); //if your player name is blank, then open the game with the first-time intro message. else, skip it and hop right in.
		
		playerAttackList[0] = "Hand Slap";
		playerAttackDamage[0] = 4;
		playerAttackList[1] = "Punch";
		playerAttackDamage[1] = 6;
		playerAttackList[2] = "Kick";
		playerAttackDamage[2] = 10;

		if (!skip) {
			WorldMap.printText("Welcome to Mage Game.\n");
			try {
			    Thread.sleep(400);
			} catch (InterruptedException ie) {
			    ie.printStackTrace();
			}
			WorldMap.printText("Some text will only advance automatically after a certain delay.\n" 
			+ "This delay will never be longer than a second.\n"
			+ "To advance text manually, press the ENTER key on your keyboard.\n"
			+ "You will know when user input is required to advance text either when \""); 
			System.out.print(INPUT_INDICATOR); //printed with system.out.print to avoid long delay printing "..." with worldmap.printtext
			WorldMap.printText("\" is printed on a new line,\nor when it is specifically requested.\n");
			nextLine();
			WorldMap.printText("To exit the game, please go to the pause menu and select \"Save and Exit\".\nDo NOT press the \"X\" button on the terminal! Your current play session's data might be lost.\n\n");
			nextLine();
			WorldMap.printText("What is your name?\n\n"
				+ "Enter the name that you wish to be called by: ");

			loop = true;
			while (loop) {
				playerName = scan.nextLine();
				if (playerName.isEmpty()) {
					WorldMap.printText("\nHey, your name can't be blank. Enter a name: ");
				} else {
					loop = false;
				}
			}
			Save.writeAccountFile();
			System.out.println("\n");
			WorldMap.printText("Your name will now be remembered for the next time you play.");
			nextLine();
			clearConsole();
	
			WorldMap.music = new AudioPlayer(AudioPlayer.INTRO_PATH, false, WorldMap.volume);
			WorldMap.music.play();
	
			WorldMap.printText("This is the story of you in a different life, " + playerName + ".\n");
			nextLine();
			WorldMap.printText("You are a mage in the land of NAME_UNDECIDED.\n");
			nextLine();
			WorldMap.printText("The dark sorcerer, Alsroth, cast a curse over everything.\n");
			nextLine();
			WorldMap.printText("He cursed all that moves, has moved, or lays still.\nEverything in these lands appears as a simple text character.\n");
			nextLine();
			WorldMap.printText("Your mission is to stop him.\n");
			nextLine();
			WorldMap.printText("However, as you currently are, there's no chance you'll be able to take on Alsroth.\nThat would be a death wish.\n");
			nextLine();
			WorldMap.printText("You must seek out the three ancient artifacts, stolen by Alsroth and hidden away througout the land.\n");
			nextLine();
			WorldMap.printText("It is your job to figure out where he has hidden them.\n");
			nextLine();
			WorldMap.printText("If you fail, it is unknown what terrible things that Alsroth will do next. You must act quickly.\n");
			nextLine();
			WorldMap.printText("Now, wake up, " + playerName + "...");
			nextLine();
			WorldMap.music.stop();
		} else {
			WorldMap.printText("Wake up, " + playerName + "...");
			nextLine();
		}
		
		while (loop) {
			//end of intro story
			WorldMap.worldMap(skip); //goes to the main game in the world map class
			skip = true;
			if (dead) {
				WorldMap.music.stop();
				WorldMap.healthSound.stop();
				WorldMap.sfx = new AudioPlayer(AudioPlayer.DEATH_PATH, false, WorldMap.sfxVolume);
				WorldMap.sfx.play();
				WorldMap.printText("You died...");
				playerHealth = 1;
				
				WorldMap.endTime = System.currentTimeMillis();
				WorldMap.playTimeSeconds = (WorldMap.playTimeSeconds + WorldMap.endTime) - WorldMap.startTime;
				Save.writeAccountFile();		
				Save.writeSettingsFile();
				nextLine();
			} else {
				Save.writeSaveFile();
				WorldMap.endTime = System.currentTimeMillis();
				WorldMap.playTimeSeconds = (WorldMap.playTimeSeconds + WorldMap.endTime) - WorldMap.startTime;
				Save.writeAccountFile();		
				Save.writeSettingsFile();
			}
		}
	}
}