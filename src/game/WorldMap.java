package game;


import game.maps.CaveMaps;
import game.maps.ErebusMaps;
import game.maps.FieldMaps;
import game.maps.TowerMaps;
import game.maps.ForestMaps;

import java.util.Arrays;

public class WorldMap {
	
	//this is the char array where all the maps are hot-swapped into and then read off of
	static char[] currentMap = new char[421];
	
	//loading zone metadata for the maps (so that room transitions don't have to be hardcoded)
	static int[] currentMapMetadata = new int[12];
	
	/*
	MAP LAYOUT DOCUMENTATION (and metadata)

	the first value is the index pos of the loading zone (int) 
	the second value is the map destination (int)
	the third value is the index pos destination (int)

	column 19 of the array should always contain only line breaks, or else the map won't render correctly.

	array index 420 should always contain the text 'T'. 
	if it doesn't, then shit goes down.

	all the map constants are stored in separate classes to avoid the bytecode size limit (i hate it here), although now that they're chars and not Strings i might be able to get away with putting them all in the main class, but idk
	although, i probably won't do that since that seems like a terrible idea.
	 */

	static final String USED_UNUSABLE_ITEM = "This isn't a useable item.";

	static int healthPotions = 0;
	static int ethers = 0;
	//sticks
	//stones
	static int goldCoinCount = 0;

	static int blacksmithMultiplier = 1;

	//key inventory items
	
	//forest quest items
	static boolean lostInDeepWoods = false; //to determine if you can get the manuscript at all
	static boolean manuscript = false; //will appear as a regular item (so you can use it)
	static boolean translatedManuscript = false; //will appear as a regular item (so you can use it
	
	//erebus quest items
	static boolean peasantKey = false;
	static boolean brokenSaw = false;
	static boolean repairedSaw = false;
	static boolean basementKey = false;

	//cave quest items (sticks and stones will be presented as regular items, but they are technically key items)
	static int sticks = 0; 
	static int stones = 0;
	static boolean brokenPickaxe = false;
	static boolean repairedPickaxe = false;

	static int positionIndex = 169; //the players position on the map
	static int movesUntilNextBattle = MagePath.rand.nextInt(100);
	static int maxMovesUntilNextBattle = 0;
	static int gameDifficulty = -1; //0 for easy, 1 for normal, 2 for hard, 3 for impossible
	static boolean canSpawnEnemies = false; //if true, then enemies can be randomly encountered on the map. as for which maps can spawn enemies, that is hardcoded.
	static int lastPositionIndex = positionIndex;
	static int arrayIndex = 0; //used for general array purposes, doesn't hold any significant values

	static int currentRoom = 8; //the room id of the current room that the player is in
	static boolean loop = true;
	static boolean gameLoop = true; //the only thing this is ever used for is the main while loop, and it shouldn't be used for anything else
	static boolean roomUpdate = false; //when true, the room needs to update its visuals (i don't think this is used for anything, could be wrong tho)

	//artifact flags
	static boolean obtainedDebugArtifact = false;
	static boolean obtainedForestArtifact = false;
	static boolean obtainedErebusArtifact = false;
	static boolean obtainedCaveArtifact = false;

	//chest flags
	static boolean caveSmallTunnelsChest1 = false;
	static boolean caveSmallTunnelsChest2 = false;
	static boolean caveTunnelsChest1 = false;
	static boolean caveTunnelsChest2 = false;
	static boolean debugChest = false;
	static boolean blacksmithChest = false;
	static boolean carpenterChest1 = false; //left chest
	static boolean carpenterChest2 = false; //top chest
	static boolean carpenterChest3 = false; //right chest
	static boolean chestHouseChest1 = false; //topleft chest
	static boolean chestHouseChest2 = false; //topright chest
	static boolean chestHouseChest3 = false; //bottomleft chest
	static boolean chestHouseChest4 = false; //bottomright chest
	static boolean forestArtifactChest = false;
	static boolean forestEntranceChest1 = false;
	static boolean forestEntranceChest2 = false;
	static boolean tower1Chest1 = false; //top chest
	static boolean tower1Chest2 = false; //bottom chest
	static boolean tower2Chest = false;

	//misc flags
	static boolean towerSwitch1 = false; //temporary flag
	static boolean towerSwitch2 = false; //temporary flag
	static boolean towerSwitch3 = false; //temporary flag
	static boolean boulderDestroyed = false;

	static boolean bronzeStaff = false; //if you have the staff
	static boolean goldStaff = false;
	static boolean enchantedStaff = false;
	static int currentStaff = 0; //normal range 0-3

	static boolean huntingArmour = false;
	static boolean chainmailArmour = false;
	static int currentArmour = 0; //normal range 0-2
	
	static boolean shield = false;
	
	static boolean traderLeftHouse = false;

	static boolean lostInWoods = false;

	static int index = 0;

	static boolean playingCaveAudio = false;
	static boolean playingErebusAudio = false;
	static boolean playingFieldAudio = false;
	static boolean playingForestAudio = false;
	static boolean playingHouseAudio = false;
	static boolean playingTowerAudio = false;

	static boolean playingLowHealthSound = false;

	static boolean erebusSongSecret = false;

	static AudioPlayer music;
	static AudioPlayer sfx;
	static AudioPlayer healthSound;

	static float volume = 0.0f;
	static float sfxVolume = 0.0f;

	static boolean fastText = false;

	static boolean noClip = false;
	static boolean seenTutorial = false;
	static boolean initialisedAudio = false; //can make a local variable (too lazy rn lol)
	static boolean debug = false; //enables debug output through system.err

	static long playTimeSeconds = 0;
	static long startTime = 0;
	static long endTime = 0;

	static int spacesMoved = 0;

	static double decPlayerHealth;
	static double decMaxPlayerHealth;

	static boolean saveGame;

	static char keyMoveUp = 'w';
	static char keyMoveLeft = 'a';
	static char keyMoveDown = 's';
	static char keyMoveRight = 'd';
	static char keyOpenInventory = 'i';
	static char keyPauseGame = 'p';

	static char keyInput;

	private static void getRoomData() {
		if (!initialisedAudio) { //loading the audio objects with their values just because i have to idk too lazy to doc this properly ;)
			music = new AudioPlayer(AudioPlayer.HOUSE_PATH, true, volume);
			sfx = new AudioPlayer(AudioPlayer.BOULDER_BREAK_PATH, true, volume);

			initialisedAudio = true; //locking out this if statement, or else we would be literally reloading the audio on every frame render and that would tank performance
		}
		index = 0;

		loop = true;

		//start of data loading switch statement
		switch (currentRoom) {
			case 0 -> { //caveMap0
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = CaveMaps.CAVE_ENTRANCE[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = CaveMaps.CAVE_ENTRANCE_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 1 -> { //first main cave area, you find the pickaxe here
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = CaveMaps.CAVE_TUNNELS[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = CaveMaps.CAVE_TUNNELS_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 2 -> { //second cave area
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = CaveMaps.CAVE_SMALL_TUNNELS[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = CaveMaps.CAVE_SMALL_TUNNELS_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 3 -> { //cave boulder room
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = CaveMaps.CAVE_BOULDER[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = CaveMaps.CAVE_BOULDER_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 4 -> { //cave artifact room
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = CaveMaps.CAVE_ARTIFACT[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = CaveMaps.CAVE_ARTIFACT[index];
				}
				canSpawnEnemies = true;
			}
			case 5 -> { //field area towards sorcerer's tower
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = FieldMaps.NORTHERN_FIELD[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = FieldMaps.NORTHERN_FIELD_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 6 -> { //main hub area
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = FieldMaps.FIELD[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = FieldMaps.FIELD_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 7 -> { //has trader steve's house where you first spawn and learn how to fight
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = FieldMaps.NORTHERN_FIELD_PATH[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = FieldMaps.NORTHERN_FIELD_PATH_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 8 -> { //trader steve's house
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = FieldMaps.TRADER_HOUSE[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = FieldMaps.TRADER_HOUSE_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 9 -> { //main erebus area
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ErebusMaps.EREBUS_SQUARE[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ErebusMaps.EREBUS_SQUARE_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 10 -> { //secondary erebus area
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ErebusMaps.EREBUS_ALLEYS[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ErebusMaps.EREBUS_ALLEYS_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 11 -> { //david the wizard's house, where you get the manuscript
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ErebusMaps.EREBUS_WIZARD_HOUSE[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ErebusMaps.EREBUS_WIZARD_HOUSE_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 12 -> { //blacksmith's house, where you get the saw sharpened and where you get the miner's pickaxe fixed
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ErebusMaps.EREBUS_BLACKSMITH[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ErebusMaps.EREBUS_BLACKSMITH_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 13 -> { //house of the poacher, the person you gives you the key to the basement in return for the saw
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ErebusMaps.EREBUS_CARPENTERS_HOUSE[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ErebusMaps.EREBUS_CARPENTERS_HOUSE_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 14 -> { //erebus artifact room
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ErebusMaps.EREBUS_CARPENTERS_BASEMENT[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ErebusMaps.EREBUS_CARPENTERS_BASEMENT_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 15 -> { //erebus chest house, filler house with chests
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ErebusMaps.EREBUS_ALLEY_CHEST_HOUSE[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ErebusMaps.EREBUS_ALLEY_CHEST_HOUSE_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 16 -> { //entrance area to the forest, where trader steve goes to after the tutorial
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ForestMaps.FOREST_ENTRANCE[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ForestMaps.FOREST_ENTRANCE_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 17 -> { //the "lost woods clone area" (8 identical maps YAAAAAAAAAAYYYYYYYYYY)
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ForestMaps.FOREST_MAZE1[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ForestMaps.FOREST_MAZE1_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 18 -> { //the "lost woods clone area"
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ForestMaps.FOREST_MAZE2[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ForestMaps.FOREST_MAZE2_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 19 -> { //the "lost woods clone area"
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ForestMaps.FOREST_MAZE3[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ForestMaps.FOREST_MAZE3_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 20 -> { //the "lost woods clone area"
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ForestMaps.FOREST_MAZE4[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ForestMaps.FOREST_MAZE4_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 21 -> { //the "lost woods clone area"
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ForestMaps.FOREST_MAZE5[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ForestMaps.FOREST_MAZE5_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 22 -> { //the FINAL "lost woods clone area"
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ForestMaps.FOREST_MAZE6[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ForestMaps.FOREST_MAZE6_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 23 -> { //forest artifact area
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = ForestMaps.FOREST_ARTIFACT[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = ForestMaps.FOREST_ARTIFACT_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 24 -> { //floor 1 of the tower
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = TowerMaps.TOWER1[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = TowerMaps.TOWER1_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 25 -> { //floor 2 of the tower
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = TowerMaps.TOWER2[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = TowerMaps.TOWER2_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 26 -> { //floor 3 of the tower
				loop = true;
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = TowerMaps.TOWER3[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = TowerMaps.TOWER3_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			case 27 -> { //final boss room
				loop = true;
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = TowerMaps.SORCERER_ROOM[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = TowerMaps.SORCERER_ROOM_METADATA[index];
				}
				canSpawnEnemies = false;
			}
			case 28 -> { //testing room
				for (index = 0; index < currentMap.length; index += 1) {
					currentMap[index] = FieldMaps.DEBUG_MAP[index];
				}
				for (index = 0; index < currentMapMetadata.length; index += 1) {
					currentMapMetadata[index] = FieldMaps.DEBUG_MAP_METADATA[index];
				}
				canSpawnEnemies = true;
			}
			default -> { //in case that the currentRoom variable holds a bad value, then the game will place you in room ID 0, just so the game doesn't like, crash
				currentRoom = 0;
				positionIndex = 169;
				getRoomData(); //infinte loop fuel lets go
			}
		} //end of room loading switch statement

		//loading music if loop
		if (
		(
			currentRoom == 8 //loading house music
			|| currentRoom == 13 
			|| currentRoom == 14 
			|| currentRoom == 15 
			|| currentRoom == 12
			|| currentRoom == 11
		) 
		&& !playingHouseAudio) {
			music.stop();
			playingErebusAudio = false;
			playingFieldAudio = false;

			music = new AudioPlayer(AudioPlayer.HOUSE_PATH, true, volume);
			playingHouseAudio = true;
			music.play();
		} else if (
		(
			currentRoom == 6 //loading field music
			|| currentRoom == 5 
			|| currentRoom == 7
		) 
		&& !playingFieldAudio) {
			music.stop();
			playingCaveAudio = false;
			playingErebusAudio = false;
			playingHouseAudio = false;
			playingForestAudio = false;
			playingTowerAudio = false;

			music = new AudioPlayer(AudioPlayer.FIELD_PATH, true, volume);
			playingFieldAudio = true;
			music.play();
		} else if (
		(
			currentRoom == 9 //loading erebus music
			|| currentRoom == 10 
		) 
		&& !playingErebusAudio) {
			music.stop();
			playingHouseAudio = false;
			playingFieldAudio = false;

			if (!erebusSongSecret) {
				music = new AudioPlayer(AudioPlayer.EREBUS_PATH, true, volume);
			} else {
				music = new AudioPlayer("resources\\music\\erebus_alt.wav", true, volume);
			}
			playingErebusAudio = true;
			music.play();
		} else if (
		(
			currentRoom == 16 //loading forest music
			|| currentRoom == 17 
			|| currentRoom == 18 
			|| currentRoom == 19 
			|| currentRoom == 20 
			|| currentRoom == 21 
			|| currentRoom == 22 
			|| currentRoom == 23 
		) 
		&& !playingForestAudio) {
			music.stop();
			playingFieldAudio = false;

			music = new AudioPlayer(AudioPlayer.FOREST_PATH, true, volume);
			playingForestAudio = true;
			music.play();
		} else if (
		(
			currentRoom == 0 //loading cave music
			|| currentRoom == 1 
			|| currentRoom == 2 
			|| currentRoom == 3 
			|| currentRoom == 4 
		) 
		&& !playingCaveAudio) {
			music.stop();
			playingFieldAudio = false;

			music = new AudioPlayer(AudioPlayer.CAVE_PATH, true, volume);
			playingCaveAudio = true;
			music.play();
		} else if (
		(
			currentRoom == 24 //loading tower music
			|| currentRoom == 25 
			|| currentRoom == 26
			|| currentRoom == 27 
		) 
		&& !playingTowerAudio) {
			music.stop();
			playingFieldAudio = false;

			music = new AudioPlayer(AudioPlayer.TOWER_PATH, true, volume);
			playingTowerAudio = true;
			music.play();
		} //end of loading music loop
	}

	private static void hijackRoomData() {
		if (obtainedDebugArtifact && currentRoom == 28) { //debug room artifact
			currentMap[193] = ' ';
		}
		if (obtainedCaveArtifact && currentRoom == 4) { //cave artifact
			currentMap[229] = ' ';
		}
		if (obtainedErebusArtifact && currentRoom == 14) { //erebus artifact
			currentMap[171] = ' ';
		}
		if (obtainedForestArtifact && currentRoom == 23) { //forest artifact
			currentMap[270] = ' ';
		}

		if (caveSmallTunnelsChest1 && currentRoom == 2) {
			currentMap[72] = '\\';
		}
		if (caveSmallTunnelsChest2 && currentRoom == 2) {
			currentMap[135] = '\\';
		}
		if (caveTunnelsChest1 && currentRoom == 1) { //cave tunnels pickaxe chest
			currentMap[261] = '\\';
		}
		if (caveTunnelsChest2 && currentRoom == 1) { //cave tunnels chest 2
			currentMap[266] = '\\';
		}
		if (debugChest  && currentRoom == 28) { //debug room chest
			currentMap[233] = '\\';
		}
		if (blacksmithChest && currentRoom == 12) {
			currentMap[286] = '\\';
		}
		if (carpenterChest1 && currentRoom == 14) {
			currentMap[227] = '\\';
		}
		if (carpenterChest2 && currentRoom == 14) {
			currentMap[169] = '\\';
		}
		if (carpenterChest3 && currentRoom == 14) {
			currentMap[230] = '\\';
		}
		if (chestHouseChest1 && currentRoom == 15) {
			currentMap[167] = '\\';
		}
		if (chestHouseChest2 && currentRoom == 15) {
			currentMap[169] = '\\';
		}
		if (chestHouseChest3 && currentRoom == 15) {
			currentMap[227] = '\\';
		}
		if (chestHouseChest4 && currentRoom == 15) {
			currentMap[250] = '\\';
		}
		if (forestArtifactChest && currentRoom == 23) {
			currentMap[327] = '\\';
		}
		if (forestEntranceChest1 && currentRoom == 23) {
			currentMap[166] = '\\';
		}
		if (tower1Chest1 && currentRoom == 24) {
			currentMap[154] = '\\';
		}
		if (tower1Chest2 && currentRoom == 24) {
			currentMap[314] = '\\';
		}
		if (tower2Chest && currentRoom == 25) {
			currentMap[107] = '\\';
		}

		if (traderLeftHouse && currentRoom == 8) { //not a chest, just to make trader steve actually leave his house
			currentMap[167] = ' ';
		}
		if (peasantKey && currentRoom == 17) { //removing the key from the ground in the forest
			currentMap[167] = ' ';
		}
		if (basementKey && currentRoom == 13) { //unlocking the poacher's basement door if you have the key
			currentMap[287] = 'S';
		}

		if (towerSwitch1 && currentRoom == 24) {
			currentMap[205] = '+';
			currentMap[225] = ' ';
			currentMap[245] = '+';
		}
		if (towerSwitch2 && currentRoom == 25) {
			currentMap[132] = '+';
			currentMap[152] = ' ';
			currentMap[172] = '+';
		}
		if (towerSwitch3 && currentRoom == 26) {
			currentMap[109] = '+';
			currentMap[129] = ' ';
			currentMap[149] = '+';
		}
	}

	private static void printWorldMap() {
		char[] frame = new char[840];
		String stringFrame;

		arrayIndex = 0;

		Arrays.fill(frame, ' ');

		if (currentMap[positionIndex] != '\n') {
			currentMap[positionIndex] = '(';
		}

		for (arrayIndex = 0; currentMap[arrayIndex] != 'T'; arrayIndex++) {
			frame[arrayIndex * 2] = currentMap[arrayIndex]; //printing the tile to the screen
			//to determine the second subtile (if it needs to be unique, otherwise the current subtile is just printed a second time)
			switch (currentMap[arrayIndex]) {
				case '(' -> //player tile
						frame[(arrayIndex * 2) + 1] = ')';
				case '{' -> //npc tile
						frame[(arrayIndex * 2) + 1] = '}';
				case '[' -> //chest tile
						frame[(arrayIndex * 2) + 1] = ']';
				case '\\' -> //open chest tile
						frame[(arrayIndex * 2) + 1] = ']';
				case '0' -> //stone tile
						frame[(arrayIndex * 2) + 1] = ' '; //print an empty sub-tile, so the tile is effectively half the size
				case '/' -> //tree branch tile
						frame[(arrayIndex * 2) + 1] = ' '; //print an empty sub-tile
				case '<' -> //artifact tile
						frame[(arrayIndex * 2) + 1] = '>';
				case '&' -> //key tile
						frame[(arrayIndex * 2) + 1] = ' '; //print an empty sub-tile
				case '\n' -> frame[(arrayIndex * 2) + 1] = ' ';

				//if a line break was just printed, then do nothing since the rendering will be shifted right one sub-tile for every line but the first one if you do print another subtile, and you obviously can't print another line break since that would arguably screw it up even more
				case '#' -> //switch tile
						frame[(arrayIndex * 2) + 1] = ' '; //print an empty sub-tile, so the tile is effectively half the size
				default -> frame[(arrayIndex * 2) + 1] = currentMap[arrayIndex]; //print the same sub-tile a second time
			}

		} //end of sub-tile printing loop

		stringFrame = " " + (new String(frame)); //adds whitespace to the start of the string, to align the first row of the frame
		MagePath.clearConsole();
		System.out.println(stringFrame + '\n' + MagePath.playerHudStats()); //finally, using all the data processed, render the frame
	}

	private static void playItemGet() {
		music.stop();
		sfx = new AudioPlayer(AudioPlayer.ITEM_GET_PATH, false, volume);
		sfx.play();
		playingCaveAudio = false;
		playingErebusAudio = false;
		playingFieldAudio = false;
		playingForestAudio = false;
		playingHouseAudio = false;
		playingTowerAudio = false;
	}

	public static void printDialogue(String lineToPrint) {
		char[] c = lineToPrint.toCharArray();
		int delay;
		int longDelay;
		sfx = new AudioPlayer(AudioPlayer.SPEAK_PATH, true, sfxVolume);
		sfx.play();

		if (playingLowHealthSound) {
			healthSound.stop();
			healthSound = new AudioPlayer(AudioPlayer.LOW_HEALTH_PATH, true, sfxVolume);
			playingLowHealthSound = false;
		}

		if (fastText) {
			delay = 10;
			longDelay = 50;
		} else {
			delay = 20;
			longDelay = 100;
		}

		for (int i = 0; i < c.length; i++) {

			if ((c[i] == '\n') || (c[i] == '.')) {
				System.out.print(c[i]);
				sfx.stop();
				sfx = new AudioPlayer(AudioPlayer.SPEAK_PATH, true, sfxVolume);
				try {
					Thread.sleep(longDelay);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				sfx.play();
			} else {
				System.out.print(c[i]);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		sfx.stop();
	}

	public static void printText(String lineToPrint) {
		char[] c = lineToPrint.toCharArray();
		int delay;
		int longDelay;
		sfx = new AudioPlayer(AudioPlayer.TEXT_PATH, true, sfxVolume);
		sfx.play();

		if (playingLowHealthSound) {
			healthSound.stop();
			healthSound = new AudioPlayer(AudioPlayer.LOW_HEALTH_PATH, true, sfxVolume);
			playingLowHealthSound = false;
		}

		if (fastText) {
			delay = 10;
			longDelay = 50;
		} else {
			delay = 20;
			longDelay = 100;
		}

		for (int i = 0; i < c.length; i++) {
			System.out.print(c[i]);
			
			
			if (c[i] == '\n' && i == c.length - 1) {
				System.out.print('\n');
			} else if (i == c.length - 1) {
				System.out.print(c[i] + '\n');
			} else if ((c[i] == '\n') || (c[i] == '.') || (c[i] == '!') || (c[i] == '?')) {
				sfx.stop();
				sfx = new AudioPlayer(AudioPlayer.TEXT_PATH, true, sfxVolume);
				try {
					Thread.sleep(longDelay);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				sfx.play();
			} else {
				try {
					Thread.sleep(delay); 
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		sfx.stop();
	}

	public static void printSeparator() {
		int delay;
		sfx = new AudioPlayer(AudioPlayer.TEXT_PATH, true, sfxVolume);
		sfx.play();

		if (playingLowHealthSound) {
			healthSound.stop();
			healthSound = new AudioPlayer(AudioPlayer.LOW_HEALTH_PATH, true, sfxVolume);
			playingLowHealthSound = false;
		}

		if (fastText) {
			delay = 3;
		} else {
			delay = 9;
		}
		//System.out.print('\n');
		for (int i = 0; i < 80; i++) {
			System.out.print('-');
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		System.out.print('\n');
		sfx.stop();
	}

	private static void checkFlags() {
		//checking if the player should be setting a flag (getting an artifact, opening a chest, etc)
		if (positionIndex == 193 && currentRoom == 28 && !obtainedDebugArtifact) {

			playItemGet();
			printText("You got the debug artifact!\nIt does absolutely nothing!\n");
			obtainedDebugArtifact = true; //gives the player the artifact
			MagePath.lineConfirm();
		}
		if (positionIndex == 229 && currentRoom == 4 && !obtainedCaveArtifact) {

			playItemGet();
			printText("You got the artifact!\nYou've learned a new spell!\n");
			obtainedCaveArtifact = true; //gives the player the artifact
			MagePath.lineConfirm();
		}
		if (positionIndex == 171 && currentRoom == 14 && !obtainedErebusArtifact) {

			playItemGet();
			printText("You got the artifact!\nYou've learned a new spell!\n");
			obtainedErebusArtifact = true; //gives the player the artifact
			MagePath.lineConfirm();
		}
		if (positionIndex == 270 && currentRoom == 23 && !obtainedForestArtifact) {

			obtainedForestArtifact = true; //gives the player the artifact
			playItemGet();
			printText("You got the artifact!\nYou've learned a new spell!\n");
			MagePath.lineConfirm();
		}
		if (!caveSmallTunnelsChest1 && currentRoom == 2 && positionIndex == 72) {
			caveSmallTunnelsChest1 = true;
			playItemGet();
			printText("There was 80 gold in the chest.\n");
			goldCoinCount += 80;
			MagePath.lineConfirm();
		}
		if (!caveSmallTunnelsChest2 && currentRoom == 2 && positionIndex == 135) {
			caveSmallTunnelsChest2 = true;
			playItemGet();
			printText("There was 2 health potions in the chest.\n");
			healthPotions += 2;
			MagePath.lineConfirm();
		}
		if (!caveTunnelsChest1 && currentRoom == 1 && positionIndex == 261) { //cave tunnels pickaxe chest
			caveTunnelsChest1 = true;
			brokenPickaxe = true;
			playItemGet();
			printText("There was a pickaxe in the chest. It's pretty old...\n");
			MagePath.lineConfirm();
		}
		if (!caveTunnelsChest2 && currentRoom == 1 && positionIndex == 266) { //cave tunnels chest 2
			caveTunnelsChest2 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!debugChest && currentRoom == 28 && positionIndex == 233) { //debug room chest
			debugChest = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!blacksmithChest && positionIndex == 286 && currentRoom == 12) {
			playItemGet();
			printText("There was 300 gold in the chest, but at what cost?\n");
			goldCoinCount += 300;
			MagePath.lineConfirm();
		}
		if (!carpenterChest1 && currentRoom == 14 && positionIndex == 227) {
			carpenterChest1 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!carpenterChest2 && currentRoom == 14 && positionIndex == 169) {
			carpenterChest2 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!carpenterChest3 && currentRoom == 14 && positionIndex == 230) {
			carpenterChest3 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!chestHouseChest1 && currentRoom == 15 && positionIndex == 167) {
			chestHouseChest1 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!chestHouseChest2 && currentRoom == 15 && positionIndex == 169) {
			chestHouseChest2 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!chestHouseChest3 && currentRoom == 15 && positionIndex == 227) {
			chestHouseChest3 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!chestHouseChest4 && currentRoom == 15 && positionIndex == 250) {
			chestHouseChest4 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!forestArtifactChest && currentRoom == 23 && positionIndex == 327) {
			forestArtifactChest = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!forestEntranceChest1 && currentRoom == 23 && positionIndex == 166) {
			forestEntranceChest1 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!tower1Chest1 && currentRoom == 24 && positionIndex == 154) {
			tower1Chest1 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!tower1Chest2 && currentRoom == 24 && positionIndex == 314) {
			tower1Chest2 = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}
		if (!tower2Chest && currentRoom == 25 && positionIndex == 107) {
			tower2Chest = true;
			playItemGet();
			printText("EMPTY_CHEST\n");
			MagePath.lineConfirm();
		}

		//temporary flags
		if (positionIndex == 235 && currentRoom == 24 && !towerSwitch1) {
			towerSwitch1 = true;
			sfx = new AudioPlayer(AudioPlayer.SWITCH_PATH, false, sfxVolume);
			sfx.play();
		}
		if (positionIndex == 203 && currentRoom == 25 && !towerSwitch2) {
			towerSwitch2 = true;
			sfx = new AudioPlayer(AudioPlayer.SWITCH_PATH, false, sfxVolume);
			sfx.play();
		}
		if (positionIndex == 229 && currentRoom == 26 && !towerSwitch3) {
			towerSwitch3 = true;
			sfx = new AudioPlayer(AudioPlayer.SWITCH_PATH, false, sfxVolume);
			sfx.play();
		}
		//resetting temporary flags
		if (currentRoom == 5) {
			towerSwitch1 = false;
		}
		if (currentRoom == 24) {
			towerSwitch2 = false;
		}
		if (currentRoom == 25) {
			towerSwitch3 = false;
		}

		if (currentRoom != 8) { //checking if you leave trader steve's house to make sure he doesn't leave while you're still there
			traderLeftHouse = true;
		}
		
		if (currentRoom == 17 && positionIndex == 167 && !peasantKey) { //picking up the key
			printText("How odd... There's just a key on the ground here.");
			MagePath.lineConfirm();
			peasantKey = true;
		}
	}

	private static void checkConversations() {
		//checking if the player should be starting a conversation
		if (positionIndex == 148 && currentRoom == 28) { //conversation with Debug Man in the debug room (top npc)

			printDialogue("Debug Man,\nHello there, young man. Can you help me put on my shoe?");
			loop = true;
			while (loop) {
				printDialogue("Enter yes or no:");
				MagePath.lineInput = MagePath.scan.nextLine();
				loop = false;
				if (MagePath.lineInput.equals("yes")) {
					printDialogue("Debug Man,\nThank you! Have one cubic centimetre of air as a reward.");
				} else if (MagePath.lineInput.equals("no")) {
					printDialogue("Debug Man,\nWhat a shame. I guess I'll be barefoot forever.");
				} else {
					loop = true;
				}
			}
			MagePath.lineConfirm();
		}

		if (positionIndex == 208 && currentRoom == 28) { //conversation with alphosphate in the debug room (bottom npc)
			printDialogue("Alphosphate");
			printSeparator();
			printDialogue("Hello there!");
			printDialogue("Have you seen Plasma? He hasn't been responding to my messages!");
			printSeparator();
			MagePath.lineConfirm();

			printDialogue(MagePath.playerName);
			printSeparator();
			printDialogue("Sorry, no, I don't know a \"Plasma\". If I find them, I'll let you know.");
			printSeparator();
			MagePath.lineConfirm();
		}

		if (positionIndex == 167 && currentRoom == 8 && !traderLeftHouse) { //trader steve, in trader house

			printDialogue("Trader Steve");
			printSeparator();
			printDialogue("""
					You're finally awake!
					What, you don't remember? I found you out cold last night, on the ground.
					You seemed to be having some sort of bad dream.
					I took you into my house, and you slept like a baby on my bed.""");
			printSeparator();
			MagePath.lineConfirm();
		}

		if (positionIndex == 183 && currentRoom == 9) { //friar john
			printDialogue("Friar John");
			printSeparator();
			printDialogue("You should go to the church!\nThis town is finally large enough to justify getting one built.");
			printSeparator();
			MagePath.lineConfirm();
		}

		if (positionIndex == 327 && currentRoom == 9) { //little timmy
			printDialogue("Little Timmy");
			printSeparator();
			printDialogue("Look at me, mom! I can put my foot behind my head!");
			printSeparator();
			MagePath.lineConfirm();
		} 

		if (positionIndex == 308 && currentRoom == 9) { //mrs baker
			printDialogue("Mrs Baker");
			printSeparator();
			printDialogue("Why, isn't my son simply amazing?");
			printSeparator();
			MagePath.lineConfirm();
		}

		if (positionIndex == 230 && currentRoom == 9) { //Alexander
			if (!peasantKey && !brokenSaw) {
				printDialogue("Alexander");
				printSeparator();
				printDialogue("I lost the key to my house. Now I can't get in...");
				printSeparator();
				MagePath.lineConfirm();
			} else if (!brokenSaw) {
				printDialogue("Alexander");
				printSeparator();
				printDialogue("Is that my key that you have?\nThanks for finding my key.\nHere, have this saw as payment. I found it in the forest, and I don't need it.");
				printSeparator();
				MagePath.lineConfirm();
				brokenSaw = true;
			} else {
				printDialogue("Alexander");
				printSeparator();
				printDialogue("Thank you for finding my key!");
				printSeparator();
				MagePath.lineConfirm();
			}
		}

		if (positionIndex == 294 && currentRoom == 9) { //michael
			printDialogue("Michael");
			printSeparator();
			printDialogue("What's that smell?\nHey, it wasn't me...");
			printSeparator();
			MagePath.lineConfirm();
		}

		if (positionIndex == 256 && currentRoom == 9) { //brian
			printDialogue("Brian");
			printSeparator();
			printDialogue("Some days, I dream of when my friend will come to visit me, but he just won't...\n"
				+ "Whatever, he doesn't get any bitches anyway, so what does it matter?");
			printSeparator();
			MagePath.lineConfirm();
		} 

		if (positionIndex == 262 && currentRoom == 9) { //robert
			printDialogue("Robert");
			printSeparator();
			printDialogue("Welcome to Erebus, our wonderful village!");
			printSeparator();
			MagePath.lineConfirm();
		}

		if (positionIndex == 212 && currentRoom == 10) { //jimmy
			printDialogue("Jimmy");
			printSeparator();
			printDialogue("I'm not doing anything suspicious, honest! I just like hanging out in this alley.");
			printSeparator();
			MagePath.lineConfirm();
		}

		if (positionIndex == 245 && currentRoom == 10) { //Robert
			if (!erebusSongSecret) {
				printDialogue("Robert");
				printSeparator();
				printDialogue("Have you ever wondered why our town was called \"Erebus\"?\n"
					+ "Well, I sure have. And I think I've figured it out.");
				printSeparator();

				MagePath.lineConfirm();

				erebusSongSecret = true;
				playingErebusAudio = false;
				getRoomData();

				printDialogue("Robert");
				printSeparator();
				printDialogue("""
						Yes, that's right.
						It's a fricking reference to the level Erebus by BoldStep
						in the video game Geometry Dash by me,
						Sweden-based indie game developer Robert Topala.
						OMG, no way.""");
				printSeparator();
				MagePath.lineConfirm();
			} else {
				printDialogue("Robert");
				printSeparator();
				printDialogue("""
						What, you don't believe me? I'm telling you!
						It's a fricking reference to the level Erebus by BoldStep
						in the video game Geometry Dash by me,
						Sweden-based indie game developer Robert Topala.""");
				printSeparator();

				MagePath.lineConfirm();
			}
		}

		if (!obtainedCaveArtifact || !obtainedErebusArtifact || !obtainedForestArtifact) {
			if (currentRoom == 5 && positionIndex < 300) {
				positionIndex += 20;
				printText("This is the way to the sorcerer's tower. Maybe you should go get the artifacts first...");
				MagePath.lineConfirm();
			}
		}

		if ((currentRoom == 17 && (positionIndex == 49 || positionIndex == 182 || positionIndex == 196)) //down
		|| (currentRoom == 18 && (positionIndex == 49 || positionIndex == 182 || positionIndex == 329)) //right
		|| (currentRoom == 19 && (positionIndex == 49 || positionIndex == 182 || positionIndex == 196)) //down
		|| (currentRoom == 20 && (positionIndex == 49 || positionIndex == 329 || positionIndex == 196)) //left
		|| (currentRoom == 21 && (positionIndex == 49 || positionIndex == 182 || positionIndex == 329)) //right
		|| (currentRoom == 22 && (positionIndex == 49 || positionIndex == 182 || positionIndex == 196)) //down
		|| (currentRoom == 22 && positionIndex == 329 && !translatedManuscript))
		{
			lostInWoods = true;
			lostInDeepWoods = true;
		}

		if (positionIndex == 269 && currentRoom == 3 && !brokenPickaxe && !repairedPickaxe) {
			printText("There's a boulder in the way...\nMaybe there's something that you can use to break it?");
			MagePath.lineConfirm();
		} else if (positionIndex == 269 && currentRoom == 3 && brokenPickaxe && !repairedPickaxe) {
			printText("There's a boulder in the way...\nYou might be able to use the pickaxe you found, but it's pretty old and would probably break before you fully destroyed the boulder.\nYou should find someone who can fix up your pickaxe.");
			MagePath.lineConfirm();
		} else if (positionIndex == 269 && currentRoom == 3 && brokenPickaxe) { //repairedPickaxe is always true
			boulderDestroyed = true;
		}

		if (positionIndex == 245 && currentRoom == 12) { //start of talking with blacksmith if

			printDialogue("Blacksmith");
			printSeparator();
			printDialogue("Hello! I'm the village blacksmith.\n"
				+ "This is your one-stop shop for anything that you need fixed up.");
			printSeparator();
			MagePath.lineConfirm();

			/*
			conversations needed if you talk to him with:

			nothing
			broken pickaxe DONE
			repaired pickaxe DONE
			broken saw DONE

			 */
			if (brokenPickaxe && !repairedPickaxe) {
				if (sticks < (5 * blacksmithMultiplier) || stones < (5 * blacksmithMultiplier)) {
					printDialogue("Blacksmith");
					printSeparator();
					printDialogue("That's a nice pickaxe you have there. Where'd you find it?");
					printSeparator();
					MagePath.lineConfirm();

					printDialogue(MagePath.playerName);
					printSeparator();
					printDialogue("I found it in the abandoned western mines. Could you fix it up for me?");
					printSeparator();
					MagePath.lineConfirm();

					printDialogue("Blacksmith");
					printSeparator();
					printDialogue("Well, unfortunately I currently don't have the proper materials to do that.\n"
						+ "But, if you were able to get around " + (5 * blacksmithMultiplier) + " decently sized stones and about " + (5 * blacksmithMultiplier) + " tree branches\nfor me, I'd be happy to do it for free.\n"
						+ "You should be able to find those in the woods, but be careful.\nThat place is more dangerous than ever with this sorcerer on the loose.");
					printSeparator();
					MagePath.lineConfirm();

					printDialogue(MagePath.playerName);
					printSeparator();
					printDialogue("Looks like you have yourself a deal.");
					printSeparator();
					MagePath.lineConfirm();
				} else {
					printDialogue(MagePath.playerName);
					printSeparator();
					printDialogue("Do you think you could fix up my pickaxe for me?");
					printSeparator();
					MagePath.lineConfirm();

					printDialogue("Blacksmith");
					printSeparator();
					printDialogue("""
							Seeing as you have the materials to do it, I'm happy to do it for free.
							I'll just take your materials and your pickaxe for a moment.
							There. All done!""");
					printSeparator();
					MagePath.lineConfirm();

					printDialogue(MagePath.playerName);
					printSeparator();
					printDialogue("Thanks a ton!");
					printSeparator();
					MagePath.lineConfirm();

					sticks -= 5 * blacksmithMultiplier;
					stones -= 5 * blacksmithMultiplier;
					repairedPickaxe = true;
				}

			} else if (brokenPickaxe) { //repairedPickaxe will always be true
				printDialogue("Blacksmith");
				printSeparator();
				printDialogue("How's your pickaxe holding up?");
				printSeparator();
				MagePath.lineConfirm();

				printDialogue(MagePath.playerName);
				printSeparator();
				printDialogue("Just great. Thanks!");
				printSeparator();
				MagePath.lineConfirm();
			} else if (brokenSaw && !repairedSaw) {
				printDialogue("Blacksmith");
				printSeparator();
				printDialogue("That's a nice saw you have there. Where'd you find it?");
				printSeparator();
				MagePath.lineConfirm();

				printDialogue(MagePath.playerName);
				printSeparator();
				printDialogue("It's a bit of a long story, but do you think you could get it sharpened for me?");
				printSeparator();
				MagePath.lineConfirm();

				printDialogue("Blacksmith");
				printSeparator();
				printDialogue("Sure thing, but that will cost you " + (20 * blacksmithMultiplier) + " gold.\n");
				if (goldCoinCount >= (20 * blacksmithMultiplier)) {
					printDialogue("Do you still want to get it fixed?");
					printSeparator();

					printDialogue(MagePath.playerName);
					printSeparator();
					printDialogue("Sure. Here's the gold, and the saw.");
					printSeparator();
					MagePath.lineConfirm();
					goldCoinCount -= 20 * blacksmithMultiplier;

					printDialogue("Blacksmith");
					printSeparator();
					printDialogue("There you go, all sharp. Just as good as if it were new.");
					printSeparator();
					repairedSaw = true;
				} else {
					printDialogue("And it appears that you don't have enough.\n"
						+ "I don't work for free, you know.");
					printSeparator();
				}
				MagePath.lineConfirm();

				printDialogue(MagePath.playerName);
				printSeparator();
				printDialogue("Alright, I'll see you next time, then.");
				printSeparator();
				MagePath.lineConfirm();

			}
		} //end of talking with blacksmith

		if (positionIndex == 286 && currentRoom == 12 && !blacksmithChest) { //stealing from blacksmith
			printDialogue("Blacksmith");
			printSeparator();
			printDialogue("Hey! Do you think I'm blind, and can't see that you're looting my store?\n"
				+ "I'll charge you five times the price from now on, thank you very much!");
			printSeparator();
			MagePath.lineConfirm();
			blacksmithMultiplier = 5;
			blacksmithChest = true;
		}

		if (positionIndex == 193 && currentRoom == 16) { //trader steve in forest
			printDialogue("Trader Steve");
			printSeparator();
			printDialogue("We meet again.");
			printSeparator();
			MagePath.lineConfirm();
		}

		if (positionIndex == 250 && currentRoom == 13) { //carpenter

			if (!brokenSaw) {
				printDialogue("Carpenter");
				printSeparator();
				printDialogue("I lost my saw again! This is the SECOND time this week.");
				printSeparator();
				MagePath.lineConfirm();
			} else if (!repairedSaw) { //brokenSaw will always be true
				printDialogue("Carpenter");
				printSeparator();
				printDialogue("Wait a minute, that can't be! Did you find my saw?\nGive it here!");
				printSeparator();
				MagePath.lineConfirm();

				printDialogue("Carpenter");
				printSeparator();
				printDialogue("Aww, it's as dull as a butter knife.\nHey, if you go get this sharpened for me, I'll let you take whatever you want from my basement.");
				printSeparator();
				MagePath.lineConfirm();
			} else { //repairedSaw and brokenSaw will always be true
				printDialogue("Carpenter");
				printSeparator();
				printDialogue("Is that my saw, all nice and sharp?\nWhy, thank you!\nGo ahead, take what you want from the basement.\nWhat are you waiting for? Go before I change my mind.");
				printSeparator();
				MagePath.lineConfirm();
				basementKey = true;
			}
		}
		
		if (positionIndex == 189 && currentRoom == 11) { //conversation with wizard
			
			printDialogue("David the Wizard");
			printSeparator();
			printDialogue("Hello! I am very old, and my magic is not as great as it used to be.");
			printSeparator();
			
			MagePath.lineConfirm();
			
			if (lostInDeepWoods && !manuscript) {
				printDialogue("David the Wizard");
				printSeparator();
				printDialogue("""
						I sense that you might want this...
						It's an old manuscript.
						My eyes are not as good as they used to be.
						I cannot read this, so it should be of more use to you.""");
				printSeparator();
			
				MagePath.lineConfirm();
				manuscript = true;
				
				printText("\nYou got the manuscript. Go to your inventory to see what it says...");
				MagePath.lineConfirm();
			}
		}
	}

	private static void printDebugHud() {
		// System.err.println("positionIndex = " + positionIndex);
		// System.err.println("currentRoom = " + currentRoom);
		// System.err.println("lostInWoods = " + lostInWoods);
		System.err.println("canSpawnEnemies = " + canSpawnEnemies);
		System.err.println("movesUntilNextBattle = " + movesUntilNextBattle);
		System.err.println("playerHealth = " + MagePath.playerHealth);
		System.err.println("playerMaxHealth = " + MagePath.playerMaxHealth);
		System.err.println("playerHealth / playerMaxHealth = " + (decPlayerHealth / decMaxPlayerHealth));
	}

	private static void switchGameDifficulty() {
		switch (gameDifficulty) {
			case 0 -> maxMovesUntilNextBattle = 100; //easy
			case 1 -> maxMovesUntilNextBattle = 75; //normal
			case 2 -> maxMovesUntilNextBattle = 50; //hard
			case 3 -> { //impossible
				maxMovesUntilNextBattle = 25;
				canSpawnEnemies = true; //effectively makes enemies able to spawn anywhere, even in normally safe areas
			}
			default -> { //in case a bad value is given, then the difficulty is set to normal
				gameDifficulty = 1;
				maxMovesUntilNextBattle = 75;
			}
		}
	}

	private static void checkForBattle() {
		if ((movesUntilNextBattle <= 0) && (canSpawnEnemies)) { //starting a random encounter
			printText("Get ready to fight!\n");
			MagePath.lineConfirm();
			music.stop();

			int fightIndex;

			if (playingCaveAudio) {
				MagePath.startFight(1);
			} else if (playingErebusAudio) {
				//erebus fight
			} else if (playingFieldAudio) {
				fightIndex = MagePath.rand.nextInt(2);
				switch (fightIndex) {
					case 0 -> MagePath.startFight(3); //fight with barbarian
					case 1 -> MagePath.startFight(1);
					case 2 -> MagePath.startFight(1);
				}
			} else if (playingForestAudio) {
				MagePath.startFight(1);
			} else if (playingHouseAudio) {
				//house fight
			} else if (playingTowerAudio) {
				MagePath.startFight(1);
			}

			//restart the music by letting the game know that it's been stopped
			playingCaveAudio = false;
			playingFieldAudio = false;
			playingForestAudio = false;
			playingTowerAudio = false;

			//set a new random number for the amount of spaces required to move until the next battle
			movesUntilNextBattle = MagePath.rand.nextInt(maxMovesUntilNextBattle - 20) + 20;

		} else if (movesUntilNextBattle > 0 && !playingErebusAudio && !playingHouseAudio) {
			//if the player moved, then decrement the moves until next battle.
			//if the player is in a town or a house (where battles normally shouldn't happen), then this line won't run.
			movesUntilNextBattle --;
		}
	}
	private static void inventory() {
		roomUpdate = false;

		while (loop) { //start of inventory loop
			printWorldMap();
			lastPositionIndex = positionIndex;
			printText("""
					Inventory:
					1 - KEY ITEMS
					2 - ITEMS
					3 - EQUIPS
					4 - Exit
					""");

			MagePath.intInput = MagePath.enterNumber(1, 4);
			switch (MagePath.intInput) {
				case 1 -> { //key item display (you can't use these)
					//pickaxe status
					if (brokenPickaxe && !repairedPickaxe) {
						printText("Old Pickaxe\n");
					} else if (brokenPickaxe) { //repairedPickaxe will always be true
						printText("Repaired Miner's Pickaxe\n");
					}
					//erebus quest display
					if (peasantKey && !brokenSaw && !repairedSaw && !basementKey) {
						printText("Mysterious Key\n");
					} else if (peasantKey && brokenSaw && !repairedSaw && !basementKey) {
						printText("Poacher's Saw\n");
					} else if (peasantKey && brokenSaw && repairedSaw && !basementKey) {
						printText("Sharpened Poacher's Saw\n");
					} else if (peasantKey && brokenSaw && repairedSaw) { //basementKey will always be true so i don't need to check it
						printText("Poacher's Basement Key\n");
					}
					//artifact display
					if (obtainedCaveArtifact) {
						printText("Cave Artifact\n");
					}
					if (obtainedDebugArtifact) {
						printText("Debug Artifact\n");
					}
					if (obtainedErebusArtifact) {
						printText("Erebus Artifact\n");
					}
					if (obtainedForestArtifact) {
						printText("Forest Artifact\n");
					}
				}
				case 2 -> { //regular item display (you can use these)
					printText("1 - " + goldCoinCount + "x Gold Coins\n"
							+ "2 - " + healthPotions + "x Health Potions\n"
							+ "3 - " + ethers + "x Ethers\n"
							+ "4 - " + sticks + "x Tree Branches\n"
							+ "5 - " + stones + "x Stones\n");
					if (manuscript && !translatedManuscript) {
						printText("6 - Manuscript\n");
					} else if (manuscript) { //translatedManuscript will always be true
						printText("6 - Translated Manuscript\n");
					}
					loop = true;
					while (loop) {
						MagePath.intInput = MagePath.enterNumber(1, 6);

						switch (MagePath.intInput) {
							case 1:
							case 4:
							case 5:
								printText(USED_UNUSABLE_ITEM);
								MagePath.nextLine();
								break;
							case 2:
								if (healthPotions >= 1) {
									healthPotions--;
									sfx = new AudioPlayer(AudioPlayer.USE_POTION_PATH, false, sfxVolume);
									sfx.play();
									printText("You drank the potion, and recovered 15 HP.\n");
									MagePath.playerHealth += 15;
								} else {
									printText("You don't have any of those.");
								}
								break;
							case 3:
								if (ethers >= 1) {
									ethers--;
									sfx = new AudioPlayer(AudioPlayer.USE_ETHER_PATH, false, sfxVolume);
									sfx.play();
									printText("You drank the ether, and recovered 5 HP.\n");
									MagePath.playerMagic += 5;
								} else {
									printText("You don't have any of those.");
								}
								break;
							case 6:
								if (!manuscript && !translatedManuscript) {
									printText(USED_UNUSABLE_ITEM);
									//just do nothing lol
								} else if (manuscript && !translatedManuscript) {
									printText("""
											The manuscript appears to be in a different language...
											You can't understand what it says.
											Maybe someone can translate it for you?""");
								} else if (manuscript) { //translatedManuscript will always be true at this else if block, so i don't have to check it
									printText("The manuscript reads:\n"
											+ "\"The way through the forest is South, East, South, West, East, South.\"");
								}
								MagePath.nextLine();
								break;
						}
					}
				}
				case 3 -> //
						printText("placeholder equips screen");
				case 4 -> //exit the inventory
						loop = false;
			}
			MagePath.lineConfirm();
		} //end of inventory loop
		loop = true;
	}

	private static void pauseMenu() {
		endTime = System.currentTimeMillis();
		playTimeSeconds = (playTimeSeconds + endTime) - startTime;
		Save.writeAccountFile();
		startTime = System.currentTimeMillis();

		printText("Statistics:\n"
			+ MagePath.returnPlayTime(playTimeSeconds) + "\n"
			+ "Total tiles moved: " + spacesMoved + "\n\n");

		printText("""
				Options:
				1 - Music Volume
				2 - Sound FX Volume
				3 - Text Speed
				4 - Edit Keybinds
				5 - Save and Exit
				6 - Return to game
				""");
		while (loop) { //start of settings input loop
			MagePath.intInput = Integer.parseInt(MagePath.scan.nextLine());
			loop = false;
			switch (MagePath.intInput) {
				case 1:
					printText("Current value: " + (volume + 80.0f) + "\nEnter new value for the volume (0-100):");
					volume = Float.parseFloat(MagePath.scan.nextLine()) - 80f;
					playingCaveAudio = false;
					playingErebusAudio = false;
					playingFieldAudio = false;
					playingForestAudio = false;
					playingHouseAudio = false;
					playingTowerAudio = false;
					break;
				case 2:
					printText("Current value: " + (sfxVolume + 80.0f) + "\nEnter new value for the volume (0-100):");
					sfxVolume = Float.parseFloat(MagePath.scan.nextLine()) - 80.0f;
					break;
				case 3:
					printText("""
							Please choose an option.
							1 - Normal text speed
							2 - Fast text speed
							""");

					loop = true;
					while (loop) {
						loop = false;
						MagePath.intInput = Integer.parseInt(MagePath.scan.nextLine());
						switch (MagePath.intInput) {
							case 1 -> fastText = false;
							case 2 -> fastText = true;
							default -> {
								loop = true;
								printText("Please enter a valid option.");
							}
						}
					}
					break;
				case 4:
					String keyBindsTemp = "";
					loop = true;
					while (loop) {
						printText("Enter a keybind for moving up (Default is 'w')\n");
						keyBindsTemp += MagePath.scan.nextLine();

						printText("Enter a keybind for moving left (Default is 'a')\n");
						keyBindsTemp += MagePath.scan.nextLine();

						printText("Enter a keybind for moving down (Default is 's')\n");
						keyBindsTemp += MagePath.scan.nextLine();

						printText("Enter a keybind for moving right (Default is 'd')\n");
						keyBindsTemp += MagePath.scan.nextLine();

						printText("Enter a keybind for opening the inventory (Default is 'i')\n");
						keyBindsTemp += MagePath.scan.nextLine();

						printText("Enter a keybind for opening the pause menu (Default is 'p')\n");
						keyBindsTemp += MagePath.scan.nextLine();

						printText("Is this correct?\n"
							+ "Move up - " + keyBindsTemp.charAt(0) + "\n"
							+ "Move left - " + keyBindsTemp.charAt(1) + "\n"
							+ "Move down - " + keyBindsTemp.charAt(2) + "\n"
							+ "Move right - " + keyBindsTemp.charAt(3) + "\n"
							+ "Open inventory - " + keyBindsTemp.charAt(4) + "\n"
							+ "Open pause menu - " + keyBindsTemp.charAt(5) + "\n"
							+ "Enter \"Yes\" to confirm. Enter any other value to use the old keybinds.\n"
							+ "Enter \"Redo\" to re-enter the values.\n");

						MagePath.lineInput = MagePath.scan.nextLine();

						loop = false;
						if (MagePath.lineInput.equalsIgnoreCase("yes")) {
							keyMoveUp = keyBindsTemp.charAt(0);
							keyMoveLeft = keyBindsTemp.charAt(1);
							keyMoveDown = keyBindsTemp.charAt(2);
							keyMoveRight = keyBindsTemp.charAt(3);
							keyOpenInventory = keyBindsTemp.charAt(4);
							keyPauseGame = keyBindsTemp.charAt(5);
						} else if (MagePath.lineInput.equalsIgnoreCase("redo")) {
							loop = true;
							keyBindsTemp = "";
						}
						Save.writeSettingsFile();
					}
					break;
				case 5:
					playingCaveAudio = false;
					playingErebusAudio = false;
					playingFieldAudio = false;
					playingForestAudio = false;
					playingHouseAudio = false;
					playingTowerAudio = false;
					playingLowHealthSound = false;
					healthSound.stop();
					music.stop();
					printText("""
							Are you sure that you want to exit the game?
							Enter "Yes" to save and close the program.
							""");
					MagePath.lineInput = MagePath.scan.nextLine();
					if (MagePath.lineInput.equalsIgnoreCase("yes")) {
						gameLoop = false;
						printText("The program will now close.\n");
					} else {
						printText("The program will continue.\n");
					}
					MagePath.nextLine();
					break;
				case 6:
					break;
				case 69:
					printText("""
							This is the debug menu.
							Please choose an option.
							1 - Map Select
							2 - Change position
							3 - Enable noclip
							4 - Artifact status editor
							5 - Enable debug information
							"""
					);

					loop = true;
					while (loop) {
						loop = false;
						MagePath.intInput = Integer.parseInt(MagePath.scan.nextLine());
						switch (MagePath.intInput) {
							case 1 -> {
								printText("Enter a map ID (intended range 0-28)\n");
								currentRoom = Integer.parseInt(MagePath.scan.nextLine());
							}
							case 2 -> {
								printText("Enter a position value (intended range 0-419)\n");
								positionIndex = Integer.parseInt(MagePath.scan.nextLine());
							}
							case 3 -> {
								printText("Noclip has been enabled.\n");
								noClip = true;
							}
							case 4 -> {
								printText("Enter value for obtainedCaveArtifact (boolean):\n");
								obtainedCaveArtifact = MagePath.scan.nextBoolean();
								printText("Enter value for obtainedDebugArtifact (boolean):\n");
								obtainedDebugArtifact = MagePath.scan.nextBoolean();
								printText("Enter value for obtainedErebusArtifact (boolean):\n");
								obtainedErebusArtifact = MagePath.scan.nextBoolean();
								printText("Enter value for obtainedForestArtifact (boolean):\n");
								obtainedForestArtifact = MagePath.scan.nextBoolean();
							}
							case 5 -> {
								printText("Debug output has been enabled.\n");
								debug = true;
							}
							default -> {
								loop = true;
								printText("Please enter a valid option.");
							}
						}
					}
					break;
				default:
					printText("Please enter a valid option.");
					loop = true;
			}
		} //end of settings input loop
		Save.writeSettingsFile();
	}
	
	private static void shop() {
		positionIndex = 233;
		music.stop();
		music = new AudioPlayer(AudioPlayer.SHOP_PATH, true, volume);
		music.play();
		playingErebusAudio = false;
		
		MagePath.clearConsole();
		
		printDialogue("Shopkeeper");
		printSeparator();
		printDialogue("Hello! Feel free to look around.");
		printSeparator();
		
		MagePath.lineConfirm();
		
		
		while (loop) {
			MagePath.clearConsole();
			System.out.print("""
					Items for sale:
					1 - Health Potion (restores 10 HP) - 10 Gold
					2 - Ether (restores 7 MP) - 15 Gold
					3 - Shield (reduces enemy damage by 50%) - 20 Gold
					4 - Exit
					""");
			
			MagePath.intInput = MagePath.enterNumber(1, 4);

			switch (MagePath.intInput) {
				case 1 -> { //health potion
					printDialogue("Shopkeeper");
					printSeparator();
					printDialogue("Ah yes... The classic health potion.\n"
							+ "How many will you need?");
					printSeparator();
					MagePath.intInput = MagePath.enterNumber(0, -1);
					if ((MagePath.intInput * 10) > goldCoinCount) {
						printDialogue("Shopkeeper");
						printSeparator();
						printDialogue("Doesn't appear you have enough Gold...");
						printSeparator();
					} else {
						printDialogue("Shopkeeper");
						printSeparator();
						printDialogue("Alright! " + MagePath.intInput + " health potions it is.");
						printSeparator();

						healthPotions += MagePath.intInput;
						goldCoinCount -= (MagePath.intInput * 10);
					}
					MagePath.nextLine();
				}
				case 2 -> { //ether
					printDialogue("Shopkeeper");
					printSeparator();
					printDialogue("Ah yes... an Ether.\n"
							+ "How many will you need?");
					printSeparator();
					MagePath.intInput = MagePath.enterNumber(0, -1);
					if ((MagePath.intInput * 15) > goldCoinCount) {
						printDialogue("Shopkeeper");
						printSeparator();
						printDialogue("Doesn't appear you have enough Gold...");
						printSeparator();
					} else {
						printDialogue("Shopkeeper");
						printSeparator();
						printDialogue("Alright! " + MagePath.intInput + " ethers it is.");
						printSeparator();

						healthPotions += MagePath.intInput;
						goldCoinCount -= (MagePath.intInput * 15);
					}
					MagePath.nextLine();
				}
				case 3 -> { //shield
					printDialogue("Shopkeeper");
					printSeparator();
					printDialogue("Ah yes... The classic health potion.\n"
							+ "How many will you need?");
					printSeparator();
					MagePath.intInput = MagePath.enterNumber(0, -1);
					if (goldCoinCount < 20) {
						printDialogue("Shopkeeper");
						printSeparator();
						printDialogue("Doesn't appear you have enough Gold...");
						printSeparator();
					} else {
						printDialogue("Shopkeeper");
						printSeparator();
						printDialogue("Alright! Here's your shield.");
						printSeparator();

						shield = true;
						goldCoinCount -= 20;
					}
					MagePath.nextLine();
				}
				case 4 -> {
					loop = false;
					printDialogue("Shopkeeper");
					printSeparator();
					printDialogue("See you later!");
					printSeparator();
					MagePath.nextLine();
				}
			}
		}
		
		Save.writeSaveFile();
	}

	public static void worldMap(boolean skip) {
		loop = true;
		String tempInput;

		healthSound = new AudioPlayer(AudioPlayer.LOW_HEALTH_PATH, true, sfxVolume);

		getRoomData();
		hijackRoomData();
		printWorldMap();
		if (!skip) {
			printText("Enter " 
				+ keyMoveUp + ", " 
				+ keyMoveLeft + ", " 
				+ keyMoveDown + ", or " 
				+ keyMoveRight + " to move around.\n"
				+ "Enter \"" + keyOpenInventory + "\" to bring up the inventory.\n" 
				+ "Enter \"" + keyPauseGame + "\" to bring up the pause menu.\n"
				+ "You can customise your keybinds in the pause menu.\n"
				+ "At any time on the world map, you can type \"Reset Keybinds\" to reset your keybinds to the default values.");
			MagePath.lineConfirm();
		}
		while (gameLoop) { //start of the massive af main game while loop
			decPlayerHealth = MagePath.playerHealth;
			decMaxPlayerHealth = MagePath.playerMaxHealth;

			tempInput = MagePath.scan.nextLine();

			if (tempInput.equalsIgnoreCase("reset keybinds")) {
				printText("Are you sure?\nEnter \"Yes\" to confirm.\n");
				tempInput = MagePath.scan.nextLine();
				if (tempInput.equalsIgnoreCase("yes")) {
					keyMoveUp = 'w';
					keyMoveLeft = 'a';
					keyMoveDown = 's';
					keyMoveRight = 'd';
					keyOpenInventory = 'i';
					keyPauseGame = 'p';
					printText("Your keybinds have been reset to default. (" 
						+ keyMoveUp + ", " 
						+ keyMoveLeft + ", " 
						+ keyMoveDown + ", " 
						+ keyMoveRight + ", " 
						+ keyOpenInventory + ", " 
						+ keyPauseGame + ")");
					MagePath.nextLine();
				} else {
					printText("Your keybinds will remain the same. (" 
						+ keyMoveUp + ", " 
						+ keyMoveLeft + ", " 
						+ keyMoveDown + ", " 
						+ keyMoveRight + ", " 
						+ keyOpenInventory + ", " 
						+ keyPauseGame + ")");
				}
				Save.writeSettingsFile();
			} else {

				try {
					keyInput = tempInput.charAt(0);
				} catch (Throwable NoSuchElementException) {
					keyInput = ' ';
				}
				
				
				if (keyInput == keyMoveUp) {
						positionIndex -= 20;
						spacesMoved += 1;
				} else if (keyInput == keyMoveLeft) {
						positionIndex -= 1;
						spacesMoved += 1;
				} else if (keyInput == keyMoveDown) {
						positionIndex += 20;
						spacesMoved += 1;
				} else if (keyInput == keyMoveRight) {
						positionIndex += 1;
						spacesMoved += 1;
				} else if (keyInput == keyOpenInventory) {
						inventory();
				} else if (keyInput == keyPauseGame) {
						pauseMenu();
				}

				checkFlags(); //checking if a flag needs to be set (artifacts, chests, or even temp flags, like item pickups, switches, etc.)
				checkConversations(); //checking if the player should be starting a conversation with an npc

				if ( //making sure the player isn't on an illegal tile
				(
					currentMap[positionIndex] == '|'
					||currentMap[positionIndex] == '='
					||currentMap[positionIndex] == '+'
					||currentMap[positionIndex] == '$'
					||currentMap[positionIndex] == '{'
					||currentMap[positionIndex] == '@'
					||currentMap[positionIndex] == '['
					||currentMap[positionIndex] == '\\'
				)
				&& !noClip //only check for illegal tiles if the player has noclip disabled
				)
				{
					positionIndex = lastPositionIndex;
					spacesMoved -= 1;
				}

				//checking if the player should transition to another screen
				if (positionIndex == currentMapMetadata[0]) {
					currentRoom = currentMapMetadata[1];
					positionIndex = currentMapMetadata[2];
				} else if (positionIndex == currentMapMetadata[3]) {
					currentRoom = currentMapMetadata[4];
					positionIndex = currentMapMetadata[5];
				} else if (positionIndex == currentMapMetadata[6]) {
					currentRoom = currentMapMetadata[7];
					positionIndex = currentMapMetadata[8];
				} else if (positionIndex == currentMapMetadata[9]) {
					currentRoom = currentMapMetadata[10];
					positionIndex = currentMapMetadata[11];
				} else if (positionIndex == 213 && currentRoom == 9) {
					shop();
				}
				
				if (MagePath.playerHealth > MagePath.playerMaxHealth) {
					MagePath.playerHealth = MagePath.playerMaxHealth;
				}

				if (lostInWoods) {
					currentRoom = 16;
					positionIndex = 289;
					getRoomData();
					hijackRoomData();
					printWorldMap();
					printText("You got lost in the woods, and ended up back at the entrance.");
					MagePath.lineConfirm();
					lostInWoods = false;
				} else {
					getRoomData();
					hijackRoomData();
					printWorldMap();
				}
				lastPositionIndex = positionIndex;

				if (debug) {
					printDebugHud();
				}

				switchGameDifficulty();

				if (!playingLowHealthSound && (decPlayerHealth / decMaxPlayerHealth) <= 0.1) {
					playingLowHealthSound = true;
					healthSound.play();
				} else if ((decPlayerHealth / decMaxPlayerHealth) > 0.1) {
					healthSound.stop();
					healthSound = new AudioPlayer(AudioPlayer.LOW_HEALTH_PATH, true, sfxVolume);
					playingLowHealthSound = false;
				}

				checkForBattle();

				if (saveGame) {
					endTime = System.currentTimeMillis();
					playTimeSeconds = (playTimeSeconds + endTime) - startTime;
					Save.writeAccountFile();
					startTime = System.currentTimeMillis();

					Save.writeSettingsFile();
					Save.writeSaveFile();
					saveGame = false;
				}
				if (MagePath.dead || MagePath.playerHealth <= 0) {
					gameLoop = false;
					MagePath.dead = true;
				}
			}
		}
	}
}