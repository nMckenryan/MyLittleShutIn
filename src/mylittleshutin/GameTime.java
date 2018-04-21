package mylittleshutin;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class handles how time passes inside the game. It also contains: 
 * - The logic for all the Random Events that occur
 * - The Functionality for updating the game screen every game day (including sprites)
 * - The failstate information that triggers a Game Over
 * - and the Game Over Screen
 * 
 * @author Nigel
 */

public class GameTime {
	Timer worldClock;
	ShutIn character;
	Scanner scan;
	int daysPassed;
	int eventChoice;
	boolean choiceMade;
	Random rand;
	StatusEvent randomEvent;

	public GameTime(ShutIn targetCharacter) {
		worldClock = new Timer();
		character = targetCharacter;
		randomEvent = new StatusEvent();
		daysPassed = character.getDaysPassed();
		eventChoice = 0;
		choiceMade = false;
		rand = new Random();
		scan = new Scanner(System.in);
	}

	public void gameUpdate() { //Handles the updating of the screen each Game Day. Shows the ShutIn's stats.
		DecimalFormat formatBW = new DecimalFormat("#.#"); //Used in conjunction .format() method, formats Bodyweight to 2 decimal places.
		character.updateStatus();						   //Applies stat gains to character each day.
		updateSprites(character);						   //Shows the characters sprite. Updates depending on his bodyweight.
		System.out.println("DAY " + character.getDaysPassed() + "\nHUNGER: " + character.getHunger() + " BOREDOM: " + character.getBoredom()
		+ " BODYWEIGHT: " + formatBW.format(character.getBodyweight()) + " HEALTH: " + character.getHealth()
		+ " FUNDS: " + character.getFunds() + "\n **Note: This Game auto-saves every day. Exit this program to exit.**\n");
		character.addDay();
		character.saveGame(); //saves the game to a text file.
	}
	
	public void updateSprites(ShutIn chub) { //checks and updates sprites, based on character's weight.
		double scales = chub.getBodyweight();
		if(scales >= 300.0) {
			System.out.println(Sprites.giganticSprite);
		}else if(scales >= 200.0) {
			System.out.println(Sprites.hugeSprite);
		}else if(scales >= 100.0) {
			System.out.println(Sprites.fatSprite);
		}else {
			System.out.println(Sprites.skinnySprite);
		}
	}
	
	/*	EVENT OCCURANCES
	Every Game Day (5 Seconds), An update appears on the screen.
	The majority of them are just show what their ShutIn is doing today.
	There is a chance of a Random Event occuring, Some give the player a choice that could 
	benefit (or hinder) their character. Others are events that give immediate benefits/penalties to stats.	
	*/
	public void runTime() {
		worldClock.schedule(new GameTime.runEvent(), 1, 5000); //An event will occur every 5 seconds.
	}
	
	public class runEvent extends TimerTask {			
		@Override
		public void run() {
			int eventChoice;
			character.checkFailStates(); //Checks if character is dead.
			if(character.checkAlive()) { //Runs game if character is alive.
				daysPassed = character.getDaysPassed();
				gameUpdate();
				if(daysPassed % 7 == 0 ) { //Forces the shopping event each week.
					randomEvent.shoppingTime(character);
				} else {
					eventChoice = rand.nextInt(20) + 1; //Determines what event (or dialogue) will trigger.
					switch(eventChoice) {
						case 1: System.out.println(character + " is complaining about the character Giren in DragonSphere Ultra on a Forum.");
								break;
						case 2: randomEvent.healthCheckUp(character);
								break;
						case 3: randomEvent.bitDucats(character);
								break;
						case 4: System.out.println(character + " is speed-running a 20 year old Video Game while streaming to a whopping audience of 3 viewers.");
								break;
						case 5: randomEvent.cleanRoom(character);
								break;
						case 6: System.out.println(character + " is having a Romantic Evening with his Anime Wife.");
								break;
						case 7: System.out.println(character + " is pretending to be a Feminist to attract the attention of Girls online.");
								break;
						case 8: randomEvent.gameSale(character);
								break;
						case 9: System.out.println(character + " is browsing Trenchcoats online.");
								break;
						case 10: System.out.println(character + " is contemplating what he'll do after everyone starts realising his Genius.");
								break;
						case 11: randomEvent.fakeDisability(character);
								break;
						case 12: System.out.println("While playing the Realm of Conflict, " + character + " offers to upgrade other player's armour for free, then logs off once they have given him their gear.");
								break;
						case 13: System.out.println(character + " makes another attempt to read 'How to make Money Drawing Anime', but gives up after ten minutes.");
								break;
						case 14: System.out.println(character + " just got banned from another Dating app for Hostile Behaviour.");
								break;
						case 15: System.out.println(character + " is giving unwanted, ill-informed and frankly, dangerous Squat form advice on the BodyCrafting.com Forums.");
								break;
						case 16: System.out.println("After staying up until 4am last night, " + character + " wakes up at 9pm, decides it's getting late and goes to bed.");
								break;
						case 17: System.out.println(character + " is blaming all his problems on someone called 'Chad'.");
								break;
						case 18: System.out.println(character + " is busy cyberbullying Phil Wheaton on Twutter.");
								break;
						case 19: System.out.println(character + " is stealing Fresh Memes and sharing them on Plebbit as his own creation.");
								break;
						case 20: System.out.println(character + " is complaining about how hard it is living in a Fully Developed Country.\n");
								break;
						default: System.out.println(character + " is staring at the ceiling.");
					}
				}
				System.out.println("+---------------------------------------------------+ \n"); //Separates each Event.
			} else { //Stops passage of time, shows funeral Screen.
				worldClock.cancel();
				character.funeral();
			}
		}
	}
}
