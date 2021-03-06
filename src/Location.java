//Java_RPG
//Alpha Pre-Release 1.3.6
//Released 11/19/2012
//©2012 Ryan Cicchiello & Jason Holman
//See LICENCE for details

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class Location {

	private int x;
	private int y;
	private int maxX;
	private int maxY;
	private boolean alive;
	private int mapSize;
	private int[][] world;
	private boolean hasMap;
	private String playerName;

	public Location(int worldSize, String charName, int locationX, int locationY) throws IOException {
		x = locationX;
		y = locationY;
		mapSize = worldSize;
		maxX = mapSize - 1;
		maxY = mapSize - 1;
		world = new int[mapSize][mapSize];
		playerName = charName;
		genWorld();
		//showMap();
	}

	public void move(int direction) {
		switch (direction) {

		case 1:
			if(y>0){
				y--;
			}
			else{
				H.pln("You have reached the limits of the island");
			}
			break;
		case 2:
			if(x<maxX){
				x++;
			}
			else{
				H.pln("You have reached the limits of the island");
			}

			break;
		case 3:
			if(y<maxY){
				y++;
			}
			else{
				H.pln("You have reached the limits of the island");
			}
			break;
		case 4:
			if(x>0){
				x--;
			}
			else{
				H.pln("You have reached the limits of the island");
			}
			break;
		default:
			break;
		}
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void died() {
		x = mapSize/2;
		y = 0;
	}
	/*
	 * This method prints the map into the system output
	 */
	public void showMap(int map) {
		for(int r = 0; r < mapSize; r++) {
			String str = "";
			for(int f = 0; f < mapSize; f++) {
				if(map == 2) {
					if(r == y && f == x) {
						str = str + "x";
					} else {
						str = str + world[r][f];
					}
				} else {
					str = str + world[r][f];
				}
			}
			System.out.println(str);
		}
	}

	public void editMap(int worldType) throws IOException {
		world[y][x] = worldType;
		File edit = new File(playerName+".map");
		edit.delete();
		edit.createNewFile();
		BufferedWriter editMap = new BufferedWriter(new FileWriter(edit));
		String str = "";
		for(int a = 0; a < mapSize; a++) {
			str = "";
			for(int b = 0; b < mapSize; b++) {
				str = str + Integer.toString(world[a][b]);
			}
			editMap.write(str);
			editMap.newLine();
		}
		editMap.close();
	}

	public int getCell() {
		return world[y][x];
	}

	/*
	 * This method generates the world or if the player has a save file reloads the world.
	 */
	private void genWorld() throws IOException {
		if(Exists(playerName)) {
			BufferedReader load = new BufferedReader(new FileReader(playerName+".map"));
			String loadMap[] = new String[mapSize];
			for(int p = 0; p < mapSize; p++) {
				loadMap[p] = load.readLine();
			}
			for(int a = 0; a < mapSize; a++) {
				for(int b = 0; b < mapSize; b++) {
					world[a][b] = Integer.parseInt(loadMap[a].substring(b, b+1));
				}
			}
			load.close();

		} else {
			Random rand = new Random();
			for(int i = 0; i < mapSize; i++) {
				for(int j = 0; j < mapSize; j++) {
					int randNum = rand.nextInt(100)+1;
					int type = 0;
					// Quicksand block 5% chance of generaton
					if(randNum > 0 && randNum < 6) 
						type = 1;
					// forest block 55% chance of generaton
					if(randNum > 5 && randNum < 61) 
						type = 2;
					// pond block 10% chance of generation
					if(randNum > 90 && randNum < 101) 
						type = 3;
					// field block 25% chance of generaton
					if(randNum > 65 && randNum < 91) 
						type = 4;
					//town block 5% chance of generation
					if(randNum > 60 && randNum < 66) 
						type = 7;
					//mine block type 9 0% chance of generation built by player
					//type 8 is a player built house 0% chance of generation
					if(type == 0) {
						System.out.println("Land Generator Error");
						System.out.println("Program Terminated");
						System.exit(0);
					}
					world[j][i] = type;
				}

			}
			for(int k = 0; k < mapSize; k++) {
				world[k][0] = 5;
				world[k][mapSize - 1] = 5;
				world[0][k] = 5;
				world[mapSize - 1][k] = 5;
			}

			int clx = rand.nextInt(mapSize);
			int cly = rand.nextInt(mapSize);

			//set castle location
			world[clx][cly] = 6;

			File newMap = new File(playerName+".map");
			newMap.createNewFile();

			BufferedWriter map = new BufferedWriter(new FileWriter(newMap));
			String str = "";
			for(int a = 0; a < mapSize; a++) {
				str = "";
				for(int b = 0; b < mapSize; b++) {
					str = str + Integer.toString(world[a][b]);
				}
				map.write(str);
				map.newLine();
			}
			map.close();

		}
	}
	public boolean Exists(String charName){
		File f = new File(charName+".map");
		return f.exists();
	}
}

