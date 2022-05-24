package game;

/* 
This program can create, write, and read data from a text file
not a very great implementation, since the values written to are all hard coded. oh well.
@author Tobin Brenner
@version 1.3
 */

import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import java.io.IOException;

public class Save {
	final static String SAVE_FILE_PATH = "resources\\game_data.mgdat";
	final static String SETTINGS_FILE_PATH = "resources\\settings.mgdat";
	final static String ACCOUNT_FILE_PATH = "resources\\account_cache.mgdat";

	public static void createFiles() {
		File saveFile = new File(SAVE_FILE_PATH);
		File settingsFile = new File(SETTINGS_FILE_PATH);
		File accountFile = new File(ACCOUNT_FILE_PATH);

		try {
			if (saveFile.exists() == false) {
				if (saveFile.createNewFile()) {
					WorldMap.printText("Save data file was successfully created.\n");
					writeSaveFile();
				}
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		try {
			if (settingsFile.exists() == false) {
				if (settingsFile.createNewFile()) {
					WorldMap.printText("Settings file was successfully created.\n");
					writeSettingsFile();
				}
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		try {
			if (accountFile.exists() == false) {
				if (accountFile.createNewFile()) {
					WorldMap.printText("Account data file was successfully created.\n");
					writeAccountFile();
				}
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	public static void readSaveFile() {

		try {
			File saveFile = new File(SAVE_FILE_PATH);
			Scanner saveRead = new Scanner(saveFile); //creates new scanner object, reads from a file object

			try {
				WorldMap.obtainedDebugArtifact = saveRead.nextBoolean();
				WorldMap.obtainedCaveArtifact = saveRead.nextBoolean();
				WorldMap.obtainedErebusArtifact = saveRead.nextBoolean();
				WorldMap.obtainedForestArtifact = saveRead.nextBoolean();

				WorldMap.peasantKey = saveRead.nextBoolean();
				WorldMap.brokenSaw = saveRead.nextBoolean();
				WorldMap.repairedSaw = saveRead.nextBoolean();
				WorldMap.basementKey = saveRead.nextBoolean();

				WorldMap.sticks = saveRead.nextInt();
				WorldMap.stones = saveRead.nextInt();
				WorldMap.brokenPickaxe = saveRead.nextBoolean();
				WorldMap.repairedPickaxe = saveRead.nextBoolean();

				WorldMap.caveTunnelsChest1 = saveRead.nextBoolean();
				WorldMap.caveTunnelsChest2 = saveRead.nextBoolean();
				WorldMap.debugChest = saveRead.nextBoolean();
				WorldMap.blacksmithChest = saveRead.nextBoolean();
				WorldMap.carpenterChest1 = saveRead.nextBoolean();
				WorldMap.carpenterChest2 = saveRead.nextBoolean();
				WorldMap.carpenterChest3 = saveRead.nextBoolean();
				WorldMap.chestHouseChest1 = saveRead.nextBoolean();
				WorldMap.chestHouseChest2 = saveRead.nextBoolean();
				WorldMap.chestHouseChest3 = saveRead.nextBoolean();
				WorldMap.chestHouseChest4 = saveRead.nextBoolean();
				WorldMap.forestArtifactChest = saveRead.nextBoolean();
				WorldMap.forestEntranceChest1 = saveRead.nextBoolean();
				WorldMap.forestEntranceChest2 = saveRead.nextBoolean();
				WorldMap.tower1Chest1 = saveRead.nextBoolean();
				WorldMap.tower1Chest2 = saveRead.nextBoolean();
				WorldMap.tower2Chest = saveRead.nextBoolean();

				MagePath.playerMaxHealth = saveRead.nextInt();
				MagePath.playerHealth = saveRead.nextInt();
				MagePath.playerMaxMagic = saveRead.nextInt();
				MagePath.playerMagic = saveRead.nextInt();

				MagePath.level = saveRead.nextInt();
				MagePath.experience = saveRead.nextInt();

				WorldMap.positionIndex = saveRead.nextInt();
				WorldMap.currentRoom = saveRead.nextInt();
				
				WorldMap.healthPotions = saveRead.nextInt();
				WorldMap.ethers = saveRead.nextInt();
			} catch (NoSuchElementException ex) {
				//setting default values for if the save file is incorrectly read
				WorldMap.obtainedDebugArtifact = false;
				WorldMap.obtainedCaveArtifact = false;
				WorldMap.obtainedErebusArtifact = false;
				WorldMap.obtainedForestArtifact = false;

				WorldMap.peasantKey = false;
				WorldMap.brokenSaw = false;
				WorldMap.repairedSaw = false;
				WorldMap.basementKey = false;

				WorldMap.sticks = 0;
				WorldMap.stones = 0;
				WorldMap.brokenPickaxe = false;
				WorldMap.repairedPickaxe = false;

				WorldMap.caveTunnelsChest1 = false;
				WorldMap.caveTunnelsChest2 = false;
				WorldMap.debugChest = false;
				WorldMap.blacksmithChest = false;
				WorldMap.carpenterChest1 = false;
				WorldMap.carpenterChest2 = false;
				WorldMap.carpenterChest3 = false;
				WorldMap.chestHouseChest1 = false;
				WorldMap.chestHouseChest2 = false;
				WorldMap.chestHouseChest3 = false;
				WorldMap.chestHouseChest4 = false;
				WorldMap.forestArtifactChest = false;
				WorldMap.forestEntranceChest1 = false;
				WorldMap.forestEntranceChest2 = false;
				WorldMap.tower1Chest1 = false;
				WorldMap.tower1Chest2 = false;
				WorldMap.tower2Chest = false;

				MagePath.playerMaxHealth = 20;
				MagePath.playerHealth = 20;
				MagePath.playerMaxMagic = 10;
				MagePath.playerMagic = 10;

				MagePath.level = 1;
				MagePath.experience = 0;

				WorldMap.positionIndex = 169;
				WorldMap.currentRoom = 8;
				
				WorldMap.healthPotions = 0;
				WorldMap.ethers = 0;
				ex.printStackTrace();
				MagePath.nextLine();
			}
			saveRead.close(); //closes scanner object
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static void writeSaveFile() {
		try {
			FileWriter saveWrite = new FileWriter(SAVE_FILE_PATH); //creates new filewriter object
			saveWrite.write(
				WorldMap.obtainedDebugArtifact + "\n" +
				WorldMap.obtainedCaveArtifact + "\n" +
				WorldMap.obtainedErebusArtifact + "\n" +
				WorldMap.obtainedForestArtifact + "\n" +

				WorldMap.peasantKey + "\n" +
				WorldMap.brokenSaw + "\n" +
				WorldMap.repairedSaw + "\n" +
				WorldMap.basementKey + "\n" +

				WorldMap.sticks + "\n" +
				WorldMap.stones + "\n" +
				WorldMap.brokenPickaxe + "\n" +
				WorldMap.repairedPickaxe + "\n" +

				WorldMap.caveTunnelsChest1 + "\n" +
				WorldMap.caveTunnelsChest2 + "\n" +
				WorldMap.debugChest + "\n" +
				WorldMap.blacksmithChest + "\n" +
				WorldMap.carpenterChest1 + "\n" +
				WorldMap.carpenterChest2 + "\n" +
				WorldMap.carpenterChest3 + "\n" +
				WorldMap.chestHouseChest1 + "\n" +
				WorldMap.chestHouseChest2 + "\n" +
				WorldMap.chestHouseChest3 + "\n" +
				WorldMap.chestHouseChest4 + "\n" +
				WorldMap.forestArtifactChest + "\n" +
				WorldMap.forestEntranceChest1 + "\n" +
				WorldMap.forestEntranceChest2 + "\n" +
				WorldMap.tower1Chest1 + "\n" +
				WorldMap.tower1Chest2 + "\n" +
				WorldMap.tower2Chest + "\n" +

				MagePath.playerMaxHealth + "\n" +
				MagePath.playerHealth + "\n" +
				MagePath.playerMaxMagic + "\n" +
				MagePath.playerMagic + "\n" +

				MagePath.level + "\n" +
				MagePath.experience + "\n" +

				WorldMap.positionIndex + "\n" +
				WorldMap.currentRoom + "\n" +
				
				WorldMap.healthPotions + "\n" +
				WorldMap.ethers + "\n" 
			); 

			saveWrite.close(); //closes filewriter object
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	public static void readSettingsFile() {
		try {
			File settingsFile = new File(SETTINGS_FILE_PATH);
			Scanner settingsRead = new Scanner(settingsFile);

			try {
				WorldMap.volume = settingsRead.nextFloat();
				WorldMap.sfxVolume = settingsRead.nextFloat();
				WorldMap.fastText = settingsRead.nextBoolean();
				WorldMap.keyMoveUp = settingsRead.next().charAt(0);
				WorldMap.keyMoveLeft = settingsRead.next().charAt(0);
				WorldMap.keyMoveDown = settingsRead.next().charAt(0);
				WorldMap.keyMoveRight = settingsRead.next().charAt(0);
				WorldMap.keyOpenInventory = settingsRead.next().charAt(0);
				WorldMap.keyPauseGame = settingsRead.next().charAt(0);
			} catch (NoSuchElementException ex) {
				//setting default values for if the save file is incorrectly read
				WorldMap.volume = -10.0f;
				WorldMap.sfxVolume = -10.0f;
				WorldMap.fastText = false;
				WorldMap.keyMoveUp = 'w';
				WorldMap.keyMoveLeft = 'a';
				WorldMap.keyMoveDown = 's';
				WorldMap.keyMoveRight = 'd';
				WorldMap.keyOpenInventory = 'i';
				WorldMap.keyPauseGame = 'p';
				ex.printStackTrace();
				MagePath.nextLine();
			}
			settingsRead.close(); //closes scanner object
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static void writeSettingsFile() {
		try {
			FileWriter saveWrite = new FileWriter(SETTINGS_FILE_PATH); //creates new filewriter object
			saveWrite.write(
				WorldMap.volume + "\n" +
				WorldMap.sfxVolume + "\n" +
				WorldMap.fastText + "\n" +
				WorldMap.keyMoveUp + "\n" +
				WorldMap.keyMoveLeft + "\n" +
				WorldMap.keyMoveDown + "\n" +
				WorldMap.keyMoveRight + "\n" +
				WorldMap.keyOpenInventory + "\n" +
				WorldMap.keyPauseGame + "\n"
			); 

			saveWrite.close(); //closes filewriter object
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	public static void readAccountFile() {
		try {
			File accountFile = new File(ACCOUNT_FILE_PATH);
			Scanner accountRead = new Scanner(accountFile);

			try {
				MagePath.playerName = accountRead.nextLine();
				WorldMap.playTimeSeconds = accountRead.nextInt();
				WorldMap.spacesMoved = accountRead.nextInt();
			} catch (NoSuchElementException ex) {
				//setting default values for if the save file is incorrectly read
				MagePath.playerName = "THIS_NAME_IS_RESERVED_AND_IS_A_RESULT_OF_DIRECT_FALIURE_BECAUSE_THE_NAME_WAS_UNINITIALISED";
				WorldMap.playTimeSeconds = 0;
				WorldMap.spacesMoved = 0;
				ex.printStackTrace();
				MagePath.nextLine();
			}
			accountRead.close(); //closes scanner object
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static void writeAccountFile() {
		try {
			FileWriter saveWrite = new FileWriter(ACCOUNT_FILE_PATH); //creates new filewriter object
			saveWrite.write(
				MagePath.playerName + "\n" +
				WorldMap.playTimeSeconds + "\n" +
				WorldMap.spacesMoved + "\n"
			); 

			saveWrite.close(); //closes filewriter object
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
}
