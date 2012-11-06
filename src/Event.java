import java.io.IOException;
import java.util.Random;

public class Event {

	private Random e_calc;
	private int choice;
	private AttackEngine encounter;
	private int event; // Type of event
	private Character player;
	private String playerName;
	private Location location;
	private int x;
	private int y;

	public Event(String charName) throws NumberFormatException, IOException {
		encounter = new AttackEngine(charName);
		player = new Character(charName);
		playerName = charName;
		x = player.getLocationX();
		y = player.getLocationY();
		location = new Location(player.getMapSize(), charName, x, y);
	}

	public void eventCalc() throws IOException {
		switch(location.getCell()){
		case 1:
			H.pln("You are stuck in quicksand!");
			H.pln("You lost 30 health!");
			player.setCHealth(player.getHealth() - 30);
			break;
		case 2:
			H.pln("You are in a Forest!");
			e_calc = new Random();
			event = e_calc.nextInt(99)+1;
			if(event>0 && event<60){ //Finding an enemy (60% chance)
				encounter.Battle();
				player.update();
			}
			if(event>=61 && event<=90){ //30% chance of nothing
				H.pln("Nothing here seems out of the ordinary.");
			}
			if(event>=91 && event<=95){ //5% chance of finding extra XP
				H.pln("You found a book");
				H.pln("It looks old and is hard to read.");
				H.pln("However after figuring out the symbols");
				H.pln("You have learned valuable skills");
				H.pln("You gained 500 XP!");
				player.addXP(500);
			}
			if(event>=96 && event<=100){
				H.pln("You found a map!");
				player.findMap();
			}
			break;
		case 3:
			H.pln("You have found a pond.");
			H.pln("All seems quiet and peaceful.");
			H.pln("You have regained 30 Health!");
			player.setCHealth(player.getCHealth()+30);
			break;
		case 4:
			H.pln("You are on a field");
			H.pln("There is a person on the Horizon.");
			H.pln("Would you like to : (1)Attack or (2)Ignore and Keep Moving?");
			choice =  H.inputInt();
			if(choice<= 1 || choice >2){
				encounter.Battle();
				player.update();
			}
			else{
				H.pln("You and the person pass by without");
				H.pln("either of you intending to attack");
			}
			break;
		}

	}
	
	public void move(int direction) throws IOException {
		location.move(direction);
		x = location.getX();
		y = location.getY();
		player.setLocation(x, y);
	}
	
	public void showMap() {
		location.showMap();
	}
	
	public void save() throws IOException {
		player.update();
		player.setLocation(x, y);
		player.saveAll();
	}

}
